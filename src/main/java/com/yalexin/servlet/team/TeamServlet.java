/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.team;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.TeamDao;
import com.yalexin.entity.Team;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = {"/getTeam"})
public class TeamServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/getTeam")) {
            getTeamByStudent(req, resp);
        }
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
