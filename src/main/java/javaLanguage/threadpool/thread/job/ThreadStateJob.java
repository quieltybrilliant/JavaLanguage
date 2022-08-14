package javaLanguage.threadpool.thread.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javaLanguage.threadpool.thread.ThreadStateInfo;
import javaLanguage.threadpool.thread.uilts.ThreadUtil;
import java.util.Map;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: 收集所有线程组中所有线程的状态信息，统计并输出汇总信息。
 */
public class ThreadStateJob extends AbstractJob {

    private static Logger _logger = LoggerFactory.getLogger(ThreadStateJob.class);

    public ThreadStateJob(int interval) {
        super._interval = interval;
    }

    @Override
    protected void execute() {
        Map<String, ThreadStateInfo> statMap = ThreadUtil.statAllGroupThreadState();

        for (Map.Entry<String, ThreadStateInfo> entry : statMap.entrySet()) {
            ThreadStateInfo stateInfo = entry.getValue();
            Object[] objects = new Object[7];
            objects[0] = entry.getKey();
            objects[1] = stateInfo.getNewCount();
            objects[2] = stateInfo.getRunnableCount();
            objects[3] = stateInfo.getBlockedCount();
            objects[4] = stateInfo.getWaitingCount();
            objects[5] = stateInfo.getTimedWaitingCount();
            objects[6] = stateInfo.getTerminatedCount();

            _logger.info("ThreadGroup:{}, New:{},  Runnable:{}, Blocked:{}, Waiting:{}, TimedWaiting:{}, Terminated:{}", objects
            );
        }

        super.sleep();
    }
}