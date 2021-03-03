package ir.piana.business.multishop.module.site.service;

import ir.piana.business.multishop.module.site.data.entity.BayaCategoryEntity;
import ir.piana.business.multishop.module.site.data.repository.BayaCategoryRepository;
import ir.piana.business.multishop.module.site.model.BayaResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class BayaCategoryService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BayaCategoryRepository repository;

    @Transactional
    public void saveAllProduct() {
        long firstId = 0;

        BayaResponseModel<BayaCategoryEntity> productCategories = getProductCategories(0);
        circle(productCategories);
//        if(productCategories.isSuccess()) {
//            for(BayaCategoryEntity categoryModel : productCategories.getData()) {
//                getProductCategories(categoryModel.getId());
//            }
//        }

        System.out.println();
    }

    Set<String> set = new HashSet<>();
    @Transactional
    void circle(BayaResponseModel<BayaCategoryEntity> productCategories) {
        if(productCategories.isSuccess()) {
            for(BayaCategoryEntity categoryModel : productCategories.getData()) {
                if(set.contains(String.valueOf(categoryModel.getId())))
                    continue;
                else
                    set.add(String.valueOf(categoryModel.getId()));
                repository.save(categoryModel);
//                if(!repository.findById(categoryModel.getId()).isPresent())
                    circle(getProductCategories(categoryModel.getId()));
            }
        }
    }

    private BayaResponseModel<BayaCategoryEntity> getProductCategories(long id) {
        BayaResponseModel forObject = restTemplate.getForObject(
                "https://storeservice.baya.ir/Api/Product/GetProductCategoryForRootPage?Id_ProductCategory=" + id,
                BayaResponseModel.class);
        return forObject;
    }
}
