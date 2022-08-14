package javaLanguage.threadpool.thread;

import javaLanguage.common.LifeCycle;
import javaLanguage.common.xml.DomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javaLanguage.common.xml.XMLNodeParser;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: ThreadPoolConfig
 */
public class ThreadPoolConfig implements LifeCycle {

    public final static String DEFAULT_CONFIG_FILE = "threadpool4j.xml";

    protected String _configFile = DEFAULT_CONFIG_FILE;



    public void set_configFile(String configFile) {
        this._configFile = configFile;
    }


    /**
     * key为线程池名称，value为{@link ThreadPoolInfo}实例。
     */
    protected Map<String, ThreadPoolInfo> _multiThreadPoolInfo = new HashMap<String, ThreadPoolInfo>();

    public Map<String, ThreadPoolInfo> get_multiThreadPoolInfo() {
        return this._multiThreadPoolInfo;
    }

    public int getMultiThreadPoolInfoSize() {
        return this._multiThreadPoolInfo.size();
    }


    /**
     * 线程池状态收集开关
     */
    protected boolean _threadPoolStateSwitch = false;
    protected int _threadPoolStateInterval = 60;   // 单位：秒

    public boolean get_threadPoolStateSwitch(){
        return this._threadPoolStateSwitch;
    }

    public int get_threadPoolStateInterval(){
        return this._threadPoolStateInterval;
    }

    /**
     * 线程状态收集开关
     */
    protected boolean _threadStateSwitch = false;
    protected int _threadStateInterval = 60;   // 单位：秒

    /**
     * 线程堆栈收集开关
     */
    protected boolean _threadStackSwitch = false;
    protected int _threadStackInterval = 60;   // 单位：秒


    @Override
    public void init() {
        initConfig();
    }


    private void initConfig() {
        Document document = DomUtil.createDocument(_configFile);
        Element root = document.getDocumentElement();
        XMLNodeParser parser = new XMLNodeParser(root);
        List<Node> nodeList = parser.getChildNodes();

        for (Node node : nodeList) {
            XMLNodeParser nodeParser = new XMLNodeParser(node);
            if ("pool".equals(node.getNodeName())) {
                ThreadPoolInfo info = new ThreadPoolInfo();
                info.setName(nodeParser.getAttributeValue("name"));
                info.setCoreSize(Integer.parseInt(nodeParser.getChildNodeValue("corePoolSize")));
                info.setMaxSize(Integer.parseInt(nodeParser.getChildNodeValue("maxPoolSize")));
                info.setThreadKeepAliveTime(Long.parseLong(nodeParser.getChildNodeValue("keepAliveTime")));
                info.setQueueSize(Integer.parseInt(nodeParser.getChildNodeValue("workQueueSize")));

                _multiThreadPoolInfo.put(info.getName(), info);
            } else if ("threadpoolstate".equals(node.getNodeName())) {
                _threadPoolStateSwitch = computeSwitchValue(nodeParser);
                _threadPoolStateInterval = computeIntervalValue(nodeParser);
            } else if ("threadstate".equals(node.getNodeName())) {
                _threadStateSwitch = computeSwitchValue(nodeParser);
                _threadStateInterval = computeIntervalValue(nodeParser);
            } else if ("threadstack".equals(node.getNodeName())) {
                _threadStackSwitch = computeSwitchValue(nodeParser);
                _threadStackInterval = computeIntervalValue(nodeParser);
            }
        }
    }

    /**
     * 解析Swith
     */
    private boolean computeSwitchValue(XMLNodeParser nodeParser) {
        return "on".equalsIgnoreCase(
                nodeParser.getAttributeValue("switch"));
    }

    private int computeIntervalValue(XMLNodeParser nodeParser) {
        return Integer.parseInt(nodeParser.getAttributeValue("interval"));
    }

    public boolean containsPool(String poolName) {
        if (null == poolName || null == _multiThreadPoolInfo || _multiThreadPoolInfo.isEmpty()) {
            return false;
        }

        return _multiThreadPoolInfo.containsKey(poolName);
    }


    public ThreadPoolInfo getThreadPoolConfig(String threadpoolName) {
        return _multiThreadPoolInfo.get(threadpoolName);
    }


    public Collection<ThreadPoolInfo> getThreadPoolConfig() {
        return _multiThreadPoolInfo.values();
    }

    public boolean getThreadPoolStateSwitch() {
        return _threadPoolStateSwitch;
    }

    public int getThreadPoolStateInterval() {
        return _threadPoolStateInterval;
    }

    public boolean getThreadStateSwitch() {
        return _threadStateSwitch;
    }

    public int getThreadStateInterval() {
        return _threadStateInterval;
    }

    public boolean getThreadStackSwitch() {
        return _threadStackSwitch;
    }

    public int getThreadStackInterval() {
        return _threadStackInterval;
    }

    @Override
    public void destroy() {
        _threadPoolStateSwitch = false;
        _threadStateSwitch = false;
        _multiThreadPoolInfo.clear();

    }


}