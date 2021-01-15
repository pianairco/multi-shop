package ir.piana.business.multishop.ds.config;

import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import ir.piana.business.multishop.ds.service.DataSourceService;
import ir.piana.business.multishop.exceptions.TenantNotSpecifiedException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Order
public class MultiTenantFilter extends OncePerRequestFilter {
    private List<DataSourceEntity> multiShopDataSources;
    private Map<String, DataSourceEntity> failedDataSources;
    private DataSourceService dataSourceService;

    public MultiTenantFilter(List<DataSourceEntity> multiShopDataSources,
                                  Map<String, DataSourceEntity> failedDataSources,
                                  DataSourceService dataSourceService) {
        this.multiShopDataSources = multiShopDataSources;
        this.failedDataSources = failedDataSources;
        this.dataSourceService = dataSourceService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        if (dataSourceService.isLock()) {
            throw new HTTPException(HttpStatus.BAD_REQUEST.value());
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.getWriter().print("{\"error\":400, \"message\":\"System is in refresh state. please try after a wait.\"}");
//            response.getWriter().flush();
//            return false;
        }
        String host = request.getHeader("Host") != null
                && !request.getHeader("Host").startsWith("localhost") ?
                request.getHeader("Host") : request.getHeader("dsCode") != null ?
                request.getHeader("dsCode") : null;
        if (request.getServletPath().startsWith("/login") || request.getServletPath().startsWith("/h2")) {
            TenantContext.setTenantId("support");
            request.setAttribute("tenantId", "support");
            request.setAttribute("host", host);
            filterChain.doFilter(request, response);
            return;
//            return true;
        }
        if (host == null) {
            throw new TenantNotSpecifiedException();
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.getWriter().print("{\"error\":400, \"message\":\"Host or dsName not found in query parameters\"}");
//            response.getWriter().flush();
//            return false;
        }
        if (failedDataSources.containsKey(host)) {
            throw new TenantNotSpecifiedException();
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.getWriter().print("{\"error\":503, \"message\":\"dsName is valid but temporarily unavailable\"}");
//            response.getWriter().flush();
//            return false;
        }
        Optional<DataSourceEntity> sejamDataSourceEntity = multiShopDataSources.stream()
                .filter(e -> e.getTenantId().equalsIgnoreCase(host)).findFirst();
        if (sejamDataSourceEntity.isPresent()) {
            if (!sejamDataSourceEntity.get().isActive()) {
                throw new TenantNotSpecifiedException();
//                response.setStatus(HttpStatus.BAD_REQUEST.value());
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                response.getWriter().print(
//                        "{\"error\":503, \"message\":\"dsName is valid but temporarily is deactivate\"}");
//                response.getWriter().flush();
//                return false;
            } else {
                TenantContext.setTenantId(sejamDataSourceEntity.get().getTenantId());
                request.setAttribute("tenantId", host);
                request.setAttribute("host", host);
                filterChain.doFilter(request, response);
                return;
//                return true;
            }
        } else {
            throw new TenantNotSpecifiedException();
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.getWriter().print("{\"error\":400, \"message\":\"dsName not found in database\"}");
//            response.getWriter().flush();
//            return false;
        }
    }

}
