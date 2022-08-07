package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyExample {

    public static void main(String[] args) {
        Thread t1 = new Thread(new RunTt2());
        Thread t2 = new Thread(new RunTt2());

        t1.start();
        t2.start();
        t2.interrupt();
        /**
         * Thread-0 running
         * Thread-1 interrupted
         * Exception in thread "Thread-1" java.lang.IllegalMonitorStateException
         * 	at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
         * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
         * 	at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
         * 	at lock.RunTt2.run(LockInterruptiblyExample.java:33)
         * 	at java.lang.Thread.run(Thread.java:748)
         * Thread-0 finished
         */
    }
}

class RunTt2 implements Runnable {

    private static final Lock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " running");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + " finished");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted");
        } finally {
            lock.unlock();
        }
    }
}