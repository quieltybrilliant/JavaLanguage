package javaLanguage.juc;


import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * corePoolSize:
 *      指定了线程池中的线程数量，它的数量决定了添加的任务是开辟新的线程去执行，还是放到workQueue任务队列中去；
 * maximumPoolSize:
 *      指定了线程池中的最大线程数量，这个参数会根据你使用的workQueue任务队列的类型，决定线程池会开辟的最大线程数量；
 * keepAliveTime:
 *      当线程池中空闲线程数量超过corePoolSize时，多余的线程会在多长时间内被销毁；
 * unit:
 *      keepAliveTime的单位workQueue:任务队列，被添加到线程池中，但尚未被执行的任务；它一般分为直接提交队列、有界任务队列、无界任务队列、优先任务队列几种；
 * threadFactory:
 *      线程工厂，用于创建线程，一般用默认即可
 *  handler:
 *      拒绝策略；当任务太多来不及处理时，如何拒绝任务；
 *
 *
 *  线程池的体系结构
 * java.util.concurrent.Executor: 负责线程的使用和调度的根接口;
 * ExecutorService: 子接口,线程池的主要接口;
 * ThreadPoolExecutor: 线程池的实现类;
 * ScheduledExecutorService: 子接口,负责线程的调度;
 * ScheduledThreadPoolExecutor: 继承了线程池的实现类,实现了负责线程调度的子接口;
 *
 * 工具类: Executors
 * ExecutorService newFixedThreadPool(): 创建固定大小的线程池;
 * ExecutorService newCachedThreadPool(): 缓存线程池,线程池中线程的数量不固定,可以根据需求自动更改数量;
 * ExecutorService newSingleThreadExecutor(): 创建单个线程池, 线程池中只有一个线程;
 * ScheduledExecutorService newScheduledThreadPool(): 创建固定大小的线程,可以延时或定时的执行任务;
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) throws InterruptedException, IOException {
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ThreadFactory threadFactory = new DemoTreadFactory();
        RejectedExecutionHandler handler = new RejectedExecutionHandlerDemo();

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
            RunnableDemo task = new RunnableDemo(String.valueOf(i));
            executor.execute(task);
        }

        System.in.read(); //阻塞主线程
    }

    //线程工厂
    static class DemoTreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-java.thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    //拒绝策略
    static class RejectedExecutionHandlerDemo implements RejectedExecutionHandler {

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println(r.toString() + " rejected");
//          System.out.println("completedTaskCount: " + e.getCompletedTaskCount());
        }
    }

    static class RunnableDemo implements Runnable {
        private String name;

        public RunnableDemo(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.toString() + " is running!");
                Thread.sleep(3000); //让任务执行慢点
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }
    }
}
