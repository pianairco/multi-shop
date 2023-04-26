package ir.piana.business.multishop.module.shop.service;

import ir.piana.business.multishop.module.shop.data.entity.MeasurementUnitEntity;
import ir.piana.business.multishop.module.shop.data.repository.MeasurementUnitRepository;
import ir.piana.business.multishop.module.shop.dto.MeasurementUnitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MeasurementUnitServiceImpl implements MeasurementUnitService {

    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;

    private List<MeasurementUnitEntity> all = null;
    private Map<Long, MeasurementUnitEntity> idToEntity = null;
    private List<MeasurementUnitDto> allForSelect = null;

    @PostConstruct
    public void init() {
        all = measurementUnitRepository.findAll();
        idToEntity = all.stream().collect(Collectors.toMap(MeasurementUnitEntity::getId, Function.identity()));

        allForSelect = all.stream()
                .map(m -> MeasurementUnitDto.builder()
                        .id(m.getId())
                        .englishName(m.getEnglishName())
                        .persianName(m.getPersianName())
                        .description(m.getPersianName() + (m.getParentId() != 0 ?
                                " (" + m.getRatio() + " * " + idToEntity.get(m.getParentId()).getPersianName() + ")" : ""))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<MeasurementUnitEntity> getAll() {
        return all;
    }

    @Override
    public List<MeasurementUnitDto> getAllForSelect() {
        return allForSelect;
    }
}
