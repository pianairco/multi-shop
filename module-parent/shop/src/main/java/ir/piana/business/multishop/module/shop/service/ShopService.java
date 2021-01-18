package ir.piana.business.multishop.module.shop.service;

import ir.piana.business.multishop.module.shop.model.ProductInventory;
import ir.piana.business.multishop.module.shop.model.ProductItemModel;

import java.util.List;

public interface ShopService {
    List<ProductItemModel> getProductItemModels();
    List<ProductInventory> retrieveProductInventory(List<String> productUuids);
}
