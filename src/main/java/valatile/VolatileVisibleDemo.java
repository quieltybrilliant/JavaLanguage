package valatile;

import java.util.concurrent.TimeUnit;

class MyDate {
    //共享变量 1.1 首先不加volatile关键字
    int number = 0;

    public void change() {
        this.number = 60;
    }
}

/**
 * 1.验证volatile的可见性
 */
public class VolatileVisibleDemo {
    public static void main(String[] args) {
        MyDate myDate = new MyDate();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myDate.change();
            System.out.println(Thread.currentThread().getName() + "\t update number value=" + myDate.number);
        }, "A线程").start();

        //main线程读到的数据
        while (myDate.number == 0) {
            //main 线程一直循环等待直到number值不等于0
        }
        System.out.println(Thread.currentThread().getName() + "\t over number value="+myDate.number);
    }
}
