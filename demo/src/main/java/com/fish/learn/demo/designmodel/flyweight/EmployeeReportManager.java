package com.fish.learn.demo.designmodel.flyweight;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/9/9 19:30
 */
public class EmployeeReportManager implements IReportManager {
    protected String tenantId = null;

    public EmployeeReportManager(String tenantId){
        this.tenantId = tenantId;
    }

    @Override
    public String createReport() {
        return "This is a employee Report";
    }
}
