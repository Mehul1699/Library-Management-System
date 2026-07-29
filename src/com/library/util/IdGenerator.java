package com.library.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private static final IdGenerator INSTANCE;

    private AtomicInteger branchIdGenerator;
    private AtomicInteger bookIdGenerator;
    private AtomicInteger patronIdGenerator;

    static {
        System.out.println("Initializing Id Generator");
        INSTANCE = new IdGenerator();
    }

    public static IdGenerator idGenerator() {
        return INSTANCE;
    }

    private IdGenerator() {
        branchIdGenerator = new AtomicInteger(0);
        bookIdGenerator = new AtomicInteger(100);
        patronIdGenerator = new AtomicInteger(0);
    }

    public int nextBranchId() {
        return branchIdGenerator.incrementAndGet();
    }

    public int nextPatronId() {
        return patronIdGenerator.incrementAndGet();
    }

    public int nextBookId() {
        return bookIdGenerator.incrementAndGet();
    }

}