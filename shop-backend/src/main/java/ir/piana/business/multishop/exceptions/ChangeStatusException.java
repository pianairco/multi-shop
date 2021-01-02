package ir.piana.business.multishop.exceptions;

import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import lombok.Getter;

@Getter
public class ChangeStatusException extends RuntimeException {
    private DataSourceEntity dataSourceEntity;

    public ChangeStatusException(DataSourceEntity dataSourceEntity) {
        this.dataSourceEntity = dataSourceEntity;
    }
}
