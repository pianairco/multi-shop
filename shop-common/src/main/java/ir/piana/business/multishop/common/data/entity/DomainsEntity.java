package ir.piana.business.multishop.common.data.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DOMAINS")
@SequenceGenerator(name = "masterSeq", initialValue = 1, allocationSize = 1, sequenceName = "MASTER_SEQ")
public class DomainsEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "masterSeq")
    private long id;

    @Column(name = "SITE_ID")
    private long siteId;

    @Column(name = "domain")
    private String domain;
}
