package javaLanguage.thread;

/**
 * @Author: guang
 * @Date: 2022/9/3
 * @Desc: thread_06_MainAndSubThreadCyclicalExecute
 */
public class thread_06_MainAndSubCyclical {
    public static void main(String[] args) {
        final ThreadCommon common = new ThreadCommon();
        new Thread((Runnable) () -> {
            for (int i = 1; i <= 50; i++) {
                common.sub(i);
            }
        }).start();
        for (int i = 1; i <= 50; i++) {
            common.main(i);
        }

    }


}


class ThreadCommon {
    private boolean sub = true;

    public synchronized void sub(int i) {
        while (!sub) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int j = 1; j <= 10; j++) {
            System.out.println("sub  " + j + " loop of " + i);
        }
        sub = false;
        this.notify();
    }

    public synchronized void main(int i) {
        while (sub) {
            try {
                this.wait();  //等待让sub运行完
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 1; j <= 100; j++) {
            System.out.println("main " + j + " loop of  " + i);
        }
        sub = true;
        this.notify();
    }

}