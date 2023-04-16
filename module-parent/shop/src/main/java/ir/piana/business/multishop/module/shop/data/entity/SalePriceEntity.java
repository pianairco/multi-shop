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
@Table(name = "sale_price")
@SequenceGenerator(name = "master_seq", initialValue = 1, sequenceName = "master_seq", allocationSize = 1)
public class SalePriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
    @Column(name = "ID")
    private long id;
    @Column(name = "PRODUCT_ID")
    private long productId;
    @Column(name = "Store_pool_ID")
    private long storePoolId;
    @Column(name = "SITE_ID")
    private long siteId;
    @Column(name = "changer_id")
    private long changerId;
    @Column(name = "change_time")
    private Time changeTime;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "percentage")
    private String percentage;
}
