<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="contactAPI" value="/api/contact" />
<c:url var="cityAPI" value="/api/city" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="<c:url value="/static/admin/libs/js/jsonForm.js"/>"></script>
</head>
<body>
	<h1>Hello ${msg.content}</h1>
	<c:forEach items="${msg.posts}" var="post">
		<h2>Post la ${post.time}</h2>
	</c:forEach>
	<button id="btn" type="button">Click do</button>


	<form:form id="formSubmit" modelAttribute="productDtoForm" method="post" enctype="multipart/form-data" action="/inventory/test/t1">
		
		<div>
			<label for="name" class="col-md-3 col-sm-3 col-form-label">Tên product DTO</label>
			<div class="col-md-6 col-sm-6">
				<!-- <form:input path="name" cssClass="form-control"/>-->
				<input id="name" name="name" class="form-control" />
			</div>
		</div>

		<div>
			<label for="age" class="col-md-3 col-sm-3 ">Miêu tả</label>
			<div class="col-md-6 col-sm-6">
				<form:textarea path="age" cssClass="form-control" rows="3" />
			</div>
		</div>
		<div>
			<label for="cx1" class="col-md-3 col-sm-3 ">Context phone 1 : </label>
			<div class="col-md-6 col-sm-6">
				<form:textarea path="phones[0].context" cssClass="form-control" rows="3" />
			</div>
		</div>
		
		<div>
			<label for="cx2" class="col-md-3 col-sm-3 ">Context phone 2 : </label>
			<div class="col-md-6 col-sm-6">
				<form:textarea path="phones[1].context" cssClass="form-control" rows="3" />
			</div>
		</div>
		
		<div >
			<div class="col-md-6 col-sm-6 offset-md-3">
				<button id="btnAdd" class="btn btn-primary" type="button">Add</button>
				<button  id="btnNormal" class="btn btn-primary" type="submit">Normal</button>
				<button id="btnApi" class="btn btn-primary" type="button">Test API</button>
			</div>
		</div>
		<div>
		<select id="cities">
		  
		</select>
		</div>
	</form:form>



	<script>
		<!--
		var length = "${msg.options[1]}";
		console.log(length);
		var btn = $('#btn').click(function(){
			alert("${msg.options[1]}");
		});
		 -->
		$(document).ready(function(){
			
		$('#btnAdd').click(function (e) {
			e.preventDefault();
			var data = {};
			var nestedData = $("#formSubmit").toJson(); 
			<!--
			var formData = $('#formSubmit').serializeArray();
			$.each(formData, function (i, v) {
		    	data[""+v.name+""] = v.value;
		    });
			console.log(data);
			-->
			console.log(nestedData);
			addNew(nestedData);
		});
		
		$('#btnApi').click(function (e) {
			e.preventDefault();
			$.ajax({
	            url: 'http://cors-anywhere.herokuapp.com/https://thongtindoanhnghiep.co/api/city',
	            headers: {  'Access-Control-Allow-Origin': 'http://cors-anywhere.herokuapp.com/https://thongtindoanhnghiep.co/api/city' },
	            type: 'GET',
	            crossOrigin: true,
	            dataType: 'json',
	            success: function (result) {
	            	//console.log(result);
	            },
	            error: function (error) {
	            	//console.log("error : "+error);
	            }
	        }).then(function(data) {
	           console.log(data);
	           
	           var json = JSON.stringify(data);
	          // var json = $.parseJSON(data);
	           console.log(json.LtsItem);
	          // $.each(json, function(index, item) {
	          // 		console.log(item.LtsItem.Title);
	          // });
	        });
		});
		
		});
		 
		function addNew(data) {
			$.ajax({
	            url: '${contactAPI}',
	            type: 'POST',
	            contentType: 'application/json',
	            data: JSON.stringify(data),
	            dataType: 'json',
	            success: function (result) {
	            	console.log(result.name);
	            },
	            error: function (error) {
	            	console.log(result.name);
	            }
	        });
		}
		//http://www.corsproxy.com/
	</script>

</body>
</html>