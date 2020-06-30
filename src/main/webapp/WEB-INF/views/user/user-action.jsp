<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
		<div class="section-block" id="basicform">
			<h3 class="section-title">${titlePage}</h3>
		</div>
		<div class="card">
			<h5 class="card-header">${titlePage}</h5>
			<div class="card-body">
				<form:form modelAttribute="userForm" method="post" servletRelativeAction="/admin/user/save" enctype="multipart/form-data">
				<form:hidden path="id"/>
				<form:hidden path="createDate"/>
				<form:hidden path="activeFlag"/>
				<form:hidden path="imgUrl"/> 
					<div class="item form-group">
						<label for="name" class="col-md-3 col-sm-3 col-form-label text-center ">Tên <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4">
							<form:input  path="name"  cssClass="form-control" disabled="${viewOnly}"/>
							<div class="invalid-feedback msg-error">
                				<form:errors path="name" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>

					<div class="item form-group">
						<label for="email" class="col-md-3 col-sm-3 col-form-label text-center">Email <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4">
							<form:input path="email" cssClass="form-control" disabled="${viewOnly}" rows="3" />
							<div class="invalid-feedback msg-error">
                				<form:errors path="email" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>
					<div class="item form-group">
						<label for="username" class="col-md-3 col-sm-3 col-form-label text-center">Username <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4">
							<form:input path="username" cssClass="form-control" disabled="${viewOnly}" rows="3" />
							<div class="invalid-feedback msg-error">
                				<form:errors path="username" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>
					<div class="item form-group">
						<label for="password" class="col-md-3 col-sm-3 col-form-label text-center">Password <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4 ">
							<form:input path="password" type="password" cssClass="form-control" disabled="${viewOnly}" rows="3" />
							<div class="invalid-feedback msg-error">
                				<form:errors path="password" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>
					<div class="item form-group">
						<label for="password" class="col-md-3 col-sm-3 col-form-label text-center">Role <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4">
							<c:choose>
								<c:when test="${!viewOnly}">
									<form:select  path="role.id" cssClass="form-control" rows="2">
										<form:options items="${mapRole}"/>
									</form:select>
									<div class="invalid-feedback msg-error">
                						<form:errors path="role.id" cssClass="form-text"></form:errors>
                					</div>
                					
								</c:when>
								<c:otherwise>
									<form:input path="role.id" type="text" value="${role.roleName}" disabled="true" cssClass="form-control col-md-7 col-xs-12"/>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="item form-group">
						<label for="password" class="col-md-3 col-sm-3 col-form-label text-center">Hinh anh </label>
						<c:choose>
							<c:when test="${!viewOnly}">
								<c:if test="${userForm.imgUrl!=null && !userForm.imgUrl.isEmpty()}">
									<div class="col-md-4 col-sm-4">	
										<img class="previewImage" src="<c:url value="/files/user/${userForm.imgUrl}"/>" width="150px" height="150px"/><br>
										<div>Cap nhat hinh moi
											<form:input path="multipartFile" type="file" cssClass="form-control" rows="1" />
										</div>
									</div>
								</c:if>
								<c:if test="${userForm.imgUrl==null || userForm.imgUrl.isEmpty() }">
									<div class="col-md-4 col-sm-4">	
										<form:input path="multipartFile" type="file" cssClass="form-control" rows="1" />
										<img class="previewImage" src="#" alt ="" width="150px" height="150px" />
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${userForm.imgUrl!=null && !userForm.imgUrl.isEmpty()}">
									<div class="col-md-4 col-sm-4">	
										<img src="<c:url value="/files/user/${userForm.imgUrl}"/>" width="150px" height="150px"/>
									</div>
								</c:if>
								<c:if test="${userForm.imgUrl==null || userForm.imgUrl.isEmpty()}">
									<div class="col-md-4 col-sm-4">	
										<label>Chua co hinh anh !!!</label>
									</div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="item form-group">
						<div class="col-md-6 col-sm-6 offset-md-3">
							<button class="btn btn-primary" type="button" onclick="cancel()">Back</button>
							<c:if test="${!viewOnly }">
								<button class="btn btn-primary" type="reset">Reset</button>
								<button type="submit" class="btn btn-success">Submit</button>
							</c:if>
						</div>
					</div>
				</form:form>
				<div class="item form-group">
						<label for="password" class="col-md-3 col-sm-3 col-form-label text-center">Test multiselect <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4 multiRole">
							 <select id= "multipleSelect" style="display:none" name="" multiple>
							 	<c:forEach items="${mapRole}" var="entry">
							 		<option value="${entry.key}">${entry.value}</option>
							 	</c:forEach>
							 </select>
						</div>
				</div>
			</div>
		</div>
	</div>
</div>
 
<script type="text/javascript">
	$(document).ready(function(){
		$('.multiRole').dropdown({
		        
		})
	});
	$('#multipartFile').change(function(){
		readURL(this);	
	})
	function cancel(){
		window.location.href = '<c:url value="/admin/user/list"/>';
	}
	
	function readURL(input){
		if(input.files && input.files[0]){
			var reader = new FileReader();
			
			reader.onload = function(e){
				$('.previewImage').attr('src',e.target.result);
			}
			
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>