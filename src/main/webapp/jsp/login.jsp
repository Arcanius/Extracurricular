<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="login"/></title>
    </head>
    <body style="text-align: center">
        <form method="post">
            <p><fmt:message key="login"/></p>
            <label for="login"><fmt:message key="username"/>:</label>
            <input type="text" name="login" id="login">
            <p>
                <c:if test="${requestScope['login.wrong'] != null}">
                    <fmt:message key="${requestScope['login.wrong']}"/>
                </c:if>
            </p>

            <label for="password"><fmt:message key="password"/>:</label>
            <input type="password" name="password" id="password">
            <p>
                <c:if test="${requestScope['password.wrong'] != null}">
                    <fmt:message key="${requestScope['password.wrong']}"/>
                </c:if>
            </p>

            <button type="submit"><fmt:message key="login"/></button>
            <br>
            <a href="/registration"><fmt:message key="registration"/></a>
        </form>
    </body>
</html>
