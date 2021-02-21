package ir.piana.business.multishop.module.shop.data.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT")
@SequenceGenerator(name = "master_seq", initialValue = 1, sequenceName = "master_seq", allocationSize = 1)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
    @Column(name = "ID")
    private long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "MEASUREMENT")
    private long measurement;
    @Column(name = "MEASUREMENT_UNIT")
    private String measurementUnit;
    @Column(name = "PRICE")
    private long price;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "PERCENT")
    private long percent;
    @Column(name = "SITE_ID")
    private long siteId;
//    @ManyToOne(targetEntity = SiteEntity.class, fetch = FetchType.EAGER)
//    private SiteEntity siteEntity;
}
