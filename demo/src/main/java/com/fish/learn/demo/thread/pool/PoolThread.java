package com.fish.learn.demo.thread.pool;


import java.util.concurrent.BlockingQueue;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/7/16 10:02
 */
public class PoolThread extends Thread {

    private BlockingQueue<Runnable> taskQueue = null;

    private  boolean isStoped = false;

    public PoolThread(BlockingQueue<Runnable> queue){
        taskQueue = queue;
    }

    public void run(){
        while(!isStoped()){
            try{
                Runnable runnable = taskQueue.take();
                runnable.run();
            }catch (Exception e){
                //写日志或者报告异常
                //但保持线程池运行
            }
        }
    }

    public synchronized void toStop(){
        isStoped = true;
        this.interrupt();//打断池中线程的dequeue()调用
    }

    public boolean isStoped(){
        return this.isStoped;
    }
}
