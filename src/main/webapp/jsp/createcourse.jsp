<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="create_course"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="css/bootstrap.min.css">
    </head>
    <body style="text-align: center">
        <jsp:include page="nav.jsp"/>
        <form method="post">
    		<p><fmt:message key="new_course"/></p>
    		<label for="title_en"><fmt:message key="title_en"/></label>
    		<input type="text" name="title_en" id="title_en">
    		<p>
    			<c:if test="${requestScope['titleEn.error'] != null}">
    				<fmt:message key="${requestScope['titleEn.error']}"/>
    			</c:if>
    		</p>
    		
    		<label for="title_uk"><fmt:message key="title_uk"/></label>
    		<input type="text" name="title_uk" id="title_uk">
    		<p>
    			<c:if test="${requestScope['titleUk.error'] != null}">
   					<fmt:message key="${requestScope['titleUk.error']}"/>
    			</c:if>
    		</p>
    			
    		<label for="topic_en"><fmt:message key="topic_en"/></label>
    		<input type="text" name="topic_en" id="topic_en">
    		<p>
    			<c:if test="${requestScope['topicEn.error'] != null}">
    				<fmt:message key="${requestScope['topicEn.error']}"/>
    			</c:if>
    		</p>
    			
    		<label for="topic_uk"><fmt:message key="topic_uk"/></label>
    		<input type="text" name="topic_uk" id="topic_uk">
    		<p>
  				<c:if test="${requestScope['topicUk.error'] != null}">
    				<fmt:message key="${requestScope['topicUk.error']}"/>
    			</c:if>
    		</p>
    			
    		<label for="start_date"><fmt:message key="start_date"/></label>
   			<input type="date" name="start_date" id="start_date">
    		<p>
    			<c:if test="${requestScope['start_date.error'] != null}">
    				<fmt:message key="${requestScope['start_date.error']}"/>
    			</c:if>
    		</p>
    			
    		<label for="duration"><fmt:message key="duration"/></label>
    		<input type="number" name="duration" id="duration">
    		<p>
   				<c:if test="${requestScope['duration.error'] != null}">
    				<fmt:message key="${requestScope['duration.error']}"/>
    			</c:if>
    		</p>
    			
    		<button type="submit"><fmt:message key="submit"/></button>
    		<br><br>
    	</form>
    </body>
</html>
    