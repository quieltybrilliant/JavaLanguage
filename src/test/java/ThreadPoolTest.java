import javaLanguage.threadpool.thread.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: ThreadPoolTest
 */
public class ThreadPoolTest {

    private ThreadPoolImpl _threadPool = new ThreadPoolImpl();

    @Rule
    public ExpectedException _expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        _threadPool.set_status(ThreadPoolStatus.UNINITIALIZED);
        _threadPool.get_threadPoolConfig().set_configFile(ThreadPoolConfig.DEFAULT_CONFIG_FILE);
        _threadPool.init();
    }

    @After
    public void tearDown() throws Exception {
   }

   @Test
   public void  test(){
//       ThreadPoolManager tpm = ThreadPoolManager.getSingleton();
//       ThreadPool threadPool = tpm.getThreadPool();
// 未指定线程池名称时，任务会提交到名为"default"的线程池执行
       _threadPool.submit(new RunnableAsynTask());

// 将task2提交到名为"other"的线程池执行
       _threadPool.submit(new RunnableAsynTask(), "other");
   }

    @Test
    public void testInit() {
        assertEquals(2, _threadPool.get_multiThreadPoolSize());
        assertTrue(_threadPool.get_multiThreadPool().containsKey("default"));
        assertTrue(_threadPool.get_multiThreadPool().containsKey("other"));
        assertTrue(_threadPool.get_threadPoolConfig().getThreadPoolStateSwitch());
        assertEquals(60, _threadPool.get_threadPoolConfig().getThreadPoolStateInterval());
    }
//
//    @Test
//    public void testDestroy() {
//        // 先销毁加载默认配置的线程池
//        _threadPool.destroy();
//        assertEquals(ThreadPoolStatus.DESTROYED, _threadPool._status);
//        assertNull(_threadPool._threadPoolStateJob);
//        assertNull(_threadPool._threadStateJob);
//        for (Map.Entry<String, ExecutorService> entry : _threadPool._multiThreadPool.entrySet()) {
//            assertTrue(entry.getValue().isShutdown());
//        }
//
//        // 加载指定配置文件的线程池，初始化后再销毁
//        String configFile = "/cn/aofeng/threadpool4j/threadpool4j_1.5.0_closethreadstate.xml";
//        _threadPool._threadPoolConfig._configFile = configFile;
//        _threadPool.init();
//        _threadPool.destroy();
//        assertEquals(ThreadPoolStatus.DESTROYED, _threadPool._status);
//        assertNull(_threadPool._threadPoolStateJob);
//        assertNull(_threadPool._threadStateJob);
//        for (Map.Entry<String, ExecutorService> entry : _threadPool._multiThreadPool.entrySet()) {
//            assertTrue(entry.getValue().isShutdown());
//        }
//    }
}