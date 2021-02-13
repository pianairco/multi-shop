package ir.piana.business.multishop.rest;

import ir.piana.business.multishop.common.data.cache.AppDataCache;
import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.entity.SiteUserEntity;
import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.common.data.repository.SiteUserRepository;
import ir.piana.business.multishop.module.auth.service.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/site")
public class SiteRest {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm:ss");

//    @Autowired
//    private DatabasSe

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private AppDataCache appDataCache;

    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity addSite(
            @RequestBody Map<String, String> body) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel userModel = (UserModel) authentication.getPrincipal();
        SiteEntity siteEntity = SiteEntity.builder()
                .agentId(userModel.getUserEntity().getAgentId())
                .tenantId(body.get("tenantId") + "." + appDataCache.getDomain())
                .creationDate(simpleDateFormat.format(new Date()))
                .creationTime(simpleTimeFormat.format(new Date()))
                .modificationDate(simpleDateFormat.format(new Date()))
                .modificationTime(simpleTimeFormat.format(new Date()))
                .isActive(true)
                .build();
        siteRepository.save(siteEntity);
        SiteUserEntity siteUserEntity = SiteUserEntity.builder()
                .siteId(siteEntity.getId())
                .agentId(siteEntity.getAgentId())
                .build();
        siteUserRepository.save(siteUserEntity);
        return ResponseEntity.ok(body);
    }
}
