package javaLanguage.thread;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: guang
 * @Date: 2022/9/3
 * @Desc: 创建线程池
 *
 *  * 工具类: Executors
 *  * ExecutorService newFixedThreadPool(): 创建固定大小的线程池;
 *  * ExecutorService newCachedThreadPool(): 缓存线程池,线程池中线程的数量不固定,可以根据需求自动更改数量;
 *  * ExecutorService newSingleThreadExecutor(): 创建单个线程池, 线程池中只有一个线程;
 *  * ScheduledExecutorService newScheduledThreadPool(): 创建固定大小的线程,可以延时或定时的执行任务;
 */
public class thread_04_CreateThreadPool {
    public static void main(String[] args) throws IOException {
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ThreadFactory threadFactory = new ThreadFactoryExample();
        RejectedExecutionHandler handler = new RejectedExecutionHandlerExample();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                //核心线程池大小
                corePoolSize,
                //最大的线程池大小
                maximumPoolSize,
                //存活时间
                keepAliveTime,
                //存活时间的时间单位
                unit,
                //阻塞队列)
                workQueue,
                //线程工厂,创建线程的,一般不动
                threadFactory,
                //拒绝策略
                handler
        );

        executor.prestartAllCoreThreads(); // 预启动所有核心线程

        for (int i = 1; i <= 10; i++) {
            RunnableExample task = new RunnableExample();
            executor.execute(task);
        }

        System.in.read(); //阻塞主线程
        
    }

    /**
     * ThreadFactory
     * 给线程命名，查看创建线程数
     * 给线程设置是否是后台运行
     * 设置线程优先级
     * 设置线程优先级
     * MIN_PRIORITY = 1  线程可以拥有的最低优先级
     * NORM_PRIORITY = 5 分配给线程的默认优先级
     * MAX_PRIORITY = 10 线程可以拥有的最大优先级
     *    t.setPriority(Thread.MIN_PRIORITY);
     *    t.setPriority(Thread.NORM_PRIORITY);
     *    t.setPriority(Thread.MAX_PRIORITY);
     */
    static class ThreadFactoryExample implements ThreadFactory {
        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "example-thread-" + mThreadNum.getAndIncrement());
            System.out.println("Create thread " + thread.getName());
            return thread;
        }
    }

    static class RejectedExecutionHandlerExample implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 记录日志
            System.err.println(r.toString() + " rejected");
        }
    }


    static class RunnableExample implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running!");
            try {
                Thread.sleep(3000); //让任务执行慢点
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}