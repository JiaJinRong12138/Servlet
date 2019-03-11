package com.servletTest;

import javax.jws.WebService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//@WebServlet(name = "ServletTest", urlPatterns = "/test", loadOnStartup = 10)
public class ServletTest extends javax.servlet.http.HttpServlet {

    private String name;
    private Integer age;
    final private String GS = "######################";

    public ServletTest(){
        System.out.println("Servlet 构造器");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.name = "OD";
        this.age = 20;
        System.out.println(GS + "初始化完成" + GS);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        //设置编码
        response.setCharacterEncoding("UTF-8");
        if(this.name.equals(request.getParameter("name")) &&
        this.age == Integer.valueOf(request.getParameter("age"))){
            request.getRequestDispatcher("success.html").forward(request,response);
        }else{
            response.setStatus(301);
            response.setHeader("location", "fail.html");

        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("Servlet 销毁");
    }
}
