/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.StudentDao;
import com.yalexin.dao.TeacherDao;
import com.yalexin.entity.Student;
import com.yalexin.entity.Teacher;

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
import java.io.PrintWriter;


/**
 * 该类主要用于前端加密
 */
@WebServlet(urlPatterns = {"/login", "/getUserSession"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().contains("/getUserSession")) {
            resp.setContentType("text/json; charset=utf-8");
            PrintWriter out = resp.getWriter();
            HttpSession session = req.getSession();
            Object student = session.getAttribute("student");
            JSONObject respData = new JSONObject();
            if (student == null) {
                Object teacher = session.getAttribute("teacher");
                if (teacher == null) {
                    respData.put("logined", false);
                } else {
                    System.out.println("教师已登陆");
                    Teacher t = (Teacher) teacher;
                    respData.put("logined", true);
                    respData.put("user", t);
                }
            } else {
                System.out.println("学生已登陆");
                Student s = (Student) student;
                respData.put("logined", true);
                respData.put("user", s);
            }
            resp.getWriter().println(respData);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");
        //先创建字符流来读取json
        BufferedReader reader = new BufferedReader(req.getReader());
        String json = reader.readLine();
        System.out.println("json = " + json);
        //这里用fastjson来解析，将字符串转为Java的对象
        JSONObject jo = JSON.parseObject(json);
        /*
        前端传来的格式一般为：
         {
            data: {
                username:
                password:
                userType: student | teacher
                ...
                ...
                ...
            }
         }
         */
        JSONObject data = jo.getJSONObject("data");
        if (data.getBoolean("isStudent")) {
            studentLogin(req, resp, data);
        } else {
            teacherLogin(req, resp, data);
        }
        // 返回数据格式：
        /*
        {
            loginSuccess: true | false,
            user: {
                username: 学生学号 | 教师工号,
                nickname: 学生姓名 | 教师姓名
                gender: "男" | "女"
                class: "计科1801" | "软件1801" ... | null
            } | null,
            userParameter: {
                role: teacher | student
                isCaptain: true | false
                teamId: 1|2|3|...
            }
        }
         */
    }

    private void teacherLogin(HttpServletRequest req, HttpServletResponse resp, JSONObject data) throws IOException {
        String username = data.getString("username");
        String password = data.getString("password");
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        HttpSession session = req.getSession();
        UserKey userKey = (UserKey) session.getAttribute("userKey");
        // psw 是解析前端密文后的密码
        String psw = userKey.trimPreAndSuf(password);
        // 数据库中都是md5
        String md5 = Md5UtilSimple.md5(psw);
        TeacherDao teacherDao = new TeacherDao();
        Teacher teacherById = teacherDao.getTeacherById(Integer.parseInt(username));
        JSONObject respData = new JSONObject();
        if (teacherById == null) {
            respData.put("loginSuccess", false);
            respData.put("error", "该用户不存在");
        } else if (!teacherById.getPassword().equals(md5)) {
            respData.put("loginSuccess", false);
            respData.put("error", "密码错误");
        } else {
            respData.put("loginSuccess", true);
            teacherById.setPassword("");
            respData.put("user", teacherById);
            req.getSession().setAttribute("teacher", teacherById);
            req.getSession().removeAttribute("student");
            JSONObject userParameter = new JSONObject();
            userParameter.put("role", "teacher");
            respData.put("userParameter", userParameter);
        }
        System.out.println("教师请求登陆 返回的数据 :");
        System.out.println(respData);
        resp.getWriter().println(respData);
    }

    void studentLogin(HttpServletRequest req, HttpServletResponse resp, JSONObject data) throws IOException {
        String username = data.getString("username");
        String password = data.getString("password");
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        HttpSession session = req.getSession();
        UserKey userKey = (UserKey) session.getAttribute("userKey");
        // psw 是解析前端密文后的密码
        String psw = userKey.trimPreAndSuf(password);
        // 数据库中都是md5
        String md5 = Md5UtilSimple.md5(psw);
        StudentDao studentDao = new StudentDao();
        Student studentById = studentDao.getStudentById(Integer.parseInt(username));
        JSONObject respData = new JSONObject();
        if (studentById == null) {
            respData.put("loginSuccess", false);
            respData.put("error", "该用户不存在");
        } else if (!studentById.getPassword().equals(md5)) {
            respData.put("loginSuccess", false);
            respData.put("error", "密码错误");
        } else {
            respData.put("loginSuccess", true);
            studentById.setPassword("");
            respData.put("user", studentById);
            req.getSession().setAttribute("student", studentById);
            req.getSession().removeAttribute("teacher");
            JSONObject userParameter = new JSONObject();
            userParameter.put("role", "student");
            respData.put("userParameter", userParameter);
        }
        System.out.println("学生请求登陆 返回的数据 :");
        System.out.println(respData);
        resp.getWriter().println(respData);
    }
}