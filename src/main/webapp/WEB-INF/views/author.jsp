<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<div
	class="page-shop-sidebar left--sidebar bg--white section-padding--lg">
	<div class="container">
		<div class="row">
			<!-- start sidebar -->
				<div class="col-lg-3 col-12 order-2 order-lg-1 md-mt-40 sm-mt-40">
					<div class="shop__sidebar">
						<aside class="wedget__categories poroduct--cat">
							<h3 class="wedget__title">Tác giả : ${author.name}</h3>
						</aside>
					</div>
				</div>
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
											href="<c:url value="/product?productId=${product.id}"/>"><img style="max-height:340px; overflow:hidden"
											src="<c:url value="/files/product/${product.imgUrl} "/>"></a>
									</div>
									<div class="product__content content--center">
										<h4>
											<a href="<c:url value="/product?productId=${product.id}"/>"> ${ product.title } </a>
											
										</h4>
										<ul class="prize d-flex">
											<li>${product.price}</li>
										</ul>
									</div>
								</div>
							</c:forEach>
						
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>

<script>
</script>