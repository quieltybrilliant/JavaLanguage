package javaLanguage.threadpool.thread.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: 收集所有线程池的状态信息，统计并输出汇总信息
 */
public class ThreadPoolStateJob extends AbstractJob{

    private static Logger _logger = LoggerFactory.getLogger(ThreadPoolStateJob.class);

    private Map<String, ExecutorService> _multiThreadPool;

    public ThreadPoolStateJob(Map<String, ExecutorService> multiThreadPool, int interval) {
        this._multiThreadPool = multiThreadPool;
        super._interval = interval;
    }


    @Override
    protected void execute() {
        Set<Map.Entry<String, ExecutorService>> poolSet = _multiThreadPool.entrySet();
        for (Map.Entry<String, ExecutorService> entry : poolSet) {
            ThreadPoolExecutor pool = (ThreadPoolExecutor) entry.getValue();
            Object[] objects = new Object[5];
            objects[0] = entry.getKey();
            objects[1] = pool.getActiveCount();
            objects[2] = pool.getTaskCount();
            objects[3] = pool.getCompletedTaskCount();
            objects[4] = pool.getQueue().size();

            _logger.info("ThreadPool:{}, ActiveThread:{}, TotalTask:{}, CompletedTask:{}, Queue:{}", objects);

        }

        super.sleep();

    }
}