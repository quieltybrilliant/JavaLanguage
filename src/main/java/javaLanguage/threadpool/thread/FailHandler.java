package javaLanguage.threadpool.thread;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: FailHandler
 */
public interface FailHandler<T> {

    /**
     * 处理无法提交线程池执行的异步任务。
     * @param task 无法提交线程池执行的异步任务
     */
    public void execute(T task);

}