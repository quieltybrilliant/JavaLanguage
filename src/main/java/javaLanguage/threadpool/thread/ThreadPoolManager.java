package javaLanguage.threadpool.thread;

import javaLanguage.common.LifeCycle;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: 线程池实例管理
 */
public class ThreadPoolManager implements LifeCycle {

    private LifeCycle _threadPool = new ThreadPoolImpl();

    private static Object _lock = new Object();
    private boolean _initStatus = false;
    private boolean _destroyStatus = false;

    private static ThreadPoolManager _instance = new ThreadPoolManager();
    public static ThreadPoolManager getSingleton() {
        return _instance;
    }

    public ThreadPool getThreadPool() {
        return (ThreadPool) _threadPool;
    }

    // 用于单元测试和子类扩展
    protected void setThreadPool(ThreadPool threadPool) {
        this._threadPool = (LifeCycle) threadPool;
    }

    @Override
    public void init() {
        synchronized (_lock) {
            if (_initStatus) {
                return;
            }
            _threadPool.init();
            _initStatus = true;
        }
    }

    @Override
    public void destroy() {
        synchronized (_lock) {
            if (_destroyStatus) {
                return;
            }
            _threadPool.destroy();
            _destroyStatus = true;
        }
    }
}