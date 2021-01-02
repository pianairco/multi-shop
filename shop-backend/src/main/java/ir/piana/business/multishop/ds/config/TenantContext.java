package ir.piana.business.multishop.ds.config;

import java.util.concurrent.atomic.AtomicInteger;

public class TenantContext {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void setTenantId(String tenantId) {
        CONTEXT.set(tenantId);
        counter.incrementAndGet();
    }

    public static String getTenantId() {
        return CONTEXT.get();
    }

    public static int getCounter() {
        return counter.intValue();
    }

    public static void clear() {
        CONTEXT.remove();
        counter.decrementAndGet();
    }
}
