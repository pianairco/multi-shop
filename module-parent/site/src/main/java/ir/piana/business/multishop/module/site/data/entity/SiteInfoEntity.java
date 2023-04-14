package ir.piana.business.multishop.module.site.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SITE_INFO")
@SequenceGenerator(name = "masterSeq", initialValue = 1, allocationSize = 1, sequenceName = "MASTER_SEQ")
public class SiteInfoEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "masterSeq")
    private long id;
    @Column(name = "tenant_id")
    private String tenantId;
    @Column
    private String title;
    @Column
    private String description;
    @Column(name = "tip_title")
    private String tipTitle;
    @Column(name = "tip_description")
    private String tipDescription;
    @Column(name = "header_image")
    private String headerImage;
}
