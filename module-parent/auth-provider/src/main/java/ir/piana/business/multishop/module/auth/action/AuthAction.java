package ir.piana.business.multishop.module.auth.action;

import ir.piana.business.multishop.common.dev.sqlrest.AjaxController;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.model.AppInfo;
import ir.piana.business.multishop.module.auth.service.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.BiFunction;

@Service("auth")
public class AuthAction extends AjaxController.Action {

    public BiFunction<HttpServletRequest, Map<String, Object>, ResponseEntity> appInfo = (request, body) -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppInfo appInfo = null;
        if(authentication.getPrincipal() instanceof UserModel) {
            GoogleUserEntity userEntity = ((UserModel) authentication.getPrincipal()).getUserEntity();
            appInfo = AppInfo.builder()
                    .isLoggedIn(true)
                    .isAdmin(userEntity.getUserRolesEntities().stream()
                            .filter(e -> e.getRoleName().equalsIgnoreCase("ROLE_SITE_OWNER"))
                            .map(e -> true).findFirst().orElse(false))
                    .isFormPassword(userEntity.getFormPassword() == null ? false : true)
                    .username(userEntity.getGivenName())
                    .email(userEntity.getEmail())
                    .pictureUrl(userEntity.getPictureUrl()).build();
        } else {
            appInfo = AppInfo.builder()
                    .isLoggedIn(false)
                    .isAdmin(false)
                    .username(authentication.getName()).build();
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
