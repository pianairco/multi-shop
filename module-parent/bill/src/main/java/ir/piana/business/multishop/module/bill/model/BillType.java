package ir.piana.business.multishop.module.bill.model;

import lombok.Getter;

@Getter
public enum BillType {
    UNKNOWN(0, "UNKNOWN"),
    CART(1, "CART");

    private int typeId;
    private String typeName;

    BillType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public static BillType byTypeId(int typeId) {
        for (BillType billType: BillType.values()) {
            if(billType.typeId == typeId)
                return billType;
        }
        return BillType.UNKNOWN;
    }

    public static BillType byTypeName(String typeName) {
        for (BillType billType: BillType.values()) {
            if(billType.typeName.equalsIgnoreCase(typeName))
                return billType;
        }
        return BillType.UNKNOWN;
    }
}
