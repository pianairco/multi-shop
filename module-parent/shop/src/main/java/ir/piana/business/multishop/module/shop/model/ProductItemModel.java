package ir.piana.business.multishop.module.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemModel {
    private long id;
    private String uuid;
    private String name;
    private String groupRouterKey;
    private String nameKey;
    private String description;
    private long amount;
    private long price;
    private long salePrice;
    private long unitPrice;
    private long unitId;
    private long unitKey;
    private long discount;
    private boolean isActive;
    private float availableAmount;
}
