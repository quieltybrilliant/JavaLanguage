package javaLanguage.thread;


/**
 * @Author: guang
 * @Date: 2022/9/3
 * @Desc: 使用Thread创建线程
 */
public class thread_01_CreateWithThread extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new thread_01_CreateWithThread().start();
        }
        System.out.println(Thread.currentThread().getName());
    }
}