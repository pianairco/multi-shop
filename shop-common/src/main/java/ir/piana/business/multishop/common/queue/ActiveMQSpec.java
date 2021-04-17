package ir.piana.business.multishop.common.queue;

import org.apache.activemq.command.ActiveMQQueue;

import java.util.Map;

public class ActiveMQSpec {
    Map<String, ActiveMQQueue> queueMap;

    ActiveMQSpec(Map<String, ActiveMQQueue> queueMap) {
        this.queueMap = queueMap;
    }

    public ActiveMQQueue getQueue(String key) {
        return queueMap.get(key);
    }
}
