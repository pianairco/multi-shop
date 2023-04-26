package ir.piana.business.multishop.module.shop.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementUnitDto {
    private long id;
    private String englishName;
    private String persianName;
    private String description;
}
