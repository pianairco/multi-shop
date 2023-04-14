package ir.piana.business.multishop.common.data.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SITE_ROLE")
@SequenceGenerator(name = "masterSeq", initialValue = 1, allocationSize = 1, sequenceName = "MASTER_SEQ")
public class SiteRoleEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "masterSeq")
    private long id;

    @Column(name = "NAME")
    private String name;
}
