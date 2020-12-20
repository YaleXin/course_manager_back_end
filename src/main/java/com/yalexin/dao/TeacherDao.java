/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.Teacher;

import java.sql.DriverManager;
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


}
