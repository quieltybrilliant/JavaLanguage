package javaLanguage.juc;

import java.util.concurrent.locks.StampedLock;

/**
 * @Author: guang
 * @Date: 2022/9/4
 * @Desc: StampedLock 读写锁的使用
 * 写，悲观读，乐观度
 */
public class juc_05_StampedLockExample {

    private double x, y;
    private final StampedLock s1 = new StampedLock();

    void move(double deltaX, double deltaY) {
        //获取写锁
        long stamp = s1.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            //释放写锁
            s1.unlockWrite(stamp);
        }
    }


    double distanceFormOrigin() {
        //乐观读操作
        long stamp = s1.tryOptimisticRead();
        //拷贝变量
        double currentX = x, currentY = y;
        //判断读期间是否有写操作
        if (!s1.validate(stamp)) {
            //升级为悲观读
            stamp = s1.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                s1.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}