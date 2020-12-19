/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet;

import com.alibaba.fastjson.JSONObject;
import com.yalexin.uitl.UserKey;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/beforeLogin")
public class BeforeLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/json; charset=utf-8");
        PrintWriter out = response.getWriter();

        HttpSession session = req.getSession();

        System.out.println("doGet: session");
        System.out.println(session);
        // 获取随机密钥对象
        UserKey userKey = (UserKey) session.getAttribute("userKey");
        if (userKey == null) {
            userKey = new UserKey();
            session.setAttribute("userKey", userKey);
        }
        JSONObject data = new JSONObject();
        // 返回密钥前缀后密钥后缀
        data.put("prefixKey", userKey.getPrefixKey());
        data.put("suffixKey", userKey.getSuffixKey());
        out.println(data);
    }
}
