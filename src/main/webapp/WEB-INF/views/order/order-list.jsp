<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="<c:url value="/static/admin/libs/js/jsonForm.js"/>"></script>
<div class="row">
	<!-- ============================================================== -->
	<!-- table -->
	<!-- ============================================================== -->
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
		<div class="card">
			<h5 class="card-header">Order Table</h5>
			<div class="card-body">
				<div class="container">
					<div class="col-sm-12 col-md-12 formSearchOrder">
					<form:form modelAttribute="orderForm" method="POST" servletRelativeAction="/inventory/admin/order/list">
						<div class="form-group">
							<label for="customer id" class="control-label col-md-3 col-sm-3 col-xs-12">customer id</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="customerId" cssClass="form-control col-md-7 col-xs-12" />
							</div>
						</div>
						<div class="form-group">
							<label for="statusType" class="control-label col-md-3 col-sm-3 col-xs-12">Tinh trang don hang</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="statusType" class="select__option">
									<form:options items="${mapStatus}"/>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="statusType" class="control-label col-md-3 col-sm-3 col-xs-12">From Date</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="input-group date" id="fromDatePicker" data-target-input="nearest">
                                	<form:input path="fromDate" cssClass="form-control datetimepicker-input" data-target="#fromDatePicker" />
                                	<div class="input-group-append" data-target="#fromDatePicker" data-toggle="datetimepicker">
                                		<div class="input-group-text"><i class="far fa-calendar-alt"></i></div>
                                	</div>
                                </div>
							</div>
						</div>
						<br>
						<div class="form-group">
							<label for="statusType" class="control-label col-md-3 col-sm-3 col-xs-12">To date</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="input-group date" id="toDatePicker" data-target-input="nearest">
                                	<form:input path="toDate" cssClass="form-control datetimepicker-input" data-target="#toDatePicker" />
                                	<div class="input-group-append" data-target="#toDatePicker" data-toggle="datetimepicker">
                                		<div class="input-group-text"><i class="far fa-calendar-alt"></i></div>
                                	</div>
                                </div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
								<button type="submit" id="btn-orderForm" class="btn btn-success">Search</button>
							</div>
						</div>
					</form:form>
					</div>
				</div>
				<table id="table-order" class="table table-striped">
					<thead>
						<tr>
							<th scope="col">STT</th>
							<th scope="col">Khách hàng</th>
							<th scope="col">Trạng thái</th>
							<th scope="col">Ngày khởi tạo</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orders}" var="order" varStatus="loop">
							<tr>
								<th scope="row">${pageInfor.offset+loop.index+1}</th>
								<td>${order.user.username}</td>
								<td><a href="javascript:void(0);" style="text-decoration:none" >${order.status}</a></td>
								<td>${order.orderDate}</td>
								<td>
									<a href="<c:url value="/admin/order/detail/${order.id}"/>">Chi tiết đơn hàng</a>
									<!--  <a href="<c:url value="/admin/order/detail?orderId=${order.id}&addrId=${order.address.id}"/>">Chi tiết đơn hàng</a>-->
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
		loadUI();
		processMessage();
		$("body").on("click",".page-item",function(){
			$(this).addClass("active").siblings().removeClass('active');
			var pageIndex = $(this).text();
			var nestedData = $("#orderForm").toJson(); 
			console.log(nestedData);
		
				 $.ajax({
					 url: "/inventory/admin/paging/order?page="+pageIndex, 
					 type :"POST",
					 contentType: 'application/json',
					 data : JSON.stringify(nestedData),
					 dataType: 'text',
					 success: function(value){
					   var bodyCategory =  $("#table-order").find("tbody");
					   bodyCategory.empty();
					   bodyCategory.append(value);
					   loadUI()
					 },
					 error: function (error) {
			         	console.log(error);
			        }
				});
			
		})
		 $('#fromDatePicker').datetimepicker({
			 format : 'YYYY-MM-DD'
		 });
		 $('#toDatePicker').datetimepicker({
			 format : 'YYYY-MM-DD'
		 })
		 
		 $("#btn-orderForm").click(function(e){
			 alert("hello ");
			if($("#orderForm").valid()){
			    $("#orderForm").submit();
			}
		 });
	});
	
	function loadUI(){
		var status = $("#table-order").find("tr td:nth-child(3) a");
		$.each(status, function(index,item) {
			if(item.text==='1'){
				$(this).addClass("btn btn-rounded btn-warning");
				item.text = "Chưa xử lý";
			}else if(item.text==='2'){
				$(this).addClass("btn btn-rounded btn-secondary");
				item.text = "Đã xác nhận";
			}else if(item.text==='3'){
				$(this).addClass("btn btn-rounded btn-success");
				item.text = "Đã giao";
			}else if(item.text==='4'){
				$(this).addClass("btn btn-rounded btn-dark");
				$(this).css("color","#fff");
				item.text = "Bị hủy";
			}
		});
	}
	function processMessage(){
		var msgSuccess = '${msgSuccess}';
		var msgError = '${msgError}';
		if(msgSuccess){
			PNotify.success({
				text : msgSuccess,
				delay : 1500
			});
		}
		if(msgError){
			PNotify.error({
				text : msgError,
				delay : 1500
			});
		}
	}
</script>