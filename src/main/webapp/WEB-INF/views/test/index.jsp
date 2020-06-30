<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <link rel="stylesheet" href="<c:url value="/static/admin/libs/css/style2.css" />">
</head>
<body>
	<h1>Hello Test</h1>
	<!-- <c:url value="/add-user" />--> 
	<form:form modelAttribute="contact" method="post" >
		<form:input path="name"/>
		<form:input path="age"/>
		
		<button type="submit">Them Contact</button>
	</form:form>
</body>
</html>