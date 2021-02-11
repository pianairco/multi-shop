package ir.piana.business.multishop.cfg;

import ir.piana.business.multishop.common.data.cache.AppDataCache;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SiteResourceResolver extends PathResourceResolver {
    private AppDataCache appDataCache;

    public SiteResourceResolver(AppDataCache appDataCache) {
        this.appDataCache = appDataCache;
    }

    @Override
    protected Resource resolveResourceInternal(
            HttpServletRequest request,
            String requestPath,
            List<? extends Resource> locations,
            ResourceResolverChain chain) {
        String host = (String) request.getAttribute("tenant");
        if(host != null && host.equalsIgnoreCase(appDataCache.getDomain()))
            requestPath = "control-panel/".concat(requestPath);
        return super.resolveResourceInternal(request, requestPath, locations, chain);
    }

}
