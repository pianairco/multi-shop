package ir.piana.business.multishop.module.zarinpalclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillRequestModel {
    private long amount;
    private String description;
    private String mobile;
    private String email;
}
