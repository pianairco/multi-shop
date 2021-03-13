package ir.piana.business.multishop.module.site.service;

import ir.piana.business.multishop.common.data.component.SpecificSchemaQueryExecutorProvider;
import ir.piana.business.multishop.module.site.data.entity.PianaCategoryEntity;
import ir.piana.business.multishop.module.site.data.repository.PianaCategoryRepository;
import ir.piana.business.multishop.module.site.model.PianaCategoryModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@DependsOn("SpecificSchemaQueryExecutorProvider")
@Slf4j
public class SiteCategoryManagerService {
    private PianaCategoryModel categoryModel;
    private Map<Long, PianaCategoryModel> categoryModelMap;

    @Autowired
    @Qualifier("txManager")
    protected PlatformTransactionManager txManager;

    @Autowired
    private PianaCategoryRepository pianaCategoryRepository;

    @Transactional
//    @PostConstruct
    public void init() {
        if (categoryModel == null) {
            synchronized (this) {
                TransactionTemplate tmpl = new TransactionTemplate(txManager);
                tmpl.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        //PUT YOUR CALL TO SERVICE HERE
                        List<PianaCategoryEntity> all = pianaCategoryRepository.getAllOrderByIdAsc();
                        categoryModelMap = all.stream().collect(
                                Collectors.toMap(x -> x.getId(), x -> PianaCategoryModel.builder()
                                        .id(x.getId())
                                        .image(x.getImage())
                                        .title(x.getTitle())
                                        .children(new ArrayList<>()).build()));

                        for (PianaCategoryEntity entity : all) {
                            if (entity.getIdParent() != 0) {
                                PianaCategoryModel parent = categoryModelMap.get(entity.getIdParent());
                                parent.getChildren().add(categoryModelMap.get(entity.getId()));
                            }
                        }
                        if(all.isEmpty()) {
                            log.error("piana categories is empty!");
                        }

                        categoryModel = categoryModelMap.get(all.get(0).getId());
                        all.clear();
                    }
                });
            }
        }
    }

    public PianaCategoryModel getRoot() {
        init();
        return categoryModel;
    }

    public List<PianaCategoryModel> getChildren(long parentId) {
        init();
        return categoryModelMap.get(parentId).getChildren();
    }

    public PianaCategoryModel getById(long id) {
        init();
        return categoryModelMap.get(id);
    }
}
