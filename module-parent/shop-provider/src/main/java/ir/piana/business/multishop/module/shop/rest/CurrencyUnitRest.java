package ir.piana.business.multishop.module.shop.rest;

import ir.piana.business.multishop.module.shop.data.entity.CurrencyUnitEntity;
import ir.piana.business.multishop.module.shop.data.entity.MeasurementUnitEntity;
import ir.piana.business.multishop.module.shop.dto.CurrencyUnitDto;
import ir.piana.business.multishop.module.shop.dto.MeasurementUnitDto;
import ir.piana.business.multishop.module.shop.service.CurrencyUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/modules/shop/currency-unit")
public class CurrencyUnitRest {
    @Autowired
    private CurrencyUnitService currencyUnitService;
    @GetMapping("list")
    public ResponseEntity<List<CurrencyUnitEntity>> getAll() {
        return ResponseEntity.ok(currencyUnitService.getAll());
    }

    @GetMapping("list-for-select")
    public ResponseEntity<List<CurrencyUnitDto>> getAllForSelect() {

        return ResponseEntity.ok(currencyUnitService.getAllForSelect());
    }
}
