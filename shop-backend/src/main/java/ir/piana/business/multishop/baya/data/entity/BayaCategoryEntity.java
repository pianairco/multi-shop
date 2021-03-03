package ir.piana.business.multishop.baya.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
@Table(name = "BAYA_CATEGORIES")
public class BayaCategoryEntity implements Serializable {
    @Id
    @JsonProperty("Id")
    private long id;

    @JsonProperty("Id_Parent")
    private long idParent;

    @JsonProperty("Title")
    private String title;
}
