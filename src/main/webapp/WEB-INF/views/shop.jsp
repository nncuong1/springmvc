<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
                                    

<div class="page-shop-sidebar left--sidebar bg--white section-padding--lg">
	<div class="container">
		<div class="row">
			<!-- start sidebar -->
				<jsp:include page="./web/sidebar.jsp"></jsp:include>
			<!-- end sidebar -->
			<div class="col-lg-9 col-12 order-1 order-lg-2">
				<div class="tab__container">
					<div class="shop-grid tab-pane fade show active" id="nav-grid"
						role="tabpanel">
						<div class="row">
							<c:forEach items="${products}" var="product">
								<div class="product product__style--3 col-lg-4 col-md-4 col-sm-6 col-12">
									<div class="product__thumb">
										<a class="first__img"
											href="<c:url value="/product?productId=${product.id}"/>"><img style="max-height:340px;overflow:hidden"
											src="<c:url value="/files/product/${product.imgUrl} "/>"></a>
									</div>
									<div class="product__content content--center">
										<h4>
											<a href="<c:url value="/product?productId=${product.id}"/>"> ${ product.title } </a>
											
										</h4>
										<ul class="prize d-flex">
											<li class="price">${product.price}</li> <li style="display:inline;text-decoration: underline">đ</li>
										</ul>
										
									</div>
								</div>
							</c:forEach>
						
						</div>
						<ul class="wn__pagination">
	        				<c:forEach begin="1" end="${pageInfor.totalPages}" varStatus="loop">
								<c:choose>
									<c:when test="${pageInfor.indexPage == loop.index }">
										<li class="page-item active"><a href="#">${loop.index}</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a href="#" onclick="gotoPage(${loop.index});">${loop.index}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
	        			</ul>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		$('.bradcaump-title').html('Cửa hàng');
		$('span.breadcrumb_item').html('Cửa hàng');
		//var des = "${pageInfor.indexPage}";
		//console.log(des);
	});
	
	function gotoPage(page){
		//$('#searchForm').attr('action','<c:url value="/shop?page="/>'+page);
		$('#page').val(page);
		$('#search_mini_form').attr('action',window.location.pathname+'?page='+page);
		$('#page').val(page);
		//event.preventDefault();
		$('#search_mini_form').submit();
	}
	function addToCart(productId){
		
		$.ajax({
			url:'add_to_cart',
			data: {productId:productId},
			success: function(data){
				//var pri = "${map.value.unitPrice}";
				//alert(pri);
				console.log(data);
			}
		});
		
	}
</script>