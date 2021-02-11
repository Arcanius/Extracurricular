<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="edit_user"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
    </head>
    <body>
    	<jsp:include page="nav.jsp"/>
    	<form method="post">
    		<input type="hidden" name="id" value="${requestScope['user'].id}">
    		<p><fmt:message key="user"/>: ${requestScope['user'].login}</p>
    		<select name="role">
    			<option value="STUDENT">STUDENT</option>
    			<option value="TEACHER">TEACHER</option>
    			<option value="ADMIN">ADMIN</option>
    		</select>
    		<br><br>
    		<select name="banned">
    			<option value="false"><fmt:message key="active"/></option>
    			<option value="true"><fmt:message key="banned"/></option>
    		</select>
    		<br><br>
    		<button type="submit"><fmt:message key="submit"/></button>
    	</form>
    </body>
</html>
