package javaLanguage.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * - Condition 接口描述了可能会与锁有关联的条件变量。
 * 这些变量在用法上与使用 Object.wait 访问的隐式监视器类似，但提供了更强大的功能。
 * 需要特别指出的是，单个 Lock 可能与多个 Condition 对象关联。
 * 为了避免兼容性问题，Condition 方法的名称与对应的 Object 版本中的不同。
 * - 在 Condition 对象中，与 wait、notify 和 notifyAll 方法对应的分别是await、signal 和 signalAll。
 * - Condition 实例实质上被绑定到一个锁上。要为特定 Lock 实例获得Condition 实例，请使用其 newCondition() 方法。
 */
public class ConditionProducerConsumer {
    public int nummber = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();


    public static void main(String[] args) {
        ConditionProducerConsumer a = new ConditionProducerConsumer();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                a.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                a.decrease();
            }
        }, "B").start();
    }

    public void increment() {
        lock.lock();
        try {
            while (nummber != 0) {
              //同thread的wait，暂停线程，释放锁
                condition.await();
            }
            nummber++;
            System.out.println(Thread.currentThread().getName() + ">>" + nummber);

            // 同thread的notifyAll，唤醒所有的线程，释放锁
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void decrease() {
        lock.lock();
        try {
            while (nummber != 1) {
                condition.await();
            }
            nummber--;
            System.out.println(Thread.currentThread().getName() + ">>" + nummber);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
