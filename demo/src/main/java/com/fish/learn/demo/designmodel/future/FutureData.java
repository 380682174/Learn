package com.fish.learn.demo.designmodel.future;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/6/19 19:36
 */
public class FutureData implements Data{

    protected RealData realData = null;

    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData){
        if(isReady){
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();
    }

    /*public synchronized String getResult (){
        while (!isReady) {
            try {
                wait();
            }catch (InterruptedException e){

            }
        }
        return realData.result;
    }

    @Override
    public String getResult() {
        return null;
    }*/
}
