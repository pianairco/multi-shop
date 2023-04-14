package ir.piana.business.multishop.module.site.rest;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.common.model.ResponseModel;
import ir.piana.business.multishop.common.util.CommonUtils;
import ir.piana.business.multishop.module.auth.model.SiteInfo;
import ir.piana.business.multishop.module.site.service.CategoryRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/modules/site")
public class SiteModuleRest {

    @Autowired
    private SiteRepository repository;

    @Autowired
    private CategoryRangeService rangeService;

//    @Autowired
//    private BayaCategoryService bayaCategoryService;

    @Transactional
    @GetMapping("all-sites")
    public ResponseEntity<ResponseModel> allSites() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserModel userModel = (UserModel) authentication.getPrincipal();

        List<SiteEntity> all = repository.findAll();
        if(CommonUtils.isNull(all)) {
            return ResponseEntity.ok(
                    ResponseModel.builder().code(1)
                            .data(new ArrayList<>())
                            .build());
        }
        return ResponseEntity.ok(ResponseModel.builder().code(0).data(all).build());
    }

    @Transactional
    @GetMapping("by-category")
    public ResponseEntity<ResponseModel> allSitesByCategory(@RequestParam("category-id") String categoryIdString) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserModel userModel = (UserModel) authentication.getPrincipal();

        long startCategoryId = Long.valueOf(categoryIdString);
        long endCategoryId = rangeService.detectBoundary(Long.valueOf(categoryIdString));

        List<SiteEntity> all = repository.findByCategory(startCategoryId, endCategoryId);
//        List<SiteEntity> all = repository.findAllByCategory(categoryId);
        if(CommonUtils.isNull(all)) {
            return ResponseEntity.ok(
                    ResponseModel.builder().code(1)
                            .data(new ArrayList<>())
                            .build());
        }
        return ResponseEntity.ok(ResponseModel.builder().code(0).data(all).build());
    }

    @Autowired
    private SiteRepository siteRepository;

    @Transactional
    @PutMapping("info")
    public ResponseEntity<ResponseModel> updateInfo(
            HttpServletRequest request, @RequestBody SiteInfo siteInfo) {
        String host = (String) request.getAttribute("host");
        SiteEntity siteEntity = siteRepository.findByTenantId(host);

        siteEntity.setTitle(siteInfo.getTitle());
        siteEntity.setDescription(siteInfo.getDescription());
        siteRepository.save(siteEntity);
        return ResponseEntity.ok(
                    ResponseModel.builder().code(0)
                            .data(SiteInfo.builder()
                                    .title(siteEntity.getTitle())
                                    .description(siteEntity.getDescription())
                                    .build())
                            .build());
    }
}
