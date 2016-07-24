package com.example.mytest4_2;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/19.
 */
public class Person implements Serializable {

    public String name;
    public String passwd;
    public String gender;

    public String getName(){
        return this.name;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getGender() {

        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPasswd(String passwd) {

        this.passwd = passwd;

    }

    public Person(String name, String passwd, String gender) {
        this.name = name;
        this.passwd = passwd;
        this.gender = gender;
    }
}
