/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.test;

import com.yalexin.entity.Student;
import com.yalexin.uitl.DateFormatUtil;
import com.yalexin.uitl.Md5UtilSimple;

import java.math.BigInteger;
import java.sql.Date;

public class Test {
    public static void main(String[] args) {

        String s = "进展跟进.docx";

        String[] split = s.split("\\.");

        System.out.println(split.length);
        System.out.println(split[0]);
        System.out.println(split[1]);

        System.out.println("s.split(\".\") = " + s.split("."));
        
//        Student student = new Student();
//        String hello = Md5UtilSimple.md5("hello");
//        System.out.println(hello);
//        System.out.println(hello.length());
//        timeTest();
//        1602086400000;
//        Date date = new Date();
//        System.out.println(date);

//        System.out.println(new Date((long )1575388800 * 1000));
//        System.out.println(new Date( ));
    }
    public static void timeTest(){
//        Date date = new Date("2020-12-16T16:00:00.000Z");
//        DateFormatUtil.getDateByString("2020-12-16T16:00:00.000Z");
    }
}
