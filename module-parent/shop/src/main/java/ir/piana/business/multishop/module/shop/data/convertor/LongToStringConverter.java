package ir.piana.business.multishop.module.shop.data.convertor;

import javax.persistence.AttributeConverter;

public class LongToStringConverter implements AttributeConverter<String, Long> {
    @Override
    public Long convertToDatabaseColumn(String s) {
        return Long.parseLong(s);
    }

    @Override
    public String convertToEntityAttribute(Long aLong) {
        return String.valueOf(aLong);
    }
}
