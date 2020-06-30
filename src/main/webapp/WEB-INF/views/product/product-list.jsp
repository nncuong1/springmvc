<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="<c:url value="/static/admin/libs/js/numeral.min.js"/>"></script>
<div class="row">
	<!-- ============================================================== -->
	<!-- table -->
	<!-- =========================================================	===== -->
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
		<div class="card">
			<h5 class="card-header">Product Table</h5>
			<div class="card-body">
				<div class=row>
					<div class="col-sm-12 col-md-6">
						<a href="<c:url value="/admin/product/add"/>" class="btn btn-app"><i class="fas fa-plus"></i>Add</a>
					</div>
					<div class="col-sm-12 col-md-6">
					<form id="searchForm" method="GET" action="<c:url value="/admin/product/list"/>">
						<div class="table_form_search">
							<label>Search : 
							<input type="search" name="keyword" id="searchPro" class="form-control form-control-sm" />
							</label>
							<button type="submit" style="display: none">Submit</button>
						</div>
					</form>
					</div>
				</div>
				<table id="table-product" class="table table-striped">
					<thead>
						<tr>
							<th scope="col">STT</th>
							<th scope="col">Image</th>
							<th scope="col">Title</th>
							<th scope="col">Price</th>
							<th scope="col">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${products}" var="product" varStatus="loop">
							<tr>
								<th scope="row">${pageInfor.offset+loop.index+1}</th>
								<c:choose>
									<c:when test="${product.imgUrl!=null && !product.imgUrl.isEmpty()}">
										<td><img src="<c:url value="/files/product/${product.imgUrl}"/>" width="50px" height="50px"/></td>
									</c:when>
									<c:otherwise>
										<td>No Image</td>
									</c:otherwise>
								</c:choose>
								<td>${product.title}</td>
								<td class="price">${product.price} VND</td>
								<td>
									<a href="<c:url value="/admin/product/view/${product.id}"/>">Detail</a>
									<a href="<c:url value="/admin/product/edit/${product.id}"/>">Edit</a>
									<a href="javascript:void(0);" onclick="confirmDelete(${product.id})">Delete</a>
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
			var urlParams = new URLSearchParams(window.location.search);
			var key = urlParams.get('keyword');
			 $.ajax({
				 url: "/inventory/admin/paging/product", 
				 type :"GET",
				 data : {
					page : pageIndex,
					keyword : key
				 },
				 success: function(value){
				   var bodyCategory =  $("#table-product").find("tbody");
				   bodyCategory.empty();
				   bodyCategory.append(value);
				 },
				 error: function (error) {
		         	console.log(error);
		        }
			});
		})
		
		$('.price').each(function(){
			$(this).text(numeral($(this).text()).format('0,0'));
		})
	});

	function confirmDelete(id){
		if(confirm('Ban muon xoa product nay ?')){
			window.location.href = '<c:url value="/admin/product/delete/"/>'+id;
		}
	}
	
	function gotoPage(page) {
		$('#searchForm').attr('action','<c:url value="/admin/product/list/"/>'+page);
		$('#searchForm').submit();
	}
	
	function getdata(){
	      var txtOne = $('#searchPro').val();
	      return txtOne;
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