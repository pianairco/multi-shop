package ir.piana.business.multishop.rest;

import ir.piana.business.multishop.common.queue.DispatcherQueue;
import ir.piana.business.multishop.common.queue.PianaJmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.jms.Queue;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@DependsOn("ActiveMQSpec")
@DispatcherQueue(beanName = "asyncTestRestQueue", queueName = "piana.http.dispatcher.queue")
public class AsyncTestRest {
    @Autowired
    private PianaJmsTemplate pianaJmsTemplate;

    /*@Autowired
    @Qualifier("asyncTestRestQueue")
    private Queue httpRequestQueue;*/

    @PostConstruct
    public void init() {
    }

    @GetMapping(path = "async/test")
    public void asyncTest(HttpServletRequest request, HttpServletResponse response) {
        AsyncContext asyncContext = request.startAsync(request, response);

        pianaJmsTemplate.sendAsync("asyncTestRestQueue", asyncContext);
//        pianaJmsTemplate.sendAsync(httpRequestQueue, asyncContext);
    }

    static int counter = 0;

    @JmsListener(destination = "piana.http.dispatcher.queue", concurrency = "5-10")
    public void listen(String uuid) throws IOException {
        System.out.println("in : " + counter++);
        try {
             Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AsyncContext asyncContext = pianaJmsTemplate.removeByUuid(uuid);
        asyncContext.getResponse().getWriter().write("hello async test");
        asyncContext.complete();
    }
}
