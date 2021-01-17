package ir.piana.business.multishop.module.auth.action;

import ir.piana.business.multishop.common.dev.sqlrest.AjaxController;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.model.AppInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.BiFunction;

@Service("auth")
public class AuthAction extends AjaxController.Action {

    public BiFunction<HttpServletRequest, Map<String, Object>, ResponseEntity> appInfo = (request, body) -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppInfo appInfo = null;
        if(authentication.getDetails() instanceof GoogleUserEntity) {
            appInfo = AppInfo.builder()
                    .isLoggedIn(true)
                    .isAdmin(true)
                    .username(((GoogleUserEntity) authentication.getDetails()).getName())
                    .email(((GoogleUserEntity) authentication.getDetails()).getEmail())
                    .pictureUrl(((GoogleUserEntity) authentication.getDetails()).getPictureUrl()).build();
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
