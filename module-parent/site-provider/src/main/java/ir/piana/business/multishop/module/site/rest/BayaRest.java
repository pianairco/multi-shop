package ir.piana.business.multishop.module.site.rest;

import ir.piana.business.multishop.common.model.ResponseModel;
import ir.piana.business.multishop.module.site.data.entity.BayaCategoryEntity;
import ir.piana.business.multishop.module.site.data.repository.BayaCategoryRepository;
import ir.piana.business.multishop.module.site.service.BayaCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/modules/site/baya")
public class BayaRest {

    @Autowired
    private BayaCategoryRepository repository;

    @Autowired
    private BayaCategoryService bayaCategoryService;

    @Transactional
    @GetMapping("fetch")
    public ResponseEntity<ResponseModel> fetch() {
        bayaCategoryService.saveAllProduct();
        return ResponseEntity.ok(ResponseModel.builder().code(1).build());
    }

    @Transactional
    @GetMapping()
    public ResponseEntity<List<BayaCategoryEntity>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }


    @Transactional
    @GetMapping("save-categories-images")
    public ResponseEntity<ResponseModel> getRepository() {
        bayaCategoryService.saveAllCategoryImages();
        return ResponseEntity.ok(ResponseModel.builder().code(0).build());
    }
}
