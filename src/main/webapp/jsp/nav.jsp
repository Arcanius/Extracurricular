<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>

<nav class="navbar navbar-expand navbar-dark bg-dark">
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="/"><fmt:message key="home"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/courses"><fmt:message key="courses"/></a>
        </li>
        <c:if test="${sessionScope['user'].role == 'ADMIN'}">
            <li class="nav-item">
                <a class="nav-link" href="/users"><fmt:message key="users"/></a>
            </li>
        </c:if>
        <li class="nav-item">
            <a class="nav-link" href="/settings"><fmt:message key="settings"/></a>
        </li>
    </ul>
    <ul class="navbar-nav ml-auto">
        <c:if test="${sessionScope['user'] == null}">
            <li class="nav-item">
                <a class="nav-link" href="/login"><fmt:message key="login"/></a>
            </li>
        </c:if>
        <c:if test="${sessionScope['user'] != null}">
        	<li class="nav-item">
        		<a class="nav-link" href="/profile">${sessionScope['user'].login}</a>
        	</li>
            <li class="nav-item">
                <a class="nav-link" href="/login?logout"><fmt:message key="logout"/></a>
            </li>
        </c:if>
    </ul>
</nav>
