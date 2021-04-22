package ir.piana.business.multishop.module.site.service;

import ir.piana.business.multishop.module.site.data.entity.SiteInfoEntity;

import javax.servlet.http.HttpServletRequest;

public interface SiteInfoService {
    SiteInfoEntity getSiteInfoEntity(HttpServletRequest request);
}
