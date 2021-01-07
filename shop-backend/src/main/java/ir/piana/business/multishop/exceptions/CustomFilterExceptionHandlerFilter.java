package ir.piana.business.multishop.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import ir.piana.business.multishop.ds.config.SpecificSchemaQueryExecutor;
import ir.piana.business.multishop.ds.config.TenantContext;
import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomFilterExceptionHandlerFilter extends OncePerRequestFilter {
    private List<DataSourceEntity> multiShopDataSources;
    private Map<String, DataSourceEntity> failedDataSources;
    private Map<String, DataSource> dataSourceMap;

    @Autowired
    public CustomFilterExceptionHandlerFilter(
            List<DataSourceEntity> multiShopDataSources,
            Map<String, DataSourceEntity> failedDataSources,
            Map<String, DataSource> dataSourceMap) {
        this.multiShopDataSources = multiShopDataSources;
        this.failedDataSources = failedDataSources;
        this.dataSourceMap = dataSourceMap;
    }

    public void checkDatabaseConnection(Exception ex) {
        String tenantId = TenantContext.getTenantId();
        if(ex instanceof SQLException) {
            try {
                SpecificSchemaQueryExecutor executor = new SpecificSchemaQueryExecutor(
                        (HikariDataSource) dataSourceMap.get(tenantId));
                executor.queryInt("select 1 from dual");
            } catch (SQLException e) {
                failedDataSources.put(tenantId,
                        multiShopDataSources.stream().filter(
                                m -> m.getTenantId().equalsIgnoreCase(tenantId))
                                .findFirst().get());
                dataSourceMap.remove(tenantId);
            }
        }
    }

    public void tenantNotSpecifiedExceptionHandler(
            TenantNotSpecifiedException ex, HttpServletResponse response) throws IOException {
        checkDatabaseConnection(ex);
        ErrorModel errorModel = ErrorModel.builder()
                .errorCode(403)
                .descriptionEN("tenant is not specified!")
                .build();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(new ObjectMapper().writeValueAsString(errorModel));
        response.getWriter().flush();
//        return ResponseEntity.status(errorModel.getErrorCode())
//                .header("content-type", MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
//                .body(errorModel);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            if(e instanceof TenantNotSpecifiedException) {
                tenantNotSpecifiedExceptionHandler((TenantNotSpecifiedException)e, httpServletResponse);
            }
        }
    }
}
