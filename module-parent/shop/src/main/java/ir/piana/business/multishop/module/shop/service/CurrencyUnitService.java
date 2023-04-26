package ir.piana.business.multishop.module.shop.service;

import ir.piana.business.multishop.module.shop.data.entity.CurrencyUnitEntity;
import ir.piana.business.multishop.module.shop.data.entity.MeasurementUnitEntity;
import ir.piana.business.multishop.module.shop.dto.CurrencyUnitDto;
import ir.piana.business.multishop.module.shop.dto.MeasurementUnitDto;

import java.util.List;

public interface CurrencyUnitService {
    List<CurrencyUnitEntity> getAll();
    List<CurrencyUnitDto> getAllForSelect();
}
