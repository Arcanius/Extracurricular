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
		
		<table border="1">
			<tr>
                <th><fmt:message key="title"/></th>
                <th><fmt:message key="topic"/></th>
                <th><fmt:message key="start_date"/></th>
                <th><fmt:message key="duration"/></th>
                <th><fmt:message key="teacher"/></th>
                <th><fmt:message key="progress"/></th>
                <th><fmt:message key="mark"/></th>
    		</tr>
    		<c:forEach items="${requestScope['courses']}" var="course">
    			<tr>
    				<td>${course.course.id}</td>
    				<c:if test="${cookie['lang'].value == 'en'}">
    					<td>${course.course.nameEn}</td>
    					<td>${course.course.topicEn}</td>
    				</c:if>
    				<c:if test="${cookie['lang'].value == 'uk'}">
    					<td>${course.course.nameUk}</td>
    					<td>${course.course.topicUk}</td>
    				</c:if>
    				<td>${course.course.startDate}</td>
    				<td>${course.course.durationInDays}</td>
    				<td>${course.course.teacher}</td>
    				<td>${course.progress}</td>
    				<td>${course.mark}</td>
    			</tr>
    		</c:forEach>
		</table>
	</body>
</html>
