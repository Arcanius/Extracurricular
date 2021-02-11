<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="registration"/></title>
    </head>
    <body style="text-align: center">
        <form method="post">
            <h1><fmt:message key="registration"/></h1>

            <label for="login"><fmt:message key="username"/>:</label>
            <input type="text" name="login" id="login">
            <p>
                <c:if test="${requestScope['login.error'] != null}">
                    <fmt:message key="${requestScope['login.error']}"/>
                </c:if>
            </p>

            <label for="password"><fmt:message key="password"/>:</label>
            <input type="password" name="password" id="password">
            <p>
                <c:if test="${requestScope['password.error'] != null}">
                    <fmt:message key="${requestScope['password.error']}"/>
                </c:if>
            </p>

            <label for="password"><fmt:message key="confirm_password"/>:</label>
            <input type="password" name="confirmPassword" id="confirmPassword">
            <p>
                <c:if test="${requestScope['confirmPassword.error'] != null}">
                    <fmt:message key="${requestScope['confirmPassword.error']}"/>
                </c:if>
            </p>

            <label for="name_en"><fmt:message key="name_en"/>:</label>
            <input type="text" name="name_en" id="name_en">
            <p>
                <c:if test="${requestScope['nameEn.error'] != null}">
                    <fmt:message key="${requestScope['nameEn.error']}"/>
                </c:if>
            </p>

            <label for="name_uk"><fmt:message key="name_uk"/>:</label>
            <input type="text" name="name_uk" id="name_uk">
            <p>
            	<c:if test="${requestScope['nameUk.error'] != null}">
                    <fmt:message key="${requestScope['nameUk.error']}"/>
                </c:if>
            </p>

            <input type="submit" value="Submit">
        </form>
    </body>
</html>