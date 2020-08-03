<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<title>BookStore</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<!-- Favicons -->
		<link rel="shortcut icon" href="<c:url value="/static/web/images/favicon.ico"/>">
		<link rel="apple-touch-icon" href="<c:url value="/static/web/images/icon.png"/>">
	
		<!-- Google font (font-family: 'Roboto', sans-serif; Poppins ; Satisfy) -->
		<link rel="stylesheet" href="<c:url value="/static/web/fonts/open+san.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/fonts/poppyn.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/fonts/familyRoboto.css"/>">
		
		<!-- Pnotyfi -->
		<link href="<c:url value="/static/admin/vendor/pnotify/css/PNotifyBrightTheme.css"/>" rel="stylesheet">
		<!-- Stylesheets -->
		<link rel="stylesheet" href="<c:url value="/static/web/css/bootstrap.min.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/css/plugins.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/css/plugins/jquery.rateyo.min.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/css/style.css"/>">
	
		<!-- Modernizer js -->
	
		<script src="<c:url value="/static/web/js/vendor/modernizr-3.5.0.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/vendor/jquery-3.2.1.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/vendor/jquery.validate.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/vendor/jquery.rateyo.min.js"/>"></script>
    </head>
    <body>
        <!-- Main wrapper -->
        <div class="wrapper" id="wrapper">

            <!-- Header -->
            <jsp:include page="./web/header.jsp"></jsp:include>
            <!-- //Header -->
            <!-- Start Bradcaump area -->
            <!--  
            <div class="ht__bradcaump__area bg-image--6">
            </div>
            --> 
          	  <!-- Start Slider area -->
        <div class="slider-area brown__nav slider--15 slide__activation slide__arrow01 owl-carousel owl-theme">
        	<!-- Start Single Slide -->
	        <div class="slide animation__style10 bg-image--1 fullscreen align__center--left">
	            <div class="container">
	            	<div class="row">
	            		<div class="col-lg-12">
	            			<div class="slider__content">
		            			<div class="contentbox">
		            				<h2>Mua <span>sách</span></h2>
		            				<h2>tại<span> đây</span></h2>  	
		            				<a class="shopbtn" href="<c:url value="/shop"/>">Cửa hàng</a>
		            			</div>
	            			</div>
	            		</div>
	            	</div>
	            </div>
            </div>
            <!-- End Single Slide -->
        	<!-- Start Single Slide -->
	        <div class="slide animation__style10 bg-image--7 fullscreen align__center--left">
	            <div class="container">
	            	<div class="row">
	            		<div class="col-lg-12">
	            			<div class="slider__content">
		            			<div class="contentbox">
		            				<h2>Mua <span>sách</span></h2>
		            				<h2>tại<span> đây</span></h2>  
		            				<a class="shopbtn" href="<c:url value="/shop"/>">Cửa hàng</a>
		            			</div>
	            			</div>
	            		</div>
	            	</div>
	            </div>
            </div>
            <!-- End Single Slide -->
        </div>
        <!-- End Slider area -->
            
            <!-- End Bradcaump area -->
            <!-- Start Shop Page -->
          	<section class="wn__product__area brown--color pt--80  pb--30">
          		<div class="container">
					<div class="row">
						<div class="col-lg-12">
							<div class="section__title text-center">
								<h2 class="title__be--2"><span class="color--theme">Sản phẩm mới</span></h2>
							</div>
						</div>
					</div>
					<div class="furniture--4 border--round arrows_style owl-carousel owl-theme row mt--50">
						<!-- Start Single Product -->
					<c:forEach var="product" items="${newestProducts}">
					<div class="product product__style--3">
						<div class="col-lg-3 col-md-4 col-sm-6 col-12">
							<div class="product__thumb">
								<a class="first__img" href="<c:url value="/product?productId=${product.id}"/>"><img src="<c:url value="/files/product/${product.imgUrl}"/>" alt="product image"></a>
								<a class="second__img animation1" href="<c:url value="/product?productId=${product.id}"/>"><img src="<c:url value="/files/product/${product.imgUrl}"/>" alt="product image"></a>
								<!-- <div class="hot__box">
									<span class="hot-label">SẢN PHẨM MỚI</span>
								</div>
								 -->
							</div>
							<div class="product__content content--center">
								<h4><a href="<c:url value="/product?productId=${product.id}"/>">${product.title}</a></h4>
								<ul class="prize d-flex">
									<li class="price">${product.price}</li>
								</ul>
							</div>
						</div>
					</div>
					</c:forEach>
					<!-- End Single Product -->
					</div>
				</div>
          	</section>
          	<!-- Start NEwsletter Area -->
			<section class="wn__newsletter__area bg-image--2">
				<div class="container">
					<div class="row">
						<div class="col-lg-7 offset-lg-5 col-md-12 col-12 ptb--150">
							<div class="section__title text-center">
								<h2>Đăng Ký Ngay</h2>
							</div>
							<div class="newsletter__block text-center">
								<p>Đăng ký ngay với chúng tôi để nhận được nhiều ưu đãi và phần quá giá trị.</p>
								<form action="#">
									<div class="newsletter__box">
										<input type="email" placeholder="Đăng ký để nhận thông tin">
										<button onclick="javascript:void(0);">Đăng ký</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</section>
		<!-- End NEwsletter Area -->
		<section class="best-seel-area pt--80 pb--60">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">
						<div class="section__title text-center pb--50">
							<h2 class="title__be--2"><span class="color--theme">SẢN PHẨM BÁN CHẠY </span></h2>
						</div>
					</div>
				</div>
			</div>
			<div class="slider center">
			<!-- Single product start -->
			<c:forEach var="product" items="${bestSellerProducts}">
				<div class="product product__style--3">
					<div class="product__thumb">
						<a class="first__img" href="<c:url value="/product?productId=${product.id}"/>"><img src="<c:url value="/files/product/${product.imgUrl}"/>" alt="product image"></a>
					</div>
					<div class="product__content content--center">
					</div>
				</div>
				<!-- Single product end -->
			</c:forEach>
			</div>
		</section>
            <!-- End Shop Page -->
            <!-- Footer Area -->
             <jsp:include page="./web/footer.jsp"></jsp:include>
            <!-- //Footer Area -->
			
        </div>
        <!-- //Main wrapper -->
        
        <script src="<c:url value="/static/admin/libs/js/numeral.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/popper.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/bootstrap.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/plugins.js"/>"></script>
		<script src="<c:url value="/static/admin/vendor/pnotify/js/PNotify.js"/>"></script>
		<script src="<c:url value="/static/web/js/active.js"/>"></script>
    </body>
</html>