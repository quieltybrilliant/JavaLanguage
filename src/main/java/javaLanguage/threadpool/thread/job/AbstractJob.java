package javaLanguage.threadpool.thread.job;

import javaLanguage.common.LifeCycle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: AbstractJob
 */
public abstract class AbstractJob implements Runnable, LifeCycle {

    protected String _lineSeparator = System.getProperty("line.separator");

    /**
     * 运行状态：true表示正在运行；false表示已停止
     */
    protected volatile AtomicBoolean _run = new AtomicBoolean(true);

    /**
     * 线程休眠时间（单位：秒）
     */
    protected int _interval = 60;


    @Override
    public void init() {
        _run.set(true);
    }



    @Override
    public void run() {
        while (_run.get()) {
            execute();
        }
    }

    protected abstract void execute();

    protected void sleep() {
        try {
            Thread.sleep(_interval * 1000);
        } catch (InterruptedException e) {
            // nothing
        }
    }

    protected String currentTime() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = Calendar.getInstance().getTime();

        return format.format(date);
    }

    @Override
    public void destroy() {
        _run.set(false);
    }
}