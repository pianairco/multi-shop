package ir.piana.business.multishop.baya;

import ir.piana.business.multishop.baya.data.entity.BayaCategoryEntity;
import ir.piana.business.multishop.baya.data.repository.BayaCategoryRepository;
import ir.piana.business.multishop.baya.model.BayaResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

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
        if(productCategories.isSuccess()) {
            for(BayaCategoryEntity categoryModel : productCategories.getData()) {
                getProductCategories(categoryModel.getId());
            }
        }

        System.out.println();
    }

    @Transactional
    void circle(BayaResponseModel<BayaCategoryEntity> productCategories) {
        if(productCategories.isSuccess()) {
            for(BayaCategoryEntity categoryModel : productCategories.getData()) {
                repository.save(categoryModel);
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
