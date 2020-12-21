/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.Teacher;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDao extends BaseDao {

    public TeacherDao() {
        super("teacher");
    }

    public int addTeacher(Teacher teacher) {

        if (teacher == null) return 0;
        int result = 0;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "insert into " + TABLE_NAME + "(id,name,gender) values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.setString(2, teacher.getUsername());
            preparedStatement.setBoolean(3, teacher.isGender());
            result = preparedStatement.executeUpdate();
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeHelper();
            return result;
        }
    }


    public Teacher getTeacherById(int id) {
        if (id <= 0) return null;
        Teacher teacher = null;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "select * from " + TABLE_NAME + " where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) teacher = getOneTeacherByResultSet(resultSet);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            closeHelper();
            return teacher;
        }
    }

    private Teacher getOneTeacherByResultSet(ResultSet resultSet) {
        Teacher teacher = new Teacher();
        try {
            teacher.setId(resultSet.getInt("id"));
            teacher.setUsername(resultSet.getString("name"));
            teacher.setPassword(resultSet.getString("password"));
            teacher.setGender(resultSet.getBoolean("gender"));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return teacher;
    }

}
