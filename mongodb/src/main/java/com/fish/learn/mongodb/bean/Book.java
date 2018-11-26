package com.fish.learn.mongodb.bean;

import lombok.Data;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/11/26 10:00
 */
@Data
public class Book {

    private String title;

    private Integer price;

    private String desc;

    private int hash;

    public Book(String title, Integer price, String desc){
        this.title = title;
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
            return this.title.equals(book.title) && this.price.equals(book.price);
        }
    }

    @Override
    public int hashCode(){

        int h = hash;
        if(h == 0 && (title != null || price != null)){
            h = h * 31 + (title != null ? title.hashCode() : 0);
            h = h * 31 + (price != null ? price.hashCode() : 0);
            hash = h;
        }

        return h;
    }

}
