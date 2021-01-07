package ir.piana.business.multishop.cfg;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;

@Configuration
public class MultiShopServletContextInitializer implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        servletContext.addServlet("H2Console", WebServlet.class)
                .addMapping("/h2/console/*");
    }
}
