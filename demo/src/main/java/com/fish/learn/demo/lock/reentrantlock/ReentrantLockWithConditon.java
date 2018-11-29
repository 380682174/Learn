package com.fish.learn.demo.lock.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: ReentrantLock 配合 Conditond 使用
 * @Author devin.jiang
 * @CreateDate 2018/11/29 11:05
 */
public class ReentrantLockWithConditon implements Runnable{
    public static ReentrantLock lock = new ReentrantLock(true);
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        lock.newCondition();
        try {
            lock.lock();
            System.err.println(Thread.currentThread().getName() + "-线程开始等待...");
            condition.await();
            System.err.println(Thread.currentThread().getName() + "-线程继续进行了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockWithConditon test = new ReentrantLockWithConditon();
        Thread t = new Thread(test, "线程ABC");
        t.start();
        Thread.sleep(1000);
        System.err.println("过了1秒后...");
        lock.lock();
        condition.signal(); // 调用该方法前需要获取到创建该对象的锁否则会产生
        // java.lang.IllegalMonitorStateException异常
        lock.unlock();
    }
}

/**
 public interface Condition {
 void await() throws InterruptedException; // 类似于Object.wait()
 void awaitUninterruptibly(); // 与await()相同，但不会再等待过程中响应中断
 long awaitNanos(long nanosTimeout) throws InterruptedException;
 boolean await(long time, TimeUnit unit) throws InterruptedException;
 boolean awaitUntil(Date deadline) throws InterruptedException;
 void signal(); // 类似于Obejct.notify()
 void signalAll();
 }
 */