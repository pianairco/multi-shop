package ir.piana.business.multishop.common.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.jms.Destination;
import javax.servlet.AsyncContext;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PianaJmsTemplate {
    protected final Map<String, AsyncContext> httpRequestExchangeMap = new LinkedHashMap<>();
    protected final Map<String, DeferredResultContext<ResponseEntity>> httpRequestExchangeMap2 = new LinkedHashMap<>();
    private JmsTemplate jmsTemplate;
    private GenericWebApplicationContext context;

    public PianaJmsTemplate(JmsTemplate jmsTemplate, GenericWebApplicationContext context) {
        this.jmsTemplate = jmsTemplate;
        this.context = context;
    }

    public String sendAsync(
            Destination destination,
            AsyncContext asyncContext)
            throws JmsException {
        String uuid = UUID.randomUUID().toString();
        httpRequestExchangeMap.put(uuid, asyncContext);
        this.jmsTemplate.convertAndSend(destination, uuid);
        return uuid;
    }

    public String sendAsync(
            String destinationBeanName,
            AsyncContext asyncContext)
            throws JmsException {
        String uuid = UUID.randomUUID().toString();
        httpRequestExchangeMap.put(uuid, asyncContext);
        this.jmsTemplate.convertAndSend(context.getBean(destinationBeanName, Destination.class), uuid);
        return uuid;
    }

    public String sendAsync(
            String destinationBeanName,
            DeferredResultContext deferredResultContext)
            throws JmsException {
        String uuid = UUID.randomUUID().toString();
        httpRequestExchangeMap2.put(uuid, deferredResultContext);
        this.jmsTemplate.convertAndSend(context.getBean(destinationBeanName, Destination.class), uuid);
        return uuid;
    }

    public AsyncContext removeByUuid(String uuid) {
        return httpRequestExchangeMap.remove(uuid);
    }

    public DeferredResultContext removeByUuid2(String uuid) {
        return httpRequestExchangeMap2.remove(uuid);
    }
}
