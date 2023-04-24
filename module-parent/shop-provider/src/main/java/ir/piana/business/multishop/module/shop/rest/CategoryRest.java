package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.util.CommonUtils;
import ir.piana.business.multishop.module.shop.data.entity.ProductCategoryEntity;
import ir.piana.business.multishop.module.shop.data.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/modules/shop/category")
public class CategoryRest {
    @Autowired
    private ProductCategoryRepository categoryRepository;

    @GetMapping("list")
    public ResponseEntity<List<ProductCategoryEntity>> getProductCategories(
            HttpServletRequest request) {
        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if(CommonUtils.isNull(siteEntity))
            return ResponseEntity.badRequest().build();
        List<ProductCategoryEntity> all = categoryRepository
                .findAllBySiteId(siteEntity.getId());
        return ResponseEntity.ok(all);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ProductCategoryEntity> saveProductCategorizations(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {

        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }
        ProductCategoryEntity categorizationEntity =
                ProductCategoryEntity.builder()
                        .title(body.get("title"))
                        .routerLink(body.get("routerLink"))
                        .siteId(siteEntity.getId())
                        .orders(categoryRepository.countBySiteId(siteEntity.getId()) + 1)
                        .pianaCategoryId(body.get("pianaCategoryId"))
                        .build();
        ProductCategoryEntity save = categoryRepository.save(categorizationEntity);
        return ResponseEntity.ok(save);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<ProductCategoryEntity> updateProductCategorizations(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {
        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        String id = body.get("id");
        if (CommonUtils.isNull(siteEntity) || CommonUtils.isNull(id) || !CommonUtils.isNumber(id)) {
            return ResponseEntity.badRequest().build();
        }
        ProductCategoryEntity categorizationEntity = categoryRepository
                .findBySiteIdAndId(siteEntity.getId(), Long.valueOf(id));
                categorizationEntity.setTitle(body.get("title"));
                categorizationEntity.setRouterLink(body.get("routerLink"));
        ProductCategoryEntity save = categoryRepository.save(categorizationEntity);
        return ResponseEntity.ok(save);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProductCategorizations(
            HttpServletRequest request,
            @PathVariable ("id") Long id) {
        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }
        if (CommonUtils.isNull(id)) {
            return ResponseEntity.badRequest().build();
        }
        categoryRepository.deleteBySiteIdAndId(siteEntity.getId(), id);
        return ResponseEntity.ok().build();
    }
}
