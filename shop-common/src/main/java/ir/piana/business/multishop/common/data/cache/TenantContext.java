package ir.piana.business.multishop.common.data.cache;

import ir.piana.business.multishop.common.exceptions.SiteRefreshException;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TenantContext {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
    private static AtomicInteger counter = new AtomicInteger(0);
    private static AtomicBoolean lock = new AtomicBoolean(false);


    static synchronized void lock() {
        lock.set(true);
    }

    static synchronized void unlock() {
        lock.set(false);
    }

    public static void setTenantId(String tenantId) {
        if(lock.get()) {
            throw new SiteRefreshException();
        }
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
