package ir.piana.business.multishop.module.shop.data.entity;

import ir.piana.business.multishop.module.shop.data.convertor.LongToStringConverter;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT_CATEGORIZATION")
@SequenceGenerator(name = "master_seq", initialValue = 1, sequenceName = "master_seq", allocationSize = 1)
public class ProductCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
    @Column(name = "ID")
    private long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "ROUTER_LINK")
    private String routerLink;
    @Column(name = "ORDERS")
    private long orders;
    @Column(name = "SITE_ID")
    private long siteId;
    @Convert(converter = LongToStringConverter.class)
    @Column(name = "PIANA_CATEGORY_ID")
    private String pianaCategoryId;
//    @ManyToOne(targetEntity = SiteEntity.class, fetch = FetchType.EAGER)
//    private SiteEntity siteEntity;
}
