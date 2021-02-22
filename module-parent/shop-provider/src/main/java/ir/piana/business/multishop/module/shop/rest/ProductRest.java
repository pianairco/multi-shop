package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.dev.uploadrest.StorageService;
import ir.piana.business.multishop.common.util.CommonUtils;
import ir.piana.business.multishop.module.shop.data.entity.ProductCategoryEntity;
import ir.piana.business.multishop.module.shop.data.entity.ProductEntity;
import ir.piana.business.multishop.module.shop.data.repository.ProductRepository;
import ir.piana.business.multishop.module.shop.model.ProductItemModel;
import ir.piana.business.multishop.module.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("items")
    public ResponseEntity<List<ProductItemModel>> getGroupItemModels() {
        return ResponseEntity.ok(shopService.getProductItemModels());
    }

    @GetMapping("items/{routerKey}")
    public ResponseEntity<List<ProductItemModel>> getGroupItemModelsByRouterKey(
            @PathVariable String routerKey) {
        return ResponseEntity.ok(shopService.getProductItemModels().stream()
                .filter(p -> p.getGroupRouterKey().equalsIgnoreCase(routerKey))
                .collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity<ProductEntity> saveProductCategorizations(
            HttpServletRequest request,
            @RequestBody Map<String, String> body) {

        SiteEntity siteEntity = (SiteEntity) request.getAttribute("site");
        if (CommonUtils.isNull(siteEntity)) {
            return ResponseEntity.badRequest().build();
        }
        String imagePath = storageService.store(body.get("imageBase64"), "product", 0);

        ProductEntity productEntity =
                ProductEntity.builder()
                        .title(body.get("title"))
                        .description(body.get("description"))
                        .siteId(siteEntity.getId())
                        .image(imagePath)
                        .price(Long.valueOf(body.get("price")))
                        .currency(body.get("currency"))
                        .measurement(Long.valueOf(body.get("measurement")))
                        .measurementUnit(body.get("measurementUnit"))
                        .build();
        ProductEntity save = productRepository.save(productEntity);
        return ResponseEntity.ok(save);
    }
}
