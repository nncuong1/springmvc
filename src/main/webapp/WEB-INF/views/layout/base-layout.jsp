<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Inventory Admin</title>

    <link rel="stylesheet" href="<c:url value="/static/admin/vendor/bootstrap/css/bootstrap.min.css"/>">
    <link href="<c:url value="/static/admin/vendor/fonts/circular-std/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/admin/vendor/multi-select/css/jquery.dropdown.css"/>" rel="stylesheet">
    <!-- pnotyfy 
     -->
     <link href="<c:url value="/static/admin/vendor/pnotify/css/PNotifyBrightTheme.css"/>" rel="stylesheet">
     <link rel="stylesheet" href="<c:url value="/static/admin/vendor/datepicker/tempusdominus-bootstrap-4.css"/>">
    
    <link rel="stylesheet" href="<c:url value="/static/admin/libs/css/style.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/admin/vendor/fonts/fontawesome/css/fontawesome-all.css"/>">
    

	 <script src="<c:url value="/static/admin/vendor/jquery/jquery-3.3.1.min.js"/>"></script>
  </head>

<body>
    <!-- ============================================================== -->
    <!-- main wrapper -->
    <!-- ============================================================== -->
    <div class="dashboard-main-wrapper">
         <!-- ============================================================== -->
        <!-- navbar -->
        <!-- ============================================================== -->
		<tiles:insertAttribute name="top-nav"></tiles:insertAttribute>
        <!-- ============================================================== -->
        <!-- end navbar -->

        <!-- ============================================================== -->
        
        <!-- left sidebar -->
        <!-- ============================================================== -->
     	<tiles:insertAttribute name="sidebar"></tiles:insertAttribute>
        <!-- ============================================================== -->
        <!-- end left sidebar -->
     
        <!-- ============================================================== -->
        
        <!-- wrapper  -->
        <!-- ============================================================== -->
        <div class="dashboard-wrapper">
            <div class="container-fluid dashboard-content">
            <tiles:insertAttribute name="body"></tiles:insertAttribute>
            </div>
            <!-- ============================================================== -->
            <!-- footer -->
            <!-- ============================================================== -->
           	<tiles:insertAttribute name="footer"></tiles:insertAttribute>
            <!-- ============================================================== -->
            <!-- end footer -->
            <!-- ============================================================== -->
        </div>
    </div>
    <!-- ============================================================== -->
    <!-- end main wrapper -->
    <!-- ============================================================== -->
    <!-- Optional JavaScript -->
   
    <script src="<c:url value="/static/admin/vendor/bootstrap/js/bootstrap.bundle.js"/>"></script>
    <script src="<c:url value="/static/admin/vendor/multi-select/js/jquery.dropdown.js"/>"></script>
    <script src="<c:url value="/static/admin/vendor/slimscroll/jquery.slimscroll.js"/>"></script>
    
    <!-- pnotyfi  -->
     <script src="<c:url value="/static/admin/vendor/pnotify/js/PNotify.js"/>"></script>
     
    <script src="<c:url value="/static/admin/libs/js/main-js.js"/>"></script>
    <script src="<c:url value="/static/admin/vendor/datepicker/moment.js"/>"></script>
    <script src="<c:url value="/static/admin/vendor/datepicker/tempusdominus-bootstrap-4.js"/>"></script>
    <script src="<c:url value="/static/admin/vendor/datepicker/datepicker.js"/>"></script>
</body>
</html>