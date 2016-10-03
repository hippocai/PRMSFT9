<%-- 
    Document   : insertscheduleresult
    Created on : 2016-9-28, 19:43:31
    Author     : hippo
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
var message="${Message}";
var error=${error};
alert(message);
if(!error){
    parent.window.location.href="<%=request.getContextPath()%>/nocturne/crudSchedule";
}
</script>
</head>
<body>
</body>
</html>
