package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.dev.uploadrest.StorageService;
import ir.piana.business.multishop.common.util.CommonUtils;
import ir.piana.business.multishop.module.shop.data.entity.ProductEntity;
import ir.piana.business.multishop.module.shop.data.repository.ProductRepository;
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

@RestController
@RequestMapping("api/modules/shop/product")
public class ProductRest {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductRepository productRepository;

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
    public ResponseEntity<List<ProductEntity>> getGroupItemModelsByRouterKey(
            HttpServletRequest request,
            @PathVariable Long categoryId) {
        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productRepository.findAllBySiteIdAndCategoryId(siteEntity.getId(), categoryId));
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<ProductEntity> saveProduct(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {

        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }

        String categoryId = body.get("categoryId");
        if (CommonUtils.isNull(categoryId) || !CommonUtils.isNumber(categoryId)) {
            return ResponseEntity.badRequest().build();
        }
        String imageBase64 = body.get("imageBase64");
        String imagePath = null;
        if(imageBase64 != null && !imageBase64.isEmpty()) {
            imagePath = storageService.store(body.get("imageBase64"), "product", 0);
        }
        ProductEntity productEntity =
                ProductEntity.builder()
                        .title(body.get("title"))
                        .description(body.get("description"))
                        .siteId(siteEntity.getId())
                        .categoryId(Long.valueOf(categoryId))
                        .image(imagePath)
                        .price(Long.valueOf(body.get("price")))
                        .currency(body.get("currency"))
                        .measurement(Long.valueOf(body.get("measurement")))
                        .measurementUnit(body.get("measurementUnit"))
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

        ProductEntity productEntity = productRepository.findBySiteIdAndId(siteEntity.getId(), Long.valueOf(id));
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
        productEntity.setPrice(Long.valueOf(body.get("price")));
        productEntity.setCurrency(body.get("currency"));
        productEntity.setMeasurement(Long.valueOf(body.get("measurement")));
        productEntity.setMeasurementUnit(body.get("measurementUnit"));
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
        productRepository.deleteBySiteIdAndId(siteEntity.getId(), id);
        return ResponseEntity.ok().build();
    }
}
