package ir.piana.business.multishop.rest;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class AsyncTestServletContextListener implements ServletContextListener {
    ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(5);
    }

    public void contextInitialized(ServletContextEvent sce) {
        java.util.Queue<AsyncContext> jobQueue = new ConcurrentLinkedQueue<AsyncContext>();
        sce.getServletContext().setAttribute("slowWebServiceJobQueue", jobQueue);
        // pool size matching Web services capacity
        while(true)
        {
            if(!jobQueue.isEmpty())
            {
                final AsyncContext asyncContext = jobQueue.poll();
                executorService.execute(new Runnable(){
                    public void run() {
                        ServletRequest request = asyncContext.getRequest();
                        try {
                            asyncContext.getResponse().getWriter().write("hello async test");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
