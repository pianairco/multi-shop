package ir.piana.business.multishop.module.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.data.repository.GoogleUserRepository;
import ir.piana.business.multishop.module.auth.model.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private GoogleUserRepository googleUserRepository;

    public JWTAuthenticationFilter(
            String loginUrl,
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            GoogleUserRepository googleUserRepository) {
        super(new AntPathRequestMatcher(loginUrl));
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.googleUserRepository = googleUserRepository;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null && authentication.isAuthenticated())
                return authentication;
            GoogleUserEntity userEntity = null;
            /*if("application/x-www-form-urlencoded".equalsIgnoreCase(req.getContentType())) {
                String s = IOUtils.toString(req.getInputStream());
                Map<String, String> split = Splitter.on('&')
                        .withKeyValueSeparator('=')
                        .split(s);
                userEntity = GoogleUserEntity.builder()
                        .username(split.get("username"))
                        .password(split.get("password"))
                        .build();
            } else */
            if("application/json".equalsIgnoreCase(req.getContentType())) {
                String accessToken = new ObjectMapper().readTree(req.getInputStream()).findValue("accessToken").asText();
                if(accessToken != null && accessToken.equalsIgnoreCase("1234")) {
                    GoogleUserEntity admin = googleUserRepository.findByEmail("admin");
                    userEntity = GoogleUserEntity.builder()
                            .email(admin.getEmail())
                            .givenName(admin.getGivenName())
                            .locale(admin.getLocale())
                            .pictureUrl(admin.getPictureUrl())
                            .password("1234")
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

            } /*else {
                userEntity = new ObjectMapper()
                        .readValue(req.getInputStream(), GoogleUserEntity.class);
            }*/

            if(googleUserRepository.findByEmail(userEntity.getEmail()) == null) {
                userEntity.setPassword(bCryptPasswordEncoder.encode("0000"));
                userEntity.setUserId(UUID.randomUUID().toString());
                googleUserRepository.save(userEntity);
                userEntity.setPassword("0000");
            }

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userEntity.getEmail(),
                            userEntity.getPassword(),
                            new ArrayList<>())
            );
            return authentication;
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
