<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %> 
<!-- Header -->
<header id="wn__header" class="oth-page header__area header__absolute sticky__header">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-4 col-sm-4 col-7 col-lg-2">
                <div class="logo">
                    <a href="/NongSan/home">
                        <img src="<c:url value="/static/web/images/logo/logo.png"/>" alt="logo images">
                    </a>
                </div>
            </div>
            <div class="col-lg-8 d-none d-lg-block">
                <nav class="mainmenu__nav">
                    <ul class="meninmenu d-flex justify-content-start">
                        <li class="drop with--one--item"><a href="<c:url value="/home"/>">Trang chủ</a></li>
                    </ul>
                    <ul class="meninmenu d-flex justify-content-start">
                        <li class="drop with--one--item"><a href="<c:url value="/shop"/>">Cửa hàng</a></li>
                    </ul>
                </nav>
            </div>
            <div class="col-md-8 col-sm-8 col-5 col-lg-2">
                <ul class="header__sidebar__right d-flex justify-content-end align-items-center">
                    <li class="shop_search"><a class="search__active" href="#"></a></li>
                    <li class="wishlist"><a href="#"></a></li>
                    <li class="shopcart"><a class="cartbox_active" href="<c:url value="/cart"/>">
                   	    <c:choose>
	                    	<c:when test="${sessionScope.cart.size()>0}">
	                    		<span class="product_qun">${sessionScope.cart.size()}</span>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<span></span>
	                    	</c:otherwise>
                    	</c:choose>
                   	</a>
                    </li>
                    <li class="setting__bar__icon"><a class="setting__active" href="#"></a>
                        <div class="searchbar__content setting__block">
                            <div class="content-inner">
                                <div class="switcher-currency">
                                    <strong class="lAccountabel switcher-label">
                                        <c:if test="${customer!=null}">
                                            <span>Xin chào, ${customer.name} (${customer.username})</span>
                                        </c:if>
                                    </strong>
                                    <div class="switcher-options">
                                        <div class="switcher-currency-trigger">
                                            <div class="setting__menu">
                                                <c:choose>
                                                    <c:when test="${customer==null}">
                                                        <span><a href="<c:url value="/login"/>">Đăng nhập</a></span>
                                                        <span><a href="<c:url value="/register"/>">Tạo tài khoản</a></span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span><a href="<c:url value="/logout"/>">Đăng xuất</a></span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <!-- Start Mobile Menu -->
        <!-- End Mobile Menu -->
        <div class="mobile-menu d-block d-lg-none">
        </div>
        <!-- Mobile Menu -->	
    </div>		
</header>
<div class="box-search-content search_active block-bg close__top">
    <form id="searchForm" action="/inventory/shop" method="get" id="search_mini_form" class="minisearch">
        <div class="field__search">
            <input type="text" placeholder="Tìm kiếm theo tên hoặc thể loại.." name="key">
            <input type="hidden" name="page" id="page"/> 
            <div class="action">
                <a href="#"><i class="zmdi zmdi-search"></i></a>
            </div>
        </div>
    </form>
    <div class="close__wrap">
        <span>đóng</span>
    </div>
</div>
<script>
	
</script>