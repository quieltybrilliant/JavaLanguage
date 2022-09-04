package javaLanguage.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 锁机制存在以下问题：
 * （1）在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度延时，引起性能问题。
 * （2）一个线程持有锁会导致其它所有需要此锁的线程挂起。
 * （3）如果一个优先级高的线程等待一个优先级低的线程释放锁会导致优先级倒置，引起性能风险。
 *
 * volatile是不错的机制，但是volatile不能保证原子性。因此对于同步最终还是要回到锁机制上来。
 * 独占锁是一种悲观锁，synchronized就是一种独占锁，会导致其它所有需要锁的线程挂起，等待持有锁的线程释放锁。
 * 而另一个更加有效的锁就是乐观锁。
 * 所谓乐观锁就是(Optimistic Lock), 顾名思义，就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会上锁，
 * 但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号等机制。
 * 乐观锁适用于多读的应用类型，这样可以提高吞吐量，像数据库如果提供类似于write_condition机制的其实都是提供的乐观锁。
 *
 * CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。
 * 当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。
 *
 * CAS核心方法是 compareAndSet，compareAndSet利用JNI（Java Native Interface）来完成CPU指令的操作。
 * 原子操作常用类（value 有volatile修饰）
 */
public class juc_cas_01_AtomicExample {

    public static void main(String[] args) {
        Demo ad = new Demo();

        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }

    static class Demo implements Runnable {

        private AtomicInteger serialNumber = new AtomicInteger(0);

        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }

            System.out.println(getSerialNumber());
        }

        public int getSerialNumber() {
            // 原子执行自增1操作
            return serialNumber.getAndIncrement();
        }
    }

}

