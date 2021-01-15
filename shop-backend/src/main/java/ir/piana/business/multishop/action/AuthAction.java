package ir.piana.business.multishop.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.piana.business.multishop.dev.sqlrest.AjaxController;
import ir.piana.business.multishop.dev.sqlrest.ServiceProperties;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("objectMapper")
    private ObjectMapper mapper;

    @Autowired
    private ServiceProperties serviceProperties;

    public BiFunction<HttpServletRequest, Map<String, Object>, ResponseEntity> appInfo = (request, body) -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppInfo appInfo = new AppInfo();
        if(authentication.getDetails() instanceof GoogleUserEntity) {
            appInfo.isLoggedIn = true;
            appInfo.isAdmin = true;
            appInfo.username = ((GoogleUserEntity) authentication.getDetails()).getName();
            appInfo.email = ((GoogleUserEntity) authentication.getDetails()).getEmail();
            appInfo.pictureUrl = ((GoogleUserEntity) authentication.getDetails()).getPictureUrl();
        } else {
            appInfo.isLoggedIn = false;
            appInfo.isAdmin = false;
            appInfo.username = authentication.getName();
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

    static class AppInfo {
        private String username;
        private String email;
        private String pictureUrl;
        private boolean isLoggedIn;
        private boolean isAdmin;

        public AppInfo() {
        }

        public String getUsername() {
            return username;
        }

        @JsonProperty("isLoggedIn")
        public boolean isLoggedIn() {
            return isLoggedIn;
        }

        @JsonProperty("isAdmin")
        public boolean isAdmin() {
            return isAdmin;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public String getEmail() {
            return email;
        }
    }
}
