/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.yalexin.entity.User;
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
import java.sql.Date;

/**
 * 该类主要用于前端加密
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet{


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //先创建字符流来读取json
        BufferedReader reader = new BufferedReader(req.getReader());
        String json = reader.readLine();
        System.out.println("json = " + json);
        //这里用fastjson来解析，将字符串转为Java的对象
        JSONObject jo = JSON.parseObject(json);
        System.out.println("jo = " + jo);
        /*

        前端传来的格式一般为：
         {
            data: {
                username:
                password:
                ...
                ...
                ...
            }
         }

         */
        JSONObject data = jo.getJSONObject("data");
        System.out.println("data = " + data);
        int a = Integer.parseInt("122");

        String username = data.getString("username");
        String password = data.getString("password");
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        HttpSession session = req.getSession();
        UserKey userKey = (UserKey) session.getAttribute("userKey");
        String psw = userKey.trimPreAndSuf(password);


        // TODO: 2020/12/18 在这里通过username + psw 查表
        // 存在用户 且密码验证成功 则将用户对象存到session中
        // 返回数据格式：
        /*

        {
            loginSuccess: true | false,
            user: {
                username: 学生学号 | 教师工号,
                nickname: 学生姓名 | 教师姓名
                type: "student" | "teacher"
                isCaptain: true | false
                gender: "男" | "女"
                class: "计科1801" | "软件1801" ... | null
                teamId: 1|2|3|...
            } | null,
        }

         */

        System.out.println(psw);
        resp.setContentType("text/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();

//
//        User user = new User();
//        user.setNickname("黄阿信");
//        user.setId(181303209);
//        user.setAge(18);
//        user.setBirthday(new Date(new java.util.Date().getTime()));
//        jsonObject.put("user",user);



        out.println(jsonObject);
    }
}