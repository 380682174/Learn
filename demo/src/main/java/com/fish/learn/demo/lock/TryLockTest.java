package com.fish.learn.demo.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 锁申请等待限时
 * @Author devin.jiang
 * @CreateDate 2018/11/29 10:51
 */
public class TryLockTest implements Runnable{
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            // 等待1秒
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                //休眠2秒
                Thread.sleep(2000);
            } else {
                System.err.println(Thread.currentThread().getName() + "获取锁失败！");
            }
        } catch (Exception e) {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TryLockTest test = new TryLockTest();
        Thread t1 = new Thread(test);
        t1.setName("线程1");
        Thread t2 = new Thread(test);
        t2.setName("线程2");
        t1.start();
        t2.start();
    }
}
/**
 可以使用 tryLock()或者tryLock(long timeout, TimeUtil unit) 方法进行一次限时的锁等待。
 前者不带参数，这时线程尝试获取锁，如果获取到锁则继续执行，如果锁被其他线程持有，则立即返回 false ，也就是不会使当前线程等待，所以不会产生死锁。
 后者带有参数，表示在指定时长内获取到锁则继续执行，如果等待指定时长后还没有获取到锁则返回false

 t1先获取到锁，并休眠2秒，这时t2开始等待，等待1秒后依然没有获取到锁，就不再继续等待，符合预期结果。
 */