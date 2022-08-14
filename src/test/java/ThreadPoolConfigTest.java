import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javaLanguage.threadpool.thread.ThreadPoolConfig;
import javaLanguage.threadpool.thread.ThreadPoolInfo;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: ThreadPoolConfigTest
 */
public class ThreadPoolConfigTest {

    private ThreadPoolConfig _threadPoolConfig = new ThreadPoolConfig();

    @Before
    public void setUp() throws Exception {
        _threadPoolConfig.set_configFile("threadpool4j.xml");
    }

    @After
    public void tearDown() throws Exception {
    }


    /**
     * 测试用例：读取线程池配置文件,default和other
     */
    @Test
    public void test_01() {
        _threadPoolConfig.set_configFile("threadpool4j.xml");
        _threadPoolConfig.init();
        assertEquals(2, _threadPoolConfig.getMultiThreadPoolInfoSize());

        // default线程池配置信息
        assertTrue(_threadPoolConfig.get_multiThreadPoolInfo().containsKey("default"));
        ThreadPoolInfo defaultInfo = _threadPoolConfig.get_multiThreadPoolInfo().get("default");
        assertEquals(30, defaultInfo.getCoreSize());
        assertEquals(150, defaultInfo.getMaxSize());
        assertEquals(5, defaultInfo.getThreadKeepAliveTime());
        assertEquals(100000, defaultInfo.getQueueSize());

        // other线程池配置信息
        assertTrue(_threadPoolConfig.get_multiThreadPoolInfo().containsKey("other"));
        ThreadPoolInfo otherInfo = _threadPoolConfig.get_multiThreadPoolInfo().get("other");
        assertEquals(10, otherInfo.getCoreSize());
        assertEquals(100, otherInfo.getMaxSize());
        assertEquals(10, otherInfo.getThreadKeepAliveTime());
        assertEquals(100000, otherInfo.getQueueSize());


        // 线程池状态收集配置信息
        assertTrue(_threadPoolConfig.getThreadPoolStateSwitch());
        assertEquals(60, _threadPoolConfig.getThreadPoolStateInterval());

        // 线程状态收集配置信息
        assertTrue(_threadPoolConfig.getThreadPoolStateSwitch());
        assertEquals(60, _threadPoolConfig.getThreadPoolStateInterval());

        // 线程堆栈收集配置信息
        assertTrue(_threadPoolConfig.getThreadPoolStateSwitch());

        assertEquals(60, _threadPoolConfig.getThreadPoolStateInterval());
    }

}