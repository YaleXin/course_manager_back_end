/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
    static String URL = "jdbc:mysql://localhost:3306/cs";
    static String EXTRA_PARAMETER = "?serverTimezone=Asia/Shanghai&characterEncoding=utf8";
    static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    String TABLE_NAME = "";
    static String USERNAME = "root";
    static String PASSWORD = "12345678";
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public BaseDao(String table) {
        this.TABLE_NAME = table;
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String getTABLE_NAME(){
        return this.TABLE_NAME;
    }
    void closeHelper() {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.clearParameters();
            if (connection != null) connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
