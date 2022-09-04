package javaLanguage.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: guang
 * @Date: 2022/9/4
 * @Desc: juc_02_ConcurrentHashMap
 *
 * ● 各种并发容器，比如ConcurrentHashMap、CopyOnWriteArrayList。
 * ● 各种线程安全队列（Queue/Deque），如ArrayBlockingQueue、SynchronousQueue。
 * ● 各种有序容器的线程安全版本等。
 */
public class juc_02_ArrayBlockingQueueExample {

    /**
     * 抛出异常
     */
    public static void test1() {
        //队列的大小
        ArrayBlockingQueue queue = new ArrayBlockingQueue<>(3);
        System.out.println(queue.add("a"));
        System.out.println(queue.add("b"));
        System.out.println(queue.add("c"));
        //java.lang.IllegalStateException: Queue full
        //System.out.println(queue.add("d"));
        System.out.println("----------------------");
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        //java.util.NoSuchElementException
        System.out.println(queue.remove());
        //抛出异常

    }

    /**
     * 有返回值没有异常
     */
    public static void test2() {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(3);

        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));
        System.out.println(queue.offer("d"));       //offer 不抛出异常
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());   //null 不抛出异常
    }


    /**
     * 等待阻塞
     */
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(3);
        queue.put("a");
        queue.put("b");
        queue.put("c");
//        queue.put("c");  队列没有位置就会阻塞
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }


    public static void main(String[] args) {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>(); //同步队列
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " put 1");
                synchronousQueue.put("1");
                System.out.println(Thread.currentThread().getName() + " put 2");
                synchronousQueue.put("2");
                System.out.println(Thread.currentThread().getName() + " put 3");
                synchronousQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "=>" + synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "=>" + synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "=>" + synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
        }, "T2").start();
    }

}