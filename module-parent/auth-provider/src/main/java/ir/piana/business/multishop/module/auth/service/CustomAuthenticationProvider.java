package ir.piana.business.multishop.module.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
        if (true) {
            return new UsernamePasswordAuthenticationToken(
                    userDetails, userDetails.getPassword(),
                    userDetails.getAuthorities());
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return authenticationClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
