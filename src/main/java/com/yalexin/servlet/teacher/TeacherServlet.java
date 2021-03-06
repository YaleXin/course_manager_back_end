/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.teacher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.TeacherDao;
import com.yalexin.dao.TeamDao;
import com.yalexin.entity.Student;
import com.yalexin.entity.Teacher;
import com.yalexin.entity.Team;
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
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/modifyTeacher.te", "/getNotApprovedTeams.te",
        "/rejectTeam.te", "/acceptTeam.te", "/getHasNoScoreTeams.te",
        "/getHasScoreTeams.te", "/setScore.te"})
public class TeacherServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/modifyTeacher.te")) {
            modiyfInfo(req, resp);
        } else if (servletPath.contains("/getNotApprovedTeams.te")) {
            getNotApprovedTeams(req, resp, -1, null);
        } else if (servletPath.contains("/acceptTeam.te")) {
            acceptTeam(req, resp);
        } else if (servletPath.contains("/getHasNoScoreTeams.te")) {
            getHasNoScoreTeams(req, resp);
        } else if (servletPath.contains("/getHasScoreTeams.te")) {
            getHasScore(req, resp);
        }else if (servletPath.contains("/setScore.te")) {
            setScore(req, resp);
        }
    }

    private void setScore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();

        System.out.println("jsonString = " + jsonString);

        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");

        int teamId = data.getIntValue("teamId");
        int score = data.getIntValue("score");
        TeamDao teamDao = new TeamDao();
        int result = teamDao.setTeamScoreByTeamId(teamId, score);
        JSONObject respData = new JSONObject();
        if (result > 0 ){
            respData.put("setScore", true);
            HttpSession session = req.getSession();
            Student student = (Student) session.getAttribute("student");
            session.removeAttribute("student");
            student.getTeam().setScore(score);
            student.getTeam().setScored(true);
            session.setAttribute("student", student);
        }else{
            respData.put("setScore", false);
        }

        resp.getWriter().println(respData);
    }

    private void getHasScore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");

        int teacherId = data.getIntValue("teacherId");
        TeamDao teamDao = new TeamDao();
        ArrayList<Team> teams = teamDao.getTeamsWithScoreByTeacherId(teacherId);
        JSONObject respData = new JSONObject();
        respData.put("hasScoreTeams", teams);
        resp.getWriter().println(respData);
    }

    private void getHasNoScoreTeams(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");

        int teacherId = data.getIntValue("teacherId");
        TeamDao teamDao = new TeamDao();
        ArrayList<Team> teams = teamDao.getTeamsWithNullScoreByTeacherId(teacherId);
        JSONObject respData = new JSONObject();
        respData.put("hasNoScoreTeams", teams);
        resp.getWriter().println(respData);
    }

    private void acceptTeam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();

        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");
        int teamId = data.getIntValue("teamId");
        TeamDao teamDao = new TeamDao();
        int result = teamDao.acceptTeamByTeamId(teamId);
        JSONObject respData = new JSONObject();
        if (result > 0) {
            respData.put("success", true);
        } else {
            respData.put("success", false);
        }
        resp.getWriter().println(respData);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/rejectTeam.te")) {
            rejectTeam(req, resp);
        }
    }

    private void rejectTeam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        System.out.println("jsonString = " + jsonString);
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");
        int teamId = data.getIntValue("teamId");
        TeamDao teamDao = new TeamDao();
        int result = teamDao.rejectTeamByTeamId(teamId);
        JSONObject respData = new JSONObject();
        if (result > 0) {
            respData.put("success", true);
        } else {
            respData.put("success", false);
        }
        resp.getWriter().println(respData);
    }

    private void getNotApprovedTeams(HttpServletRequest req, HttpServletResponse resp, int teacherId1, JSONObject respData1) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();

        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");

        int teacherId = data.getIntValue("teacherId");
        TeamDao teamDao = new TeamDao();
        ArrayList<Team> teams = teamDao.getNotApprovedTeamsByTeacherId(teacherId);

        JSONObject respData = new JSONObject();
        respData.put("notApprovedTeams", teams);
        resp.getWriter().println(respData);
    }

    private void modiyfInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json; charset=utf-8");
        System.out.println("--------  ?????????????????? ------");
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
            respData.put("error", "???????????????");
        } else if (!teacherById.getPassword().equals(md5_old)) {
            respData.put("updated", false);
            respData.put("error", "???????????????");
        } else {
            teacherById.setPassword(md5_new);
            int result = teacherDao.updateTeacher(teacherById);
            if (result > 0) {
                respData.put("updated", true);
                teacherById.setPassword("");
                session.setAttribute("teacher", teacherById);
            } else {
                respData.put("updated", false);
                respData.put("error", "??????????????????");
            }
        }
        resp.getWriter().println(respData);
    }
}
