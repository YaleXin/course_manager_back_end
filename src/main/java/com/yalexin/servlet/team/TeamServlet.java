/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.team;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.TeamDao;
import com.yalexin.entity.Student;
import com.yalexin.entity.Team;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = {"/getTeam.st", "/addTeam.st"})
public class TeamServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/getTeam")) {
            getTeamByStudent(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/addTeam.st")) {
            addTeam(req, resp);
        }
    }

    private void addTeam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();

        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");
        int captain = data.getIntValue("captain");
        int subject = data.getIntValue("subject");
        JSONArray jsonArray = data.getJSONArray("members");
        int[] members = new int[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            Integer tem = (Integer) jsonArray.get(i);
            members[i] = tem;
        }
        TeamDao teamDao = new TeamDao();
        int result = teamDao.addTeadByCaptain(captain, members, subject);
        JSONObject respData = new JSONObject();
        if (result > 0) {
            Team teamByCaptainId = teamDao.getTeamByCaptainId(captain);
            Student student = (Student) req.getSession().getAttribute("student");
            student.setTeam(teamByCaptainId);
            req.getSession().setAttribute("student", student);
            respData.put("addSuccess", true);
        } else {
            respData.put("addSuccess", false);
        }
        resp.getWriter().println(respData);
    }

    private void getTeamByStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType("text/json; charset=utf-8");
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();

        System.out.println("jsonString = " + jsonString);

        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");

        Integer studentId = data.getInteger("studentId");
        TeamDao teamDao = new TeamDao();
        Team teamByOneStudnet = teamDao.getTeamByOneStudnet(studentId);

        JSONObject respData = new JSONObject();
        if (teamByOneStudnet == null) {
            respData.put("hasTeam", false);
        } else {
            respData.put("hasTeam", true);
            respData.put("team", teamByOneStudnet);
        }
        resp.getWriter().println(respData);
    }
}
