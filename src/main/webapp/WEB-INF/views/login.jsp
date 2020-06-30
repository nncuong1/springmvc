<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
 
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value="/static/admin/vendor/bootstrap/css/bootstrap.min.css"/>">
    <link href="<c:url value="/static/admin/vendor/fonts/circular-std/style.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/static/admin/libs/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/admin/vendor/fonts/fontawesome/css/fontawesome-all.css"/>">
    <style>
    html,
    body {
        height: 100%;
    }

    body {
        display: -ms-flexbox;
        display: flex;
        -ms-flex-align: center;
        align-items: center;
        padding-top: 40px;
        padding-bottom: 40px;
        font-size: 16px;
    }
    <!-- 
    .msg-error{
    	display: block;
    	font-size: 100%;
    }-->
    </style>
</head>

<body>
    <!-- ============================================================== -->
    <!-- login page  -->
    <!-- ============================================================== -->
    <div class="splash-container">
        <div class="card ">
            <div class="card-header text-center"><span class="splash-description">Admin login</span></div>
            <div class="card-body">
                <form:form  modelAttribute="loginForm" servletRelativeAction="" method="POST">
                    <div class="form-group">
                        <form:input cssClass="form-control form-control-lg"  path="username" placeholder="Username" />
                         <div class="invalid-feedback msg-error">
                			<form:errors path="username" cssClass="form-text"></form:errors>
                		</div>
                    </div>
                   
                    <div class="form-group">
                        <form:password cssClass="form-control form-control-lg" path="password" placeholder="Password" />
                        <div class="invalid-feedback msg-error">
                			<form:errors path="password" cssClass="form-text"></form:errors>
                		</div>
                    </div>
                    <button type="submit" class="btn btn-primary btn-lg btn-block">Sign in</button>
                </form:form>
            </div>
        </div>
    </div>
  
    <!-- ============================================================== -->
    <!-- end login page  -->
    <!-- ============================================================== -->
    <!-- Optional JavaScript -->
    <script src="<c:url value="/static/admin/vendor/jquery/jquery-3.3.1.min.js"/>"></script>
    <script src="<c:url value="/static/admin/vendor/bootstrap/js/bootstrap.bundle.js"/>"></script>
</body>
 
</html>