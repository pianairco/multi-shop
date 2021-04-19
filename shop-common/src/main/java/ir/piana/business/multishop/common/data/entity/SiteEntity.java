package ir.piana.business.multishop.common.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private long agentId;

    @Column(name = "TENANT_ID")
    @JsonProperty("siteName")
    private String tenantId;

    @Column(name = "CATEGORY")
    private long category;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "INSTAGRAM_LINK")
    private String instagramLink;

    @Column(name = "WHATSAPP_LINK")
    private String whatsappLink;

    @Column(name = "FACEBOOK_LINK")
    private String facebookLink;

    @Column(name = "TEL_NUMBER")
    private String telNumber;

    @Column(name = "IS_ACTIVE")
    @JsonProperty("isActive")
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
