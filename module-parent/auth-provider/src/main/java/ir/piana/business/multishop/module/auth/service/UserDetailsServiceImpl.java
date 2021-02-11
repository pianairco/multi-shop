package ir.piana.business.multishop.module.auth.service;

import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.data.repository.GoogleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private GoogleUserRepository googleUserRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String encodedUsername) throws UsernameNotFoundException {
        String username = null;
        boolean isForm = false;
        if(encodedUsername.contains(":")) {
            String[] split = encodedUsername.split(":");
            username = new String(Base64.getDecoder().decode(username = split[1]));
            if(split[0].equalsIgnoreCase("form")) {
                isForm = true;
            } else {
//                ToDo
            }
        }

        GoogleUserEntity googleUserEntity = googleUserRepository.findByEmail(username);
        if (googleUserEntity == null) {
            throw new UsernameNotFoundException(encodedUsername);
        }
        List<GrantedAuthority> authorities = googleUserEntity.getUserRolesEntities().stream()
                .map(e -> new SimpleGrantedAuthority(e.getRoleName())).collect(Collectors.toList());
        return new UserModel(googleUserEntity.getEmail(),
                isForm ? googleUserEntity.getFormPassword() : googleUserEntity.getPassword(),
                authorities, googleUserEntity);
    }
}
