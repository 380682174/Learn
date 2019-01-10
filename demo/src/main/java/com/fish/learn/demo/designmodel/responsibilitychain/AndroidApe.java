package com.fish.learn.demo.designmodel.responsibilitychain;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/10 16:56
 */
public class AndroidApe extends ProgramApes {

    /**
     * 声明整型成员变量表示出差费用
     */
    private int expenses;
    /**
     * 声明字符串型成员变量表示差旅申请
     */
    private String apply = "爹要点钱出差";

    /**
     * 含参构造方法
     */
    public AndroidApe(int expenses) {
        this.expenses = expenses;
    }

    @Override
    public int getExpenses() {
        return expenses;
    }

    @Override
    public String getApply() {
        return apply;
    }

}
