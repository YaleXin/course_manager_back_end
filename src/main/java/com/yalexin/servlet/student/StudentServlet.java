/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.student;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.StudentDao;
import com.yalexin.entity.Student;
import com.yalexin.uitl.Md5UtilSimple;
import com.yalexin.uitl.UserKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/modifyStudent.st", "/getAllStudents.st"})
public class StudentServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/modifyStudent.st")) {
            modiyfInfo(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/getAllStudents.st")) {
            getAllStudents(req, resp);
        }
    }

    private void getAllStudents(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONArray students = new JSONArray();
        JSONObject respData = new JSONObject();
        StudentDao studentDao = new StudentDao();
        ArrayList<Student> allStudentsAsList = studentDao.getAllStudentsAsList();
        for (Student s : allStudentsAsList) {
            s.setPassword("");
            students.add(s);
        }
        respData.put("students", students);
        resp.getWriter().println(respData);
    }

    private void modiyfInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=utf-8");
        System.out.println("--------  学生修改信息 ------");
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");
        System.out.println("data = " + data);

        Integer id = data.getInteger("id");
        Long newBirthday = data.getLong("birthday");
        String oldPass_raw = data.getString("oldPass");
        String newPass_raw = data.getString("newPass");

        HttpSession session = req.getSession();
        UserKey userKey = (UserKey) session.getAttribute("userKey");

        String md5_old = Md5UtilSimple.md5(userKey.trimPreAndSuf(oldPass_raw));
        String md5_new = Md5UtilSimple.md5(userKey.trimPreAndSuf(newPass_raw));

        StudentDao studentDao = new StudentDao();
        Student studentById = studentDao.getStudentById(id);
        JSONObject respData = new JSONObject();
        if (studentById == null) {
            respData.put("updated", false);
            respData.put("error", "用户不存在");
        } else if (!studentById.getPassword().equals(md5_old)) {
            respData.put("updated", false);
            respData.put("error", "原密码错误");
        } else {
            studentById.setBirthday(new Date(newBirthday));
            studentById.setPassword(md5_new);
            int result = studentDao.updateStudent(studentById);
            if (result > 0) {
                respData.put("updated", true);
                studentById.setPassword("");
                session.setAttribute("student", studentById);
            } else {
                respData.put("updated", false);
                respData.put("error", "信息修改失败");
            }
        }
        resp.getWriter().println(respData);
    }

}
