package ir.piana.business.multishop.module.cart.rest;

import ir.piana.business.multishop.common.model.ResponseModel;
import ir.piana.business.multishop.module.cart.model.CartItemModel;
import ir.piana.business.multishop.module.cart.model.CartModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/cart")
public class CartRest {
    @PostMapping(path = "add-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<String>> addProduct(
            HttpServletRequest request,
            @RequestBody CartItemModel cartItemModel) {
        CartModel cartModel = (CartModel) request.getSession().getAttribute("cart-model");
        if(cartModel == null) {
            cartModel = CartModel.builder()
                    .uuid(UUID.randomUUID().toString())
                    .cartItemModels(new ArrayList<>())
                    .build();
            request.getSession().setAttribute("cart-model", cartModel);
        }
        Optional<CartItemModel> first = cartModel.getCartItemModels().stream()
                .filter(p -> p.getProductUuid().equalsIgnoreCase(cartItemModel.getProductUuid()))
                .findFirst();
        if(first.isPresent()) {
            CartItemModel existItemModel = first.get();
            existItemModel.setAmount(existItemModel.getAmount() + cartItemModel.getAmount());
        } else {
            cartModel.getCartItemModels().add(cartItemModel);
        }
        return ResponseEntity.ok(ResponseModel.<String>builder()
                .code(0).data("ok").build());
    }
}
