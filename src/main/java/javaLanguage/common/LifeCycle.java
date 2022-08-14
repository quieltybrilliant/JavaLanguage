package javaLanguage.common;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: 组件生命周期
 */
public interface LifeCycle {
    /**
     * 初始化资源。
     */
    public void init();

    /**
     * 释放资源。
     */
    public void destroy();
}