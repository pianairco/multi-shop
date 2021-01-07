package ir.piana.business.multishop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import ir.piana.business.multishop.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.data.repository.GoogleUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private GoogleUserRepository googleUserRepository;

    public JWTAuthenticationFilter(
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            GoogleUserRepository googleUserRepository) {
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.googleUserRepository = googleUserRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
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
                GoogleCredential credential = new GoogleCredential().setAccessToken((String) accessToken);


                Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName(
                        "Oauth2").build();
                Userinfo userinfo = oauth2.userinfo().get().execute();
                String email = userinfo.getEmail();
                String name = userinfo.getName();
                String picture = userinfo.getPicture();
                String locale = userinfo.getLocale();

                userEntity = GoogleUserEntity.builder()
                        .email(email)
                        .givenName(name)
                        .locale(locale)
                        .pictureUrl(picture)
                        .password("0000")
                        .build();
            } /*else {
                userEntity = new ObjectMapper()
                        .readValue(req.getInputStream(), GoogleUserEntity.class);
            }*/

            if(googleUserRepository.findByEmail(userEntity.getEmail()) == null) {
                userEntity.setPassword("$2a$10$IEgruxRGFH8Ruf4l23Niou4BamMER1/NBg.zHz4xA/w8pl597R8SO");
                googleUserRepository.save(userEntity);
                userEntity.setPassword("0000");
            }

            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userEntity.getEmail(),
                            userEntity.getPassword(),
                            new ArrayList<>())
            );
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

//        String token = JWT.create()
//                .withSubject(((User) auth.getPrincipal()).getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_000))
//                .sign(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()));
        req.getSession().setAttribute("authentication", auth.getPrincipal());
        req.getSession().setAttribute("authorization", ((User) auth.getPrincipal()).getUsername());
        res.sendRedirect("hello");
//        res.addHeader("Authorization", "Bearer " + token);
    }
}
