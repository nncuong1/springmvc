<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<section class="wn__checkout__area section-padding--lg bg__white">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-12  md-mt-40 sm-mt-40">
			
			
				<div class="customer_details">
					<h3>Billing details</h3>
					  
					<div class="input_box">
						<label>Địa chỉ đã lưa chữ<span>*</span></label> 
						<select id="addresses" class="select__option" onchange="changeAddress(this)">
							<option value="0">Thêm địa chỉ mới</option>
							<c:forEach items="${addresses}" var="address">
								<option value="${address.id}" data-fullName="${address.fullName}" data-phone="${address.phone}" 
									data-province="${address.province.id}" data-district="${address.district.id}" data-addr="${address.addr}">
								${address.addr}
								</option>
							</c:forEach>
						</select>
					</div>
		
					<form:form id="customer_address" modelAttribute="addressForm" method="post" servletRelativeAction="/customer/order">
					<form:hidden path="id"/>
					
					<div class="customar__field">
						<div class="input_box">
							<label>Họ và tên<span>*</span></label> <form:input path="fullName" />
						</div>
						<!--  
						<div class="input_box">
							<label>Thành phố<span>*</span></label> 
							<form:select path="provinceId" cssClass="select__option">
								<option disabled="disabled" value="">Chọn thành phố</option>
								<form:options items="${mapProvince}" data-province="${mapProvince.key}"/>
							</form:select>
						</div>
						<div class="input_box">
							<label>Quận huyện<span>*</span></label> 
							<form:select path="districtId" cssClass="select__option">
								<option disabled="disabled" value="">Chọn quận huyện…</option>
								<form:options items="${mapDistrict}"/>
							</form:select>
						</div>
						-->
						<div class="input_box">
							<label>Thành phố<span>*</span></label> 
								<select id="province_id" class="select__option">
								  	<option disabled="disabled" value="" >Chọn thành phố</option>
									<c:forEach items="${provinces}" var="province">
										<option value="${province.id}" data-province="${province.id}">
										${province.name}
										</option>
									</c:forEach>
								</select>
								<form:hidden path="provinceId"/>
						</div> 
						
						<div class="input_box">
							<label>Quận huyện<span>*</span></label> 
							<select id="district_id" class="select__option">
								<option disabled="disabled" value="">Chọn quan huyen</option>
								<c:forEach items="${districts}" var="district">
									<option value="${district.id}" data-province="${district.province.id}">
									${district.name}
									</option>
								</c:forEach>
							</select>
							<form:hidden path="districtId"/>
						</div>
						
						<div class="input_box">
							<label>Địa chỉ <span>*</span></label> <form:input path="addr" placeholder="Street address" />
						</div>
						
						<div class="input_box">
							<label>Số điện thoại<span>*</span></label> <form:input path="phone" />
						</div>
					</div>
					</form:form>
				</div>
				
			</div>
			<div class="col-lg-6 col-12 md-mt-40 sm-mt-40">
				<div class="wn__order__box">
					<h3 class="onder__title">Đơn hàng của bạn</h3>
					<ul class="order__total">
						<li>Sản phẩm</li>
						<li>Total</li>
					</ul>
					<ul class="order_product">
					<c:forEach items="${sessionScope.cart}" var="map">
						<li>${ map.value.product.title } × ${map.value.quantity}<span class="price">${map.value.unitPrice * map.value.quantity}</span></li>
					</c:forEach>
					</ul>
					<ul class="total__amount">
						<li>Tổng tiền <span></span></li>
					</ul>
				</div>
				<div class="order__btn">
					<a id="order__btn" href="#">Đặt hàng</a>
				</div>
			</div>
		</div>
	</div>
</section>
<script>
	$options = $('#district_id').find('option');
	
	$(document).ready(function(){
		$('.bradcaump-title').html('Đặt hàng');
		$('span.breadcrumb_item').html('Đặt hàng');
		$(".order__btn").click(function(e){
			$('#provinceId').val($('#province_id').val());
			$('#districtId').val($('#district_id').val());
			e.preventDefault();
			if($("#customer_address").valid()) {
				$("#customer_address").submit(); // Submit the form
			}
	    });
		var $select1 = $('#province_id'),
		$select2 = $('#district_id');
		
			$('#province_id').on('change', function() {
				console.log("change !!")
				var province = $(this).find("option:selected").data("province");
				$select2.html($options.filter('[data-province="' + province + '"]'));
				$select2.val($select2.find("option:first").val());
			}).trigger('change');
			
		var sum = 0;
		$('ul.order_product li span').each(function(){
			sum = sum + parseInt(parseCurrency($(this).text()));
		});
		$('ul.total__amount li span').text(sum);	
		$('ul.total__amount li span').text(numeral($('ul.total__amount li span').text()).format('0,0'))
		
	});
	function parseCurrency( num ) {
	    return parseFloat( num.replace( /,/g, '') );
	}
	
	function changeAddress(select){
		var option = select.options[select.selectedIndex];
		var doc = option.value;
		var fullName = option.getAttribute("data-fullName");
		var addr = option.getAttribute("data-addr");
		var phone = option.getAttribute("data-phone");
		var provinceId = option.getAttribute("data-province");
		var districId = option.getAttribute("data-district");
		$('#id').val(doc);
		if(doc===0){
			changeUiAddrForm('',0,0,'','');
		}else{
			changeUiAddrForm(fullName,provinceId, districId,addr,phone);
		}
		
	};
	function changeUiAddrForm(fullName,provinceId, districtId,addr,phone){
		$('#fullName').val(fullName);
		$('#addr').val(addr);
		$('#phone').val(phone);
		if(provinceId!=0){
			$('#province_id').val(provinceId);
			$('#district_id').html($options);
		}
		else{
			$('#province_id').val($('#province_id').find("option:first").val());
			//$('#provinceId option[value=""]').attr("selected",true);
		}
		if(districtId!=0){
			$('#district_id').html($options.filter('[data-province="' + provinceId + '"]'));
			$('#district_id').val(districtId);
		}
		else{
			$('#district_id').val($('#district_id').find("option:first").val());
			//$('#districtId option[value=""]').attr("selected",true);
		}
	};
	
</script>