/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yalexin.dao.AdminDao;
import com.yalexin.entity.AdminUser;
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
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/adminLogin")
public class AdminLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AdminLoginServlet");
        resp.setContentType("text/json; charset=utf-8");
        //先创建字符流来读取json
        BufferedReader reader = new BufferedReader(req.getReader());
        String json = reader.readLine();
        System.out.println("json = " + json);
        //这里用fastjson来解析，将字符串转为Java的对象
        JSONObject jo = JSON.parseObject(json);
        System.out.println("jo = " + jo);

        JSONObject data = jo.getJSONObject("data");
        System.out.println("data = " + data);
        int a = Integer.parseInt("122");

        String username = data.getString("username");
        String password = data.getString("password");
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        HttpSession session = req.getSession();
        UserKey userKey = (UserKey) session.getAttribute("userKey");

        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        if (userKey == null) {
            jsonObject.put("loginSuccess", false);
//            out.println(jsonObject);
        } else {
            // 对前端传来的密文解密
            String psw = userKey.trimPreAndSuf(password);
            // TODO: 2020/12/18 在这里通过username + psw 查表
            // 对数据进行加密运算 与数据库中进行比对
            String md5Psw = Md5UtilSimple.md5(psw);
            AdminDao adminDao = new AdminDao();
            AdminUser adminUser = adminDao.getAdminUserByUsername(username);
            if (adminUser == null) {
                jsonObject.put("loginSuccess", false);
                jsonObject.put("error", "用户不存在");
//                out.println(jsonObject);
            } else if (!adminUser.getPassword().equals(md5Psw)) {
                jsonObject.put("loginSuccess", false);
                jsonObject.put("error", "密码错误");
//                out.println(jsonObject);
            } else {
                session.removeAttribute("userKey");
                session.setAttribute("adminUser", adminUser);
                adminUser.setPassword("");
                jsonObject.put("loginSuccess", true);
                jsonObject.put("user", adminUser);
//                out.println(jsonObject);
            }
            System.out.println("返回的数据");
            System.out.println(jsonObject);
            out.println(jsonObject);
        }

    }
}
