package ir.piana.business.multishop.module.site.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BAYA_CATEGORIES")
public class BayaCategoryEntity implements Serializable {
    @Id
    @JsonProperty("Id")
    private long id;

    @JsonProperty("Id_Parent")
    private long idParent;

    @JsonProperty("Title")
    private String title;

//    @JsonIgnore
    @JsonProperty("sub-categories")
    @Transient
    private List<BayaCategoryEntity> bayaCategoryEntities = new ArrayList<>();
}
