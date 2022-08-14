import java.util.Arrays;
import java.util.Random;

/**
 * @Author: guang
 * @Date: 2022/8/14
 * @Desc: RunnableAsynTask
 */
public class RunnableAsynTask implements Runnable {

    // 耗时的操作（配置低一些的机器小心CPU 100％，反应慢）
    public void needSomeTime() {
        int len = 50;
        String[] intArray = new String[len];
        Random random = new Random(len);

        // 初始化数组
        for (int i = 0; i < len; i++) {
            intArray[i] = String.valueOf(random.nextInt());
        }

        // 排序
        Arrays.sort(intArray);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        needSomeTime();
        long endTime = System.currentTimeMillis();

        System.out.println("执行任务耗时："+(endTime-startTime));
    }
}