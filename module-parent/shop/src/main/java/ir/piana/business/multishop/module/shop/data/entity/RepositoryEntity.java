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
@Table(name = "repository")
@SequenceGenerator(name = "master_seq", initialValue = 1, sequenceName = "master_seq", allocationSize = 1)
public class RepositoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
    @Column(name = "ID")
    private long id;
    @Column(name = "PRODUCT_ID")
    private long productId;
    @Column(name = "SITE_ID")
    private long siteId;
    @Column(name = "PIANA_CATEGORY_ID")
    private long PianaCategoryId;
    @Column(name = "CATEGORY_ID")
    private long categoryId;
    @Column(name = "MEASUREMENT_UNIT_id")
    private long measurementUnitId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "amount")
    private boolean amount;
}
