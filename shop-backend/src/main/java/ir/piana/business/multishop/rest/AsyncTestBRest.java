package ir.piana.business.multishop.rest;

import ir.piana.business.multishop.common.queue.DeferredResultContext;
import ir.piana.business.multishop.common.queue.DispatcherQueue;
import ir.piana.business.multishop.common.queue.PianaJmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@DependsOn("ActiveMQSpec")
@DispatcherQueue(beanName = "asyncTestBRestQueue", queueName = "piana.http.dispatcher.queue.b")
public class AsyncTestBRest {
    @Autowired
    private PianaJmsTemplate pianaJmsTemplate;

    /*@Autowired
    @Qualifier("asyncTestRestQueue")
    private Queue httpRequestQueue;*/

    @PostConstruct
    public void init() {
    }

    @GetMapping(path = "async/test2")
    public DeferredResult<ResponseEntity> asyncTest(HttpServletRequest request, HttpServletResponse response) {
        DeferredResult deferredResult = new DeferredResult();
        pianaJmsTemplate.sendAsync("asyncTestBRestQueue",
                DeferredResultContext.builder().request(request).deferredResult(deferredResult).build());
//        pianaJmsTemplate.sendAsync("asyncTestBRestQueue", deferredResult);

        return deferredResult;
//        pianaJmsTemplate.sendAsync("asyncTestRestQueue", asyncContext);
//        pianaJmsTemplate.sendAsync(httpRequestQueue, asyncContext);
    }

    static int counter = 0;

    @JmsListener(destination = "piana.http.dispatcher.queue.b", concurrency = "5-10")
    public void listen(String uuid) throws IOException {
        System.out.println("in : " + counter++);
        try {
             Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DeferredResultContext deferredResult = pianaJmsTemplate.removeByUuid2(uuid);
        deferredResult.getDeferredResult().setResult(ResponseEntity.ok().body("hello async test"));
    }
}
