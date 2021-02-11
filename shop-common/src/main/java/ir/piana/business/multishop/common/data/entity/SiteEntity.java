package ir.piana.business.multishop.common.data.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SITE")
@SequenceGenerator(name = "masterSeq", initialValue = 1, allocationSize = 1, sequenceName = "MASTER_SEQ")
public class SiteEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "masterSeq")
    private long id;

    @Column(name = "AGENT_ID")
    private String agentId;

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "CREATION_DATE")
    private String creationDate;

    @Column(name = "CREATION_TIME")
    private String creationTime;

    @Column(name = "MODIFICATION_DATE")
    private String modificationDate;

    @Column(name = "MODIFICATION_TIME")
    private String modificationTime;
}
