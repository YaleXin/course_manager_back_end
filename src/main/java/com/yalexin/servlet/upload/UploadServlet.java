/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.upload;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

@MultipartConfig(
        location = "/Users/yalexin",
        maxFileSize = 1024 * 1024 * 10)
@WebServlet(urlPatterns = {"/upload.st"})
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath.contains("/upload.st")){
            upload(req, resp);
        }
    }

    private void upload(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTf-8");
        String teamId = req.getParameter("teamId");
        System.out.println("teamId = " + teamId);
        Collection<Part> parts = req.getParts();

        for (Part part : parts) {
            String filename = getFilename(part);
            if (filename != null) {
                part.write(filename);
            }
        }
    }


    private String getFilename(Part part) {
        if (part == null) return null;
        String filename = part.getHeader("content-disposition");
        if (StringUtils.isBlank(filename)) return null;
        return StringUtils.substringBetween(filename, "filename=\"", "\"");
    }
}
