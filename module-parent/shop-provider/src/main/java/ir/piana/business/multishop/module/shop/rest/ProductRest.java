package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.module.shop.model.ProductItemModel;
import ir.piana.business.multishop.module.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ShopService shopService;

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
}
