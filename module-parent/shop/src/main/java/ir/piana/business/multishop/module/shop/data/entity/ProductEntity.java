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
@Table(name = "PRODUCT")
@SequenceGenerator(name = "master_seq", initialValue = 1, sequenceName = "master_seq", allocationSize = 1)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "master_seq")
    @Column(name = "ID")
    private long id;
    @Column(name = "registrar_site_ID")
    private long registrarSiteId;
    @Column(name = "PIANA_CATEGORY_ID")
    private long pianaCategoryId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "is_confirmed")
    private boolean confirmed;
    @Column(name = "register_time")
    private Time registerTime;
}
