package ir.piana.business.multishop.module.bill.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillModel {
    private String billNumber;
    private BillType billType;
    private long amount;
    private String description;
    private String mobile;
    private String email;
    private String userNumber;
}
