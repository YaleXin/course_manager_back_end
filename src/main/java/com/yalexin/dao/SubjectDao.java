/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.Subject;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectDao extends BaseDao {
    public SubjectDao() {
        super("subject");
    }

    public int addSubject(Subject subject) {
        if (subject == null) return 0;
        int result = 0;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "insert into " + TABLE_NAME + "(name,description,category, t_id) values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, subject.getName());
            preparedStatement.setString(2, subject.getDescription());
            preparedStatement.setString(3, subject.getCategory());
            preparedStatement.setInt(4, subject.getT_id());
            result = preparedStatement.executeUpdate();
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeHelper();
            return result;
        }
    }

    public int updateSubject(Subject newSubject) {
        if (newSubject == null) return 0;
        int result = 0;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "update " + TABLE_NAME + "set name=?, description=? where id=? and t_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newSubject.getName());
            preparedStatement.setString(2, newSubject.getDescription());
            preparedStatement.setInt(3, newSubject.getId());
            preparedStatement.setInt(4, newSubject.getT_id());
            result = preparedStatement.executeUpdate();
            System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeHelper();
            return result;
        }
    }

    public ArrayList<Subject> getAllSubjectsByList() {
        ArrayList<Subject> list = new ArrayList<>();
        TeacherDao teacherDao = new TeacherDao();
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
//            SELECT `subject`.*, teacher.`name` as teacherName from `subject`,teacher WHERE subject.t_id=teacher.id;
            String sql = "select " + teacherDao.getTABLE_NAME() + ".name as teacherName," + TABLE_NAME + ".* from " + teacherDao.getTABLE_NAME() + "," + TABLE_NAME + " " +
                    "where " + teacherDao.getTABLE_NAME() + ".id=" + TABLE_NAME + ".t_id";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            System.out.println(preparedStatement);
            while (resultSet.next()) list.add(getOneSubjectByResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeHelper();
            return list;
        }
    }

    private Subject getOneSubjectByResultSet(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();
        subject.setId(resultSet.getInt("id"));
        subject.setName(resultSet.getString("name"));
        subject.setDescription(resultSet.getString("description"));
        subject.setCategory(resultSet.getString("category"));
        subject.setT_id(resultSet.getInt("t_id"));
        subject.setTeacherName(resultSet.getString("teacherName"));
        return subject;
    }

    public Subject getSubjectByName(String name) {
        if (name == null || name.trim().equals("")) ;
        Subject subject = null;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "select * from " + TABLE_NAME + " where name=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) subject = getOneSubjectByResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeHelper();
            return subject;
        }
    }

    public int deleteSubjectById(int id) {
        if (id <= 0) return 0;
        int result = 0;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "delete from " + TABLE_NAME + " where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
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
