package ir.piana.business.multishop.zarinpalclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataModel {
    private Integer code;
    private String message;
    private String authority;
    private String fee_type;
    private Integer fee;
}
