package ir.piana.business.multishop.common.exceptions;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import lombok.Getter;

@Getter
public class ChangeStatusException extends RuntimeException {
    private SiteEntity siteEntity;

    public ChangeStatusException(SiteEntity siteEntity) {
        this.siteEntity = siteEntity;
    }
}
