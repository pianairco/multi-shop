package ir.piana.business.multishop.module.site.initializr;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.piana.business.multishop.common.BaseInitializer;
import ir.piana.business.multishop.common.data.component.SpecificSchemaQueryExecutorProvider;
import ir.piana.business.multishop.common.data.util.SpecificSchemaQueryExecutor;
import ir.piana.business.multishop.module.site.data.entity.BayaCategoryEntity;
import ir.piana.business.multishop.module.site.data.entity.PianaCategoryEntity;
import ir.piana.business.multishop.module.site.service.BayaCategoryService;
import ir.piana.business.multishop.module.site.service.CategoryRangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Slf4j
@DependsOn({"SpecificSchemaQueryExecutorProvider", "CommonInitializer"})
public class SiteInitializer extends BaseInitializer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRangeService categoryRangeService;

    @Autowired
    private SpecificSchemaQueryExecutor executor;

    @Autowired
    public void setQueryExecutorProvider(SpecificSchemaQueryExecutorProvider executorProvider) {
        this.queryExecutorProvider = executorProvider;
    }

    @PostConstruct
    public void init() throws SQLException, IOException {
        initData();

        if(queryExecutorProvider.getSupportExecutor()
                .countOfResults("select * from PIANA_CATEGORIES") == 0) {
            savePianaCategories();
        }
        log.info("AuthModuleInitializer => initialized");
    }

    @Override
    public InputStream getSupportSql() {
        return SiteInitializer.class.getResourceAsStream("/site.sql");
    }

    @Override
    public InputStream getAllSchemaSql() {
        return null;
//        return AuthInitializer.class.getResourceAsStream("/site.sql");
    }

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

    protected PianaCategoryEntity savePianaCategory(BayaCategoryEntity bayaCategoryEntity, long parentId, int level, long siblingCount) {
        long id = categoryRangeService.createId(parentId, level, siblingCount);
        try {
            PianaCategoryEntity category = PianaCategoryEntity.builder()
                    .id(id)
                    .hexView(Long.toHexString(id))
                    .binaryView(Long.toBinaryString(id))
                    .idParent(parentId)
                    .title(bayaCategoryEntity.getTitle())
                    .image(String.valueOf(bayaCategoryEntity.getId()))
                    .build();

            boolean execute = executor.execute(String.format("insert into PIANA_CATEGORIES " +
                            "(id, HEX_VIEW, BINARY_VIEW, PARENT_ID, TITLE, IMAGE)" +
                            " values (%d, '%s', '%s', %d, '%s', '%s')",
                    category.getId(), category.getHexView(), category.getBinaryView(),
                    category.getIdParent(), category.getTitle(), category.getImage()));
//            pianaCategoryRepository.save(pianaCategoryEntity);
            return category;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
