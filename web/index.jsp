<%--
  Created by IntelliJ IDEA.
  User: JJR
  Date: 2019/3/6 0006
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Test</title>
  </head>
  <body>
  <form action="uploadphoto" method="post" enctype="multipart/form-data">
      name:<input type="text" name="name" /><br>
      img:<input type="file" name="file" /><br>
      <input type="submit" value="上传">
  </form>

  </body>
</html>
