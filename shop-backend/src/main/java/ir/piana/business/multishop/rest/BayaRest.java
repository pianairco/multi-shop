package ir.piana.business.multishop.rest;

import ir.piana.business.multishop.baya.BayaCategoryService;
import ir.piana.business.multishop.common.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/baya")
public class BayaRest {

    @Autowired
    private BayaCategoryService bayaCategoryService;

    @Transactional
    @GetMapping("fetch")
    public ResponseEntity<ResponseModel> fetch() {
        bayaCategoryService.saveAllProduct();
        return ResponseEntity.ok(ResponseModel.builder().code(1).build());
    }
}
