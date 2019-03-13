##Servlet 学习笔记及遇到的问题

* servlet 添加映射的方式：
    >1、web.xml添加：
    ```
    <servlet>
        <servlet-name>ServletTest</servlet-name>
        <servlet-class>com.servletTest.ServletTest</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>ServletTest</servlet-name>
        <url-pattern>/test</url-pattern>
    </servlet-mapping>
    ```
    >2、注解添加：
    ```
    @WebServlet(name = "ServletTest", urlPatterns = "/test")
    ```
    ![注解](https://images2018.cnblogs.com/blog/1360356/201804/1360356-20180425164552175-960983124.png)
* 出现乱码，则将将编码设置为UTF-8

```
    //设置编码
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
```

##Java Web运行流程
![流程图](http://stepimagewm.how2j.cn/7461.png)

##Servlet 内的方法
1. doPost():处理来自前端的Post请求
2. doGet():处理来自前端的Get请求
3. Service():在执行doGet()和doPost()时是先执行该方法，由service()判断，是执行doGet()还是执行doPost(),也可以直接重写Service()方法，则不需要再重写doGet()和doPost()

##Servlet 生命周期
* ###生命周期：
![生命周期](http://stepimagewm.how2j.cn/1593.png)
* 执行顺序：
    1. 构造器Servlet()
    2. 初始化init()
    3. service()、doGet()、doPost()
    4. 销毁destory()
    5. 回收(垃圾回收)
    
## Servlet跳转
1. 服务端内部跳转(转发)：
    ```
    request.getRequestDispatcher("htmlFileName.html").forward(request, response);
    ```
    
2. 客户端跳转（重定向）：
    ```
    response.sendRedirect("fail.html");
    ```
   ###区别
   ![区别](http://stepimagewm.how2j.cn/1602.png)
   
 
##Servlet 自启动
####在Servlet启动时调用初始化方法，实现Servlet自启动
####方法：
1、 web.xml
```
    <servlet>
            <servlet-name>ServletTest</servlet-name>
            <servlet-class>com.servletTest.ServletTest</servlet-class>
            <!--实现Servlet自启动-->
            <load-on-startup>优先级</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>ServletTest</servlet-name>
            <url-pattern>/test</url-pattern>
        </servlet-mapping>
```
2、注解
```
@WebServlet(loadOnStartup = 优先级)
```
[注：数值越低，优先级越低]()


## Request常用方法
###一、常用方法
>* getRequestURL():获取请求的完整的URL
>* getRequestURI():获取请求的部分资源名（去掉请求协议和主机名）
>* getQueryString():获取请求行的参数（只能显示Get请求的参数）
>* getRemoteAddr():获取客户端IP地址
>* getRemotePort():获取客户端请求端口
>* getLocalAddr():获取服务器IP
>* getLocalName():获取服务器的主机名
>* getLocalPort():获取服务器端口号
>* getMethod():获取请求方法

###二、获取参数
>* getParameter(String name):获取参数名为name的值
>* getParameterValue(String name):获取具有多个值的参数（如CheckBox），并返回String[]值
>* getparameterMap():遍历所有参数，并返回Map<String, String[]>值

###三、获取请求头
>* getHeater(String name):获取请求头信息
   >>请求头信息：
   >>* host:主机地址
   >>* user-agent:浏览器基本资料
   >>* accept:浏览器接受的数据类型
   >>* accept-language:浏览器接受的语言
   >>* accept-encoding:浏览器接受的[压缩方式]()
   >>* connection:是否保持链接
   >>* cache-control:缓存时限
>* getHeaderNames():获取所有请求头信息

###四、服务器传参
>* setAttribute:转递参数
>* getAttribute:获取

## Response用法
>1. 获取PrintWriter对象
`PrintWriter pw = response.getWriter();`
>2. 设置响应格式 --- 可实现下载功能
`response.setContentType("格式");`
>3. 设置编码
>>`response.setContentType("text/html;charset=UTF-8");`
>>`response.setCharacterEncoding("UTF-8");`
>4. 301或者302跳转
>>客户端两种跳转：
>>>302：临时跳转
>>>`response.sendRedirect("url");`
>>>301：永久性跳转
>>```
>>>response.setStatus(301);
>>>response.setHeader("location", "url);
>>>```
>5. 设置不使用缓存
```
response.setDateHeader("Expries", 0);
        response.setHeader("Cache-control", "no-cache");
        response.setHeader("pragma", "no-cache");
```
##Servlet上传文件
>###这里先上代码：
>>HTML
```
<!-- form 表单 enctype属性：告诉服务器怎么对上传资源文件进行编码 -->
<form action="uploadphoto" method="post" enctype="multipart/form-data">
      name:<input type="text" name="name" /><br>
      img:<input type="file" name="file" /><br>
      <input type="submit" value="上传">
  </form>

```
>>>JAVA
```
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

```
>> ### 注意：
>>>1. enctype属性:告知服务器对表单上传资源进行编码
>>>2. `req.getServletContext().getRealPath("DirectoryName");`找到名为DirectoryName的文件夹在服务器的绝对路径

