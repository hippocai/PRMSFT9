<%-- 
    Document   : setupuser
    Created on : Sep 27, 2016, 8:07:57 PM
    Author     : Guo Qi
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<fmt:setBundle basename="ApplicationResources" />

<title><fmt:message key="title.setupuser" /></title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/nocturne/enteruser" method=post>
            <center>
                <table cellpadding ="5" cellspacing="3" border="1">
                    <tr>
                        <td><fmt:message key = "label.cruduser.id" /> </td>
                        <td> <c:if test="${param['insert']==true}">
                                <input type="text" name="id" value="${param['id']}" size=15 maxlength=30>
                                <input type="hidden" name="insert" value="true">
                             </c:if>
                             <c:if test="${param['insert'] == 'false'}">
                                    <input type="text" name="id" value="${param['id']}" size=15 maxlength=30 readonly="readonly">
                                    <input type="hidden" name="insert" value="false">
                             </c:if></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="label.cruduser.name"/></td>
                        <td><input type="text" name="name" value="${param['name']}" size=30 maxlength=200></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="label.cruduser.password"/></td>
                        <td><input type="text" name="password" value="${param['password']}" size=15 maxlength=30> </td>
                    </tr>
                    <tr>
                        <td><fmt:message key="label.cruduser.role"/></td>
                        <td><input type="text" name="role" value="${param['role']}" size=30 maxlength=200></td>
                    </tr>
                                    
                    
                </table>
            </center>
            <input type="submit" value="Submit"> <input type="reset" value="Reset">
        </form>
                    
</body>
</html>
