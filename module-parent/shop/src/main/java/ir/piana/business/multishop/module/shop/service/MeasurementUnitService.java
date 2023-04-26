package ir.piana.business.multishop.module.shop.service;

import ir.piana.business.multishop.module.shop.data.entity.MeasurementUnitEntity;
import ir.piana.business.multishop.module.shop.dto.MeasurementUnitDto;

import java.util.List;

public interface MeasurementUnitService {
    List<MeasurementUnitEntity> getAll();
    List<MeasurementUnitDto> getAllForSelect();
}
