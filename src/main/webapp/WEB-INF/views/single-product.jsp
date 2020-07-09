
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:url var="reviewPostAPI" value="/api/reviews" />
<c:url var="reviewGetAPI" value="/paging/review" />
<script src="<c:url value="/static/admin/libs/js/jsonForm.js"/>"></script>
<!-- Start main Content -->
<div class="maincontent bg--white pt--80 pb--55">
	<div class="container">
		<div class="row">
			<div class="col-lg-9 col-12">
				<div class="wn__single__product">
					<div class="row">
						<div class="col-lg-6 col-12">
							<div class="wn__fotorama__wrapper">
								<div class="fotorama wn__fotorama__action" data-nav="thumbs">
									<a href="${product.title}"><img src="<c:url value="/files/product/${product.imgUrl} "/>"
										alt=""></a>
								</div>
							</div>
						</div>
						<div class="col-lg-6 col-12">
							<div class="product__info__main">
								<h1>${product.title}</h1>
								<div class="price-box">
									<span class="price">${product.price}</span>	
								</div>
								<form action="<c:url value="/add_to_cart"/>" method="get">
									<div class="box-tocart d-flex">
										<span>Số Lượng</span> <input id="qty" class="input-text qty"
											name="qty" min="1" value="1" title="Qty" type="number">
										<input type="hidden" class="productId" name="productId" value="${product.id}" />
										<div class="addtocart__actions">
											<!-- <button class="tocart" type="submit" title="Add to Cart">Thêm vào giỏ hàng</button>-->
											<button class="tocart" type="submit" onclick="toCart(${product.id})" title="Add to Cart">Thêm vào giỏ hàng</button>
									</div>
								</form>
							</div>
							<div class="product_meta">
								<span class="posted_in">Tác giả : </span>
								<span id="authorName" class="posted_in"><a href="#">${product.isbn}</a>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="product__info__detailed">
				<div class="pro_details_nav nav justify-content-start"
					role="tablist">
					<a class="nav-item nav-link active" data-toggle="tab"
						href="#nav-details" role="tab">Miêu tả sản phẩm</a>
				</div>
				<div class="tab__container">
					<!-- Start Single Tab Content -->
					<div class="pro__tab_label tab-pane fade show active"
						id="nav-details" role="tabpanel">
						<div class="description__attribute">
							<p>${product.description}</p>
						</div>
					</div>
					<!-- End Single Tab Content -->
				</div>
			</div>
			<!-- Recommended products -->
			<div class="wn__related__product pt--80 pb--50">
				<div class="section__title text-center">
					<h2 class="title__be--2">Thường được mua cùng </h2>
				</div>
				<div class="row mt--60">
				<!-- <div class="productcategory__slide--2 arrows_style owl-carousel owl-theme">-->
				<c:forEach items="${recommendedProducts}" var="p">
					<div class="product product__style--3 col-lg-4 col-md-4 col-sm-6 col-12">
						<div class="product__thumb">
							<a class="first__img"
								href="<c:url value="/product?productId=${p.id}"/>"><img
								style="max-height: 340px; overflow: hidden"
								src="<c:url value="/files/product/${p.imgUrl} "/>"></a>
						</div>
						<div class="product__content content--center">
							<h4>
								<a href="<c:url value="/product?productId=${p.id}"/>">
									${ p.title } </a>
							</h4>
							<ul class="prize d-flex">
								<li class="price">${p.price}</li>
								<li style="display: inline; text-decoration: underline">đ</li>
							</ul>
							<!--  
							<div class="action">
								<div class="actions_inner">
									<ul class="add_to_links">
										<li><a class="cart"
											href="<c:url value="/add_to_cart?productId=${p.id}"/>"><i
												class="bi bi-shopping-bag4"></i></a></li>
									
									</ul>
								</div>
							</div>
							<div class="product__hover--content">Thêm vào giỏ hàng</div>
							-->
						</div>
					</div>
					</c:forEach>
				<!-- </div> -->
			</div>
			</div>
			<!-- end recommended product -->
		<!-- comment -->
		<div class="blog-details content">
				<!-- form comment-->
				<div class="comment_respond">	
					<div class="cmt__btn">
					<c:if test="${customer==null}">
						<a id="showModel_btn" href="#">Gửi đánh giá của bạn</a> 
					</c:if>
					<c:if test="${customer!=null}">
						<a id="showCommment" href="#">Gửi đánh giá của bạn</a>
					</c:if>
					
					</div>
					<div class="checkout_login">
						<form class="comment__form" action="#">
							<div class="rateYo_form">
								<span> Chọn đánh giá của bạn </span>
								<div id="rateYo"></div>
								<span id="value_star"></span>
								<input type="hidden" name="rating" id="rating" ></input>
							
							</div>
							<div class="input__box">
							  <textarea class="comment_area" name="comment" placeholder="Your comment here"></textarea>
								
							</div>
							<input type="hidden" name="productId" id="productId" value="${product.id}"></input>
							<c:if test="${customer!=null}">
								<input type="hidden" name="customerId" id="customerId" value="${customer.id}"></input>
							</c:if>
							<div class="submite__btn">
								<a href="#" id="sendComment">Post Comment</a>
							</div>
						</form>
					</div>
				</div>
				<!--  -->
				<!-- comment -->
				<div class="comments_area">
					<ul class="comment__list">
						<c:forEach items="${reviews}" var="r" varStatus="i">
							<li>
								<div class="wn__comment">
									<div class="thumb">
										<img src="<c:url value="/static/web/images/blog/comment/1.jpeg"/>" alt="comment images">
									</div>
									<div class="content">
										<div class="comnt__author d-block d-sm-flex">
											<span class="author_name"> ${r.customerName}</span> 
											<span  class="rate">${r.rating}</span> 
											<span>${r.createDate}</span>
										</div>
										<p>${r.comment}</p>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
					<ul class="wn__pagination">
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
				</div>
				<!-- end comment -->
				<!--  -->

		</div>
		
		<!-- end comment -->
		</div>
		 <jsp:include page="./web/sidebar.jsp"></jsp:include>
	</div>
</div>
<!-- End main Content -->
<script>
	$(document).ready(function(){
		loadAuthorNames();
		$('#rateYo').rateYo({
			rating: 0,
			halfStar:true,
			startWidth: '40px',
			numStars: 5,
			minValue: 0,
			maxValue: 5,
			normalFill:'gray',
			ratedFill:'orange',
			onChange : function(rating, rateYoInstance){
				$('#rateYo').val(rating);
				$('#value_star').text(rating);
				$('#rating').val(rating);
			}
		});
		loadStar();
		
		$('#sendComment').click(function (e) {
			e.preventDefault();
			if($(".comment__form").valid()){
				var nestedData = $(".comment__form").toJson(); 
				sendComment(nestedData);
			}else{
				alert("that bai");
			}
		});
		
		$("body").on("click",".page-item",function(e){
			e.preventDefault();
			$(this).addClass("active").siblings().removeClass('active');
			var pageIndex = $(this).text();
			var productId = $('.productId').val();
			 $.ajax({
				 url: "${reviewGetAPI}", 
				 type :"GET",
				 data : {
					page : pageIndex,
					productId : productId
				 },
				 success: function(value){
					var bodyComment =  $("ul.comment__list")
					bodyComment.empty();
					bodyComment.append(value);
					loadStar();
					//console.log(value);
					//$.each(value, function(index, item) {
			       //   console.log(item.comment);
			       // });
				 }
			});
		})
		
	});	
	
	function toCart(productId){
		var qty = $('#qty').val();
		event.preventDefault();
		 $.ajax({
			 url: "/inventory/add_to_cart", 
			 type :"GET",
			 data : {
				 productId : productId,
				 qty : qty
			 },
			 success: function(value){
				if(value==='1'){
					$('ul li.shopcart span').addClass('product_qun');
				}
			 	$('ul li.shopcart span').text(value);
			 	PNotify.success({
					text : 'Them vao gio hang thanh cong',
					delay : 1000
				 });
			 },
			 error: function (error) {
	         	console.log(error);
	        }
		});
	}
	
	function loadStar(){
		$(".comment__list span.rate").each(function(index,item){
			var s = $(item).html();
			$(item).rateYo({
				rating: s,
				startWidth: '5px',
				readOnly: true			
			});
    	});
	}
	function sendComment(data){
		$.ajax({
            url: '${reviewPostAPI}',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'text',
            success: function () {
            	//console.log("thanh cong "+result);
            	// clear form commet
            	clearFormComment();
            	// display modal
            	openSuccessReview();
            },
            error: function (error) {
            	console.log("That bai : "+error);
            }
        });
	}
	
	function loadAuthorNames(){
		var data = [];
		<c:forEach items="${authors}" var="author">
        	data.push("<a href='<c:url value='/author?authorId="+"${author.id}"+"'/>'>" +"${author.name}"+ "</a>");
   		</c:forEach>
   		if(data.length <= 1){
   			$('#authorName').empty();
   	   		$('#authorName').append(data[0]);
   		}else {
   			$('#authorName').empty();
   			var a = data.toString();
   			$('#authorName').append(a);
   		}
	}
	
	function clearFormComment(){
		$('#rateYo').rateYo("method","rating", 0);
		$('#value_star').text('');
		$('.comment_area').val('');	
	}
	function openSuccessReview(){
		$('.modelMsg').addClass("active");
		$('#overplay').addClass("active");
	}
	// contentType: Kiểu nội dung của dữ liệu được gửi lên server.
	// data : Dữ liệu được gửi lên server khi thực thi một request Ajax.
	// dataType: Kiểu của dữ liệu mong muốn được trả về từ server.
	// "<li> <div class='wn__comment'> <div class='thumb'> <img src='/inventory/static/web/images/blog/comment/1.jpeg' alt='comment images'> </div> <div class='content'> <div class='comnt__author d-block d-sm-flex'><span class='author_name'>"+data.customerName+"</span> <span class='rate'>" +data.rating+" </span>  <span>"+data.createDate+"</span> </div> <p>"+data.comment+"</p> </div> </div> </li>");
</script>