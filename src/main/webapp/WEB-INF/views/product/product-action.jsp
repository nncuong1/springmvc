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
				<form:form modelAttribute="productForm" method="post" servletRelativeAction="/admin/product/save" enctype="multipart/form-data">
				<form:hidden path="id"/>
				<form:hidden path="createDate"/>
				<form:hidden path="activeFlag"/>
				<form:hidden path="imgUrl"/> 
					<div class="item form-group">
						<label for="title" class="col-md-3 col-sm-3 col-form-label text-center ">Tên sach<span class="required">*</span></label>
						<div class="col-md-4 col-sm-4">
							<form:input  path="title"  cssClass="form-control" disabled="${viewOnly}"/>
							<div class="invalid-feedback msg-error">
                				<form:errors path="title" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>
					
					<div class="item form-group">
						<label for="description" class="col-md-3 col-sm-3 col-form-label text-center ">Mieu ta<span class="required">*</span></label>
						<div class="col-md-4 col-sm-4">
							<form:textarea  path="description"  rows="10" cssClass="form-control" disabled="${viewOnly}"/>
							<div class="invalid-feedback msg-error">
                				<form:errors path="description" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>

					<div class="item form-group">
						<label for="isbn" class="col-md-3 col-sm-3 col-form-label text-center">Ma ISBN <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4">
							<form:input path="isbn" cssClass="form-control" disabled="${viewOnly}" rows="3" />
							<div class="invalid-feedback msg-error">
                				<form:errors path="isbn" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>
					<div class="item form-group">
						<label for="qty" class="col-md-3 col-sm-3 col-form-label text-center">So luong <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4">
							<form:input path="qty" cssClass="form-control" disabled="${viewOnly}" rows="3" />
							<div class="invalid-feedback msg-error">
                				<form:errors path="qty" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>
					<div class="item form-group">
						<label for="price" class="col-md-3 col-sm-3 col-form-label text-center">Price <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4 ">
							<form:input path="price" type="price" cssClass="form-control" disabled="${viewOnly}" rows="3" />
							<div class="invalid-feedback msg-error">
                				<form:errors path="price" cssClass="form-text"></form:errors>
                			</div>
						</div>
					</div>
					<div class="item form-group">
						<label for="price" class="col-md-3 col-sm-3 col-form-label text-center">Chon the loai <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4">
							<c:choose>
								<c:when test="${!viewOnly}">
									<form:select  path="category.id" cssClass="form-control" rows="2">
										<form:options items="${mapCategory}"/>
									</form:select>
									<div class="invalid-feedback msg-error">
                						<form:errors path="category.id" cssClass="form-text"></form:errors>
                					</div>
                					
								</c:when>
								<c:otherwise>
									<form:input path="category.id" type="text" value="${category.name}" disabled="true" cssClass="form-control col-md-7 col-xs-12"/>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					
					<div class="item form-group">
						<label for="authors" class="col-md-3 col-sm-3 col-form-label text-center">Nhap ten tac gia <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4 multiAuthor">
						<c:choose>
							<c:when test="${!viewOnly}">
								<form:select path="authorId" cssStyle="display:none" multiple="true">
							 		<form:options items="${mapAuthor}"/>
							 	</form:select>
							</c:when>
							<c:otherwise>
								<input type="text" value="${names}" disabled = "${viewOnly}"></input>
							</c:otherwise>
						</c:choose>
							 
						</div>
					</div>
					
					<div class="item form-group">
						<label for="price" class="col-md-3 col-sm-3 col-form-label text-center">Hinh anh </label>
						<c:choose>
							<c:when test="${!viewOnly}">
								<c:if test="${productForm.imgUrl!=null && !productForm.imgUrl.isEmpty()}">
									<div class="col-md-4 col-sm-4">	
										<img class="previewImage" src="<c:url value="/files/product/${productForm.imgUrl}"/>" width="150px" height="150px"/><br>
										<div>Cap nhat hinh moi
											<form:input path="multipartFile" type="file" cssClass="form-control" rows="1" />
										</div>
									</div>
								</c:if>
								<c:if test="${productForm.imgUrl==null || productForm.imgUrl.isEmpty() }">
									<div class="col-md-4 col-sm-4">	
										<form:input path="multipartFile" type="file" cssClass="form-control" rows="1" />
										<img class="previewImage" src="#" alt ="" width="150px" height="150px" />
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${productForm.imgUrl!=null && !productForm.imgUrl.isEmpty()}">
									<div class="col-md-4 col-sm-4">	
										<img src="<c:url value="/files/product/${productForm.imgUrl}"/>" width="150px" height="150px"/>
									</div>
								</c:if>
								<c:if test="${productForm.imgUrl==null || productForm.imgUrl.isEmpty()}">
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
				<!--  
				<div class="item form-group">
						<label for="price" class="col-md-3 col-sm-3 col-form-label text-center">Test multiselect <span class="required">*</span></label>
						<div class="col-md-4 col-sm-4 multicategory">
							 <select id= "multipleSelect" style="display:none" name="" multiple>
							 	<c:forEach items="${mapcategory}" var="entry">
							 		<option value="${entry.key}">${entry.value}</option>
							 	</c:forEach>
							 </select>
						</div>
				</div>
				-->
			</div>
		</div>
	</div>
</div>
 
<script type="text/javascript">
	$(document).ready(function(){
		
		$('.qty').text("1");
		$('.multiAuthor').dropdown({
		        
		})
	});
	$('#multipartFile').change(function(){
		readURL(this);	
	})
	function cancel(){
		window.location.href = '<c:url value="/admin/product/list"/>';
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
