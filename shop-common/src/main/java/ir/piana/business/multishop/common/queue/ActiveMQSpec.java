package ir.piana.business.multishop.common.queue;

import org.apache.activemq.command.ActiveMQQueue;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActiveMQSpec {
    private Map<String, ActiveMQQueue> queueMap;

    ActiveMQSpec() {
        this.queueMap = new LinkedHashMap<>();
    }

    void addQueue(String beanName, ActiveMQQueue activeMQQueue) {
        queueMap.put(beanName, activeMQQueue);
    }
    public ActiveMQQueue getQueue(String key) {
        return queueMap.get(key);
    }
}
