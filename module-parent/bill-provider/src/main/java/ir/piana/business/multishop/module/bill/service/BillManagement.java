package ir.piana.business.multishop.module.bill.service;

import ir.piana.business.multishop.module.bill.model.BillModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

//@Service("BillManagement")
public class BillManagement {


    public String createBill(BillModel billModel) {
        UUID billNumber = UUID.randomUUID();
        return billNumber.toString();
    }
}
