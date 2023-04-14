package ir.piana.business.multishop.common.data.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SITE_USER")
@SequenceGenerator(name = "masterSeq", initialValue = 1, allocationSize = 1, sequenceName = "MASTER_SEQ")
public class SiteUserEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "masterSeq")
    private long id;

    @Column(name = "AGENT_ID")
    private long agentId;

    @Column(name = "SITE_ID")
    private long siteId;
}
