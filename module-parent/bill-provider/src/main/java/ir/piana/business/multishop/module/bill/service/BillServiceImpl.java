package ir.piana.business.multishop.module.bill.service;

import ir.piana.business.multishop.module.bill.model.BillModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("BillService")
public class BillServiceImpl implements BillService {
    @Override
    public String createBill(BillModel billModel) {
        UUID billNumber = UUID.randomUUID();
        return billNumber.toString();
    }

    @Override
    public BillModel retrieveBillModel(String billNumber) {
        return BillModel.builder()
                .billNumber(billNumber)
                .build();
    }

    @Override
    public void paymentAdvice(String billNumber) {

    }
}
