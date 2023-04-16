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
@Table(name = "store_pool")
@SequenceGenerator(name = "master_seq", initialValue = 1, sequenceName = "master_seq", allocationSize = 1)
public class StorePoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
    @Column(name = "ID")
    private long id;
    @Column(name = "PIANA_CATEGORY_ID")
    private long PianaCategoryId;
    @Column(name = "PRODUCT_ID")
    private long productId;
    @Column(name = "SITE_ID")
    private long siteId;
    @Column(name = "CATEGORY_ID")
    private long categoryId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "MEASUREMENT_UNIT")
    private String MeasurementUnit;
    @Column(name = "MEASUREMENT_UNIT_ratio")
    private String MeasurementUnitRatio;
    @Column(name = "inventory")
    private boolean inventory;
    @Column(name = "PRICE")
    private Time PRICE;
    @Column(name = "PERCENTAGE")
    private int PERCENTAGE;
}
