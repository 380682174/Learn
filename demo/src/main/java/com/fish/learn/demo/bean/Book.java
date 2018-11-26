package com.fish.learn.demo.bean;

import lombok.Data;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/11/26 10:00
 */
@Data
public class Book {

    private String name;

    private Integer price;

    private String desc;

    private int hash;

    public Book(String name,Integer price,String desc){
        this.name = name;
        this.price = price;
        this.desc = desc;
    }

    @Override
    public boolean equals(final Object obj){
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }

        final Book book = (Book) obj;
        if(this == obj){
            return true;
        }else{
            return this.name.equals(book.name) && this.price.equals(book.price);
        }
    }

    @Override
    public int hashCode(){

        int h = hash;
        if(h == 0 && (name != null || price != null)){
            h = h * 31 + (name != null ? name.hashCode() : 0);
            h = h * 31 + (price != null ? price.hashCode() : 0);
            hash = h;
        }

        return h;
    }

}
