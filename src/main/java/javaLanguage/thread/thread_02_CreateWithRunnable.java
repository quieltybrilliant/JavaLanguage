package javaLanguage.thread;


/**
 * @Author: guang
 * @Date: 2022/9/3
 * @Desc: 通过实现Runnable接口实现线程
 */
public class thread_02_CreateWithRunnable implements Runnable {
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(new thread_02_CreateWithRunnable());
        t.start();    //启动子线程
        //主线程继续同时向下执行
        for (int i = 0; i < 10000; i++) {
            System.out.print(i + " ");
        }
    }
}