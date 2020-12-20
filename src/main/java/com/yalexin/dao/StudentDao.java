/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.Student;

import java.sql.DriverManager;
import java.sql.SQLException;

public class StudentDao extends BaseDao {
    public StudentDao() {
        super("student");
    }

    public int addStudent(Student student) {
        if (student == null) return 0;
        int result = 0;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "insert into " + TABLE_NAME + "(id,name,gender,class, password) values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getUsername());
            preparedStatement.setBoolean(3, student.isGender());
            preparedStatement.setString(4, student.getClassName());
            preparedStatement.setString(5, student.getPassword());
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
