package javaLanguage.threadpool.thread.uilts;

import javaLanguage.threadpool.thread.ThreadStateInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: ThreadUtil
 */
public class ThreadUtil {

    /**
     * 获取当前线程的Top Level线程组。
     *
     * @return
     */
    public static ThreadGroup getRootThreadGroup() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        while (null != threadGroup.getParent()) {
            threadGroup = threadGroup.getParent();
        }

        return threadGroup;
    }


    public static Map<String, ThreadStateInfo> statAllGroupThreadState() {
        ThreadGroup root = ThreadUtil.getRootThreadGroup();
        int groupCapacity = root.activeGroupCount() * 2;
        ThreadGroup[] groupList = new ThreadGroup[groupCapacity];
        int groupNum = root.enumerate(groupList, true);

        Map<String, ThreadStateInfo> stateInfoList = new HashMap<String, ThreadStateInfo>();
        stateInfoList.put(root.getName(), statSingleGroupThreadState(root));
        for (int i = 0; i < groupNum; i++) {
            ThreadGroup threadGroup = groupList[i];
            ThreadStateInfo stateInfo = statSingleGroupThreadState(threadGroup);
            stateInfoList.put(threadGroup.getName(), stateInfo);
        }

        return stateInfoList;
    }

    /**
     * 收集指定线程组{@link ThreadGroup}中所有线程的状态信息。
     */
    public static ThreadStateInfo statSingleGroupThreadState(ThreadGroup threadGroup) {
        if (null == threadGroup) {
            throw new IllegalArgumentException("threadGroup is null");
        }

        int threadCapacity = threadGroup.activeCount() * 2;
        Thread[] threadList = new Thread[threadCapacity];
        int threadNum = threadGroup.enumerate(threadList);

        ThreadStateInfo stateInfo = new ThreadStateInfo();
        for (int j = 0; j < threadNum; j++) {
            Thread thread = threadList[j];
            switch (thread.getState()) {
                case NEW:
                    stateInfo.setNewCount(stateInfo.getNewCount() + 1);
                    break;
                case RUNNABLE:

                    stateInfo.setRunnableCount(stateInfo.getRunnableCount() + 1);
                    break;
                case BLOCKED:
                    stateInfo.setBlockedCount(stateInfo.getBlockedCount() + 1);
                    break;
                case WAITING:
                    stateInfo.setWaitingCount(stateInfo.getWaitingCount() + 1);
                    break;
                case TIMED_WAITING:
                    stateInfo.setTimedWaitingCount(stateInfo.getTimedWaitingCount() + 1);
                    break;
                case TERMINATED:
                    stateInfo.setTerminatedCount(stateInfo.getTerminatedCount() + 1);
                    break;
                default:
                    // nothing
                    break;
            }
        }

        return stateInfo;
    }
}