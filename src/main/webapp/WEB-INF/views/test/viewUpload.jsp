<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="contactAPI" value="/api/contact" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	
</head>
<body>
	<p>Thong tin file</p>
		<c:forEach items="${contacts}" var="contact">
		<p>Ten : ${contact.name}</p>
		<p>Tuoi : ${contact.age}</p>
		<p><img style="width : 100px" src = "<c:url value='/files/test2/${contact.imgUrl}'/>"/></p>
	</c:forEach>
	
</body>
</html>
<!-- <p>HInh anh : ${file.getOriginalFilename()}</p> -->