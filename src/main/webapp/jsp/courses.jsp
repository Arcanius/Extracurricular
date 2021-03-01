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
    	<c:if test="${sessionScope['user'].role == 'ADMIN'}">
    		<a href="/createcourse"><fmt:message key="create_course"/></a>
    	</c:if>
    	<p><fmt:message key="courses_available"/>: ${requestScope['count']}</p>
    	<form method="post" action="${pageContext.request.contextPath}/filter">
    		<label for="orderBy"><fmt:message key="order_by"/>:</label>
    		<select name="orderBy" id="orderBy">
    			<option value="none"><fmt:message key="no"/></option>
    			<option value="title"><fmt:message key="title"/></option>
    			<option value="duration"><fmt:message key="duration"/></option>
    			<option value="students"><fmt:message key="students"/></option>
    		</select>
    		<label for="order"><fmt:message key="order"/>:</label>
    		<select name="order" id="order">
    			<option value="asc"><fmt:message key="asc"/></option>
    			<option value="desc"><fmt:message key="desc"/></option>
    		</select>
    		<label for="topic"><fmt:message key="topic"/>:</label>
    		<select name="topic" id="topic">
    			<option value="all"><fmt:message key="all"/></option>
    			<c:forEach items="${requestScope['topics']}" var="topic">
    				<option value="${topic}">${topic}</option>
    			</c:forEach>
    		</select>
    		<label for="teacher"><fmt:message key="teacher"/>:</label>
    		<select name="teacher" id="teacher">
    			<option value="all"><fmt:message key="all"/></option>
    			<c:forEach items="${requestScope['teachers']}" var="teacher">
    				<option value="${teacher.login}">
    					<c:if test="${cookie['lang'].value == 'en'}">
    						${teacher.nameEn}
    					</c:if>
    					<c:if test="${cookie['lang'].value == 'uk'}">
    						${teacher.nameUk}
    					</c:if>
    				</option>
    			</c:forEach>
    		</select>
    		<button type="submit"><fmt:message key="filter"/></button>
    	</form>
    	<table border="1">
    		<tr>
    			<th>ID</th>
                <th><fmt:message key="title"/></th>
                <th><fmt:message key="topic"/></th>
                <th><fmt:message key="start_date"/></th>
                <th><fmt:message key="duration"/></th>
                <th><fmt:message key="students"/></th>
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
    				<td>${course.students}</td>
    				<td>
    					<c:if test="${course.teacher != null}">
    						<c:if test="${cookie['lang'].value == 'en'}">
    							${course.teacher.nameEn}
    						</c:if>
    						<c:if test="${cookie['lang'].value == 'uk'}">
    							${course.teacher.nameUk}
    						</c:if>
    					</c:if>
    				</td>
    				<c:if test="${sessionScope['user'].role == 'STUDENT'}">
    					<td>
    						<form method="post">
    							<input type="hidden" name="id" value="${course.id}">
    							<button type="submit"><fmt:message key="enroll"/></button>
    						</form>
    					</td>
    				</c:if>
    				<c:if test="${sessionScope['user'].role == 'ADMIN'}">
    					<td>
    						<a href="${pageContext.request.contextPath}/editcourse?id=${course.id}"><fmt:message key="edit"/></a>
    					</td>
    				</c:if>
    			</tr>
    		</c:forEach>
    	</table>
    	<ul>
    		<c:forEach begin="1" end="${requestScope['pages']}" var="i">
    			<li>
    				<a href="${pageContext.request.contextPath}/courses?page=${i}">${i}</a>
    			</li>
    		</c:forEach>
    	</ul>
    	
    	<script type="text/javascript">
    		for (const option of document.getElementById('orderBy').options) {
    			if (option.value === "${sessionScope['orderBy']}") {
    				option.selected = true
    				break
    			}
    		}
    		
    		for (const option of document.getElementById('order').options) {
    			if (option.value === "${sessionScope['order']}") {
    				option.selected = true
    				break
    			}
    		}
    		
    		for (const option of document.getElementById('topic').options) {
    			if (option.value === "${sessionScope['topic']}") {
    				option.selected = true
    				break
    			}
    		}
    		
    		for (const option of document.getElementById('teacher').options) {
    			if (option.value === "${sessionScope['teacher']}") {
    				option.selected = true
    				break
    			}
    		}
    	</script>
    </body>
</html>
