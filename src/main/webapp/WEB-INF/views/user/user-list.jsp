<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<!-- ============================================================== -->
	<!-- table -->
	<!-- ============================================================== -->
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
		<div class="card">
			<h5 class="card-header">User Table</h5>
			<div class="card-body">
				<div class=row>
					<div class="col-sm-12 col-md-6">
						<a href="<c:url value="/admin/user/add"/>" class="btn btn-app"><i class="fas fa-plus"></i>Add</a>
					</div>
					<div class="col-sm-12 col-md-6">
					<form id="searchForm" method="GET" action="<c:url value="/admin/user/list"/>">
						<div class="table_form_search">
							<label>Search : 
							<input type="search" name="keyword" class="form-control form-control-sm" />
							</label>
							<button type="submit" style="display: none">Submit</button>
						</div>
					</form>
					</div>
				</div>
				<table id="table-user" class="table table-striped">
					<thead>
						<tr>
							<th scope="col">STT</th>
							<th scope="col">Image</th>
							<th scope="col">Name</th>
							<th scope="col">Role</th>
							<th scope="col">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${users}" var="user" varStatus="loop">
							<tr>
								<th scope="row">${pageInfor.offset+loop.index+1}</th>
								<c:choose>
									<c:when test="${user.imgUrl!=null && !user.imgUrl.isEmpty()}">
										<td><img src="<c:url value="/files/user/${user.imgUrl}"/>" width="50px" height="50px"/></td>
									</c:when>
									<c:otherwise>
										<td>No Image</td>
									</c:otherwise>
								</c:choose>
								<td>${user.name}</td>
								<td>${user.role.roleName}</td>
								<td>
									<a href="<c:url value="/admin/user/view/${user.id}"/>">Detail</a>
									<a href="<c:url value="/admin/user/edit/${user.id}"/>">Edit</a>
									<a href="javascript:void(0);" onclick="confirmDelete(${user.id})">Delete</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<nav aria-label="...">
					<ul class="pagination pagination-sm">
					<c:forEach begin="1" end="${pageInfor.totalPages}" varStatus="loop">
						<c:choose>
							<c:when test="${pageInfor.indexPage == loop.index }">
								<li class="page-item active"><a class="page-link" href="#">${loop.index}</a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item"><a class="page-link" href="#">${loop.index}</a></li>
							</c:otherwise>
						</c:choose>
						
					</c:forEach>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		processMessage();
		
		$("body").on("click",".page-item",function(){
			$(this).addClass("active").siblings().removeClass('active');
			var pageIndex = $(this).text();
			 $.ajax({
				 url: "/inventory/admin/paging/user", 
				 type :"GET",
				 data : {
					page : pageIndex
				 },
				 success: function(value){
				   var bodyCategory =  $("#table-user").find("tbody");
				   bodyCategory.empty();
				   bodyCategory.append(value);
				 },
				 error: function (error) {
		         	console.log(error);
		        }
			});
		})
	});

	function confirmDelete(id){
		if(confirm('Ban muon xoa user nay ?')){
			window.location.href = '<c:url value="/admin/user/delete/"/>'+id;
		}
	}
	
	function processMessage(){
		var msgSuccess = '${msgSuccess}';
		var msgError = '${msgError}';
		if(msgSuccess){
			PNotify.success({
				text : msgSuccess,
				delay : 2000
			});
		}
		if(msgError){
			PNotify.error({
				text : msgError,
				delay : 2000
			});
		}
	}
</script>