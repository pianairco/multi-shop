package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.module.shop.data.entity.MeasurementUnitEntity;
import ir.piana.business.multishop.module.shop.dto.MeasurementUnitDto;
import ir.piana.business.multishop.module.shop.service.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/modules/shop/measurement-unit")
public class MeasurementUnitRest {
    @Autowired
    private MeasurementUnitService measurementUnitService;
    @GetMapping("list")
    public ResponseEntity<List<MeasurementUnitEntity>> getAll() {
        return ResponseEntity.ok(measurementUnitService.getAll());
    }

    @GetMapping("list-for-select")
    public ResponseEntity<List<MeasurementUnitDto>> getAllForSelect() {

        return ResponseEntity.ok(measurementUnitService.getAllForSelect());
    }
}
