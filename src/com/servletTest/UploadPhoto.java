package com.servletTest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "UploadPhoto", urlPatterns = "/uploadphoto", loadOnStartup = 1)
public class UploadPhoto extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file = null;
        try{
//            定义磁盘文件工厂
            DiskFileItemFactory dfif = new DiskFileItemFactory();
//            处理表单数据
            ServletFileUpload sfu = new ServletFileUpload(dfif);
//            定义上传文件大小 ---- 3m
            dfif.setSizeThreshold(1024*1024*3);
            List items = null;
            try{

                items = sfu.parseRequest(req);

            }catch (Exception e){
                e.printStackTrace();
            }

//            定义迭代器
            Iterator itrt = items.iterator();

            while (itrt.hasNext()){
                FileItem item = (FileItem) itrt.next();
                if(!item.isFormField()){
//                    根据时间戳插件头像文件
                    file = System.currentTimeMillis() + ".jpg";
//                    定义文件路径
                    String path = req.getServletContext().getRealPath("img");
//                    String path = "D:\\java_work\\IDEA_work\\How2J_Servlet_class_1\\out\\artifacts\\How2J_Servlet_class_1_war_exploded\\img";
                    System.out.println(path);
                    File f = new File(path, file);
//                    不存在目录则创建
                    f.getParentFile().mkdirs();

//                    通过item.getInputStream()获取浏览器上传的文件的输入流
                    InputStream is = item.getInputStream();

//                    复制文件
                    FileOutputStream fos = new FileOutputStream(f);
//                    设置读取大小 --- 1m
                    byte[] bytes = new byte[1024*1024];
//                    定义长度
                    int length = 0;
                    while (-1 != (length = is.read(bytes))){
//                        写入文件
                        fos.write(bytes, 0, length);
                    }
//                    关闭流
                    fos.close();

                } else {
                    System.out.println(item.getFieldName());
                    String value = item.getString();
                    value = new String(value.getBytes("ISO-8859-1"), "utf-8");
                    System.out.println(value);
                }
                String html = "<img src=\"img/%s\" />";
                resp.setContentType("text/html");
                PrintWriter pw = resp.getWriter();
                pw.format(html, file);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
