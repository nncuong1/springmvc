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
	<h1>Hello upload form</h1>

	<c:url value="/test" var="url"/>
	<form:form  action="${url }" modelAttribute="contactForm" method="post" enctype="multipart/form-data">
		<div>
			<label for="name">Tên contact </label>
			<div class="col-md-6 col-sm-6">
				<form:input path="name"/>
			</div>
		</div>

		<div>
			<label for="age" class="col-md-3 col-sm-3 ">Tuoi </label>
			<div class="col-md-6 col-sm-6">
				<form:textarea path="age"  />
			</div>
		</div>
		
		<div>
			<label for="avatar" class="col-md-3 col-sm-3 ">Hinh anh </label>
			<form:input type="file" path="multipartFile" />	
		</div>
	
		<div >
			<div class="col-md-6 col-sm-6 offset-md-3">
				<button id="btnAdd" class="btn btn-primary" type="submit">Add</button>
			</div>
		</div>
	</form:form>

</body>
</html>