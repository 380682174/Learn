package com.fish.learn.demo.io.queue;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description:阻塞队列
 * @Author devin.jiang
 * @CreateDate 2018/7/13 15:36
 */
public class BlockingQueue {
    private List queue = new LinkedList<>();
    private int limit = 10;
    public BlockingQueue(int limit){
        this.limit = limit;
    }

    /**
     * 元素加入队列
     * @param item
     * @throws InterruptedException
     */
    public synchronized void enqueue(Object item) throws InterruptedException{
        while (this.queue.size() == this.limit){
            wait();
        }

        if(this.queue.size() == 0){
            notifyAll();
        }

        this.queue.add(item);
    }

    /**
     * 从队列中移除一个元素
     * @return
     * @throws InterruptedException
     */
    public synchronized Object dequeue() throws InterruptedException {
        while(this.queue.size() == 0){
            wait();
        }

        if(this.queue.size() == this.limit){
            notifyAll();
        }

        return this.queue.remove(0);
    }

}
