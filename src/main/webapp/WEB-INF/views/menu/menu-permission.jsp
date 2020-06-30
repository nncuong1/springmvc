<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
		<div class="section-block" id="basicform">
			<h3 class="section-title">Update permission</h3>
		</div>
		<div class="card">
			<h5 class="card-header">Update permission}</h5>
			<div class="card-body">
				<form:form modelAttribute="authForm" method="post" servletRelativeAction="/admin/menu/update-permission">
					<div class="item form-group">
						<label for="name" class="col-md-3 col-sm-3 col-form-label">Role</label>
						<div class="col-md-6 col-sm-6">
							<form:select  path="roleId"  cssClass="form-control">
								<form:options items="${mapRole}"/>
							</form:select>
						</div>
					</div>
					
					<div class="item form-group">
						<label for="name" class="col-md-3 col-sm-3 col-form-label">Menu</label>
						<div class="col-md-6 col-sm-6">
							<form:select path="menuId" cssClass="form-control">
								<form:options items="${mapMenu}"/>
							</form:select>
						</div>
					</div>
					
					<div class="item form-group">
						<label for="name" class="col-md-3 col-sm-3 col-form-label">Permission</label>
						<div class="col-md-6 col-sm-6">
							<form:radiobutton path="permission" value="1"/> Yes
							<form:radiobutton path="permission" value="0"/> No
						</div>
					</div>
					
					<div class="item form-group">
						<div class="col-md-6 col-sm-6 offset-md-3">
							<button class="btn btn-primary" type="button" onclick="cancel()">Back</button>
							<button type="submit" class="btn btn-success">Submit</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
 
<script type="text/javascript">
	$(document).ready(function(){
		
	});
	
	function cancel(){
		window.location.href = '<c:url value="/admin/menu/list"/>';
	}
</script>