package ir.piana.business.multishop.module.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartModel {
    private long id;
    private String uuid;
    private List<CartItemModel> cartItemModels;
}
