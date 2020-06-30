<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
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
		<!-- Favicons -->
		<link rel="shortcut icon" href="<c:url value="/static/web/images/favicon.ico"/>">
		<link rel="apple-touch-icon" href="<c:url value="/static/web/images/icon.png"/>">
		<!-- Google font (font-family: 'Roboto', sans-serif; Poppins ; Satisfy) -->
		<link rel="stylesheet" href="<c:url value="/static/web/fonts/open+san.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/fonts/poppyn.css"/>">
		<link rel="stylesheet" href="<c:url value="/static/web/fonts/familyRoboto.css"/>">
		<!-- Stylesheets -->
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
                                    <h3 class="account__title">Tạo tài khoản</h3>
                        
                                     <form:form  servletRelativeAction="" modelAttribute="registerForm" method="post">
                                    <div class="account__form">
                                        <div class="input__box">
                                            <label>Tên của bạn<span>*</span></label>
                                            <form:input path="name" />
                                            <form:errors path="name" cssClass="form-text"></form:errors>
                                        </div>
                                        <div class="input__box">
                                            <label>Email <span>*</span></label>
                                            <form:input path="email" />
                                            <form:errors path="email" cssClass="form-text"></form:errors>
                                        </div>
                                        <div class="input__box">
                                            <label>Username <span>*</span></label>
                                            <form:input path="username" />
                                            <form:errors path="username" cssClass="form-text"></form:errors>
                                        </div>
                                        <div class="input__box">
                                            <label>Mật khẩu<span>*</span></label>
                                            <form:password path="password" />
                                            <form:errors path="password" cssClass="form-text"></form:errors>
                                        </div>
                                        <div class="input__box">
                                            <label>Nhập lại mật khẩu<span>*</span></label>
                                            <form:password path="confirmPassword" />
                                            <form:errors path="confirmPassword" cssClass="form-text"></form:errors>
                                        </div>
                                        <div class="form__btn">
                                            <button>Đăng ký</button>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            </div>
	        <script src="/NongSan/static/client/js/vendor/jquery-3.2.1.min.js"></script>
			<script src="/NongSan/static/client/js/popper.min.js"></script>
			<script src="/NongSan/static/client/js/bootstrap.min.js"></script>
			<script src="/NongSan/static/client/js/plugins.js"></script>
			<script src="/NongSan/static/client/js/active.js"></script>
    </body>
</html>