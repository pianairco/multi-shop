package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.entity.SiteUserEntity;
import ir.piana.business.multishop.common.util.CommonUtils;
import ir.piana.business.multishop.module.shop.data.entity.ProductCategorizationEntity;
import ir.piana.business.multishop.module.shop.data.repository.ProductCategorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/modules/shop")
public class ShopRest {
    @Autowired
    private ProductCategorizationRepository categorizationRepository;

    @GetMapping("product-categorization")
    public ResponseEntity<List<ProductCategorizationEntity>> getProductCategorizations(
            HttpServletRequest request) {
        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if(CommonUtils.isNull(siteEntity))
            return ResponseEntity.badRequest().build();
        List<ProductCategorizationEntity> all = categorizationRepository
                .findAllBySiteId(siteEntity.getId());
        return ResponseEntity.ok(all);
    }

    @PostMapping("product-categorization")
    public ResponseEntity<ProductCategorizationEntity> saveProductCategorizations(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {

        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }
        ProductCategorizationEntity categorizationEntity =
                ProductCategorizationEntity.builder()
                        .title(body.get("title"))
                        .routerLink(body.get("routerLink"))
                        .siteId(siteEntity.getId())
                        .orders(categorizationRepository.countBySiteId(siteEntity.getId()) + 1)
                        .build();
        ProductCategorizationEntity save = categorizationRepository.save(categorizationEntity);
        return ResponseEntity.ok(save);
    }
}
