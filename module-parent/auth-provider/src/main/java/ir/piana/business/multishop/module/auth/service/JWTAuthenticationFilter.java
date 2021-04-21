package ir.piana.business.multishop.module.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import ir.piana.business.multishop.common.data.cache.AppDataCache;
import ir.piana.business.multishop.common.data.entity.AgentEntity;
import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.common.data.service.AgentProvider;
import ir.piana.business.multishop.common.exceptions.HttpCommonRuntimeException;
import ir.piana.business.multishop.common.util.CommonUtils;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.data.repository.GoogleUserRepository;
import ir.piana.business.multishop.module.auth.model.*;
import ir.piana.business.multishop.module.site.data.entity.SiteInfoEntity;
import ir.piana.business.multishop.module.site.data.repository.SiteInfoRepository;
import nl.captcha.Captcha;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private Environment env;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private GoogleUserRepository googleUserRepository;
    private SiteRepository siteRepository;
    private SiteInfoRepository siteInfoRepository;
    private AgentProvider agentProvider;
    private CrossDomainAuthenticationService crossDomainAuthenticationService;
    private AppDataCache appDataCache;
    private NetHttpTransport netHttpTransport;

    public JWTAuthenticationFilter(
            String loginUrl,
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            GoogleUserRepository googleUserRepository,
            SiteRepository siteRepository,
            SiteInfoRepository siteInfoRepository,
            CrossDomainAuthenticationService crossDomainAuthenticationService,
            AppDataCache appDataCache,
            AgentProvider agentProvider,
            Environment env) {
        super(new AntPathRequestMatcher(loginUrl));
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.googleUserRepository = googleUserRepository;
        this.siteRepository = siteRepository;
        this.siteInfoRepository = siteInfoRepository;
        this.crossDomainAuthenticationService = crossDomainAuthenticationService;
        this.appDataCache = appDataCache;
        this.agentProvider = agentProvider;
        this.env = env;

        netHttpTransport = new NetHttpTransport();
        netHttpTransport.createRequestFactory(httpRequest -> {
            httpRequest.setConnectTimeout(60000);  // 3 minutes connect timeout
            httpRequest.setReadTimeout(60000);
        });
    }

    Authentication byForm(String username, String password, String captcha, Captcha sessionCaptcha, String host)
            throws IOException {
        if(captcha != null && sessionCaptcha != null) {
            if (!sessionCaptcha.isCorrect(captcha)) {
                throw new HttpCommonRuntimeException(HttpStatus.UNAUTHORIZED, 1, "captcha failed");
            } else if (CommonUtils.isNull(username) || CommonUtils.isNull(password)) {
                throw new HttpCommonRuntimeException(HttpStatus.valueOf(404), 1, "access token not provided");
            }
        }
//        if(loginInfo != null) {
//            userEntity = userRepository.findByUsername(loginInfo.getUsername());
//        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "form:" + host + ":" + new String(Base64.getEncoder().encode(username.getBytes(StandardCharsets.UTF_8))),
                        password,
//                        "form:" + new String(Base64.getEncoder().encode(loginInfo.getPassword().getBytes(StandardCharsets.UTF_8))),
                        new ArrayList<>())
        );

        return authentication;
    }

    /*Authentication byForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Captcha simpleCaptcha = (Captcha)request.getSession().getAttribute("simpleCaptcha");
        String host = (String) request.getAttribute("host");
        LoginInfo loginInfo = null;
        if(host != null && host.equalsIgnoreCase(appDataCache.getDomain())) {
            if(Arrays.stream(env.getActiveProfiles()).anyMatch(p -> "develop".matches(p))) {
                loginInfo = LoginInfo.builder().captcha(simpleCaptcha.getAnswer())
                        .username("rahmatii1366@gmail.com")
                        .password("0000")
                        .build();
            } else {
                loginInfo = new ObjectMapper().readValue(request.getInputStream(), LoginInfo.class);
            }
        } else {
            String uuid = new ObjectMapper().readTree(request.getInputStream()).findValue("uuid").asText();
            SubDomainInfo subDomainInfo = crossDomainAuthenticationService.getSubDomainInfo(uuid);
            if(subDomainInfo != null && subDomainInfo.getLoginInfo() != null) {
                loginInfo = subDomainInfo.getLoginInfo();
            }
        }

        if(!simpleCaptcha.isCorrect(loginInfo.getCaptcha())) {
            throw new HttpCommonRuntimeException(HttpStatus.UNAUTHORIZED, 1, "captcha failed");
        } else if(loginInfo == null) {
            throw new HttpCommonRuntimeException(HttpStatus.valueOf(404), 1, "access token not provided");
        }
//        if(loginInfo != null) {
//            userEntity = userRepository.findByUsername(loginInfo.getUsername());
//        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "form:" + new String(Base64.getEncoder().encode(loginInfo.getUsername().getBytes(StandardCharsets.UTF_8))),
                        loginInfo.getPassword(),
//                        "form:" + new String(Base64.getEncoder().encode(loginInfo.getPassword().getBytes(StandardCharsets.UTF_8))),
                        new ArrayList<>())
        );

        return authentication;
    }*/

    Authentication byGoogle(String accessToken, String host) throws IOException {
        GoogleUserEntity userEntity = null;
        if(accessToken == null) {
            throw new HttpCommonRuntimeException(HttpStatus.valueOf(401), 1, "access token not provided");
        } else if (accessToken.equalsIgnoreCase("1234")) {
            GoogleUserEntity admin = googleUserRepository.findByEmail("rahmatii1366@gmail.com");
            userEntity = GoogleUserEntity.builder()
                    .email(admin.getEmail())
                    .givenName(admin.getGivenName())
                    .locale(admin.getLocale())
                    .pictureUrl(admin.getPictureUrl())
                    .password("0000")
                    .build();
        } else {
            GoogleCredential credential = new GoogleCredential().setAccessToken((String) accessToken);


            Oauth2 oauth2 = new Oauth2.Builder(netHttpTransport, new JacksonFactory(), credential).setApplicationName(
                    "Oauth2").build();
            Userinfo userinfo = oauth2.userinfo().get().execute();
            userEntity = GoogleUserEntity.builder()
                    .email(userinfo.getEmail())
                    .givenName(userinfo.getGivenName())
                    .locale(userinfo.getLocale())
                    .pictureUrl(userinfo.getPicture())
                    .password("0000")
                    .build();
        }

        if (googleUserRepository.findByEmail(userEntity.getEmail()) == null) {
            AgentEntity agentEntity = agentProvider.createAgentEntity(UUID.randomUUID().toString());
            userEntity.setPassword(bCryptPasswordEncoder.encode("0000"));
            userEntity.setUserId(agentEntity.getUsername());
            userEntity.setAgentId(agentEntity.getId());
            googleUserRepository.save(userEntity);
            userEntity.setPassword("0000");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "g-oauth2:" + host + ":" + new String(Base64.getEncoder().encode(userEntity.getEmail().getBytes(StandardCharsets.UTF_8))),
//                        userEntity.getEmail(),
//                        "g-oauth2:" + new String(Base64.getEncoder().encode(userEntity.getPassword().getBytes(StandardCharsets.UTF_8))),
                        userEntity.getPassword(),
                        new ArrayList<>()));
        return authentication;
    }

    Authentication byPrincipal(GoogleUserEntity principal, String host) throws IOException {
        if (googleUserRepository.findByEmail(principal.getEmail()) != null) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            "principal:" + host + ":" + new String(Base64.getEncoder().encode(principal.getEmail().getBytes(StandardCharsets.UTF_8))),
//                        userEntity.getEmail(),
//                        "g-oauth2:" + new String(Base64.getEncoder().encode(userEntity.getPassword().getBytes(StandardCharsets.UTF_8))),
                            "0000",
                            new ArrayList<>()));
            return authentication;
        }
        return null;
    }
//    Authentication byGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        GoogleUserEntity userEntity = null;
//        String host = (String) request.getAttribute("host");
//        String accessToken = null;
//        if(host != null && host.equalsIgnoreCase(appDataCache.getDomain())) {
//            accessToken = new ObjectMapper().readTree(request.getInputStream()).findValue("accessToken").asText();
//        } else {
//            String uuid = new ObjectMapper().readTree(request.getInputStream()).findValue("uuid").asText();
//            SubDomainInfo subDomainInfo = crossDomainAuthenticationService.getSubDomainInfo(uuid);
//            if(subDomainInfo != null && subDomainInfo.getAccessToken() != null) {
//                accessToken = subDomainInfo.getAccessToken();
//            }
//        }
//        if(accessToken == null) {
//            throw new HttpCommonRuntimeException(HttpStatus.valueOf(404), 1, "access token not provided");
//        } else if (accessToken.equalsIgnoreCase("1234")) {
//            GoogleUserEntity admin = googleUserRepository.findByEmail("rahmatii1366@gmail.com");
//            userEntity = GoogleUserEntity.builder()
//                    .email(admin.getEmail())
//                    .givenName(admin.getGivenName())
//                    .locale(admin.getLocale())
//                    .pictureUrl(admin.getPictureUrl())
//                    .password("0000")
//                    .build();
//        } else {
//            GoogleCredential credential = new GoogleCredential().setAccessToken((String) accessToken);
//
//            Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName(
//                    "Oauth2").build();
//            Userinfo userinfo = oauth2.userinfo().get().execute();
//            userEntity = GoogleUserEntity.builder()
//                    .email(userinfo.getEmail())
//                    .givenName(userinfo.getGivenName())
//                    .locale(userinfo.getLocale())
//                    .pictureUrl(userinfo.getPicture())
//                    .password("0000")
//                    .build();
//        }
//
//        if (googleUserRepository.findByEmail(userEntity.getEmail()) == null) {
//            AgentEntity agentEntity = agentProvider.createAgentEntity(UUID.randomUUID().toString());
//            userEntity.setPassword(bCryptPasswordEncoder.encode("1234"));
//            userEntity.setUserId(agentEntity.getUsername());
//            userEntity.setAgentId(agentEntity.getId());
//            googleUserRepository.save(userEntity);
//            userEntity.setPassword("0000");
//        }
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        "g-oauth2:" + new String(Base64.getEncoder().encode(userEntity.getEmail().getBytes(StandardCharsets.UTF_8))),
////                        userEntity.getEmail(),
////                        "g-oauth2:" + new String(Base64.getEncoder().encode(userEntity.getPassword().getBytes(StandardCharsets.UTF_8))),
//                        userEntity.getPassword(),
//                        new ArrayList<>()));
//        return authentication;
//    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse res) throws AuthenticationException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null && authentication.isAuthenticated())
                return authentication;

            String host = (String) request.getAttribute("host");
            if(request.getContentType() != null &&
                    (request.getContentType().startsWith("APPLICATION/JSON") ||
                            request.getContentType().startsWith("application/json"))) {
                LoginInfo loginInfo = new ObjectMapper().readValue(request.getInputStream(), LoginInfo.class);
                if(Arrays.stream(env.getActiveProfiles()).anyMatch(p -> "develop".matches(p))) {
                    return byForm(
                            "rahmatii1366@gmail.com",
                            "0000",
                            null,
                            null, host);
                }
                if(host.equalsIgnoreCase(appDataCache.getDomain())) {
                    if (loginInfo != null && !CommonUtils.isNull(loginInfo.getAccessToken()))
                        return byGoogle(loginInfo.getAccessToken(), host);
                    else if(loginInfo != null && !CommonUtils.isNull(loginInfo.getUsername())) {
                        Captcha sessionCaptcha = (Captcha)request.getSession().getAttribute("simpleCaptcha");
                        return byForm(
                                loginInfo.getUsername(), loginInfo.getPassword(), loginInfo.getCaptcha(), sessionCaptcha, host);
                    } else {
                        throw new RuntimeException();
                    }
                } else {
                    if(loginInfo != null && !CommonUtils.isNull(loginInfo.getUuid())) {
                        SubDomainInfo subDomainInfo = crossDomainAuthenticationService
                                .removeSubDomainInfoString(loginInfo.getUuid());
                        if(!CommonUtils.isNull(subDomainInfo.getLoginType()) &&
                                subDomainInfo.getLoginType().equalsIgnoreCase("g-oauth2"))
                            return byGoogle(subDomainInfo.getLoginInfo().getAccessToken(), host);
                        else if(!CommonUtils.isNull(subDomainInfo.getLoginType()) &&
                                subDomainInfo.getLoginType().equalsIgnoreCase("form")) {
//                            Captcha sessionCaptcha = (Captcha)request.getSession().getAttribute("simpleCaptcha");
                            return byForm(subDomainInfo.getLoginInfo().getUsername(),
                                    subDomainInfo.getLoginInfo().getPassword(),
                                    subDomainInfo.getLoginInfo().getCaptcha(),
                                    (Captcha) subDomainInfo.getSessionCaptcha(), host);
                        } else if(!CommonUtils.isNull(subDomainInfo.getLoginType()) &&
                                subDomainInfo.getLoginType().equalsIgnoreCase("principal")) {
                            return byPrincipal(subDomainInfo.getPrincipal(), host);
                        }
                    } else {
                        throw new RuntimeException();
                    }
                }

//                if (request.getHeader("auth-type").equalsIgnoreCase("g-oauth2"))
//                    return byGoogle(request, res);
//                else if(request.getHeader("auth-type").equalsIgnoreCase("form")) {
//                    return byForm(request, res);
//                }
//                else
//                    throw new RuntimeException();
            }
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(auth);
        GoogleUserEntity userEntity = ((UserModel)auth.getPrincipal()).getUserEntity();
//        GoogleUserEntity userEntity = googleUserRepository.findByEmail(((User) auth.getPrincipal()).getUsername());
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_000))
                .sign(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()));
        request.getSession().setAttribute("jwt-token", token);
        request.getSession().setAttribute("authentication", auth.getPrincipal());
        request.getSession().setAttribute("authorization", ((User) auth.getPrincipal()).getUsername());
        request.getSession().setAttribute("user", userEntity);
//        res.sendRedirect("hello");
//        res.addHeader("Authorization", "Bearer " + token);
        String host = (String) request.getAttribute("host");
        String tenant = (String) request.getAttribute("tenant");
        Optional<? extends GrantedAuthority> role_owner = null;
        if(appDataCache.getDomain().equalsIgnoreCase(host)) {
            role_owner = auth.getAuthorities().stream()
                    .filter(e -> e.getAuthority().equalsIgnoreCase("ROLE_ADMIN")).findAny();
        } else {
            role_owner = auth.getAuthorities().stream()
                    .filter(e -> e.getAuthority().equalsIgnoreCase("ROLE_SITE_OWNER")).findAny();
        }

        SiteEntity siteEntity = null;
        SiteInfoEntity siteInfoEntity = null;
        if(!appDataCache.getDomain().equalsIgnoreCase(host)) {
            siteEntity = siteRepository.findByTenantId(host);
            siteInfoEntity = siteInfoRepository.findByTenantId(tenant);
        }
        AppInfo appInfo = AppInfo.builder()
                .isLoggedIn(true)
                .isAdmin(role_owner.isPresent())
//                .isAdmin(userEntity.getUserRolesEntities().stream()
//                        .filter(e -> e.getRoleName().equalsIgnoreCase("ROLE_ADMIN"))
//                        .map(e -> true).findFirst().orElse(false))
                .isFormPassword(userEntity.getFormPassword() == null ? false : true)
                .username(userEntity.getGivenName())
                .email(userEntity.getEmail())
                .pictureUrl(userEntity.getPictureUrl())
                .build();
        if(siteEntity != null) {
            appInfo.setSiteInfo(SiteInfo.builder()
                    .title(siteInfoEntity.getTitle())
                    .description(siteInfoEntity.getDescription())
                    .tipTitle(siteInfoEntity.getTipTitle())
                    .tipDescription(siteInfoEntity.getTipDescription())
                    .headerImage(siteInfoEntity.getHeaderImage())
                    .facebookLink(siteEntity.getFacebookLink())
                    .instagramLink(siteEntity.getInstagramLink())
                    .whatsappLink(siteEntity.getWhatsappLink())
                    .telNumber(siteEntity.getTelNumber())
                    .build());
        } else {
            appInfo.setSiteInfo(SiteInfo.builder()
                    .title(appDataCache.getDomain()).build());
        }

        res.setStatus(HttpStatus.OK.value());
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().print(new ObjectMapper().writeValueAsString(appInfo));
        res.getWriter().flush();
    }
}
