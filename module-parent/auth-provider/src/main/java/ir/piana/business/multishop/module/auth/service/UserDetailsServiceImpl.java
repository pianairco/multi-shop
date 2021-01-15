package ir.piana.business.multishop.module.auth.service;

import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.data.repository.GoogleUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private GoogleUserRepository googleUserRepository;

    public UserDetailsServiceImpl(GoogleUserRepository googleUserRepository) {
        this.googleUserRepository = googleUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GoogleUserEntity googleUserEntity = googleUserRepository.findByEmail(username);
        if (googleUserEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(googleUserEntity.getEmail(), googleUserEntity.getPassword(), Collections.emptyList());
    }
}
