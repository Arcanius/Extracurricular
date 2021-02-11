<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="users"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
    </head>
    <body>
    	<jsp:include page="nav.jsp"/>
        <table border="1">
            <tr>
                <th>ID</th>
                <th><fmt:message key="username"/></th>
                <th><fmt:message key="name_en"/></th>
                <th><fmt:message key="name_uk"/></th>
                <th><fmt:message key="role"/></th>
                <th><fmt:message key="banned"/></th>
            </tr>
            <c:forEach items="${requestScope['users']}" var="usr">
                <tr>
                    <td>${usr.id}</td>
                    <td>${usr.login}</td>
                    <td>${usr.nameEn}</td>
                    <td>${usr.nameUk}</td>
                    <td>${usr.role}</td>
                    <td>${usr.banned}</td>
                    <td><a href="/users?id=${usr.id}"><fmt:message key="edit"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>