package ir.piana.business.multishop.module.auth.model;

import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubDomainInfo {
    private String tenant;
    private String accessToken;
    private String loginType;
    private LoginInfo loginInfo;
    private GoogleUserEntity principal;
    private Object sessionCaptcha;
}
