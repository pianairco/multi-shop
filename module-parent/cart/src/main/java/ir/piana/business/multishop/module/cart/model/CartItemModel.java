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
    private String shopItemUuid;
    private float amount;
}
