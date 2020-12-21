/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.student;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.StudentDao;
import com.yalexin.entity.Student;
import com.yalexin.uitl.Md5UtilSimple;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = {"/modifyStudent.st", "/studentLogout"})
public class StudentServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().contains("/studentLogout")) {
            req.getSession().removeAttribute("student");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/modifyStudent.st")) {
            modiyfInfo(req, resp);
        }
    }

    private void modiyfInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=utf-8");
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");
        System.out.println("data = " + data);

        StudentDao studentDao = new StudentDao();
//        Student studentById = studentDao.getStudentById(data.getInteger("id"));
//        if (studentById == null){

//        }else if(Md5UtilSimple.md5()) {
//
//        }

    }

}
