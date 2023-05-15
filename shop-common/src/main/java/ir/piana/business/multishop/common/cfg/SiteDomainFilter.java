package ir.piana.business.multishop.common.cfg;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class SiteDomainFilter extends OncePerRequestFilter {
    @Autowired
    private SiteRepository siteRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String domain = (String) request.getAttribute("host");
        if(!CommonUtils.isNull(domain)) {
//            String tenantId = siteRepository.getTenantId(domain);
            //ToDo should be get from ram
            SiteEntity byTenantId = siteRepository.findByDomain(domain).orElseGet(() -> null);
            request.setAttribute("site", byTenantId);
            filterChain.doFilter(request, response);
        }
    }
}
