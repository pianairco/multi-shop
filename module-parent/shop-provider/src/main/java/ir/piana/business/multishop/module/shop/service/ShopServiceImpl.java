package ir.piana.business.multishop.module.shop.service;

import ir.piana.business.multishop.module.shop.model.ProductInventory;
import ir.piana.business.multishop.module.shop.model.ProductItemModel;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("ShopService")
@Getter
public class ShopServiceImpl implements ShopService {
    private List<ProductItemModel> productItemModels = new ArrayList<>();

    public List<ProductItemModel> getProductItemModels() {
        return productItemModels;
    }

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

    @Override
    public List<ProductInventory> retrieveProductInventory(List<String> productUuids) {
        List<ProductInventory> productInventoryList = productItemModels.stream()
                .filter(p -> productUuids.contains(p.getUuid()))
                .map(p -> ProductInventory.builder().productUuid(p.getUuid()).price(p.getPrice()).build())
                .collect(Collectors.toList());
        return productInventoryList;
    }
}
