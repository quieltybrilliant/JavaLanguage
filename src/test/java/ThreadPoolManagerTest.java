import javaLanguage.common.LifeCycle;
import javaLanguage.threadpool.thread.ThreadPool;
import javaLanguage.threadpool.thread.ThreadPoolImpl;
import javaLanguage.threadpool.thread.ThreadPoolManager;


import static org.easymock.EasyMock.*;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: ThreadPoolManagerTest
 */
public class ThreadPoolManagerTest {

    private LifeCycle _threadPool = createMock(ThreadPoolImpl.class);

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

    public void init() {
        synchronized (_lock) {
            if (_initStatus) {
                return;
            }
            _threadPool.init();
            _initStatus = true;
        }
    }

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