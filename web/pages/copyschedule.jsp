<%-- 
    Document   : copyschedule
    Created on : 2016-10-1, 22:09:16
    Author     : hippo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
         alert("${Message}");
         window.location.href="<%=request.getContextPath()%>/nocturne/crudSchedule";
        </script>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
