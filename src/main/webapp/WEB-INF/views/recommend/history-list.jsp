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
			<h5 class="card-header">Lịch sử cập nhật danh sách gợi ý </h5>

			<div class="card-body">
				<div class=row>
					<div class="col-sm-12 col-md-6">
						<a href="<c:url value="/admin/update-rating-file"/>" class="btn btn-app"><i class="fas fa-plus"></i>Cập nhật danh sách</a>
					</div>
				</div>
				<table id="table-category" class="table table-striped">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">Admin</th>
							<th scope="col">Thời gian cập nhật</th>
						</tr>
					</thead>
					<tbody>
							<tr>
								<th scope="row">1</th>
								<td>Cuong</td>
								<td>3 ngày trước</td>
							</tr>
							<tr>
								<th scope="row">2</th>
								<td>Admin2</td>
								<td>5 ngày trước</td>
							</tr>
					</tbody>
				</table>
				<nav aria-label="...">
				</nav>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
	});
</script>