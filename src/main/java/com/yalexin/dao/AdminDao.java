/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.AdminUser;

import java.sql.*;

public class AdminDao {
    static String URL = "jdbc:mysql://localhost:3306/cs";
    static String EXTRA_PARAMETER = "?serverTimezone=Asia/Shanghai&characterEncoding=utf8";
    static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    static String TABLE_NAME = "admin_user";
    static String USERNAME = "root";
    static String PASSWORD = "12345678";
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public AdminUser getAdminUserByUsername(String username) {

        if (username == null || username.trim().equals(""))return null;
        AdminUser adminUser = null;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "select * from " + TABLE_NAME + " where username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            System.out.println(preparedStatement.toString());
            if (resultSet.next()) adminUser = getOneAdminUserByResultSet(resultSet);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        closeHelper();
        return adminUser;
    }

    private AdminUser getOneAdminUserByResultSet(ResultSet resultSet) throws SQLException {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(resultSet.getInt("id"));
        adminUser.setUsername(resultSet.getString("username"));
        adminUser.setPassword(resultSet.getString("password"));
        return adminUser;
    }

    public AdminDao() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void closeHelper() {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.clearParameters();
            if (connection != null) connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
