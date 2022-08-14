package javaLanguage.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/*
 * - ReadWriteLock 维护了一对相关的锁，一个用于只读操作，另一个用于写入操作。
 *   只要没有 writer，读取锁可以由多个 reader 线程同时保持。写入锁是独占的。
-   ReadWriteLock 读取操作通常不会改变共享资源，但执行写入操作时，必须独占方式来获取锁。
*   对于读取操作占多数的数据结构。 ReadWriteLock 能提供比独占锁更高的并发性。
*   而对于只读的数据结构，其中包含的不变性可以完全不需要考虑加锁操作。
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        RWDemo rw = new RWDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                rw.set((int) (Math.random() * 101));
            }
        }, "Write:").start();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    rw.get();
                }
            }).start();
        }
    }


    static class RWDemo {
        private int number = 0;

        private ReadWriteLock lock = new ReentrantReadWriteLock();

        //读
        public void get() {
            lock.readLock().lock(); //上锁
            try {
                System.out.println(Thread.currentThread().getName() + " : " + number);
            } finally {
                lock.readLock().unlock(); //释放锁
            }
        }

        //写
        public void set(int number) {
            lock.writeLock().lock();
            try {

                this.number = number;
                System.out.println(Thread.currentThread().getName() + " : " + number);
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
}

