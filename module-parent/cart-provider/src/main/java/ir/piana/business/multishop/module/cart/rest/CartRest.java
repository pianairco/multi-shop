package ir.piana.business.multishop.module.cart.rest;

import ir.piana.business.multishop.common.model.ResponseModel;
import ir.piana.business.multishop.module.bill.model.BillType;
import ir.piana.business.multishop.module.bill.model.CreateBillModel;
import ir.piana.business.multishop.module.bill.service.BillService;
import ir.piana.business.multishop.module.cart.model.CartItemModel;
import ir.piana.business.multishop.module.cart.model.CartModel;
import ir.piana.business.multishop.module.shop.model.ProductInventory;
import ir.piana.business.multishop.module.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/cart")
public class CartRest {

    @Autowired
    private BillService billService;

    @Autowired
    private ShopService shopService;

    @PostMapping(path = "add-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<String>> addProduct(
            HttpServletRequest request,
            @RequestBody CartItemModel cartItemModel) {
        CartModel cartModel = (CartModel) request.getSession().getAttribute("cart-model");
        if(cartModel == null) {
            cartModel = CartModel.builder()
                    .uuid(UUID.randomUUID().toString())
                    .cartItemModelMap(new LinkedHashMap<>())
                    .build();
            cartModel.getCartItemModelMap().put(cartItemModel.getProductUuid(), cartItemModel);
            request.getSession().setAttribute("cart-model", cartModel);
        }
//        Optional<CartItemModel> first = cartModel.getCartItemModelMap()..stream()
//                .filter(p -> p.getProductUuid().equalsIgnoreCase(cartItemModel.getProductUuid()))
//                .findFirst();
//        if(first.isPresent()) {
//            CartItemModel existItemModel = first.get();
//            existItemModel.setAmount(existItemModel.getAmount() + cartItemModel.getAmount());
//        } else {
//            cartModel.getCartItemModels().add(cartItemModel);
//        }
        return ResponseEntity.ok(ResponseModel.<String>builder()
                .code(0).data("ok").build());
    }

    @GetMapping(path = "retrieve-current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<CartModel>> retrieveCurrent(
            HttpServletRequest request) {
        CartModel cartModel = (CartModel) request.getSession().getAttribute("cart-model");
        if(cartModel == null) {
            cartModel = CartModel.builder()
                    .uuid(UUID.randomUUID().toString())
                    .cartItemModelMap(new LinkedHashMap<>())
                    .build();
            request.getSession().setAttribute("cart-model", cartModel);
        }

        return ResponseEntity.ok(ResponseModel.<CartModel>builder()
                .code(0).data(cartModel).build());
    }

    @PostMapping(path = "init-bill")
    public ResponseEntity<ResponseModel<String>> initBill(HttpServletRequest request) {
        CartModel cartModel = (CartModel) request.getSession().getAttribute("cart-model");
        if(cartModel == null) {
            List<ProductInventory> productInventories = shopService.retrieveProductInventory(
                    cartModel.getCartItemModelMap().keySet().stream().collect(Collectors.toList()));

            String unavailable = productInventories.stream()
                    .filter(p -> p.getRemainAmount() < cartModel.getCartItemModelMap().get(p.getProductUuid()).getAmount())
                    .map(p -> String.valueOf(p.getProductUuid()))
                    .collect(Collectors.joining(","));
            if(!unavailable.isEmpty()) {
                return ResponseEntity.ok(ResponseModel.<String>builder()
                        .code(40).data(unavailable).build());
            }

            long totalPrice = productInventories.stream()
                    .mapToLong(p -> cartModel.getCartItemModelMap().get(p.getProductUuid()).getAmount() * p.getPrice())
                    .sum();

            billService.createBill(CreateBillModel.builder()
                    .billType(BillType.CART)
                    .price(totalPrice)
                    .referenceId(cartModel.getUuid())
                    .build());
        }

        return ResponseEntity.ok(ResponseModel.<String>builder()
                .code(0).data("").build());
    }
}
