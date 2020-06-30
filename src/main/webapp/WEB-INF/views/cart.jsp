
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
         <!-- cart-main-area start -->
        <div class="cart-main-area section-padding--lg bg--white">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 col-sm-12 ol-lg-12">
                        <i style="color:red">${message}</i>
                        <form action="#">               
                            <div class="table-content wnro__table table-responsive">
                                <table id="table">
                                    <thead>
                                        <tr class="title-top">
                                            <th class="product-thumbnail">Hình ảnh</th>
                                            <th class="product-name">Sản phẩm</th>
                                            <th class="product-price">Giá</th>
                                            <th class="product-quantity">Số lượng</th>
                                            <th class="product-subtotal">Tổng tiền</th>
                                            <th class="product-remove">Xóa</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${sessionScope.cart}" var="map">
                                    		<tr>
                                            <td class="product-thumbnail"><a href="#"><img src="<c:url value="/files/product/${map.value.product.imgUrl }"/>" alt="product img"></a></td>
                                            <td class="product-name"><a href="#">${ map.value.product.title }</a></td>
                                            <td class="product-price"><span class="amount price" id="amountId">${map.value.unitPrice }</span></td>
                                            <td class="product-quantity"><input type="number" min=1 onchange="changeQuantity(this,${map.value.unitPrice},${map.key})" id="valueNumber" value="${map.value.quantity}"></td>
                                           	<td class="product-subtotal"><span class="subtotal-price price">${map.value.unitPrice * map.value.quantity}</span></td>
                                            <!-- <td class="product-remove"><a href="<c:url value="/remove_cartItem?key=${map.key }"/>">X</a></td> -->
                                            <td class="product-remove"><a href="#" onclick="removeCartItem(${map.key},this)" >X</a></td>
                                       		</tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </form> 
                        <div class="cartbox__btn">
                            <ul class="cart__btn__list d-flex flex-wrap flex-md-nowrap flex-lg-nowrap justify-content-between">
                                <li><a href="<c:url value="/shop"/>">Cập nhật giỏ hàng</a></li>
                 				<c:choose>
                 					<c:when test="${sessionScope.cart.size() >= 1}">
                 					<c:if test="${customer!=null}">
										<li><a class="preOrderpreOrder" href="<c:url value="/customer/checkout"/>">Tiến hành đặt hàng</a></li>
									</c:if>
									<c:if test="${customer==null}">
										<li><a class="preOrder" id="showModel_btn">Tiến hành đặt hàng</a></li>
									</c:if>
                 					</c:when>
                 					<c:otherwise>
                 						 <li><a class="preOrder" href="javascript:void(0)" onclick="noItemInCart()">Chưa có sản phẩm nào trong giỏ hàng</a></li>
                 					</c:otherwise>
                 				</c:choose>
                                
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6 offset-lg-6">
                        <div class="cartbox__total__area">
                            <div class="cartbox-total d-flex justify-content-between">
                                <ul class="cart__total__list">
                                    <li>Tổng tiền giỏ hàng</li>
                                </ul>
                                <ul class="cart__total__tk">
                                    <li id="totalCart" class="price"></li>
                                </ul>
                            </div>
                            <div class="cart__total__amount">
                                <span></span>
                                <span id="totalFinal"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>  
        </div>
<script>
	$(document).ready(function(){
		processMessage();
		loadPrice();
		
	});
	
	function changeQuantity(input, pri,productId){
		console.log("product id : "+productId);
		var finalPrice = pri * $(input).val();
		var qty = $(input).val();
		console.log("price : "+finalPrice);
		$(input).closest("tr").find('td.product-subtotal span').text(numeral(finalPrice).format('0,0'));
		loadPrice();
		$.ajax({
			 url: "/inventory/update_to_cart", 
			 type :"GET",
			 data : {
				 productId : productId,
				 qty : qty
			 },
			 success: function(value){
			 	
			 }
		});
	}
		
	function loadPrice(){
		var sum = 0;
		$('tbody td.product-subtotal span').each(function(){
			sum = sum + parseInt(parseCurrency($(this).text()));
		});
		$('#totalCart').text(sum);
		var forma = numeral(sum).format('0,0');
		$('#totalCart').text(forma);
	}
	
	function processMessage(){
		var msgNoItem = '${msgNoItem}';
		if(msgNoItem){
			PNotify.success({
				text : msgNoItem,
				delay : 1500
			});
		}
	}
	
	function parseCurrency( num ) {
	    return parseFloat( num.replace( /,/g, '') );
	}
	
	function removeCartItem(key,item){
		event.preventDefault();
		 $.ajax({
			 url: "/inventory/remove_cartItem", 
			 type :"GET",
			 data : {
				 key : key
			 },
			 success: function(value){
			 	$(item).closest("tr").remove();
			 	$('span.product_qun').text(value);
			 	if($('span.product_qun').text()==='0'){
			 		console.log("het hang");
			 		$('li a.preOrder').hide();
			 		$('ul li.shopcart span').removeClass('product_qun')
			 		$('ul li.shopcart span').text('');
			 	}
			 	loadPrice();
			 },
			 error: function (error) {
	         	console.log(error);
	        }
		});
	}
	
	function noItemInCart(){
		alert("Chua co san pham nao trong gio")
	}

	//var table =  document.getElementById("table");
	//var sumVal =0;
	//for(var i=1 ;i<table.rows.length;i++){
//		sumVal= sumVal + parseInt(table.rows[i].cells[4].innerText);
	//}
//	var cartTotal = document.getElementById("totalCart");
//	cartTotal.innerHTML=sumVal;

</script>