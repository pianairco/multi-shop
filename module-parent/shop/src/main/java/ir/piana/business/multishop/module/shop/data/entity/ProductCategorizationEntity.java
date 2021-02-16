package ir.piana.business.multishop.module.shop.data.entity;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PRODUCT_CATEGORIZATION")
@SequenceGenerator(name = "master_seq", initialValue = 1, sequenceName = "master_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCategorizationEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
    @Column(name = "ID")
    private long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "ROUTER_LINK")
    private String routerLink;
    @Column(name = "ORDERS")
    private String orders;
    @Column(name = "SITE_ID")
    private long siteId;
//    @ManyToOne(targetEntity = SiteEntity.class, fetch = FetchType.EAGER)
//    private SiteEntity siteEntity;
}
