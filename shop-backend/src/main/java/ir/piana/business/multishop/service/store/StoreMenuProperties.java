package ir.piana.business.multishop.service.store;

import ir.piana.business.multishop.model.StoreMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("store-menu")
@Getter
@Setter
@NoArgsConstructor
public class StoreMenuProperties {
    private List<StoreMenu> groups;
}
