/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet;


import org.apache.catalina.core.ApplicationPart;
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

@MultipartConfig(
        location = "/Users/yalexin",
        maxFileSize = 1024 * 1024 * 10)
@WebServlet(urlPatterns = "/progressUploadFile")
public class progressUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTf-8");
        Collection<Part> parts = req.getParts();

        for (Part part :
                parts) {

            String filename = getFilename(part);
            part.write(filename);


        }
    }

    private String getFilename(Part part) {
        if (part == null) return null;
        String filename = part.getHeader("content-disposition");
        if (StringUtils.isBlank(filename)) return null;
        return StringUtils.substringBetween(filename, "filename=\"", "\"");
    }
}
