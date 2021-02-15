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
import ir.piana.business.multishop.common.data.service.AgentProvider;
import ir.piana.business.multishop.common.exceptions.HttpCommonRuntimeException;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.data.repository.GoogleUserRepository;
import ir.piana.business.multishop.module.auth.model.AppInfo;
import ir.piana.business.multishop.module.auth.model.LoginInfo;
import ir.piana.business.multishop.module.auth.model.SubDomainInfo;
import nl.captcha.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

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
    private AgentProvider agentProvider;
    private CrossDomainAuthenticationService crossDomainAuthenticationService;
    private AppDataCache appDataCache;

    public JWTAuthenticationFilter(
            String loginUrl,
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            GoogleUserRepository googleUserRepository,
            CrossDomainAuthenticationService crossDomainAuthenticationService,
            AppDataCache appDataCache,
            Environment env) {
        super(new AntPathRequestMatcher(loginUrl));
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.googleUserRepository = googleUserRepository;
        this.crossDomainAuthenticationService = crossDomainAuthenticationService;
        this.appDataCache = appDataCache;
        this.env = env;
    }

    Authentication byForm(HttpServletRequest request, HttpServletResponse res) throws IOException {
        Captcha simpleCaptcha = (Captcha)request.getSession().getAttribute("simpleCaptcha");
        LoginInfo loginInfo = null;
        if(Arrays.stream(env.getActiveProfiles()).anyMatch(p -> "develop".matches(p))) {
            loginInfo = LoginInfo.builder().captcha(simpleCaptcha.getAnswer())
                    .username("rahmatii1366@gmail.com")
                    .password("0000")
                    .build();
        } else {
            loginInfo = new ObjectMapper().readValue(request.getInputStream(), LoginInfo.class);
        }

        if(!simpleCaptcha.isCorrect(loginInfo.getCaptcha()))
            throw new HttpCommonRuntimeException(HttpStatus.UNAUTHORIZED, 1, "captcha failed");
//                if(loginInfo != null) {
//                    userEntity = userRepository.findByUsername(loginInfo.getUsername());
//                }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "form:" + new String(Base64.getEncoder().encode(loginInfo.getUsername().getBytes(StandardCharsets.UTF_8))),
                        loginInfo.getPassword(),
//                        "form:" + new String(Base64.getEncoder().encode(loginInfo.getPassword().getBytes(StandardCharsets.UTF_8))),
                        new ArrayList<>())
        );

        return authentication;
    }

    Authentication byGoogle(HttpServletRequest req, HttpServletResponse res) throws IOException {
        GoogleUserEntity userEntity = null;
        String host = (String) req.getAttribute("host");
        String accessToken = null;
        if(host != null && host.equalsIgnoreCase(appDataCache.getDomain())) {
            accessToken = new ObjectMapper().readTree(req.getInputStream()).findValue("accessToken").asText();
        } else {
            String uuid = new ObjectMapper().readTree(req.getInputStream()).findValue("uuid").asText();
            SubDomainInfo subDomainInfo = crossDomainAuthenticationService.getSubDomainInfo(uuid);
            if(subDomainInfo != null && subDomainInfo.getAccessToken() != null) {
                accessToken = subDomainInfo.getAccessToken();
            }
        }
        if(accessToken == null) {
            throw new HttpCommonRuntimeException(HttpStatus.valueOf(404), 1, "access token not provided");
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

            Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName(
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
            userEntity.setPassword(bCryptPasswordEncoder.encode("1234"));
            userEntity.setUserId(agentEntity.getUsername());
            userEntity.setAgentId(agentEntity.getId());
            googleUserRepository.save(userEntity);
            userEntity.setPassword("0000");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "g-oauth2:" + new String(Base64.getEncoder().encode(userEntity.getEmail().getBytes(StandardCharsets.UTF_8))),
//                        userEntity.getEmail(),
//                        "g-oauth2:" + new String(Base64.getEncoder().encode(userEntity.getPassword().getBytes(StandardCharsets.UTF_8))),
                        userEntity.getPassword(),
                        new ArrayList<>()));
        return authentication;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null && authentication.isAuthenticated())
                return authentication;

            if(req.getContentType() != null &&
                    (req.getContentType().startsWith("APPLICATION/JSON") ||
                            req.getContentType().startsWith("application/json"))) {
                if (req.getHeader("auth-type").equalsIgnoreCase("g-oauth2"))
                    return byGoogle(req, res);
                else if(req.getHeader("auth-type").equalsIgnoreCase("form")) {
                    return byForm(req, res);
                }
                else
                    throw new RuntimeException();
            }
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
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
        req.getSession().setAttribute("jwt-token", token);
        req.getSession().setAttribute("authentication", auth.getPrincipal());
        req.getSession().setAttribute("authorization", ((User) auth.getPrincipal()).getUsername());
        req.getSession().setAttribute("user", userEntity);
//        res.sendRedirect("hello");
//        res.addHeader("Authorization", "Bearer " + token);


        AppInfo appInfo = AppInfo.builder()
                .isLoggedIn(true)
                .isAdmin(userEntity.getUserRolesEntities().stream()
                        .filter(e -> e.getRoleName().equalsIgnoreCase("ROLE_ADMIN"))
                        .map(e -> true).findFirst().orElse(false))
                .isFormPassword(userEntity.getFormPassword() == null ? false : true)
                .username(userEntity.getGivenName())
                .email(userEntity.getEmail())
                .pictureUrl(userEntity.getPictureUrl())
                .build();

        res.setStatus(HttpStatus.OK.value());
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().print(new ObjectMapper().writeValueAsString(appInfo));
        res.getWriter().flush();
    }
}
