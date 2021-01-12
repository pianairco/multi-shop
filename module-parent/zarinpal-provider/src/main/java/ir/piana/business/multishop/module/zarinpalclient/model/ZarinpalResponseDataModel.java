package ir.piana.business.multishop.module.zarinpalclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZarinpalResponseDataModel {
    private Integer code;
    private String message;
    private String authority;
    private String fee_type;
    private Integer fee;
}
