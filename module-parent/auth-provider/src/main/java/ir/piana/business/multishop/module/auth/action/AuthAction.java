package ir.piana.business.multishop.module.auth.action;

import ir.piana.business.multishop.common.data.cache.AppDataCache;
import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.common.dev.sqlrest.AjaxController;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.model.AppInfo;
import ir.piana.business.multishop.module.auth.model.SiteInfo;
import ir.piana.business.multishop.module.auth.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.BiFunction;

@Service("auth")
public class AuthAction extends AjaxController.Action {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private AppDataCache appDataCache;

    public BiFunction<HttpServletRequest, Map<String, Object>, ResponseEntity> appInfo = (request, body) -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // ToDo => appInfo setter
        String host = (String) request.getAttribute("host");
        SiteEntity siteEntity = null;
        if(!appDataCache.getDomain().equalsIgnoreCase(host))
            siteEntity = siteRepository.findByTenantId(host);
        AppInfo appInfo = null;
        if(authentication.getPrincipal() instanceof UserModel) {
            GoogleUserEntity userEntity = ((UserModel) authentication.getPrincipal()).getUserEntity();
            appInfo = AppInfo.builder()
                    .isLoggedIn(true)
                    .isAdmin(authentication.getAuthorities().stream()
                            .filter(e -> e.getAuthority().equalsIgnoreCase("ROLE_SITE_OWNER"))
                            .map(e -> true).findFirst().orElse(false))
//                    .isAdmin(userEntity.getUserRolesEntities().stream()
//                            .filter(e -> e.getRoleName().equalsIgnoreCase("ROLE_SITE_OWNER"))
//                            .map(e -> true).findFirst().orElse(false))
                    .isFormPassword(userEntity.getFormPassword() == null ? false : true)
                    .username(userEntity.getGivenName())
                    .email(userEntity.getEmail())
                    .pictureUrl(userEntity.getPictureUrl())
                    .build();
            if(siteEntity != null) {
                appInfo.setSiteInfo(SiteInfo.builder()
                        .title(siteEntity.getTitle())
                        .facebookLink(siteEntity.getFacebookLink())
                        .instagramLink(siteEntity.getInstagramLink())
                        .whatsappLink(siteEntity.getWhatsappLink())
                        .telNumber(siteEntity.getTelNumber())
                        .build());
            } else {
                appInfo.setSiteInfo(SiteInfo.builder()
                        .title(appDataCache.getDomain()).build());
            }
        } else {
            appInfo = AppInfo.builder()
                    .isLoggedIn(false)
                    .isAdmin(false)
                    .username(authentication.getName())
                    .build();

            if(siteEntity != null) {
                appInfo.setSiteInfo(SiteInfo.builder()
                        .title(siteEntity.getTitle())
                        .facebookLink(siteEntity.getFacebookLink())
                        .instagramLink(siteEntity.getInstagramLink())
                        .whatsappLink(siteEntity.getWhatsappLink())
                        .telNumber(siteEntity.getTelNumber())
                        .build());
            } else {
                appInfo.setSiteInfo(SiteInfo.builder()
                        .title(appDataCache.getDomain()).build());
            }
        }
        return ResponseEntity.ok(appInfo);
//        try {
//            JsonNode jsonNode = mapper.readTree(request.getInputStream());
//            int size = jsonNode.size();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        HttpHeaders responseHeaders = new HttpHeaders();
//        return new ResponseEntity("Failed!", responseHeaders, HttpStatus.valueOf(200));
    };
}
