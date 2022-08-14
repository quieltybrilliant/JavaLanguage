package javaLanguage.valatile;


class MyDate2 {
    volatile int number = 0;

    public void add() {
        number++;
    }
}

//2.1证明原子性
//原子性是指 完整性，不可分割，就是说某个线程执行时不可分割或被插队。
public class VolatileAtomicityDemo {
    public static void main(String[] args) {
        MyDate2 myDate = new MyDate2();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myDate.add();
                }
            }, String.valueOf(i)).start();
        }
        // 等待线程计算完成后 由main线程取最终结果 10*1000 = 1万
        while (Thread.activeCount() > 2) { //mian线程和GC后台线程
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t sum value=" + myDate.number);
    }
}