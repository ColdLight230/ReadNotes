package com.ipctest.bean;

import java.io.Serializable;

/**
 * 描    述：
 * 作    者：xul@13322.com
 * 时    间：2017/7/11
 */

public class User implements Serializable {
    public int age;
    public String name;
    public boolean isMale;

    public User(){}

    public User(int age, String name, boolean isMale) {
        this.age = age;
        this.name = name;
        this.isMale = isMale;
    }

    @Override
    public String toString() {
        return "User:" + this.hashCode() + "  age:" + age + "  name:" + name + "  isMale:" + isMale;
    }
}
