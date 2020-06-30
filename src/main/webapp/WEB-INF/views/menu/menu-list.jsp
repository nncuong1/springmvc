<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<!-- ============================================================== -->
	<!-- table -->
	<!-- ============================================================== -->
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
		<div class="card">
			<h5 class="card-header">Menu List</h5>
			<div class="card-body">
				<div class=row>
					<div class="col-sm-12 col-md-6">
						<a href="<c:url value="/admin/menu/permission"/>" class="btn btn-app"><i class="fas fa-plus"></i>Permission</a>
					</div>
					<div class="col-sm-12 col-md-6">
					<form id="searchForm" method="GET" action="<c:url value="/admin/menu/list"/>">
						<div class="table_form_search">
							<label>Search : 
							<input type="search" name="keyword" class="form-control form-control-sm" />
							</label>
							<button type="submit" style="display: none">Submit</button>
						</div>
					</form>
					</div>
				</div>
				<table id="table-menu" class="table table-striped">
					<thead>
						<tr>
							<th rowspan="2" scope="col" style="border-right : 2px solid">STT</th>
							<th rowspan="2" scope="col" style="border-right : 2px solid">Url</th>
							<th rowspan="2" scope="col" style="border-right : 2px solid">Status</th>
							<th class="text-center" colspan="${roles.size()}" scope="col" style="border-right : 2px solid " >Role</th>
						</tr>
						<tr>
							<c:forEach var="role" items="${roles}">
								<th style="border-right : 2px solid">${role.roleName}</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${menuList}" var="menu" varStatus="loop">
							<tr>
								<td scope="row">${pageInfor.offset+loop.index+1}</td>
								<td>${menu.url}</td>
								<td><a href="javascript:void(0);" style="text-decoration:none" onclick="confirmChange(${menu.id},${menu.activeFlag},this);">${menu.activeFlag}</a></td>
								<!-- 
								<c:choose>
									<c:when test="${menu.activeFlag==1 }">
										<td><a href="javascript:void(0);" onclick="confirmChange(${menu.id},${menu.activeFlag});" class="btn btn-round btn-danger">Disable</a></td></td>
									</c:when>
									<c:otherwise>
										<td><a href="javascript:void(0);" onclick="confirmChange(${menu.id},${menu.activeFlag});" class="btn btn-round btn-primary">Enable</a></td></td>
									</c:otherwise>
								</c:choose>
								-->
								
								<c:forEach items="${menu.mapAuth}" var="auth">
									<td class="permission-${auth.value}"><i></i></td>
									<!--  
									<c:choose>
										<c:when test="${auth.value==1}">
											<td><i class="fa fa-check" style="color:green"></i></td>
										</c:when>
										<c:otherwise>
											<td><i class="fa fa-times" style="color: red"></i></td>
										</c:otherwise>
									</c:choose>
									-->
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<nav aria-label="...">
					<ul class="pagination pagination-sm">
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
				</nav>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		processMessage();
		loadUI();
		$("body").on("click",".page-item",function(){
			$(this).addClass("active").siblings().removeClass('active');
			var pageIndex = $(this).text();
			 $.ajax({
				 url: "/inventory/admin/paging/menu", 
				 type :"GET",
				 data : {
					page : pageIndex
				 },
				 success: function(value){
				   var bodyMenu =  $("#table-menu").find("tbody");
				   bodyMenu.empty();
				   bodyMenu.append(value);
				   loadUI();
				 },
				 error: function (error) {
		         	console.log(error);
		        }
			});
		})
	});
	
	function loadUI(){
		var status = $("#table-menu").find("td a");
		$.each(status, function(index,item) {
			if(item.text==='1'){
				$(this).addClass("btn btn-rounded btn-danger");
				item.text = "Disable";
			}else{
				$(this).addClass("btn btn-rounded btn-primary");
				item.text = "Enable";
			}
		});
		$("#table-menu td.permission-0 i").addClass("fa fa-times").css("color", "red");
		$("#table-menu td.permission-1 i").addClass("fa fa-check").css("color", "green");;
	}
	
	function confirmChange(id,flag,item){
		var msg = flag==1 ? 'Do you want disable this menu ?' : 'Do you want enable this menu ?';
		if(confirm(msg)){
			window.location.href = '<c:url value="/admin/menu/change-status/"/>'+id;
			//if(item.text==='Disable'){
		//		$(item).removeClass("btn-danger");
			//	$(item).addClass("btn-primary");
			//	item.text = "Enable";
		//	}else{
			//	$(item).addClass("btn-danger");
			//	$(item).removeClass("btn-primary");
		//		item.text = "Disable";
		//	}
		}
	}
	
	function processMessage(){
		var msgSuccess = '${msgSuccess}';
		var msgError = '${msgError}';
		if(msgSuccess){
			PNotify.success({
				text : msgSuccess,
				delay : 2000
			});
		}
		if(msgError){
			PNotify.error({
				text : msgError,
				delay : 2000
			});
		}
	}
</script>