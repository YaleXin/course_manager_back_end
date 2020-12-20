/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.test;

import com.yalexin.entity.Student;
import com.yalexin.uitl.Md5UtilSimple;

public class Test {
    public static void main(String[] args) {
        Student student = new Student();
        String hello = Md5UtilSimple.md5("hello");
        System.out.println(hello);
        System.out.println(hello.length());
    }
}
