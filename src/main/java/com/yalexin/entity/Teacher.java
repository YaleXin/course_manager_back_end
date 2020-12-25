/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.entity;

public class Teacher extends User{
    private boolean gender;

    public Teacher(String username, String password, boolean gender) {
        super(username, password);
        this.gender = gender;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Teacher() {
        super("teacher");
    }
}
