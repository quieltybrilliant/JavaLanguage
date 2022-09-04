package javaLanguage.juc;

import java.util.concurrent.CountDownLatch;


/**
 * CountDownLatch 是一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * 闭锁可以延迟线程的进度直到其到达终止状态，闭锁可以用来确保某些活动直到其他活动都完成才继续执行：
 *
 * 1. 确保某个计算在其需要的所有资源都被初始化之后才继续执行;
 * 2. 确保某个服务在其依赖的所有其他服务都已经启动之后才启动;
 * 3. 等待直到某个操作所有参与者都准备就绪再继续执行。
 *
 * 使用案例：
 * ①某一线程在开始运行前等待n个线程执行完毕。
 * 将CountDownLatch的计数器初始化为n ：new CountDownLatch(n)，
 * 每当一个任务线程执行完毕， 就将计数器减1 countdownlatch.countDown()，
 * 当计数器的值变为0时， 在CountDownLatch上 await() 的线程就会被唤醒。
 * 一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
 *
 * ②实现多个线程开始执行任务的最大并行性。
 * 注意是并行性，不是并发，强调的是多个线程在某一时刻同时开始执行。
 * 类似于赛跑，将多个线程放到起点，等待发令枪响，然后同时开跑。
 * 做法是初始化一个共享的 CountDownLatch 对象，将其计数器初始化为 1 ：new CountDownLatch(1)，
 * 多个线程在开始执行任务前首先 coundownlatch.await()，当主线程调用 countDown() 时，
 * 计数器变为0，多个线程同时被唤醒。
 */
public class juc_01_CountDownLatchExample {

    public static void main(String[] agrs) {
        CountDownLatch latch = new CountDownLatch(5); // 5表示有5个线程
        LatchDemo ld = new LatchDemo(latch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(ld).start();
        }

        try {
            latch.await(); // 等待，直到latch变为0时才运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("耗费时间为：" + (end - start) + "ms");
    }

    static class LatchDemo implements Runnable {
        private CountDownLatch latch;

        public LatchDemo(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i <= 400; i++) {
                    if (i % 100 == 0) {
                        System.out.println(Thread.currentThread().getName()+
                                "----"+ this.latch +"----" + i + "------");
                    }
                }
            } finally { // 必须执行的操作
                latch.countDown();
            }
        }
    }
}


