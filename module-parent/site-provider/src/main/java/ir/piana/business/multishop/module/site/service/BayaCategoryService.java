package ir.piana.business.multishop.module.site.service;

import ir.piana.business.multishop.common.dev.uploadrest.StorageProperties;
import ir.piana.business.multishop.module.site.data.entity.BayaCategoryEntity;
import ir.piana.business.multishop.module.site.data.repository.BayaCategoryRepository;
import ir.piana.business.multishop.module.site.model.BayaResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class BayaCategoryService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BayaCategoryRepository repository;

    private final Path rootLocation;

    StorageProperties storageProperties;

    @Autowired
    public BayaCategoryService(StorageProperties properties) {
        storageProperties = properties;
        this.rootLocation = Paths.get(properties.getLocation());
    }

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

    public void saveAllCategoryImages() {
        List<BayaCategoryEntity> all = repository.findAll();
        all.stream().map(c -> c.getId()).forEach(id -> {
            try {
                saveCategoryImage(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void saveCategoryImage(long id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(":authority", "s3.ir-thr-at1.arvanstorage.com");
        headers.set(":method", "GET");
        headers.set(":path", "/store/common/categories/" + id + ".jpg");
        headers.set("scheme", "https");
        headers.set("referer", "https://www.baya.ir/");
        byte[] imageBytes = restTemplate.getForObject(
//                "https://s3.ir-thr-at1.arvanstorage.com/store/common/categories/" + id + ".jpg",
                "https://s3.ir-thr-at1.arvanstorage.com/store/common/category-banner/" + id + ".jpg",
                byte[].class);

        String filePath = "/assets/baya/categories/banner/" + id + ".jpg";
//        String filePath = "/assets/baya/categories/" + id + ".jpg";
        Files.write(this.rootLocation.resolve("./" + filePath), imageBytes);
    }
}
