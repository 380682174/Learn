package com.fish.learn.mongodb.controller;

import com.fish.learn.mongodb.bean.Book;
import com.fish.learn.mongodb.bean.MongoTest;
import com.fish.learn.mongodb.dao.MongoTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/11/26 15:22
 */

@RestController
public class MongoTestController {
    @Autowired
    private MongoTestDao mtdao;

    @GetMapping(value="/test1")
    public void saveTest() throws Exception {
        for(int i=0;i<10;i++){
            MongoTest mgtest=new MongoTest();
            mgtest.setId(i);
            mgtest.setAge(33);
            mgtest.setName("ceshi"+i);
            mtdao.saveTest(mgtest);
        }
    }

    @GetMapping(value="/test2")
    public MongoTest findTestByName(){
        MongoTest mgtest= mtdao.findTestByName("ceshi");
        System.out.println("mgtest is "+mgtest);
        return mgtest;
    }

    @GetMapping(value="/test3")
    public void updateTest(){
        MongoTest mgtest=new MongoTest();
        mgtest.setId(11);
        mgtest.setAge(44);
        mgtest.setName("ceshi2");
        mtdao.updateTest(mgtest);
    }

    @GetMapping(value="/test4")
    public void deleteTestById() {
        mtdao.deleteTestById(11);

    }

    @GetMapping(value = "/test5")
    public List<MongoTest> testFindList(){
        List<Integer> ids = new ArrayList();
        ids.add(3);
        ids.add(4);
        ids.add(7);
        ids.add(9);
        return mtdao.findList(ids);
    }

    @GetMapping(value="/test6")
    public void saveTest6() throws Exception {
        for(int i=0;i<10;i++){
            MongoTest mgtest=new MongoTest();
            mgtest.setId(i);
            mgtest.setAge(33);
            mgtest.setName("ceshi啊啊"+i);
            List<Book> books = new ArrayList<>();
            {
                books.add(new Book("Core Java", 2001,"haoshu"));
                books.add(new Book("Core Java", 200,"huaishu"));
                books.add(new Book("Learning Freemarker", 150,"hao"));
                books.add(new Book("Spring MVC", 300,null));
                books.add(new Book("Spring MVC", 300,"好书"));
            }
            mgtest.setBookList(books);
            mtdao.saveTest(mgtest);
        }
    }

    @GetMapping(value = "/test7")
    public List<MongoTest> testFindList7(){
        List<String> ids = new ArrayList();
        ids.add("Core Java");
        ids.add("Learning Freemarker");
        return mtdao.findList7(ids);
    }
}
