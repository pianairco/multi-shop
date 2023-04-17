package ir.piana.business.multishop.module.auth.service;

import ir.piana.business.multishop.common.exceptions.HttpCommonRuntimeException;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServiceProvider implements AuthenticationService {
    public GoogleUserEntity getUserEntity(boolean isForce) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() != null && authentication.getPrincipal() instanceof UserModel) {
            return  ((UserModel) authentication.getPrincipal()).getUserEntity();
        }
        if (isForce)
            throw new HttpCommonRuntimeException(HttpStatus.UNAUTHORIZED, 1, "not authenticated");
        return null;
    }
}
