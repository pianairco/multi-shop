package ir.piana.business.multishop.ds.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DATASOURCES")
@SequenceGenerator(name = "uniqueSeq", initialValue = 1, allocationSize = 1, sequenceName = "UNIQUE_SEQUENCE")
public class DataSourceEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uniqueSeq")
    private long id;

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "DB_SCHEMA")
    private String schemaName;

    @Column(name = "DB_SCRIPT")
    private String scriptPath;

    @Column(name = "DB_DRIVER")
    private String driver;

    @Column(name = "DB_URL")
    private String url;

    @Column(name = "DB_USERNAME")
    private String username;

    @Column(name = "DB_PASSWORD")
    private String password;

    @Column(name = "MAX_PULL_SIZE")
    private int maxPullSize;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;
}
