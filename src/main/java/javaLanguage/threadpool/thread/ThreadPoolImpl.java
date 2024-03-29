package javaLanguage.threadpool.thread;

import javaLanguage.common.LifeCycle;
import javaLanguage.common.thread.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javaLanguage.common.lang.StringUtil;
import javaLanguage.threadpool.thread.job.ThreadPoolStateJob;
import javaLanguage.threadpool.thread.job.ThreadStackJob;
import javaLanguage.threadpool.thread.job.ThreadStateJob;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: ThreadPoolImpl
 */
public class ThreadPoolImpl implements LifeCycle, ThreadPool {

    private static final String DEFAULT_THREAD_POOL = "default";

    private static Logger _logger = LoggerFactory.getLogger(ThreadPoolImpl.class);

    protected ThreadPoolConfig _threadPoolConfig = new ThreadPoolConfig();

    public ThreadPoolConfig get_threadPoolConfig(){
        return this._threadPoolConfig;
    }

    protected int _status = ThreadPoolStatus.UNINITIALIZED;

    public void set_status(int status){
        this._status = status;
    }

    Map<String, ExecutorService> _multiThreadPool = new HashMap<String, ExecutorService>();

    public int get_multiThreadPoolSize(){
       return this._multiThreadPool.size();
    }

    public Map<String, ExecutorService> get_multiThreadPool(){
        return this._multiThreadPool;
    }


    ThreadPoolStateJob _threadPoolStateJob;
    ThreadStateJob _threadStateJob;
    ThreadStackJob _threadStackJob;

    public ThreadPoolImpl() {
        // nothing
    }


    @Override
    public void init() {
        if (ThreadPoolStatus.UNINITIALIZED != _status) {
            _logger.warn("initialization thread pool failed, because the status was wrong, current status was {} (0:UNINITIALIZED, 1:INITIALITION_SUCCESSFUL, 2:INITIALITION_FAILED, 3:DESTROYED)", _status);
            return;
        }

        try {
            initThreadPool();
            startThreadPoolStateJob();
            startThreadStateJob();
            startThreadStackJob();
            _status = ThreadPoolStatus.INITIALITION_SUCCESSFUL;
        } catch (RuntimeException e) {
            _status = ThreadPoolStatus.INITIALITION_FAILED;
            throw e;
        }


    }

    /**
     * 初始化所有线程池。
     */
    private void initThreadPool() {
        _threadPoolConfig.init();
        if (! _threadPoolConfig.containsPool(DEFAULT_THREAD_POOL)) {
            throw new IllegalStateException( String.format("the default thread pool not exists, please check the config file '%s'", _threadPoolConfig._configFile) );
        }
        Collection<ThreadPoolInfo> threadPoolInfoList = _threadPoolConfig.getThreadPoolConfig();
        for (ThreadPoolInfo threadPoolInfo : threadPoolInfoList) {
            BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(threadPoolInfo.getQueueSize());
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                    threadPoolInfo.getCoreSize(),
                    threadPoolInfo.getMaxSize(),
                    threadPoolInfo.getThreadKeepAliveTime(),
                    TimeUnit.SECONDS,
                    workQueue,
                    new DefaultThreadFactory(threadPoolInfo.getName())
            );
            _multiThreadPool.put(threadPoolInfo.getName(), threadPool);
            _logger.info("initialization thread pool '{}' success", threadPoolInfo.getName());
        }
    }

    private void startThreadPoolStateJob() {
        if (! _threadPoolConfig.getThreadPoolStateSwitch()) {
            return;
        }

        _threadPoolStateJob = new ThreadPoolStateJob(
                _multiThreadPool,
                _threadPoolConfig.getThreadPoolStateInterval() );
        _threadPoolStateJob.init();
        Thread jobThread = new Thread(_threadPoolStateJob);
        jobThread.setName("threadpool4j-threadpoolstate");
        jobThread.start();

        _logger.info("start job 'threadpool4j-threadpoolstate' success");
    }

    /**
     * 初始化并启动线程状态统计Job。
     */
    private void startThreadStateJob() {
        if (! _threadPoolConfig.getThreadStateSwitch()) {
            return;
        }

        _threadStateJob = new ThreadStateJob(_threadPoolConfig.getThreadStateInterval());
        _threadStateJob.init();
        Thread jobThread = new Thread(_threadStateJob);
        jobThread.setName("threadpool4j-threadstate");
        jobThread.start();

        _logger.info("start job 'threadpool4j-threadstate' success");
    }

    private void startThreadStackJob() {
        if (! _threadPoolConfig.getThreadStackSwitch()) {
            return;
        }

        _threadStackJob = new ThreadStackJob(_threadPoolConfig.getThreadStackInterval());
        _threadStackJob.init();
        Thread jobThread = new Thread(_threadStackJob);
        jobThread.setName("threadpool4j-threadstack");
        jobThread.start();

        _logger.info("start job 'threadpool4j-threadstack' success");
    }



    @Override
    public Future<?> submit(Runnable task) {
        return submit(task, DEFAULT_THREAD_POOL);
    }

    @Override
    public Future<?> submit(Runnable task, String threadpoolName) {
        if (null == task) {
            throw new IllegalArgumentException("task is null");
        }

        ExecutorService threadPool = getExistsThreadPool(threadpoolName);
        _logger.debug("submit a task to thread pool {}", threadpoolName);

        return threadPool.submit(task);
    }

    @Override
    public Future<?> submit(Runnable task, String threadpoolName, FailHandler<Runnable> failHandler) {
        try {
            return submit(task, threadpoolName);
        } catch (RejectedExecutionException e) {
            if (null != failHandler) {
                failHandler.execute(task);
            }
        }

        return null;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return submit(task, DEFAULT_THREAD_POOL);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task, String threadpoolName) {
        if (null == task) {
            throw new IllegalArgumentException("task is null");
        }

        ExecutorService threadPool = getExistsThreadPool(threadpoolName);
        _logger.debug("submit a task to thread pool {}", threadpoolName);

        return threadPool.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task, String threadpoolName, FailHandler<Callable<T>> failHandler) {
        try {
            return submit(task, threadpoolName);
        } catch (RejectedExecutionException e) {
            if (null != failHandler) {
                failHandler.execute(task);
            }
        }

        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks, long timeout, TimeUnit timeoutUnit) {
        return invokeAll(tasks, timeout, timeoutUnit, DEFAULT_THREAD_POOL);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<Callable<T>> tasks, long timeout, TimeUnit timeoutUnit, String threadpoolName) {
        if (null == tasks || tasks.isEmpty()) {
            throw new IllegalArgumentException("task list is null or empty");
        }
        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout less than or equals zero");
        }

        ExecutorService threadPool = getExistsThreadPool(threadpoolName);
        _logger.debug("invoke task list in thread pool {}", threadpoolName);

        try {
            return threadPool.invokeAll(tasks, timeout, timeoutUnit);
        } catch (InterruptedException e) {
            _logger.error("invoke task list occurs error", e);
        }

        return null;
    }

    @Override
    public boolean isExists(String threadpoolName) {
        ExecutorService threadPool = getThreadPool(threadpoolName);

        return (null == threadPool ? false : true);
    }

    @Override
    public ThreadPoolInfo getThreadPoolInfo(String threadpoolName) {
        ThreadPoolInfo info = _threadPoolConfig.getThreadPoolConfig(threadpoolName);

        return info.clone();
    }

    private ExecutorService getExistsThreadPool(String threadpoolName) {
        ExecutorService threadPool = getThreadPool(threadpoolName);
        if (null == threadPool) {
            throw new IllegalArgumentException( String.format("thread pool %s not exists", threadpoolName) );
        }

        return threadPool;
    }

    ExecutorService getThreadPool(String threadpoolName) {
        if (StringUtil.isBlank(threadpoolName)) {
            throw new IllegalArgumentException("thread pool name is empty");
        }

        ExecutorService threadPool = _multiThreadPool.get(threadpoolName);

        return threadPool;
    }

    @Override
    public void destroy() {
        if (ThreadPoolStatus.DESTROYED == _status) {
            return;
        }

        for (Map.Entry<String, ExecutorService> entry : _multiThreadPool.entrySet()) {
            _logger.info("shutdown the thread pool '{}'", entry.getKey());
            entry.getValue().shutdown();
        }

        if (null != _threadPoolStateJob) {
            _threadPoolStateJob.destroy();
            _logger.info("stop job 'threadpool-threadpoolstate' success");
            _threadPoolStateJob = null;
        }

        if (null != _threadStateJob) {
            _threadStateJob.destroy();
            _logger.info("stop job 'threadpool-threadstate' success");
            _threadStateJob = null;
        }

        if (null != _threadStackJob) {
            _threadStackJob.destroy();
            _logger.info("stop job 'threadpool-threadstack' success");
            _threadStackJob = null;
        }

        _threadPoolConfig.destroy();
        _status = ThreadPoolStatus.DESTROYED;
    }
}