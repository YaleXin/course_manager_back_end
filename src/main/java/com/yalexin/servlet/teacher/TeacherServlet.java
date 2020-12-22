/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.teacher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.TeacherDao;
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

@WebServlet(urlPatterns = {"/modifyTeacher.te"})
public class TeacherServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/modifyTeacher.te")) {
            modiyfInfo(req, resp);
        }
    }

    private void modiyfInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=utf-8");
        System.out.println("--------  教师修改信息 ------");
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");
        System.out.println("data = " + data);

        Integer id = data.getInteger("id");
        String oldPass_raw = data.getString("oldPass");
        String newPass_raw = data.getString("newPass");

        HttpSession session = req.getSession();
        UserKey userKey = (UserKey) session.getAttribute("userKey");

        String md5_old = Md5UtilSimple.md5(userKey.trimPreAndSuf(oldPass_raw));
        String md5_new = Md5UtilSimple.md5(userKey.trimPreAndSuf(newPass_raw));

        TeacherDao teacherDao = new TeacherDao();
        Teacher teacherById = teacherDao.getTeacherById(id);
        JSONObject respData = new JSONObject();
        if (teacherById == null) {
            respData.put("updated", false);
            respData.put("error", "用户不存在");
        } else if (!teacherById.getPassword().equals(md5_old)) {
            respData.put("updated", false);
            respData.put("error", "原密码错误");
        } else {
            teacherById.setPassword(md5_new);
            int result = teacherDao.updateTeacher(teacherById);
            if (result > 0) {
                respData.put("updated", true);
                teacherById.setPassword("");
                session.setAttribute("teacher", teacherById);
            } else {
                respData.put("updated", false);
                respData.put("error", "信息修改失败");
            }
        }
        resp.getWriter().println(respData);
    }
}
