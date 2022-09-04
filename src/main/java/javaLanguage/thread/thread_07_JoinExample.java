package javaLanguage.thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: guang
 * @Date: 2022/9/4
 * @Desc: thread_07_JoinExample
 */
public class thread_07_JoinExample implements Runnable{

    public static void main(String[] args) {
        JoinTest test = new JoinTest();
        Thread thread = new Thread(test, "test");

        JoinTest test1 = new JoinTest();
        Thread thread1 = new Thread(test1, "test1");
        thread.start();
        thread1.start();

//        thread.join();
//        thread1.join();

        System.out.println("Finshed!!");   //等到thread和thread1运行完之后才会运行
    }

    @Override
    public void run() {
        System.out.printf("Test Begining data source loading: %s\n", new Date());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Test loading has finished:%s\n", new Date());
    }

    static class JoinTest implements Runnable {
        @Override
        public void run() {
            System.out.printf("Test1 Begining data source loading: %s\n", new Date());
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Test1 loading has finished:%s\n", new Date());
        }
    }

}