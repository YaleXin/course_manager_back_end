/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.upload;

import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.ProgressDao;
import com.yalexin.entity.Progress;
import com.yalexin.uitl.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@MultipartConfig(
        location = "/Users/yalexin/temp",
        maxFileSize = 1024 * 1024 * 10)
@WebServlet(urlPatterns = {"/upload.st"})
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/upload.st")) {
            upload(req, resp);
        }
    }

    private void upload(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTf-8");
        String teamId0 = req.getParameter("teamId");
        int teamId = Integer.parseInt(teamId0);
        String studentId = req.getParameter("studentId");
        System.out.println("teamId = " + teamId);
        Collection<Part> parts = req.getParts();
        Date date = new Date();
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        ProgressDao progressDao = new ProgressDao();
//        TeamDao teamDao = new TeamDao();
//        Team team = teamDao.getTeamByTeamId(Integer.parseInt(teamId));
        JSONObject respData = new JSONObject();
        for (Part part : parts) {
            String filename = getFilename(part);
            if (filename != null) {
                Progress progress = new Progress();
                System.out.println("filename = " + filename);
                String[] split = filename.split("\\.");
                String fileName1 = split[0] + "_" + date1.getTime() + "." + split[1];
                part.write(fileName1);

                progress.setContent(fileName1);
                progress.setStu_id(Integer.parseInt(studentId));
                progress.setDate(date1);
                progress.setTeam_id(teamId);
                int result = progressDao.addProgressByTeamId(teamId, progress);
                if (result > 0) {
                    respData.put("upload", true);
                    break;
                } else {
                    respData.put("upload", false);
                    break;
                }
            }
            resp.getWriter().println(respData);
        }
    }


    private String getFilename(Part part) {
        if (part == null) return null;
        String filename = part.getHeader("content-disposition");
        if (StringUtils.isBlank(filename)) return null;
        return StringUtils.substringBetween(filename, "filename=\"", "\"");
    }
}
