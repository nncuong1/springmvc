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
			<h5 class="card-header">Category Table</h5>
			<div class="card-body">
				<div class=row>
					<div class="col-sm-12 col-md-6">
						<a href="<c:url value="/admin/category/add"/>" class="btn btn-app"><i class="fas fa-plus"></i>Add</a>
					</div>
					<div class="col-sm-12 col-md-6">
					<form id="searchForm" method="GET" action="<c:url value="/admin/category/list"/>">
						<div class="table_form_search">
							<label>Search : 
							<input type="search" name="keyword" class="form-control form-control-sm" />
							</label>
							<button type="submit" id="searchCategory" style="display: none">Submit</button>
						</div>
					</form>
					</div>
				</div>
				<table id="table-category" class="table table-striped">
					<thead>
						<tr>
							<th scope="col">STT</th>
							<th scope="col">ID</th>
							<th scope="col">Name</th>
							<th scope="col">Description</th>
							<th scope="col">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${categories}" var="category" varStatus="loop">
							<tr>
								<th scope="row">${pageInfor.offset+loop.index+1}</th>
								<td>${category.id}</td>
								<td>${category.name}</td>
								<td>${category.description}</td>
								<td>
									<a href="<c:url value="/admin/category/view/${category.id}"/>">Detail</a>
									<a href="<c:url value="/admin/category/edit/${category.id}"/>">Edit</a>
									<a href="javascript:void(0);" onclick="confirmDelete(${category.id})">Delete</a>
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
			//var key = getParameterByName('keyword');
			//var key = window.getParameterByName("keyword");
			var urlParams = new URLSearchParams(window.location.search);
			var key = urlParams.get('keyword');
			//console.log("key : "+key);
			 $.ajax({
				 url: "/inventory/admin/paging/category", 
				 type :"GET",
				 data : {
					page : pageIndex,
					keyword : key
				 },
				 success: function(value){
				   var bodyCategory =  $("#table-category").find("tbody");
				   bodyCategory.empty();
				   bodyCategory.append(value);
				 },
				 error: function (error) {
		         	console.log(error);
		        }
			});
		})
	});
	function getParameterByName(name, url) {
	    if (!url) url = window.location.href;
	    name = name.replace(/[\[\]]/g, '\\$&');
	    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
	        results = regex.exec(url);
	    if (!results) return null;
	    if (!results[2]) return '';
	    return decodeURIComponent(results[2].replace(/\+/g, ' '));
	}
	
	function confirmDelete(id){
		if(confirm('Ban muon xoa user nay ?')){
			window.location.href = '<c:url value="/admin/category/delete/"/>'+id;
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