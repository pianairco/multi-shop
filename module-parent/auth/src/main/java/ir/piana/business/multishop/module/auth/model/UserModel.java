package ir.piana.business.multishop.module.auth.model;

import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class UserModel extends User implements UserDetails {
    private GoogleUserEntity userEntity;

    public UserModel(String username, String password, Collection<? extends GrantedAuthority> authorities, GoogleUserEntity userEntity) {
        super(username, password, authorities);
        this.userEntity = userEntity;
    }

    public UserModel(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, GoogleUserEntity userEntity) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userEntity = userEntity;
    }
}
