package com.fish.learn.demo.designmodel.future;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/6/19 19:37
 */
public class RealData implements Data{

    protected final String result;

    public RealData(String para, String result, String result1) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10;i++){
            sb.append(para);
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){

            }
        }
        this.result = sb.toString();
    }

    /*@Override
    public String getResult() {
        return result;
    }*/
}
