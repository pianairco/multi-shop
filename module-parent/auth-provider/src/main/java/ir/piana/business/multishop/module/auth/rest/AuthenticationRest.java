package ir.piana.business.multishop.module.auth.rest;

import ir.piana.business.multishop.common.exceptions.HttpCommonRuntimeException;
import ir.piana.business.multishop.module.auth.action.AuthAction;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.data.repository.GoogleUserRepository;
import ir.piana.business.multishop.module.auth.model.AppInfo;
import ir.piana.business.multishop.module.auth.model.LoginInfo;
import ir.piana.business.multishop.module.auth.service.CrossDomainAuthenticationService;
import ir.piana.business.multishop.module.auth.model.UserModel;
import nl.captcha.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/modules/auth")
public class AuthenticationRest {
    @Autowired
    private AuthAction authAction;

    @Autowired
    private GoogleUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CrossDomainAuthenticationService crossDomainAuthenticationService;

    @Value("${login.redirect.url}")
    private String loginRedirect;

    @PostConstruct
    public void init() {
        System.out.println(loginRedirect);
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
        body.put("redirect", loginRedirect.concat("/#/login?sub-domain=" + uuid));
//        body.put("redirect", "https://piana.ir:8443/#/login?sub-domain=" + uuid);
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

    @CrossOrigin
    @PostMapping(path = "sign-in/sub-domain/set-login-info",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> signInBySubDomainSetLoginInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody LoginInfo loginInfo,
            HttpSession session) throws IOException {
        GoogleUserEntity byEmail = userRepository.findByEmail(loginInfo.getUsername());
        Captcha sessionCaptcha = (Captcha)session.getAttribute("simpleCaptcha");
        if(byEmail != null &&
                sessionCaptcha != null &&
                bCryptPasswordEncoder.matches(loginInfo.getPassword(), byEmail.getPassword()) &&
                sessionCaptcha.isCorrect(loginInfo.getCaptcha())) {
            if(crossDomainAuthenticationService.addLoginInfo(loginInfo.getUuid(), loginInfo, sessionCaptcha)) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @PostMapping(path = "sign-in/sub-domain/set-principal",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> signInBySubDomainSetPrincipal(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Map<String, String> body) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() != null && authentication.getPrincipal() instanceof UserModel) {
            GoogleUserEntity userEntity = ((UserModel) authentication.getPrincipal()).getUserEntity();
            if(crossDomainAuthenticationService.addPrincipal(
                    body.get("uuid"), ((UserModel) authentication.getPrincipal()).getUserEntity())) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
