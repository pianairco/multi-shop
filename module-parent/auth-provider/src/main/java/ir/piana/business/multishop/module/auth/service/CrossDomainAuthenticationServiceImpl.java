package ir.piana.business.multishop.module.auth.service;

import ir.piana.business.multishop.module.auth.model.LoginInfo;
import ir.piana.business.multishop.module.auth.model.SubDomainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component("crossDomainAuthenticationService")
public class CrossDomainAuthenticationServiceImpl implements CrossDomainAuthenticationService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Map<String, SubDomainInfo> subDomainInfoMap = new LinkedHashMap<>();

    @Override
    public String createInstance(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        String tenantId = (String) request.getAttribute("tenantId");
        subDomainInfoMap.put(uuid, SubDomainInfo.builder().tenant(tenantId).build());
        return uuid;
    }

    @Override
    public SubDomainInfo getSubDomainInfo(String uuid) {
        return subDomainInfoMap.get(uuid);
    }

    @Override
    public boolean addAccessToken(String uuid, String accessToken) {
        SubDomainInfo subDomainInfo = subDomainInfoMap.get(uuid);
        if (subDomainInfo == null)
            return false;
        subDomainInfo.setLoginType("g-oauth2");
        subDomainInfo.setLoginInfo(LoginInfo.builder()
                .uuid(uuid)
                .accessToken(accessToken).build());
//        subDomainInfo.setAccessToken(accessToken);
        return true;
    }

    @Override
    public boolean addLoginInfo(String uuid, LoginInfo loginInfo, Object captcha) {
        SubDomainInfo subDomainInfo = subDomainInfoMap.get(uuid);
        if (subDomainInfo == null)
            return false;
        subDomainInfo.setLoginType("form");
        subDomainInfo.setSessionCaptcha(captcha);
        subDomainInfo.setLoginInfo(loginInfo);
        return true;
    }

    public SubDomainInfo removeSubDomainInfoString(String uuid) {
        return subDomainInfoMap.remove(uuid);
    }
}
