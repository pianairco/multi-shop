package ir.piana.business.multishop.module.auth.service;

import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;

public interface AuthenticationService {
    GoogleUserEntity getUserEntity(boolean isForce);
}
