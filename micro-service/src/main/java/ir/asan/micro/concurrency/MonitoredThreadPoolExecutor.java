package ir.asan.micro.concurrency;

import java.util.concurrent.*;

public class MonitoredThreadPoolExecutor extends ThreadPoolExecutor {

    public MonitoredThreadPoolExecutor(
            int queueMaxSize1, int queueStrictMaxSize,
            int corePoolSize, int maximumPoolSize,
            long keepAliveTimeMillis,
            RejectedExecutionHandler handler) {

        super(corePoolSize, maximumPoolSize, keepAliveTimeMillis, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), handler);
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable task) {
        super.beforeExecute(thread, task);
    }

    @Override
    protected void afterExecute(Runnable task, Throwable throwable) {
        super.afterExecute(task, throwable);
        if (throwable == null && task instanceof Future<?> && ((Future<?>) task).isDone()) {
            try {
                ((Future<?>) task).get();
            } catch (CancellationException ce) {
                throwable = ce;
            } catch (ExecutionException ee) {
                throwable = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}