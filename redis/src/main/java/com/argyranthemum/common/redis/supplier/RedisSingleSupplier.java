package com.argyranthemum.common.redis.supplier;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class RedisSingleSupplier<T> {
    private long expiration;
    private TimeUnit timeUnit;
    private Supplier<T> supplier;

    public T get() {
        return this.supplier.get();
    }

    public static <T> RedisSingleSupplier<T> of(long expiration, TimeUnit timeUnit, Supplier<T> supplier) {
        return new RedisSingleSupplier(expiration, timeUnit, supplier);
    }

    public static <T> RedisSingleSupplier<T> of(long expiration, Supplier<T> supplier) {
        return new RedisSingleSupplier(expiration, TimeUnit.SECONDS, supplier);
    }

    public long getExpiration() {
        return this.expiration;
    }

    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    public Supplier<T> getSupplier() {
        return this.supplier;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void setSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public String toString() {
        return "RedisSingleSupplier(expiration=" + this.getExpiration() + ", timeUnit=" + this.getTimeUnit() + ", supplier=" + this.getSupplier() + ")";
    }

    public RedisSingleSupplier(long expiration, TimeUnit timeUnit, Supplier<T> supplier) {
        this.expiration = expiration;
        this.timeUnit = timeUnit;
        this.supplier = supplier;
    }
}