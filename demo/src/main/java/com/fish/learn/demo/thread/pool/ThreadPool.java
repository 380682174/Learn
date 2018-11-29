package com.fish.learn.demo.thread.pool;

import com.queue.BlockingQueue;
import com.thread.pool.PoolThread;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/7/16 10:01
 */
public class ThreadPool {
    private BlockingQueue taskQueue = null;
    private List<PoolThread> threads = new ArrayList<PoolThread>();
    private boolean isStopped = false;

    public ThreadPool (int noOfThreads,int maxNoOfTasks){
        taskQueue = new BlockingQueue(maxNoOfTasks);
        for(int i=0; i < noOfThreads;i++){
            threads.add(new PoolThread((java.util.concurrent.BlockingQueue<Runnable>) taskQueue));
        }
        for(PoolThread thread : threads){
            thread.start();
        }
    }

    public synchronized void execute(Runnable task) throws InterruptedException {
        if(this.isStopped)
            new IllegalStateException("ThreadPool is stoped");
        this.taskQueue.enqueue(task);
    }

    public synchronized boolean stop(){
        this.isStopped = true;
        for(PoolThread thread : threads){
            thread.stop();
        }
        return isStopped;
    }

}
