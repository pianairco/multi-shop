package ir.piana.business.multishop.rest;

import ir.piana.business.multishop.common.data.cache.AppDataCache;
import ir.piana.business.multishop.common.data.cache.DataSourceService;
import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.entity.SiteUserEntity;
import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.common.data.repository.SiteUserRepository;
import ir.piana.business.multishop.common.model.ResponseModel;
import ir.piana.business.multishop.common.util.CommonUtils;
import ir.piana.business.multishop.module.auth.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private DataSourceService dataSourceService;

    @Autowired
    private AppDataCache appDataCache;

    @Transactional
    @GetMapping("check-name/{name}")
    public ResponseEntity<ResponseModel> checkSiteName(@PathVariable("name") String siteName) {
        SiteEntity byTenantId = siteRepository.findByTenantId(siteName.concat("." + appDataCache.getDomain()));
        if(byTenantId == null) {
            return ResponseEntity.ok(ResponseModel.builder().code(0).build());
        }
        return ResponseEntity.ok(ResponseModel.builder().code(1).build());
    }

    @Transactional
    @GetMapping("my-site")
    public ResponseEntity<ResponseModel> mySites() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel userModel = (UserModel) authentication.getPrincipal();

        List<SiteEntity> byAgentId = siteRepository.findAllByAgentId(userModel.getUserEntity().getAgentId());
        if(CommonUtils.isNull(byAgentId)) {
            return ResponseEntity.ok(
                    ResponseModel.builder().code(1)
                            .data(new ArrayList<>())
                            .build());
        }
        return ResponseEntity.ok(ResponseModel.builder().code(0).data(byAgentId).build());
    }

    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity addSite(
            @RequestBody Map<String, String> body) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel userModel = (UserModel) authentication.getPrincipal();

        String siteName = body.get("siteName");
        if(CommonUtils.isNull(siteName) || siteName.length() < 5)
            return ResponseEntity.ok(ResponseModel.builder().code(1).build());
        siteName = siteName.trim().toLowerCase();
        String title = body.get("title");
        if(CommonUtils.isNull(title))
            return ResponseEntity.ok(ResponseModel.builder().code(2).build());
        SiteEntity siteEntity = SiteEntity.builder()
                .agentId(userModel.getUserEntity().getAgentId())
                .tenantId(siteName + "." + appDataCache.getDomain())
                .title(title)
                .instagramLink(body.get("instagramLink"))
                .facebookLink(body.get("facebookLink"))
                .whatsappLink(body.get("whatsappLink"))
                .category(Long.valueOf(body.get("category")))
                .telNumber(body.get("telNumber"))
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
        dataSourceService.siteActivation(siteEntity.getTenantId());
        return ResponseEntity.ok(body);
    }
}
