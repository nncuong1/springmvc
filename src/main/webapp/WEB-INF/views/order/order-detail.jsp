<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<c:url value="/static/admin/libs/js/numeral.min.js"/>"></script>
<div class="influence-profile">
	<div class="container-fluid dashboard-content ">
		<!-- ============================================================== -->
		<!-- pageheader -->
		<!-- ============================================================== -->
		<div class="row">
			<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="page-header">
					<h3 class="mb-2"> Chi tiết đơn hàng </h3>
					<p class="pageheader-text">Proin placerat ante duiullam
						scelerisque a velit ac porta, fusce sit amet vestibulum mi. Morbi
						lobortis pulvinar quam.</p>
					<div class="page-breadcrumb">
						<nav aria-label="breadcrumb">
							<ol class="breadcrumb">
								<li class="breadcrumb-item"><a href="#"
									class="breadcrumb-link">Dashboard</a></li>
								<li class="breadcrumb-item active" aria-current="page">Influencer
									Profile Template</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>
		</div>
		<!-- end pageheader -->
		<!-- content -->
		<div class="row">
			<!-- profile -->
			<div class="col-xl-3 col-lg-3 col-md-5 col-sm-12 col-12">
				<!-- card profile -->
				<div class="card">
					<div class="card-body">
						<div class="text-center">
							<h2 class="font-24 mb-0">Khách hàng</h2>
							<p>${order.user.username}</p>
						</div>
					</div>
					<div class="card-body border-top">
						<h3 class="font-16">Thông tin người nhận </h3>
						<div class="">
							<ul class="list-unstyled mb-0">
								<li class="mb-2"><i class="fas fa-fw fa-envelope mr-2"></i>${address.fullName}</li>
								<li class="mb-0"><i class="fas fa-fw fa-phone mr-2"></i>${address.phone}</li>
							</ul>
						</div>
					</div>
					<div class="card-body border-top">
						<h3 class="font-16">Địa chỉ giao hàng</h3>
						<div>
							<p>${address.addr}, ${address.district.prefix} ${address.district.name}, ${address.province.name}</p>
						</div>
					</div>
				</div>
				<!-- end card profile -->
			</div>
			<!-- end profile -->
			<!-- campaign data -->
			<div class="col-xl-9 col-lg-9 col-md-7 col-sm-12 col-12">
				<div class="card">
					<div class="card-header">
						<div class="header-order">
							<h4 class="mb-0">Chi tiết đơn hàng</h4>
							<button class="btn btn-primary btn-lg" onclick="confirmDelete(${order.id})">Xóa đơn hàng</button>
						</div>
					</div>
					<div class="card-body">
						<form class="needs-validation">
							<div class="row">
								<div class="col-md-12 mb-6">
								 <table class="table table-striped">
                                        <tbody>
                                       		<c:forEach var="item" items="${items}">
                                            <tr>
                                                <td>${item.product.title}</td>
                                                <td>${item.unitPrice} x ${item.quantity}</td>
                                                <td class="price">${item.unitPrice *item.quantity}</td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
								</div>
							</div>
							<hr class="mb-4">
							<div class="row">
								<div class="col-md-6 mb-3"></div>
								<div class="col-md-6 mb-3">
									<ul class="order_product">
										<li>Tổng giá trị sản phẩm<span></span></li>
										<li>Mã khuyến mại<span>0</span></li>
										<li>Vận chuyển<span>0</span></li>
										<li>Số tiền phải thanh toán<span></span></li>
									</ul>
								</div>
							</div>
							<hr class="mb-4">
							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="cc-name">Xác thực đơn hàng</label>
								</div>
								<div class="col-md-6 mb-3">
								<c:if test="${order.status==1}">
									<button type="button"  class="btn btn-primary btn-lg" onclick="confirmOrder(${order.id})">Xác thực</button>
								</c:if>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- end campaign data -->
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		loadPrice();
		$('.price').each(function(){
			$(this).text(numeral($(this).text()).format('0,0'));
		})
	});
	function loadPrice(){
		var sum = 0;
		$('tbody td:nth-child(3)').each(function(){
			sum = sum + parseInt(parseCurrency($(this).text()));
		});
		//$('ul.order_product li:nth-child(4) span').text(sum);
		var forma = numeral(sum).format('0,0');
		$('ul.order_product li:nth-child(1) span').text(forma);
		$('ul.order_product li:nth-child(4) span').text(forma);
	}
	function parseCurrency( num ) {
	    return parseFloat( num.replace( /,/g, '') );
	}
	function confirmDelete(id){
		if(confirm('Ban muon xoa order nay ?')){
			window.location.href = '<c:url value="/admin/order/delete/"/>'+id;
		}
	}
	
	function confirmOrder(id){
		if(confirm('Bạn có muốn xác thực đơn hàng ?')){
			window.location.href = '<c:url value="/admin/order/change-status/"/>'+id;
		}
	}
	
</script>