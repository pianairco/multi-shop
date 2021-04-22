package ir.piana.business.multishop.module.site.rest;

import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.common.dev.uploadrest.StorageService;
import ir.piana.business.multishop.common.model.ResponseModel;
import ir.piana.business.multishop.module.auth.model.SiteInfo;
import ir.piana.business.multishop.module.site.data.entity.SiteInfoEntity;
import ir.piana.business.multishop.module.site.data.repository.SiteInfoRepository;
import ir.piana.business.multishop.module.site.service.CategoryRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/modules/site/site-info")
public class SiteInfoRest {
    @Autowired
    private SiteInfoRepository siteInfoRepository;

    @Transactional
    @GetMapping
    public ResponseEntity<ResponseModel> getSiteInfo(
            HttpServletRequest request, @RequestBody SiteInfo siteInfo) {
//        String host = (String) request.getAttribute("host");
//        SiteEntity siteEntity = siteRepository.findByTenantId(host);
        String tenant = (String) request.getAttribute("tenant");

        SiteInfoEntity byTenantId = siteInfoRepository.findByTenantId(tenant);

        return ResponseEntity.ok(
                ResponseModel.builder().code(0)
                        .data(byTenantId)
                        .build());
    }

    @Autowired
    @Qualifier("fileSystemStorageService")
//    @Qualifier("databaseStorageService")
    private StorageService storageService;

    @Transactional
    @PutMapping("header-text")
    public ResponseEntity<ResponseModel> updateSiteInfoHeaderText(
            HttpServletRequest request, @RequestBody Map<String, String> siteInfo) {
        String tenant = (String) request.getAttribute("tenant");
        SiteInfoEntity siteInfoEntity = siteInfoRepository.findByTenantId(tenant);

        siteInfoEntity.setTitle(siteInfo.get("title"));
        siteInfoEntity.setDescription(siteInfo.get("description"));
//        String headerImage = siteInfo.get("headerImage");
//        if(headerImage != null && !headerImage.isEmpty()) {
//            headerImage = storageService.store(headerImage, "header2", 0);
//            siteInfoEntity.setHeaderImage(headerImage);
//        }
        siteInfoRepository.save(siteInfoEntity);
        return ResponseEntity.ok(
                    ResponseModel.builder().code(0)
                            .data(siteInfo)
                            .build());
    }

    @Transactional
    @PutMapping("header-image")
    public ResponseEntity<ResponseModel> updateSiteInfoHeaderImage(
            HttpServletRequest request, @RequestBody Map<String, String> siteInfo) {
        String tenant = (String) request.getAttribute("tenant");
        SiteInfoEntity siteInfoEntity = siteInfoRepository.findByTenantId(tenant);

//        siteInfoEntity.setTitle(siteInfo.get("title"));
//        siteInfoEntity.setDescription(siteInfo.get("description"));
        String headerImage = siteInfo.get("headerImage");
        if(headerImage != null && !headerImage.isEmpty()) {
            headerImage = storageService.store(headerImage, "header2", 0);
            siteInfoEntity.setHeaderImage(headerImage);
        }
        siteInfoRepository.save(siteInfoEntity);
        return ResponseEntity.ok(
                ResponseModel.builder().code(0)
                        .data(siteInfo)
                        .build());
    }

    @Transactional
    @PutMapping("tip")
    public ResponseEntity<ResponseModel> updateSiteInfoTip(
            HttpServletRequest request, @RequestBody Map<String, String> siteInfo) {
        String tenant = (String) request.getAttribute("tenant");
        SiteInfoEntity siteInfoEntity = siteInfoRepository.findByTenantId(tenant);

        siteInfoEntity.setTipTitle(siteInfo.get("tipTitle"));
        siteInfoEntity.setTipDescription(siteInfo.get("tipDescription"));
//        String headerImage = siteInfo.get("headerImage");
//        if(headerImage != null && !headerImage.isEmpty()) {
//            headerImage = storageService.store(headerImage, "header2", 0);
//            siteInfoEntity.setHeaderImage(headerImage);
//        }
        siteInfoRepository.save(siteInfoEntity);
        return ResponseEntity.ok(
                ResponseModel.builder().code(0)
                        .data(siteInfo)
                        .build());
    }

}
