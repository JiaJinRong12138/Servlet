package com.servletTest;

import javax.jws.WebService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

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
        this.name = "郁金香OD";
        this.age = 20;
        System.out.println(GS + "初始化完成" + GS);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        /*request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        String name = request.getParameter("name");
        Integer age = Integer.valueOf(request.getParameter("age"));
        System.out.println(name + age);
        String html;
        if(name.equals("郁金香OD")){
            html = "<h1 style=\"color: green\">登陆成功</h1>";
        }else{
            html = "<h1 style=\"color: red\">登陆失败</h1>";
        }
        pw.print(html);*/
/*
//        实现跳转
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("name");
        Integer age = Integer.valueOf(request.getParameter("age"));
        if(name.equals(name) && 20 == age) {
//            request.getRequestDispatcher("success.html").forward(request, response);
            response.sendRedirect("success.html");
        }else{
//            request.getRequestDispatcher("fail.html").forward(request, response);
            response.sendRedirect("fail.html");
        }*/


        //Servlet request方法
        System.out.println("request 方法：");
        System.out.println("request.getRequestURL():" + request.getRequestURL());
        System.out.println("request.getRequestURI():"+request.getRequestURI());
        System.out.println("request.getQueryString():"+request.getQueryString());
        System.out.println("request.getRemoteAddr()"+request.getRemoteAddr());
        System.out.println("request.getRemoteHost():"+request.getRemoteHost());
        System.out.println("request.getRemotePort():"+request.getRemotePort());
        System.out.println("request.getLocalAddr():"+request.getLocalAddr());
        System.out.println("request.getLocalName():"+request.getLocalName());
        System.out.println("request.getLocalPort():"+request.getLocalPort());
        System.out.println("request.getMethod():"+request.getMethod());
        System.out.println("request.getAuthType():"+request.getAuthType());
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("Servlet 销毁");
    }
}
