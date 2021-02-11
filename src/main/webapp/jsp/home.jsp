<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="extracurricular"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
    </head>
    <body style="text-align: center">
        <jsp:include page="nav.jsp"/>
        <h1><fmt:message key="welcome"/>!</h1>
        <c:if test="${sessionScope['user'].banned}">
        	<h2 style="color:red"><fmt:message key="blocked"/></h2>
        </c:if>
    </body>
</html>