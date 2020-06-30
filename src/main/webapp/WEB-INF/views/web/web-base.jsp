<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
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
	
		<!-- Cusom css -->
	<!--        <link rel="stylesheet" href="/NongSan/static/client/css/custom.css">-->
	
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
             <tiles:insertAttribute name="header"></tiles:insertAttribute>
            <!-- //Header -->
            <!-- Start Bradcaump area -->
            <div class="ht__bradcaump__area bg-image--6">
            </div>
            <!-- End Bradcaump area -->
            <!-- Start Shop Page -->
          	<tiles:insertAttribute name="body"></tiles:insertAttribute>
            <!-- End Shop Page -->
            <!-- Footer Area -->
            <tiles:insertAttribute name="footer"></tiles:insertAttribute>
            <!-- //Footer Area -->
					<div class="simple-model">
		    <!-- Modal -->
			<div class="model">
				<div class="model-header">
					<span class="close">&times;</span>
				</div>
				<br>
				<h1>Bạn cần đăng nhập để sử dụng tính năng này</h1>
				<div class="addtocart-btn">
					<a onclick="loginRedirect()" href="#">Đăng nhập</a>
				</div>
				<!-- requestScope['javax.servlet.forward.request_uri'] -->
			</div>
			<div class="modelMsg">
				<div class="model-header">
					<span class="close">&times;</span>
				</div>
				<br>
				<h1>Gui thanh cong dang cho phe duyet</h1>
			</div>
		    <!-- END Modal -->
		</div>
        </div>
        <div id="overplay"></div>
        <!-- //Main wrapper -->
        
        <script src="<c:url value="/static/admin/libs/js/numeral.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/popper.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/bootstrap.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/plugins.js"/>"></script>
		<script src="<c:url value="/static/admin/vendor/pnotify/js/PNotify.js"/>"></script>
		<script src="<c:url value="/static/web/js/active.js"/>"></script>
		<script>
			function loginRedirect(){
				var path = $(location).attr('pathname');
				var search = $(location).attr('search');
				var finalPath = getPathName(path);
				if(search){
					console.log("search "+search);
				}
				var url = finalPath+search;
				//console.log("url : "+url);
				window.location.href="/inventory/login?from="+url;
			}
			function getPathName(input){
				var position = input.lastIndexOf('/');
				return input.substring(position,input.length);
			}
		</script>
    </body>
</html>