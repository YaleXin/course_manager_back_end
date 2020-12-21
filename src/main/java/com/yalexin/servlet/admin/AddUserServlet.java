/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.StudentDao;
import com.yalexin.dao.TeacherDao;
import com.yalexin.entity.Student;
import com.yalexin.entity.Teacher;
import com.yalexin.uitl.Md5UtilSimple;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/addStudent.admin", "/addTeacher.admin", "/logout.admin"})
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        System.out.println(servletPath);
        if (servletPath.contains("/addStudent.admin")) addStudentPost(req, resp);
        else if (servletPath.contains("/addTeacher.admin")) addTeacherPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().contains("/logout.admin")) req.getSession().removeAttribute("adminUser");
    }

    private void addTeacherPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        BufferedReader reader = new BufferedReader(req.getReader());
        String json = reader.readLine();

        System.out.println("json = " + json);

        JSONObject jo = JSON.parseObject(json);
        JSONObject data = jo.getJSONObject("data").getJSONObject("teacher");
        System.out.println("data = " + data);
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = new Teacher();
        teacher.setId(data.getInteger("id"));
        teacher.setUsername(data.getString("username"));
        teacher.setPassword(Md5UtilSimple.md5(String.valueOf(teacher.getId())));
        teacher.setGender(data.getBoolean("gender"));
        int result = teacherDao.addTeacher(teacher);
        if (result > 0) {
            jsonObject.put("addSuccess", true);
        } else {
            jsonObject.put("addSuccess", false);
            jsonObject.put("error", "工号重复或者其他错误，请检查后重新提交");
        }

        out.println(jsonObject);
    }


    private void addStudentPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        BufferedReader reader = new BufferedReader(req.getReader());
        String json = reader.readLine();
        System.out.println("json = " + json);
        ;
        JSONObject jo = JSON.parseObject(json);
        JSONObject data = jo.getJSONObject("data").getJSONObject("student");
        System.out.println("data = " + data);
        StudentDao studentDao = new StudentDao();
        Student student = new Student();

        student.setId(data.getInteger("id"));
        student.setUsername(data.getString("username"));
        // 默认密码是学号
        student.setPassword(Md5UtilSimple.md5(String.valueOf(student.getId())));
        student.setClassName(data.getString("className"));
        student.setGender(data.getBoolean("gender"));
        int result = studentDao.addStudent(student);
        if (result > 0) {
            jsonObject.put("addSuccess", true);
        } else {
            jsonObject.put("addSuccess", false);
            jsonObject.put("error", "学号重复或者其他错误，请检查后重新提交");
        }

        out.println(jsonObject);
    }


}
