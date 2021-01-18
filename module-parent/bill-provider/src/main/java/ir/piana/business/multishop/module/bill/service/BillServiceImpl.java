package ir.piana.business.multishop.module.bill.service;

import ir.piana.business.multishop.module.bill.model.CreateBillModel;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service("BillService")
public class BillServiceImpl implements BillService {
    Map<String, CreateBillModel> billModelMap = new LinkedHashMap<>();

    @Override
    public String createBill(CreateBillModel createBillModel) {
        UUID billUuid = UUID.randomUUID();
        billModelMap.put(billUuid.toString(), createBillModel);
        return billUuid.toString();
    }

    @Override
    public CreateBillModel retrieveBillModel(String billUuid) {
        return billModelMap.get(billUuid);
    }

    @Override
    public void paymentAdvice(String billUuid) {

    }
}
