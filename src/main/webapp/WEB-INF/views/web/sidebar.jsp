<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<div class="col-lg-3 col-12 order-2 order-lg-1 md-mt-40 sm-mt-40">
	<div class="shop__sidebar">
		<aside class="wedget__categories poroduct--cat">
			<h3 class="wedget__title">Danh mục sản phẩm</h3>
			<c:forEach items="${categories}" var="category">
				<ul>
					<li><a href="/inventory/category/${category.id}">${category.name}</a></li>
				</ul>
			</c:forEach>
		</aside>
		<!--  
		<aside class="wedget__categories pro--range">
			<h3 class="wedget__title">Lọc theo giá</h3>
			<div class="content-shopby">
				<div class="price_filter s-filter clear">
					<form action="/NongSan/filterByPrice" method="POST">
						<div id="slider-range"></div>
						<div class="slider__range--output">
							<div class="price__output--wrap">
								<div class="price--output">
									<span>Giá: </span><input type="text" name="amount" id="amount"
										readonly=""> <input type="hidden" name="amount1"
										id="amount1"> <input type="hidden" name="amount2"
										id="amount2">
								</div>
								<div class="price--filter">
									<button class="btn btn-outline-dark" type="submit">Lọc</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</aside>
		-->
	</div>
</div>