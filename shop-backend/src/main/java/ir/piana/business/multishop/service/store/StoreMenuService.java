package ir.piana.business.multishop.service.store;

import ir.piana.business.multishop.model.StoreMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("singleton")
public class StoreMenuService {
    @Autowired
    private StoreMenuProperties storeMenuMap;

    public List<StoreMenu> getStoreMenus() {
        return storeMenuMap.getGroups();
    }
}
