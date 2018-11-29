package com.fish.learn.demo.lock.simple;

/**
 * @Description:锁的简单实现
 * @Author devin.jiang
 * @CreateDate 2018/7/12 11:11
 */
public class Lock {

    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException{
        while (isLocked){
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock(){
        isLocked = false;
        notify();
    }
}
