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
			<h5 class="card-header">Danh sach binh luan</h5>
			<div class="card-body">
				<table id="table-review" class="table table-striped">
					<thead>
						<tr>
							<th>STT</th>
							<th>Id khách hàng</th>
							<th>Nội dung</th>
							<th>Id sản phẩm</th>
							<th>Trạng thái</th>
							<th>Ngày bình luận</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${reviews}" var="review" varStatus="loop">
							<tr>
								<td scope="row">${pageInfor.offset+loop.index+1}</td>
								<td>${review.customer.id}</td>
								<td>${review.comment}</td>
								<td>${review.product.id}</td>
								<td><a class="review_status" href="javascript:void(0);" style="text-decoration:none;color:#fff" onclick="confirmChange(${review.id},${review.activeFlag},this);">${review.activeFlag}</a></td>
								<td>${review.commentDate}</td>
								<td><a href="javascript:void(0);" class="btn btn-rounded btn-danger" style="text-decoration:none;color:#fff" onclick="confirmDelete(${review.id},this)">Xóa bình luận</a></td>
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
		loadUI();
		$("body").on("click",".page-item",function(){
			$(this).addClass("active").siblings().removeClass('active');
			var pageIndex = $(this).text();
			 $.ajax({
				 url: "/inventory/admin/paging/review", 
				 type :"GET",
				 data : {
					page : pageIndex
				 },
				 success: function(value){
				   var bodyMenu =  $("#table-review").find("tbody");
				   bodyMenu.empty();
				   bodyMenu.append(value);
				   loadUI();
				 },
				 error: function (error) {
		         	console.log(error);
		        }
			});
		})
	});
	
	function loadUI(){
		var status = $("#table-review").find("td a.review_status");
		$.each(status, function(index,item) {
			if(item.text==='1'){
				$(this).addClass("btn btn-rounded btn-primary");
				item.text = "Đã được phê duyệt";
			}else{
				$(this).addClass("btn btn-rounded btn-warning");
				item.text = "Chưa được phê duyệt";
			}
		});
	}
	
	function confirmDelete(id, item){
		if(confirm('Ban muon xoa binh luan nay ?')){
			$.ajax({
				 url: "/inventory/admin/review/delete", 
				 type :"GET",
				 data : {
					id : id
				 },
				 success: function(value){
					 $(item).closest("tr").remove();
					 PNotify.success({
						text : value,
						delay : 1500
					 });
					 $('table > tbody  > tr').each(function(index, tr) { 
						   $(this).children('td:first').text(index+1);
					 });
				 },
				 error: function (error) {
					 PNotify.error({
						text : error,
						delay : 1500
					 });
		        }
			});
		}
	}
	
	function confirmChange(id,flag,item){
		var msg = flag==1 ? 'Bạn có muốn ẩn bình luận này ?' : 'Bạn có muốn phê duyệt bình luận này ?';
		if(confirm(msg)){
			if(item.text==='Đã được phê duyệt'){
				$(item).removeClass("btn-primary");
				$(item).addClass("btn-warning");
				item.text = "Chưa được phê duyệt";
			}else{
				$(item).addClass("btn-primary");
				$(item).removeClass("btn-warning");
				item.text = "Đã được phê duyệt";
			}
			window.location.href = '<c:url value="/admin/review/change-status/"/>'+id;
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