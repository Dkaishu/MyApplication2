package com.example.mytest4_4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/20.
 */
public class BookContent {
    public static class book{
        public Integer id;
        public String title;
        public String desc;

        public book(Integer id, String title, String desc) {
            this.id = id;
            this.title = title;
            this.desc = desc;
        }
        @Override
        public String toString(){
            return title;
        }
    }

    public static List<book> ITEMS = new ArrayList<book>();
    public static Map<Integer,book> ITEM_MAP = new HashMap<Integer, book>() {

    };

    static {
        addItem(new book(1,"book1","book1_describe"));
        addItem(new book(2,"book2","book2_describe"));
        addItem(new book(3,"book3","book3_describe"));
        addItem(new book(4,"book4","book4_describe"));
    }

    private static void addItem(book book){
        ITEMS.add(book);
        ITEM_MAP.put(book.id,book);
    }
}
