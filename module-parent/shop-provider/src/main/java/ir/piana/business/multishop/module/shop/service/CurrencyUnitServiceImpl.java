package ir.piana.business.multishop.module.shop.service;

import ir.piana.business.multishop.module.shop.data.entity.CurrencyUnitEntity;
import ir.piana.business.multishop.module.shop.data.entity.MeasurementUnitEntity;
import ir.piana.business.multishop.module.shop.data.repository.CurrencyUnitRepository;
import ir.piana.business.multishop.module.shop.dto.CurrencyUnitDto;
import ir.piana.business.multishop.module.shop.dto.MeasurementUnitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CurrencyUnitServiceImpl implements CurrencyUnitService {

    @Autowired
    private CurrencyUnitRepository currencyUnitRepository;

    private List<CurrencyUnitEntity> all = null;
    private List<CurrencyUnitDto> allForSelect = null;
    private Map<Long, CurrencyUnitEntity> idToEntity = null;

    @PostConstruct
    public void init() {
        all = currencyUnitRepository.findAll();
        idToEntity = all.stream().collect(Collectors.toMap(CurrencyUnitEntity::getId, Function.identity()));

        allForSelect = all.stream()
                .map(m -> CurrencyUnitDto.builder()
                        .id(m.getId())
                        .englishName(m.getEnglishName())
                        .persianName(m.getPersianName())
                        .description(m.getPersianName() + (m.getParentId() != 0 ?
                                " (" + m.getRatio() + " * " + idToEntity.get(m.getParentId()).getPersianName() + ")" : ""))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<CurrencyUnitEntity> getAll() {
        return all;
    }

    @Override
    public List<CurrencyUnitDto> getAllForSelect() {
        return allForSelect;
    }
}
