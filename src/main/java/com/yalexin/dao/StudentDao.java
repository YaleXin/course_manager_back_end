/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.Student;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public Student getStudentById(int id) {
        if (id <= 0) return null;
        Student student = null;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "select * from " + TABLE_NAME + " where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) student = getOneStudentByResultSet(resultSet);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            closeHelper();
            return student;
        }
    }
    public int updateStudent(Student student){
        if (student == null || student.getId() <= 0)return 0;
        int updateResult = 0;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "update " + TABLE_NAME + " set password=?,birthday=? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getPassword());
            preparedStatement.setDate(2,student.getBirthday());
            preparedStatement.setInt(3, student.getId());
            updateResult  = preparedStatement.executeUpdate();
            System.out.println(preparedStatement);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            closeHelper();
            return updateResult;
        }
    }
    private Student getOneStudentByResultSet(ResultSet resultSet) {
        Student student = new Student();
        try {
            student.setId(resultSet.getInt("id"));
            student.setUsername(resultSet.getString("name"));
            student.setPassword(resultSet.getString("password"));
            student.setClassName(resultSet.getString("class"));
            student.setBirthday(resultSet.getDate("birthday"));
            student.setGender(resultSet.getBoolean("gender"));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return student;
    }

    public ArrayList<Student> getAllStudentsAsList(){
        ArrayList<Student> students = new ArrayList<>();
        int updateResult = 0;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "select * from " + TABLE_NAME ;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())students.add(getOneStudentByResultSet(resultSet));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            closeHelper();
        }
        return students;
    }

}
