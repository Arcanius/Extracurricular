<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="edit_course"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
    </head>
    <body>
    	<jsp:include page="nav.jsp"/>
    	<form method="post">
    		<p><fmt:message key="edit_course"/></p>
    		<input type="hidden" value="${requestScope['course'].id}">
    		
    		<label for="title_en"><fmt:message key="title_en"/></label>
    		<input type="text" name="title_en" id="title_en" value="${requestScope['course'].nameEn}">
    		<p>
    			<c:if test="${requestScope['titleEn.error'] != null}">
    				<fmt:message key="${requestScope['titleEn.error']}"/>
    			</c:if>
    		</p>
    		
    		<label for="title_uk"><fmt:message key="title_uk"/></label>
    		<input type="text" name="title_uk" id="title_uk" value="${requestScope['course'].nameUk}">
    		<p>
    			<c:if test="${requestScope['titleUk.error'] != null}">
   					<fmt:message key="${requestScope['titleUk.error']}"/>
    			</c:if>
    		</p>
    			
    		<label for="topic_en"><fmt:message key="topic_en"/></label>
    		<input type="text" name="topic_en" id="topic_en" value="${requestScope['course'].topicEn}">
    		<p>
    			<c:if test="${requestScope['topicEn.error'] != null}">
    				<fmt:message key="${requestScope['topicEn.error']}"/>
    			</c:if>
    		</p>
    			
    		<label for="topic_uk"><fmt:message key="topic_uk"/></label>
    		<input type="text" name="topic_uk" id="topic_uk" value="${requestScope['course'].topicUk}">
    		<p>
  				<c:if test="${requestScope['topicUk.error'] != null}">
    				<fmt:message key="${requestScope['topicUk.error']}"/>
    			</c:if>
    		</p>
    			
    		<label for="start_date"><fmt:message key="start_date"/></label>
   			<input type="date" name="start_date" id="start_date" value="${requestScope['course'].startDate}">
    		<p>
    			<c:if test="${requestScope['start_date.error'] != null}">
    				<fmt:message key="${requestScope['start_date.error']}"/>
    			</c:if>
    		</p>
    			
    		<label for="duration"><fmt:message key="duration"/></label>
    		<input type="number" name="duration" id="duration" value="${requestScope['course'].durationInDays}">
    		<p>
   				<c:if test="${requestScope['duration.error'] != null}">
    				<fmt:message key="${requestScope['duration.error']}"/>
    			</c:if>
    		</p>
    		
    		<select name="teacher">
    			<option value="null"><fmt:message key="no_teacher"/></option>
    			<c:forEach items="${requestScope['teachers']}" var="teacher">
    				<option value="${teacher.login}">${teacher.login}</option>
    			</c:forEach>
    		</select>
    		<br><br>
    			
    		<button type="submit"><fmt:message key="submit"/></button>
    		<br><br>
    	</form>
    </body>
</html>
