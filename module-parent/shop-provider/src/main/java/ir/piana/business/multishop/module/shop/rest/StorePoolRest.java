package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.dev.uploadrest.StorageService;
import ir.piana.business.multishop.common.model.ResponseModel;
import ir.piana.business.multishop.common.util.CategoryUtils;
import ir.piana.business.multishop.common.util.CommonUtils;
import ir.piana.business.multishop.module.auth.data.entity.GoogleUserEntity;
import ir.piana.business.multishop.module.auth.service.AuthenticationService;
import ir.piana.business.multishop.module.shop.data.entity.ProductCategoryEntity;
import ir.piana.business.multishop.module.shop.data.entity.ProductEntity;
import ir.piana.business.multishop.module.shop.data.entity.StorePoolEntity;
import ir.piana.business.multishop.module.shop.data.repository.ProductCategoryRepository;
import ir.piana.business.multishop.module.shop.data.repository.ProductRepository;
import ir.piana.business.multishop.module.shop.data.repository.StorePoolRepository;
import ir.piana.business.multishop.module.shop.model.ProductItemModel;
import ir.piana.business.multishop.module.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/modules/shop/store-pool")
public class StorePoolRest {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StorePoolRepository storePoolRepository;
    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    @Qualifier("fileSystemStorageService")
//    @Qualifier("databaseStorageService")
    private StorageService storageService;

    @Transactional
    @GetMapping("list")
    public ResponseEntity<List<ProductItemModel>> getGroupItemModels() {
        return ResponseEntity.ok(shopService.getProductItemModels());
    }

//    @GetMapping("list/{routerKey}")
//    public ResponseEntity<List<ProductItemModel>> getGroupItemModelsByRouterKey(
//            @PathVariable String routerKey) {
//        return ResponseEntity.ok(shopService.getProductItemModels().stream()
//                .filter(p -> p.getGroupRouterKey().equalsIgnoreCase(routerKey))
//                .collect(Collectors.toList()));
//    }

    @Transactional
    @GetMapping("list/{categoryId}")
    public ResponseEntity<ResponseModel<List<StorePoolEntity>>> getGroupItemModelsByRouterKey(
            HttpServletRequest request,
            @PathVariable Long categoryId) {
        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }
        List<StorePoolEntity> byId = storePoolRepository.findAllBySiteIdAndCategoryId(siteEntity.getId(), categoryId);
        return ResponseEntity.ok(
                ResponseModel.<List<StorePoolEntity>>builder()
                        .code(0)
                        .data(byId)
                        .build()
                );
    }

    /*@Transactional
    @GetMapping("list/by-piana-categories/{categoryId}")
    public ResponseEntity<ResponseModel<List<ProductEntity>>> getGroupItemModelsByPianaCategories(
            HttpServletRequest request,
            @PathVariable Long categoryId) {
        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(
                ResponseModel.<List<ProductEntity>>builder()
                        .code(0)
                        .data(productRepository.findAllByRegistrarSiteIdAndPianaCategoryId(
                                siteEntity.getId(), categoryId,
                                CategoryUtils.getNextCategory(categoryId, new int[]{2, 8, 8, 8, 8, 8, 8, 6})))
                        .build()
        );
    }

    @Autowired
    private AuthenticationService authenticationService;

    @Transactional
    @PostMapping()
    public ResponseEntity<ProductEntity> saveProduct(
            HttpServletRequest request,
            @RequestBody Map<String, Object> body) {

        GoogleUserEntity userEntity = authenticationService.getUserEntity(true);

        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }

        long categoryId = (long) body.get("categoryId");
        if (CommonUtils.isNull(categoryId) || !CommonUtils.isNumber(categoryId)) {
            return ResponseEntity.badRequest().build();
        }
        String imageBase64 = (String) body.get("imageBase64");
        String imagePath = null;
        if(imageBase64 != null && !imageBase64.isEmpty()) {
            imagePath = storageService.store((String) body.get("imageBase64"), "product", 0);
        }
        ProductEntity productEntity =
                ProductEntity.builder()
                        .title((String) body.get("title"))
                        .description((String) body.get("description"))
                        .registrarSiteId(siteEntity.getId())
                        .image(imagePath)
                        .confirmed(false)
                        .pianaCategoryId(categoryId)
                        .build();
        ProductEntity save = productRepository.save(productEntity);
        return ResponseEntity.ok(save);
    }

    @Transactional
    @PutMapping()
    public ResponseEntity<ProductEntity> updateProduct(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {
        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }
        String id = body.get("id");
        if (CommonUtils.isNull(id) || !CommonUtils.isNumber(id)) {
            return ResponseEntity.badRequest().build();
        }

        ProductEntity productEntity = productRepository.findByRegistrarSiteIdAndId(siteEntity.getId(), Long.valueOf(id));
        String imageBase64 = body.get("imageBase64");
        String imagePath = null;
        if(imageBase64 != null && !imageBase64.isEmpty()) {
            imagePath = storageService.store(body.get("imageBase64"), "product", 0);
            if(imagePath != null) {
                productEntity.setImage(imagePath);
            }
        }

        productEntity.setTitle(body.get("title"));
        productEntity.setDescription(body.get("description"));
        ProductEntity save = productRepository.save(productEntity);
        return ResponseEntity.ok(save);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(
            HttpServletRequest request,
            @PathVariable("id") Long id) {
        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }
//        Object id = body.get("id");
        if (CommonUtils.isNull(id)) {
            return ResponseEntity.badRequest().build();
        }
        productRepository.deleteByRegistrarSiteIdAndId(siteEntity.getId(), id);
        return ResponseEntity.ok().build();
    }*/
}