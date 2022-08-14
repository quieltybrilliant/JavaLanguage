package javaLanguage.juc;

import java.util.concurrent.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * -  java.util.concurrent 提供了一个新的创建执行线程的方式：Callable 接口
 * -  Callable 接口类似于 Runnable，两者都是为那些其实例可能被另一个线程执行的类设计的。
 *      但是 Runnable 不会返回结果，并且无法抛出经过检查的异常。
 * -  Callable 需要依赖Future
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        List<Future<Integer>> resultList = new ArrayList<Future<Integer>>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int rand = random.nextInt(10);

            FactorialCalculator factorialCalculator = new FactorialCalculator(rand);
            // 1.执行Callable方式，需要FutureTask实现类的支持，用于接受运算结果
            Future<Integer> res = executor.submit(factorialCalculator);//异步提交, non blocking.
            resultList.add(res);
        }

        // in loop check out the result is finished
        do {
            for (int i = 0; i < resultList.size(); i++) {
                // 2.接收线程运算后的结果
                Future<Integer> result = resultList.get(i);
                System.out.printf("Task %d : %s and the result is %s\n",
                        i, result.isDone(),result.get());
            }
        } while (executor.getCompletedTaskCount() < resultList.size());
        executor.shutdown();
    }

    static class FactorialCalculator implements Callable<Integer> {

        private Integer number;

        public FactorialCalculator(Integer number) {
            this.number = number;
        }

        public Integer call() throws Exception {
            int result = 1;

            for (int i = 2; i < number; i++) {
                result *= i;
            }
            System.out.printf("%s: %d\n", Thread.currentThread().getName(), result);
            return result;
        }
    }
}