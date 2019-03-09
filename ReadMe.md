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
