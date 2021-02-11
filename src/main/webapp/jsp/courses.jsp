<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="courses"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
    </head>
    <body>
    	<jsp:include page="nav.jsp"/>
    	<table border="1">
    		<tr>
    			<th>ID</th>
                <th><fmt:message key="title"/></th>
                <th><fmt:message key="topic"/></th>
                <th><fmt:message key="start_date"/></th>
                <th><fmt:message key="duration"/></th>
                <th><fmt:message key="price"/></th>
                <th><fmt:message key="teacher"/></th>
    		</tr>
    		<c:forEach items="${requestScope['courses']}" var="course">
    			<tr>
    				<td>${course.id}</td>
    				<c:if test="${cookie['lang'].value == 'en'}">
    					<td>${course.nameEn}</td>
    					<td>${course.topicEn}</td>
    				</c:if>
    				<c:if test="${cookie['lang'].value == 'uk'}">
    					<td>${course.nameUk}</td>
    					<td>${course.topicUk}</td>
    				</c:if>
    				<td>${course.startDate}</td>
    				<td>${course.durationInDays}</td>
    				<td>${course.price}</td>
    				<td>${course.teacher}</td>
    			</tr>
    		</c:forEach>
    	</table>
    </body>
</html>
