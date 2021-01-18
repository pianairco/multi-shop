package ir.piana.business.multishop.module.bill.service;

import ir.piana.business.multishop.module.bill.model.BillModel;
import ir.piana.business.multishop.module.bill.model.CreateBillModel;

public interface BillService {
    String createBill(CreateBillModel createBillModel);
    CreateBillModel retrieveBillModel(String billUuid);
    void paymentAdvice(String billNumber);
}
