package ir.piana.business.multishop.module.bill.service;

import ir.piana.business.multishop.module.bill.model.BillModel;

public interface BillService {
    String createBill(BillModel billModel);
    BillModel retrieveBillModel(String billNumber);
    void paymentAdvice(String billNumber);
}
