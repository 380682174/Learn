package com.fish.learn.demo.designmodel.flyweight;

/**
 * @Description:财务报表
 * @Author devin.jiang
 * @CreateDate 2018/9/9 19:27
 */
public class FinancialReportManager implements IReportManager {

    protected String tenantId = null;

    public FinancialReportManager(String tenantId){
        this.tenantId = tenantId;
    }

    @Override
    public String createReport() {
        return "This is a financial Report";
    }
}
