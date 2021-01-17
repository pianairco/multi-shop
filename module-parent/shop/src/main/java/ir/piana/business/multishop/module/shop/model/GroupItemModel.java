package ir.piana.business.multishop.module.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupItemModel {
    private long id;
    private String uuid;
    private String name;
    private String routerKey;
    private String description;
}
