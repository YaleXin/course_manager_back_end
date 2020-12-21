/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.uitl;

import java.sql.Date;

public class DateFormatUtil {
    public static Date getDateByString(String dateString){
        Date date = null;
        try{
            String[] strings = dateString.split("-");
            int year = Integer.parseInt(strings[0]);
            int month = Integer.parseInt(strings[1]);
            int day = Integer.parseInt(strings[2]);
            date = new Date(year, month - 1, day);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return date;
        }
    }
}
