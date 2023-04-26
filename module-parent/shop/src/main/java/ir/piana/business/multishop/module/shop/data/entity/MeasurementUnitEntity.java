package ir.piana.business.multishop.module.shop.data.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MEASUREMENT_UNIT")
@SequenceGenerator(name = "master_seq", initialValue = 1, sequenceName = "master_seq", allocationSize = 1)
public class MeasurementUnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
    @Column(name = "ID")
    private long id;
    @Column(name = "parent_ID")
    private long parentId;
    @Column(name = "is_primary")
    private boolean isPrimary;
    @Column(name = "english_name")
    private String englishName;
    @Column(name = "persian_name")
    private String persianName;
    @Column(name = "ratio")
    private String ratio;
}
