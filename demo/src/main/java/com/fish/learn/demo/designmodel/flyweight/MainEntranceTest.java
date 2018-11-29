package com.fish.learn.demo.designmodel.flyweight;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/9/9 19:37
 */
public class MainEntranceTest {

    public static void main(String[] args) {
        ReportManagerFactory rmf = new ReportManagerFactory();
        IReportManager r = rmf.getFinancialReportManager("A");
        System.out.println(r.createReport());
    }

}
