package ir.piana.business.multishop.module.site.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.piana.business.multishop.common.dev.uploadrest.StorageProperties;
import ir.piana.business.multishop.module.site.data.entity.BayaCategoryEntity;
import ir.piana.business.multishop.module.site.data.entity.PianaCategoryEntity;
import ir.piana.business.multishop.module.site.data.repository.BayaCategoryRepository;
import ir.piana.business.multishop.module.site.data.repository.PianaCategoryRepository;
import ir.piana.business.multishop.module.site.model.BayaResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
@Profile("production")
public class BayaCategoryService {
    @Autowired
    private CategoryRangeService categoryRangeService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BayaCategoryRepository repository;

    @Autowired
    private PianaCategoryRepository pianaCategoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final Path rootLocation;

    StorageProperties storageProperties;

    @Autowired
    public BayaCategoryService(StorageProperties properties) {
        storageProperties = properties;
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @PostConstruct
    public void init() throws IOException {
//        InputStream resourceAsStream = BayaCategoryService.class.getResourceAsStream("/site-categories.json");
//        BayaCategoryEntity[] list = objectMapper.readValue(resourceAsStream, BayaCategoryEntity[].class);
//
//        Map<Long, BayaCategoryEntity> map = new LinkedHashMap<>();
//        map.put(0l, BayaCategoryEntity.builder().id(0).idParent(0).title("parent")
//                .bayaCategoryEntities(new ArrayList<>()).build());
//        for(BayaCategoryEntity e : list) {
//            if(!map.containsKey(e.getId())) {
//                map.put(e.getId(), e);
//                if(map.containsKey(e.getIdParent())) {
//                    map.get(e.getIdParent()).getBayaCategoryEntities().add(e);
//                }
//            }
//        }
//
//        proccess(map.get(0l), 0, 0, 1);


//        String s = objectMapper.writeValueAsString(map.get(0l));
//        System.out.println(map);
    }

    @Transactional
    public void savePianaCategories() throws IOException {
        InputStream resourceAsStream = BayaCategoryService.class.getResourceAsStream("/site-categories.json");
        BayaCategoryEntity[] list = objectMapper.readValue(resourceAsStream, BayaCategoryEntity[].class);

        Map<Long, BayaCategoryEntity> map = new LinkedHashMap<>();
        map.put(0l, BayaCategoryEntity.builder().id(0).idParent(0).title("parent")
                .bayaCategoryEntities(new ArrayList<>()).build());
        for(BayaCategoryEntity e : list) {
            if(!map.containsKey(e.getId())) {
                map.put(e.getId(), e);
                if(map.containsKey(e.getIdParent())) {
                    map.get(e.getIdParent()).getBayaCategoryEntities().add(e);
                }
            }
        }

        proccess(map.get(0l), 0, 0, 1);
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

    @Transactional
    protected void proccess(BayaCategoryEntity bayaCategoryEntity, long parentId, int level, long siblingCount) {
        PianaCategoryEntity pianaCategoryEntity = savePianaCategory(
                bayaCategoryEntity, parentId, level, siblingCount);
        if(bayaCategoryEntity.getBayaCategoryEntities() != null &&
                !bayaCategoryEntity.getBayaCategoryEntities().isEmpty()) {
            long childSiblingCount = 0;
            for (BayaCategoryEntity child : bayaCategoryEntity.getBayaCategoryEntities()) {
                proccess(child, pianaCategoryEntity.getId(), level + 1, ++childSiblingCount);
            }
        }
    }

    @Transactional
    protected PianaCategoryEntity savePianaCategory(BayaCategoryEntity bayaCategoryEntity, long parentId, int level, long siblingCount) {
        long id = categoryRangeService.createId(parentId, level, siblingCount);
        try {
            PianaCategoryEntity pianaCategoryEntity = PianaCategoryEntity.builder()
                    .id(id)
                    .hexView(Long.toHexString(id))
                    .binaryView(Long.toBinaryString(id))
                    .idParent(parentId)
                    .title(bayaCategoryEntity.getTitle())
                    .image(String.valueOf(bayaCategoryEntity.getId()))
                    .build();
            pianaCategoryRepository.save(pianaCategoryEntity);
            return pianaCategoryEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
