<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập</title>
                <meta charset="utf-8">
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<title>BookStore</title>
		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1">
				<link rel="shortcut icon" href="<c:url value="/static/web/images/favicon.ico"/>">
		<link rel="apple-touch-icon" href="<c:url value="/static/web/images/icon.png"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/fonts/open+san.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/fonts/poppyn.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/fonts/familyRoboto.css"/>">

		<link rel="stylesheet" href="<c:url value="/static/web/css/bootstrap.min.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/css/plugins.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/css/style.css"/>">
		<script src="<c:url value="/static/web/js/vendor/modernizr-3.5.0.min.js"/>"></script>
		<script src="<c:url value="/static/web/js/vendor/jquery-3.2.1.min.js"/>"></script>

        </head>
        <body>
            <div class="wrapper" id="wrapper">
                <section class="my_account_area pt--80 pb--55 bg--white">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-6 col-12">
                                <div class="my__account__wrapper">
                                    <h3 class="account__title">Đăng nhập</h3>
                                    <form:form modelAttribute="customerForm" servletRelativeAction="" method = "post">
                                    	<input type="hidden" name="from" >
                                        <div class="account__form">
                                            <div class="input__box">
                                                <label>Tên người dùng<span>*</span></label>
                                                <form:input path="username"/>
                                                <form:errors path="username"></form:errors>
                                        </div>
                                        <div class="input__box">
                                            <label>Mật khẩu<span>*</span></label>
                                            <form:password path="password"/>
                                             <form:errors path="password"></form:errors>
                                        </div>
                                        <div class="form__btn">
                                            <button>Đăng nhập</button>
                                        </div>
                                         <a class="forget_pass" href="<c:url value="/register"/>">Đăng ký tài khoản mới</a>
                                    </div>
                               
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- End My Account Area -->
            </div>
			<script src="<c:url value="/static/web/js/popper.min.js"/>"></script>
			<script src="<c:url value="/static/web/js/bootstrap.min.js"/>"></script>
			<script src="<c:url value="/static/web/js/plugins.js"/>"></script>
			<script src="<c:url value="/static/web/js/active.js"/>"></script>
    </body>
</html>