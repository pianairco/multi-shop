package ir.piana.business.multishop.ds.config;

import ir.piana.business.multishop.ds.entity.DataSourceEntity;
import ir.piana.business.multishop.ds.service.DataSourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MultiTenantInterceptor extends HandlerInterceptorAdapter {

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
		String tenantId = request.getParameter("dsName") != null ?
				request.getParameter("dsName") : (request.getParameter("dsCode") != null ?
				request.getParameter("dsCode") : request.getParameter("tenantId"));
		if (!request.getServletPath().startsWith("/api/v2/fund/sejam")) {
			TenantContext.setTenantId("00000");
			request.setAttribute("tenantId", tenantId);
			return true;
		}
		if (tenantId == null) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print("{\"error\":400, \"message\":\"dsName not found in query parameters\"}");
			response.getWriter().flush();
			return false;
		}
		if (failedDataSources.containsKey(tenantId)) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().print("{\"error\":503, \"message\":\"dsName is valid but temporarily unavailable\"}");
			response.getWriter().flush();
			return false;
		}
		Optional<DataSourceEntity> sejamDataSourceEntity = sejamDataSources.stream()
				.filter(e -> e.getTenantId().equalsIgnoreCase(tenantId)).findFirst();
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
				request.setAttribute("tenantId", tenantId);
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
