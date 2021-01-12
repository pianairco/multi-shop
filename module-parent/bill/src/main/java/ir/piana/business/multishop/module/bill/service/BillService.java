package ir.piana.business.multishop.module.bill.service;

import ir.piana.business.multishop.module.bill.model.BillModel;

public interface BillService {
    BillModel getBillModel(String billNumber);
}
