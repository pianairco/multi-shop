package ir.piana.business.multishop.module.auth.rest;

import ir.piana.business.multishop.common.exceptions.HttpCommonRuntimeException;
import ir.piana.business.multishop.module.auth.action.AuthAction;
import ir.piana.business.multishop.module.auth.data.repository.GoogleUserRepository;
import ir.piana.business.multishop.module.auth.model.AppInfo;
import ir.piana.business.multishop.module.auth.model.SubDomainInfo;
import ir.piana.business.multishop.module.auth.service.CrossDomainAuthenticationService;
import ir.piana.business.multishop.module.auth.service.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthRest {
    @Autowired
    private AuthAction authAction;

    @Autowired
    private GoogleUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CrossDomainAuthenticationService crossDomainAuthenticationService;

    @PostMapping(path = "sign-out", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppInfo> signOut(@RequestBody Map map, HttpSession session) throws IOException {
        session.invalidate();
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(authenticate);
        AppInfo appInfo = AppInfo.builder().isLoggedIn(false)
                .isAdmin(false).build();
        return ResponseEntity.ok(appInfo);
    }

    @Transactional
    @CrossOrigin
    @PostMapping(path = "change-password",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> changePassword(HttpServletRequest request,
                                              @RequestBody Map<String, String> body,
                                              HttpSession session) {
        String password = body.get("password");
        String passwordRepeat = body.get("passwordRepeat");
        if(password.equalsIgnoreCase(passwordRepeat)) {
            String encode = bCryptPasswordEncoder.encode(password);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserModel userModel = (UserModel)authentication.getPrincipal();
            userModel.getUserEntity().setFormPassword(encode);
            userRepository.save(userModel.getUserEntity());
            body.clear();
            body.put("message", "OK");
        } else {
            throw new HttpCommonRuntimeException(HttpStatus.valueOf(400), 1, "کلمه عبور مغایرت دارد");
        }

        return ResponseEntity.ok(body);
    }

    @CrossOrigin
    @PostMapping(path = "app-info",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppInfo> getAppInfo(HttpServletRequest request,
                                              @RequestBody Map<String, Object> body,
                                              HttpSession session) {
        return authAction.appInfo.apply(request, body);

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        AppInfo appInfo = null;
//        if(authentication.getDetails() instanceof GoogleUserEntity) {
//            appInfo = AppInfo.builder()
//                    .isLoggedIn(true)
//                    .isAdmin(false)
//                    .username(((GoogleUserEntity) authentication.getDetails()).getName())
//                    .email(((GoogleUserEntity) authentication.getDetails()).getEmail())
//                    .pictureUrl(((GoogleUserEntity) authentication.getDetails()).getPictureUrl())
//                    .build();
//        } else {
//            appInfo = AppInfo.builder().isLoggedIn(false)
//                    .isAdmin(false)
//                    .username(authentication.getName()).build();
//        }
//        return ResponseEntity.ok(appInfo);
    }

    @CrossOrigin
    @PostMapping(path = "sign-in/sub-domain", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> signInBySubDomain(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Map body, HttpSession session) throws IOException {

        String uuid = crossDomainAuthenticationService.createInstance(request);

        body.clear();
        body.put("uuid", uuid);
        body.put("redirect", "https://piana.ir:8443/#/login?sub-domain=" + uuid);
//        body.put("redirect", "https://piana.ir:8443/#/login?sub-domain=" + tenantId);
//        response.sendRedirect("https://piana.ir:8443/#/login?sub-domain=" + uuid.toString());
        return ResponseEntity.ok(body);
    }

    @CrossOrigin
    @PostMapping(path = "sign-in/sub-domain/set-token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> signInBySubDomainSetAccessToken(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Map body, HttpSession session) throws IOException {
        String uuid = (String) body.get("uuid");
        String accessToken = (String) body.get("accessToken");
        if(!crossDomainAuthenticationService.addAccessToken(uuid, accessToken)) {
            return ResponseEntity.notFound().build();
        }
      return ResponseEntity.ok().build();
    }
}
