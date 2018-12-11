package com.fish.learn.demo;

import com.fish.learn.demo.bean.Book;
import com.fish.learn.demo.bean.DateUtils;
import com.fish.learn.demo.bean.Student;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoApplicationTests {

    /*@Test
    public void contextLoads() {
        testList();
        //testForeach();
        //testHashCode();
        //testDistinctObject();
        //testSort();
        //testGroupingBy();
        //testDistinct();
    }*/

    public static void main(String[] args) {
        testList();
    }

    private static void testList() {
        //排除上次有 本次没有的数据
        List<String> list = Lists.newArrayList();
        List<String> listBefore = Lists.newArrayList();
        List<String> notFix = Lists.newArrayList();

        listBefore.add("111");
        listBefore.add("222");
        listBefore.add("333");
        listBefore.add("444");
        listBefore.add("555");

        list.add("111");
        list.add("222");
        list.add("333");
        list.add("666");


        for (int i = 0; i < list.size(); i++) {
            if (listBefore.contains(list.get(i))) {
                //上次有的 本次也有 则未修复
                notFix.add(list.get(i));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!listBefore.contains(list.get(i))) {
                //上次没有的本次有，并且排除误报提交的 则未修复
                notFix.add(list.get(i));
            }
        }

        notFix.stream().forEach(i->{
            System.out.println(i);
        });
    }

    private void testForeach() {
        List<Integer> ids = new ArrayList<>();
        for(int i= 0;i<10000000;i++){
            ids.add(i);
        }

        long cur = System.currentTimeMillis();
        ids.stream().forEach(i -> {
             if("a".equals("a")){ String a = "a";}
        });
        long cur2 = System.currentTimeMillis();
        System.out.println("耗时："+DateUtils.getDistanceOfTwoDate(new Date(cur),new Date(cur2)));
    }

    private void testHashCode() {
        System.out.println("啊啊啊啊hashCode:"+"1".hashCode());
    }

    private void testDistinctObject() {

        List<Book> list = new ArrayList<>();
        {
            list.add(new Book("Core Java", 2001,"haoshu"));
            list.add(new Book("Core Java", 200,"huaishu"));
            list.add(new Book("Learning Freemarker", 150,"hao"));
            list.add(new Book("Spring MVC", 300,null));
            list.add(new Book("Spring MVC", 300,"好书"));
        }
        long l = list.stream().distinct().count();
        System.out.println("No. of distinct books:"+l);
        list.stream().distinct().forEach(b -> System.out.println(b.getName()+ "," + b.getPrice()));

    }

    private void testSort() {

        List<Student> students = new ArrayList<>();
        students.add(new Student("zhangsan","nan",9,"福州"));
        students.add(new Student("zhangsan2","nan",19,"福州"));
        students.add(new Student("zhangsan3","nan",91,"福州2"));
        students.add(new Student("zhangsan4","nv",20,"福州4"));
        students.add(new Student("zhangsan","nan",9,"福州24"));

        students.stream().sorted(Comparator.comparing(Student::getAge).reversed()).forEach(System.out::println);
    }

    private void testGroupingBy() {
        List<Student> students = new ArrayList<>();

        students.add(new Student("zhangsan","nan",9,"福州"));
        students.add(new Student("zhangsan2","nan",19,"福州"));
        students.add(new Student("zhangsan3","nan",91,"福州2"));
        students.add(new Student("zhangsan4","nv",119,"福州4"));
        students.add(new Student("zhangsan","nan",9,"福州24"));

        Map<String,List<Student>> map = students.stream().collect(Collectors.groupingBy(Student::getName));

        map.forEach((k,v)->{
            System.out.println(k+":");
           v.forEach(i-> System.out.println(i.toString()));
        });
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private void testDistinct() {
        List<Student> students = new ArrayList<>();

        students.add(new Student("zhangsan","nan",9,"福州"));
        students.add(new Student("zhangsan2","nan",19,"福州"));
        students.add(new Student("zhangsan3","nan",91,"福州2"));
        students.add(new Student("zhangsan4","nv",119,"福州4"));
        students.add(new Student("zhangsan","nan",9,"福州24"));

        students.parallelStream().filter(distinctByKey(Student::getAddress))
                .forEach(System.out::println);
    }
}
