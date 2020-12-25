/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.entity;

import java.sql.Date;

public class Student extends User {
    private int age;
    private Date birthday;
    private boolean gender;
    private String className;

    public Student() {
        super("student");
    }

    public Student(String username, String password, int age, Date birthday, boolean gender, String className) {
        super(username, password);
        this.age = age;
        this.birthday = birthday;
        this.gender = gender;
        this.className = className;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
