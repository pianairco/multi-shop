package ir.piana.business.multishop.common.data.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SITE_AGENT_ROLE")
@SequenceGenerator(name = "masterSeq", initialValue = 1, allocationSize = 1, sequenceName = "MASTER_SEQ")
public class SiteAgentRoleEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "masterSeq")
    private long id;

    @Column(name = "AGENT_ID")
    private long agentId;

    @Column(name = "SITE_ROLE_ID")
    private long siteRoleId;
}
