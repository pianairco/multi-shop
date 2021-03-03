package ir.piana.business.multishop.module.site.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.piana.business.multishop.module.site.data.entity.BayaCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BayaResponseModel<T> {
    @JsonProperty("Success")
    private boolean success;

    @JsonProperty("Errors")
    private List<Map> errors;

    @JsonProperty("Data")
    private List<BayaCategoryEntity> data;

    @JsonProperty("Messages")
    private List<Map> messages;
}
