/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.subject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.SubjectDao;
import com.yalexin.entity.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/addSubject.te", "/getAllSubject"})
public class SubjectServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/addSubject.te")) {
            addSubject(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/getAllSubject")) {
            getAllSubjects(req, resp);
        }
    }

    private void getAllSubjects(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=utf-8");
        SubjectDao subjectDao = new SubjectDao();
        ArrayList<Subject> list = subjectDao.getAllSubjectsByList();
        JSONArray subjects = new JSONArray();
        for (Subject s : list) {
            subjects.add(s);
        }
        JSONObject respData = new JSONObject();
        respData.put("subjects", subjects);
        resp.getWriter().println(respData);
    }

    private void addSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=utf-8");
        String jsonString = req.getReader().readLine();
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");

        Integer t_id = data.getInteger("t_id");
        String name = data.getString("name");
        String description = data.getString("description");

        Subject subject = new Subject();
        subject.setT_id(t_id);
        subject.setName(name);
        subject.setDescription(description);

        SubjectDao subjectDao = new SubjectDao();
        int result = subjectDao.addSubject(subject);
        JSONObject respData = new JSONObject();
        if (result > 0) {
            respData.put("addSuccess", true);
        } else {
            respData.put("addSuccess", false);
            respData.put("error", "名字重复或者题目描述超限");
        }
        resp.getWriter().println(respData);
    }
}
