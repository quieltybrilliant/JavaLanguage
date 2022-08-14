package javaLanguage.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: DefaultThreadFactory
 * <p>
 * <p>
 * ThreadFactory:对线程池中创建的线程属性进行定制化
 * ThreadGroup:是用来管理一组线程的，可以控制线程的执行，查看线程的执行状态等操作，方便对于一组线程的统一管理。
 * daemon thread :守护线程,守护线程则是用来服务用户线程的，如果没有其他用户线程在运行，线程结束
 */
public class DefaultThreadFactory implements ThreadFactory {

    private AtomicLong _count = new AtomicLong(1);

    private final static String DEFAULT_THREAD_NAME_PREFIX = "thread";

    private ThreadGroup _group;

    private String _threadNamePrefix = DEFAULT_THREAD_NAME_PREFIX;


    public DefaultThreadFactory(String _threadNamePrefix) {
        this._threadNamePrefix = _threadNamePrefix;
        _group = new ThreadGroup(getRootThreadGroup(), _threadNamePrefix);
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(_group, r);
        thread.setName(_threadNamePrefix + "_" + _count.getAndIncrement());

        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (Thread.NORM_PRIORITY != thread.getPriority()) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }

    private ThreadGroup getRootThreadGroup(){

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        while (null != threadGroup.getParent()) {
            threadGroup = threadGroup.getParent();
        }

        return threadGroup;
    }


}