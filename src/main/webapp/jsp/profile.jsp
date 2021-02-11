<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
	<head>
		<meta charset="UTF-8">
		<title><fmt:message key="profile"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
	</head>
	<body>
		<jsp:include page="nav.jsp"/>
		<p>${sessionScope['user'].login}</p>
		<p>${sessionScope['user'].nameEn}</p>
		<p>${sessionScope['user'].nameUk}</p>
	</body>
</html>
