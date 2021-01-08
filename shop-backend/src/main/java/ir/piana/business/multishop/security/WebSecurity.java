package ir.piana.business.multishop.security;

import ir.piana.business.multishop.ds.config.MultiTenantFilter;
import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import ir.piana.business.multishop.ds.service.DataSourceService;
import ir.piana.business.multishop.exceptions.CustomFilterExceptionHandlerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("dataSources")
    private Map<String, DataSource> dataSourceMap;

    @Autowired
    @Qualifier("failedDataSources")
    private Map<String, DataSourceEntity> failedDataSources;

    @Autowired
    @Qualifier("multiShopDataSources")
    private List<DataSourceEntity> multiShopDataSources;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier("CustomUserDetailsService")
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
//    @Autowired
//    private GoogleUserRepository googleUserRepository;


    //https://www.logicbig.com/tutorials/spring-framework/spring-boot/jdbc-security-with-h2-console.html
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/home").permitAll()
//                .antMatchers(HttpMethod.POST, "/vavishka-shop/login").permitAll()
//                .antMatchers(HttpMethod.POST, "/action").permitAll()//.authenticated()
//                .antMatchers(HttpMethod.POST, "/logout").authenticated()
//                .antMatchers(HttpMethod.GET, "/hello").authenticated()
//                .antMatchers(HttpMethod.GET, "/home.do").authenticated()
//                .antMatchers(HttpMethod.GET, "/**").permitAll()
//                .anyRequest().authenticated()
//                .antMatchers("/images/**").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().permitAll()
                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
//                .successForwardUrl("/hello")
//                .defaultSuccessUrl("/home")
//                .successHandler(getSuccessHandler())
//                .failureUrl("/error")
//                .permitAll()
//                .and()
                .headers().frameOptions().disable()
                .and()
//                .addFilterBefore(new CustomFilterExceptionHandlerFilter(
//                        multiShopDataSources, failedDataSources, dataSourceMap),
//                        MultiTenantFilter.class)
                .addFilterBefore(new MultiTenantFilter(
                        multiShopDataSources, failedDataSources, dataSourceService),
                        CustomAuthenticationFilter.class)
                .addFilter(new CustomAuthenticationFilter(authenticationManager()))
//                .addFilter(new JWTAuthenticationFilter(
//                        authenticationManager(), bCryptPasswordEncoder, googleUserRepository))
//                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
//                 this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler getSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setUseReferer(true);
        return handler;
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
                .and()
                .authenticationProvider(authenticationProvider);
    }
}
