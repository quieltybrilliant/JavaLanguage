package javaLanguage.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: guang
 * @Date: 2022/9/3
 * @Desc: 创建线程使用JUC
 */
public class thread_03_CreateWithCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {

        // FutureTask用于接收运算结果
        FutureTask<Integer> result = new FutureTask<>(new thread_03_CreateWithCallable());
        new Thread(result).start();

        // 返回运算结果
        try {
            Integer sum = result.get();
            System.out.println(sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();

        }

    }
}