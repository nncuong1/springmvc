<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- left sidebar -->
<!-- ============================================================== -->
<div class="nav-left-sidebar sidebar-dark">
    <div class="menu-list">
      <div id="accordion">
        <nav class="navbar navbar-expand-lg navbar-light">
            <a class="d-xl-none d-lg-none" href="#">Dashboard</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav flex-column">
                    <li class="nav-divider">
                        Menu
                    </li>
                    <c:forEach items="${menuSession}" var="menu" varStatus="loop">
                    <!--<c:if test="${menu.orderIndex !=-1 }"> -->
                    <li id="${menu.idMenu}" class="nav-item">
                    	<!-- active -->
                        <a class="nav-link" href="#" data-toggle="collapse" aria-expanded="false" data-target="#submenu-${loop.index}" aria-controls="submenu-1">${menu.name} <span class="badge badge-success">6</span></a>
                        <div id="submenu-${loop.index}" class="collapse submenu" style="" data-parent="#accordion">
                            <ul class="nav flex-column">
                               <c:forEach items="${menu.child}" var="child">
                               		  <!--<c:if test="${child.orderIndex!=-1}">-->
		                                <li id="${child.idMenu}" class="nav-item">
		                                    <a class="nav-link" href="<c:url value="/admin${child.url}"/>">${child.name}</a>
		                                </li>
	                               <!-- </c:if>-->
                            	</c:forEach>
                            </ul>
                        </div>
                    </li>
                    <!-- </c:if> -->
                    </c:forEach>
                </ul>
            </div>
        </nav>
        </div>
    </div>
</div>
<!-- ============================================================== -->
<!-- end left sidebar -->