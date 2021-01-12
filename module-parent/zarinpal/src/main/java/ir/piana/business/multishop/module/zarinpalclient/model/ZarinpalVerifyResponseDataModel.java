package ir.piana.business.multishop.module.zarinpalclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZarinpalVerifyResponseDataModel {
    private Integer code;
    private String message;
    private String card_hash;
    private String card_pan;
    private Long ref_id;
    private String fee_type;
    private Integer fee;
}
