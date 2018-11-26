package com.fish.learn.mongodb.bean;

import java.util.List;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/11/26 15:24
 */
public class MongoTest {

    private Integer id;
    private Integer age;
    private String name;

    List<Book> bookList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBookList(List<Book> studentList){
        this.bookList = bookList;
    }

    public List<Book> getBookList(){
        return this.bookList;
    }

    public void MongoTest(Integer id,Integer age,String name,List<Book> bookList){
        this.id = id;
        this.age = age;
        this.name=name;
        this.bookList = bookList;
    }


}
