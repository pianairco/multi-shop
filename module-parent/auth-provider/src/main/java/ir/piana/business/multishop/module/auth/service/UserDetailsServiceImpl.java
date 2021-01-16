package ir.piana.business.multishop.module.auth.service;

import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.data.repository.GoogleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private GoogleUserRepository googleUserRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GoogleUserEntity googleUserEntity = googleUserRepository.findByEmail(username);
        if (googleUserEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(googleUserEntity.getEmail(), googleUserEntity.getPassword(), Collections.emptyList());
    }
}
