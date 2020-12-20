/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.AdminUser;

import java.sql.*;

public class AdminDao extends BaseDao{

    public AdminUser getAdminUserByUsername(String username) {

        if (username == null || username.trim().equals(""))return null;
        AdminUser adminUser = null;
        try {
            this.connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "select * from " + TABLE_NAME + " where username=?";
            this.preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            this.resultSet = preparedStatement.executeQuery();
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
        super("admin_user");
    }
}
