package javaLanguage.threadpool.thread;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: ThreadPoll
 */
public interface ThreadPool {

    /***
     * 提交一个不需要返回值的异步任务给默认的线程池执行。
     * @param task
     * @return 异步任务执行的结果
     */
    public Future<?> submit(Runnable task);

    public Future<?> submit(Runnable task, String threadpoolName);

    public Future<?> submit(Runnable task, String threadpoolName, FailHandler<Runnable> failHandler);

    public <T> Future<T> submit(Callable<T> task);

    public <T> Future<T> submit(Callable<T> task, String threadpoolName);

    public <T> Future<T> submit(Callable<T> task, String threadpoolName, FailHandler<Callable<T>> failHandler);


    /**
     * 在线程池"default"中执行多个需要返回值的异步任务，并设置超时时间。
     *
     * @param tasks
     * @param timeout
     * @param timeoutUnit
     * @param <T>
     * @return 如果在指定的时间内，有任务没有执行完，将抛出CancellationException
     */
    public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks, long timeout, TimeUnit timeoutUnit);

    public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks, long timeout, TimeUnit timeoutUnit, String threadpoolName);


    /**
     * 查询指定名称的线程池是否存在
     * @param threadpoolName
     * @return 如果指定的线程池存在，返回true；否则，返回true
     */
    public boolean isExists(String threadpoolName);

    /**
     * 获取线程池的信息。如：线程池容量，队列容量。
     * @param threadpoolName
     * @return 线程池的信息
     */
    public ThreadPoolInfo getThreadPoolInfo(String threadpoolName);
}