package com.fish.learn.demo.designmodel.builder;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2019/1/26 13:50
 */
public class ConcreteBuilder extends Builder{

    Computer computer = new Computer();

    @Override
    public void  buildCPU(){
        computer.Add("组装CPU");
    }

    @Override
    public void  buildMainboard(){
        computer.Add("组装主板");
    }

    @Override
    public void  buildHD(){
        computer.Add("组装主板");
    }

    @Override
    public  Computer getComputer(){
        return computer;
    }  
    
}
