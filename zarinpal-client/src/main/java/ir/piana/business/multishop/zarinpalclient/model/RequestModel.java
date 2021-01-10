package ir.piana.business.multishop.zarinpalclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {
    private String merchant_id;
    private Integer amount;
    private String description;
    private String callback_url;
    private List<MetaDataModel> metadata;
    private String mobile;
    private String email;
}
