package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.module.shop.model.ProductItemModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/shop/product")
public class ProductRest {
    List<ProductItemModel> productItemModels = new ArrayList<>();

    @PostConstruct
    public void init() {
        productItemModels.add(ProductItemModel.builder()
                .uuid(UUID.randomUUID().toString())
                .name("apple").groupRouterKey("fruit").price(1000)
                .build());
        productItemModels.add(ProductItemModel.builder()
                .uuid(UUID.randomUUID().toString())
                .name("orange").groupRouterKey("fruit").price(1400)
                .build());
        productItemModels.add(ProductItemModel.builder()
                .uuid(UUID.randomUUID().toString())
                .name("pineapple").groupRouterKey("fruit").price(2500)
                .build());
        productItemModels.add(ProductItemModel.builder()
                .uuid(UUID.randomUUID().toString())
                .name("tomato").groupRouterKey("cucurbits").price(1000)
                .build());
        productItemModels.add(ProductItemModel.builder()
                .uuid(UUID.randomUUID().toString())
                .name("cucumber").groupRouterKey("cucurbits").price(1340)
                .build());
        productItemModels.add(ProductItemModel.builder()
                .uuid(UUID.randomUUID().toString())
                .name("potato").groupRouterKey("cucurbits").price(1660)
                .build());
        productItemModels.add(ProductItemModel.builder()
                .uuid(UUID.randomUUID().toString())
                .name("green peas").groupRouterKey("vegetable").price(900)
                .build());
        productItemModels.add(ProductItemModel.builder()
                .uuid(UUID.randomUUID().toString())
                .name("french beans").groupRouterKey("vegetable").price(1500)
                .build());
        productItemModels.add(ProductItemModel.builder()
                .uuid(UUID.randomUUID().toString())
                .name("fresh beetroot").groupRouterKey("vegetable").price(1250)
                .build());
    }

    @GetMapping("items")
    public ResponseEntity<List<ProductItemModel>> getGroupItemModels() {
        return ResponseEntity.ok(productItemModels);
    }

    @GetMapping("items/{routerKey}")
    public ResponseEntity<List<ProductItemModel>> getGroupItemModelsByRouterKey(
            @PathVariable String routerKey
    ) {
        return ResponseEntity.ok(productItemModels.stream()
                .filter(p -> p.getGroupRouterKey().equalsIgnoreCase(routerKey))
                .collect(Collectors.toList()));
    }
}
