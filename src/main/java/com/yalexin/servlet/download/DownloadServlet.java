/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.download;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.ProgressDao;
import com.yalexin.entity.Progress;
import com.yalexin.uitl.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/download.user", "/getAllProgresses.te", "/getAllProgresses.st"})
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/download.user")) {
            downloadProgress(req, resp);
        }else if (servletPath.contains("/getAllProgresses.te")){
            getAllProgressesByTeacher(req, resp);
        }else if(servletPath.contains("/getAllProgresses.st")){
            getAllProgressesByTeamId(req, resp);
        }
    }

    private void getAllProgressesByTeamId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        System.out.println("jsonString = " + jsonString);
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");

        int teamId = data.getIntValue("teamId");
        ProgressDao progressDao = new ProgressDao();
        ArrayList<Progress> progresses = progressDao.getAllTeamsProgressesByTeamId(teamId);
        JSONObject respData = new JSONObject();
        respData.put("progresses", progresses);
        resp.getWriter().println(respData);
    }

    private void getAllProgressesByTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        System.out.println("jsonString = " + jsonString);
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");

        int teacherId = data.getIntValue("teacherId");
        ProgressDao progressDao = new ProgressDao();
        ArrayList<Progress> progresses = progressDao.getAllTeamsProgressesByTeacherId(teacherId);
        JSONObject respData = new JSONObject();
        respData.put("progresses", progresses);
        resp.getWriter().println(respData);
    }

    private void downloadProgress(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String jsonString = reader.readLine();
        System.out.println("jsonString = " + jsonString);
        JSONObject jo = JSON.parseObject(jsonString);
        JSONObject data = jo.getJSONObject("data");

        String fileName = data.getString("fileName");
        downloadChineseFileByOutputStream(resp, fileName);
    }

    private void downloadChineseFileByOutputStream(HttpServletResponse response, String filename)
            throws FileNotFoundException, IOException {
        response.setHeader("conten-type", "application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename));
        String realPath = Constants.FILE_PATH + "/" + filename;
        InputStream in = new FileInputStream(realPath);//?????????????????????
        int len = 0;
        byte[] buffer = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);//????????????????????????????????????????????????
        }
        in.close();
    }

}
