package ir.piana.business.multishop.module.auth.rest;

import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.model.AppInfo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthRest {
    @PostMapping(path = "sign-out", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppInfo> signOut(@RequestBody Map map, HttpSession session) throws IOException {
        session.invalidate();
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(authenticate);
        AppInfo appInfo = AppInfo.builder().isLoggedIn(false)
                .isAdmin(false).build();
        return ResponseEntity.ok(appInfo);
    }

    @GetMapping("app-info")
    public ResponseEntity<AppInfo> getAppInfo(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppInfo appInfo = null;
        if(authentication.getDetails() instanceof GoogleUserEntity) {
            appInfo = AppInfo.builder()
                    .isLoggedIn(true)
                    .isAdmin(false)
                    .username(((GoogleUserEntity) authentication.getDetails()).getName())
                    .email(((GoogleUserEntity) authentication.getDetails()).getEmail())
                    .pictureUrl(((GoogleUserEntity) authentication.getDetails()).getPictureUrl())
                    .build();
        } else {
            appInfo = AppInfo.builder().isLoggedIn(false)
                    .isAdmin(false)
                    .username(authentication.getName()).build();
        }
        return ResponseEntity.ok(appInfo);
    }
}
