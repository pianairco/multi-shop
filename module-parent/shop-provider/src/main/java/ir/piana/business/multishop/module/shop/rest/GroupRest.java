package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.module.shop.model.GroupItemModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/shop/group")
public class GroupRest {

    List<GroupItemModel> groupItemModels = new ArrayList<>();

    @PostConstruct
    public void init() {
        groupItemModels.add(GroupItemModel.builder()
                .id(1)
                .name("میوه")
                .routerKey("fruit")
                .build());
        groupItemModels.add(GroupItemModel.builder()
                .id(2)
                .name("سبزیجات")
                .routerKey("vegetable")
                .build());
        groupItemModels.add(GroupItemModel.builder()
                .id(3)
                .name("صیفیجات")
                .routerKey("cucurbits")
                .build());
    }

    @GetMapping("items")
    public ResponseEntity<List<GroupItemModel>> getGroupItemModels() {
        return ResponseEntity.ok(groupItemModels);
    }
}
