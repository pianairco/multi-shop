package ir.piana.business.multishop.module.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartModel {
    private long id;
    private String uuid;
    private Map<String, CartItemModel> cartItemModelMap;
}
