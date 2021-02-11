<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
    <head>
        <meta charset="UTF-8">
        <title>Settings</title>
    </head>
    <body style="text-align:center">
        <form method="post">
            <p><fmt:message key="choose_lang"/>:</p>
            <input type="radio" name="lang" id="lang1" value="en" checked>
            <label for="lang1">English</label>
            <br>
            <input type="radio" name="lang" id="lang2" value="uk">
            <label for="lang2">Українська</label>
            <br>
            <button type="submit"><fmt:message key="submit"/></button>
        </form>
    </body>
</html>