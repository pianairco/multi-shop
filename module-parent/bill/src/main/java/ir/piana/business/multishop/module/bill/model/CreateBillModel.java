package ir.piana.business.multishop.module.bill.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBillModel {
    private String referenceId;
    private BillType billType;
    private long price;
    private String description;
    private String mobile;
    private String email;
    private String userId;
}
