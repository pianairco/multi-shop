package ir.piana.business.multishop.module.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemModel {
    private long id;
    private long productItemId;
    private String productUuid;
    private int amount;
    private int imageUrl;
    private int price;
}
