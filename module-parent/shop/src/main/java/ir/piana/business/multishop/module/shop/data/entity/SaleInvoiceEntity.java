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
@Table(name = "sale_invoice")
@SequenceGenerator(name = "master_seq", initialValue = 1, sequenceName = "master_seq", allocationSize = 1)
public class SaleInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
    @Column(name = "ID")
    private long id;
    @Column(name = "PRODUCT_ID")
    private long productId;
    @Column(name = "store_pool_ID")
    private long storePoolId;
    @Column(name = "SITE_ID")
    private long siteId;
    @Column(name = "seller_id")
    private long sellerId;
    @Column(name = "sell_date")
    private String sellDate;
    @Column(name = "sell_time")
    private Time sellTime;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "MEASUREMENT_UNIT")
    private long measurementUnit;
    @Column(name = "MEASUREMENT_UNIT_RATIO")
    private long measurementUnitRatio;
    @Column(name = "unit_price")
    private long unitPrice;
    @Column(name = "total_price")
    private long totalPrice;
}
