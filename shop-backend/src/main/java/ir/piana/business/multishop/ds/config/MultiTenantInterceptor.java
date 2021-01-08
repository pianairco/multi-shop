package ir.piana.business.multishop.ds.config;

import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import ir.piana.business.multishop.ds.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MultiTenantInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	@Qualifier("dataSources")
	private Map<String, DataSource> dataSourceMap;
	private List<DataSourceEntity> sejamDataSources;
	private Map<String, DataSourceEntity> failedDataSources;
	private DataSourceService dataSourceService;

	public MultiTenantInterceptor(List<DataSourceEntity> sejamDataSources,
								  Map<String, DataSourceEntity> failedDataSources,
								  DataSourceService dataSourceService) {
		this.sejamDataSources = sejamDataSources;
		this.failedDataSources = failedDataSources;
		this.dataSourceService = dataSourceService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (dataSourceService.isLock()) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print("{\"error\":400, \"message\":\"System is in refresh state. please try after a wait.\"}");
			response.getWriter().flush();
			return false;
		}
		String host = request.getParameter("Host") != null
				&& request.getParameter("Host").startsWith("localhost") ?
				request.getParameter("Host") : request.getParameter("dsCode") != null ?
				request.getParameter("dsCode") : null;
		if (!request.getServletPath().startsWith("/api/auth/")) {
			TenantContext.setTenantId("support");
			request.setAttribute("tenantId", "support");
			request.setAttribute("host", host);
			return true;
		}
		if (host == null) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print("{\"error\":400, \"message\":\"Host or dsName not found in query parameters\"}");
			response.getWriter().flush();
			return false;
		}
		if (failedDataSources.containsKey(host)) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print("{\"error\":503, \"message\":\"dsName is valid but temporarily unavailable\"}");
			response.getWriter().flush();
			return false;
		}
		Optional<DataSourceEntity> sejamDataSourceEntity = sejamDataSources.stream()
				.filter(e -> e.getTenantId().equalsIgnoreCase(host)).findFirst();
		if (sejamDataSourceEntity.isPresent()) {
			if (!sejamDataSourceEntity.get().isActive()) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().print(
						"{\"error\":503, \"message\":\"dsName is valid but temporarily is deactivate\"}");
				response.getWriter().flush();
				return false;
			} else {
				TenantContext.setTenantId(sejamDataSourceEntity.get().getTenantId());
				request.setAttribute("tenantId", host);
				request.setAttribute("host", host);
				return true;
			}
		} else {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print("{\"error\":400, \"message\":\"dsName not found in database\"}");
			response.getWriter().flush();
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		TenantContext.clear();
	}
}
