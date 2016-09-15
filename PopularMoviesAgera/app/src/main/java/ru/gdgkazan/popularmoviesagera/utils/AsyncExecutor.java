package ru.gdgkazan.popularmoviesagera.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncExecutor {

    private AsyncExecutor() {
    }

    public static final int POOL_SIZE = 2;

    public static final int MAX_POOL_SIZE = 4;

    public static final int TIMEOUT = 30;

    public static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            POOL_SIZE,
            MAX_POOL_SIZE,
            TIMEOUT,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());
}
