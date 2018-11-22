package com.fish.learn.demo.bean;

import lombok.Data;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/11/20 17:19
 */
@Data
public class Student {

    private String name;

    private String sex;

    private Integer age;

    private String address;

    public Student(String name, String sex, Integer age, String address) {
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.age =age;
    }
}
