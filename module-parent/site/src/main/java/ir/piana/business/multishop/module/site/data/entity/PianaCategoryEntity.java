package ir.piana.business.multishop.module.site.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PIANA_CATEGORIES")
public class PianaCategoryEntity implements Serializable {
    @Id
    @JsonProperty("id")
    private long id;

    @Column("PARENT_ID")
    @JsonProperty("parentId")
    private long idParent;

    @Column("TITLE")
    @JsonProperty("title")
    private String title;

    @Column("IMAGE")
    @JsonProperty("image")
    private String title;
}
