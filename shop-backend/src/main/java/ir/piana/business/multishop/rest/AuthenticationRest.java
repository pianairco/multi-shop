package ir.piana.business.multishop.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import ir.piana.business.multishop.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.data.repository.GoogleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthenticationRest {
    @Autowired
    private GoogleUserRepository googleUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @CrossOrigin(origins = "http://localhost", maxAge = 3600)
    @PostMapping(path = "sign-in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppInfo> signIn(@RequestBody Map map, HttpSession session) throws IOException {
        SecurityContext sc = SecurityContextHolder.getContext();
        if(sc.getAuthentication() instanceof GoogleUserEntity)
            return ResponseEntity.badRequest().build();

        Object accessToken = map.get("accessToken");
        if(map.containsKey("accesstoken"))
            accessToken = map.get("accesstoken");
        GoogleCredential credential = new GoogleCredential().setAccessToken((String) accessToken);
        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName(
                "Oauth2").build();
        Userinfo userinfo = oauth2.userinfo().get().execute();
        String email = userinfo.getEmail();
        String name = userinfo.getName();
        String picture = userinfo.getPicture();
        String locale = userinfo.getLocale();

        GoogleUserEntity userEntity = GoogleUserEntity.builder()
                .email(email)
                .name(name.length() > 20 ? name.substring(0,20).concat("..") : name)
                .givenName(name)
                .locale(locale)
                .pictureUrl(picture)
                .password("0000")
                .build();

        if(googleUserRepository.findByEmail(userEntity.getEmail()) == null) {
            userEntity.setPassword("$2a$10$IEgruxRGFH8Ruf4l23Niou4BamMER1/NBg.zHz4xA/w8pl597R8SO");
            googleUserRepository.save(userEntity);
            userEntity = GoogleUserEntity.builder()
                    .email(email)
                    .givenName(name)
                    .name(name.length() > 20 ? name.substring(0,20).concat("..") : name)
                    .locale(locale)
                    .pictureUrl(picture)
                    .password("0000")
                    .build();
        }

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        new ArrayList<>())
        );

        ((UsernamePasswordAuthenticationToken)authenticate).setDetails(userEntity);

        sc.setAuthentication(authenticate);

        AppInfo appInfo = new AppInfo();
        appInfo.isLoggedIn = true;
        appInfo.isAdmin = true;
        appInfo.username = userEntity.getName();
        appInfo.email = userEntity.getEmail();
        appInfo.pictureUrl = userEntity.getPictureUrl();

        return ResponseEntity.ok(appInfo);
    }

    @PostMapping(path = "sign-out", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppInfo> signOut(@RequestBody Map map, HttpSession session) throws IOException {
        session.invalidate();
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(authenticate);
        AppInfo appInfo = new AppInfo();
        appInfo.isLoggedIn = false;
        appInfo.isAdmin = false;
        return ResponseEntity.ok(appInfo);
    }

    @GetMapping("app-info")
    public ResponseEntity<AppInfo> getAppInfo(HttpSession session) {
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
    }

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
