<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
		<div class="section-block" id="basicform">
			<h3 class="section-title">${titlePage}</h3>
			<p>Hllo</p>
		</div>
		<div class="card">
			<h5 class="card-header">${titlePage}</h5>
			<div class="card-body">
				<form:form modelAttribute="authorForm" method="post" servletRelativeAction="/admin/author/save">
				<form:hidden path="id"/>
				<form:hidden path="createDate"/>
				<form:hidden path="activeFlag"/>
					<div class="item form-group">
						<label for="name" class="col-md-3 col-sm-3 col-form-label">Tên</label>
						<div class="col-md-6 col-sm-6">
							<form:input  path="name"  cssClass="form-control" disabled="${viewOnly}"/>
							<div class="invalid-feedback msg-error">
                				<form:errors path="name" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>

					<div class="item form-group">
						<label for="description"
							class="col-md-3 col-sm-3 ">Code</label>
						<div class="col-md-6 col-sm-6">
							<form:input  path="code"  cssClass="form-control" disabled="${viewOnly}"/>
							<div class="invalid-feedback msg-error">
                				<form:errors path="code" cssClass="form-text"></form:errors>
                			</div>
						</div>
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
			</div>
		</div>
	</div>
</div>
 
<script type="text/javascript">
	$(document).ready(function(){
			
	});
	function cancel(){
		window.location.href = '<c:url value="/admin/author/list"/>';
	}
</script>
