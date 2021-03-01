package ir.piana.business.multishop.module.auth.service;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.data.repository.GoogleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private GoogleUserRepository googleUserRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String encodedUsername) throws UsernameNotFoundException {
        String username = null;
        boolean isForm = false;
        String[] split = null;
        if(encodedUsername.contains(":")) {
            split = encodedUsername.split(":");
            username = new String(Base64.getDecoder().decode(username = split[split.length - 1]));
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

        SiteEntity byTenantId = siteRepository.findByTenantId(split[1]);
        if(byTenantId.getAgentId() == googleUserEntity.getAgentId())
            authorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_AUTHENTICATED"));
        return new UserModel(googleUserEntity.getEmail(),
                isForm ? googleUserEntity.getFormPassword() : googleUserEntity.getPassword(),
                authorities, googleUserEntity);
    }
}
