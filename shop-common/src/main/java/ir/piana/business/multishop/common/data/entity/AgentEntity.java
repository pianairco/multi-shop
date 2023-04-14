package ir.piana.business.multishop.common.data.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AGENT")
@SequenceGenerator(name = "masterSeq", initialValue = 1, allocationSize = 1, sequenceName = "MASTER_SEQ")
public class AgentEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "masterSeq")
    private long id;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "SITE_ID")
    private long siteId;
}
