package ir.piana.business.multishop.module.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel {
    private long id;
    private String uuid;
    private String name;
    private String nameKey;
    private String description;
    private float amount;
    private long price;
    private long salePrice;
    private long unitPrice;
    private long unitId;
    private long unitKey;
    private long discount;
    private boolean isActive;
    private float availableAmount;
}
