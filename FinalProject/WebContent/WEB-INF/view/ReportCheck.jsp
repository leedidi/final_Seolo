<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ReportCheck.jsp</title>
<!-- 체크리스트 신고받은 리스트 목록 페이지 -->

<!-- 통일하기로 한 부트스트랩 -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
	integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
	crossorigin="anonymous"></script>

</head>
<body>

	<!-- 내비바 -->
	<div>
		<c:import url="MenuNavbar_admin.jsp"></c:import>
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>

	<div class="container">
		<!-- 게시판 메인 -->
		<div class="page-header">
			<h1 class="page-heading">신고처리</h1>
		</div>
		<br> <br>

	   <!-- 신고처리 분류 -->
	   <ul class="nav nav-tabs page-header-tabs" id="categori" role="tablist">
	   	   <li class="nav-item"><a class="nav-link" href="reportlist.action">전체</a></li>
	       <c:forEach var="cateNameList" items="${cateNameList }">
	       <li class="nav-item">
	         <a class="nav-link" href="reportlist.action?report_check=${cateNameList.statusname }">${cateNameList.statusname }</a>
	       </li>
	       </c:forEach> 
	       <li class="nav-item"><a class="nav-link" href="reportlist.action?report_check=미해결">미해결</a></li>
	    </ul>

		<div class="table-responsive">
			<table class="card-text table table-striped">
				<thead>
					<tr>
						<th>순번</th>
						<th>&nbsp;&nbsp;상태</th>
						<th style="text-align: center;" width="50%;">신고사유</th>
						<th>신고자</th>
						<th>&nbsp;&nbsp;작성일시</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach var="list" items="${list }">
					<tr>
						<th scope="row">${list.rpcheck_no }</th>
						<td>[${list.statusname }]</td>
						<td><a href="reportview.action?rpcheck_no=${list.rpcheck_no}">${list.title }</a></td>
						<td>${list.reportername }</td>
						<td>${list.reportdate }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
			
			<!-- 페이징 처리 부분 -->
			<div class="dataTables_paginate paging_simple_numbers"
				style="text-align: center;"datatable_paginate">
				<ul id="datatable_pagination" class="pagination datatable-custom-pagination justify-content-center">
		            <!-- prev 부분도 필요할까봐 넣어둠! -->
		            <li class="paginate_item page-item disabled">
		               <a class="paginate_button previous page-link" aria-controls="datatable" data-dt-idx="0" tabindex="0" id="datatable_previous">
		               <span aria-hidden="true">Prev</span></a>
		            </li>
		            <li class="paginate_item page-item active">
		               <a class="paginate_button page-link" aria-controls="datatable" data-dt-idx="1" tabindex="0">1</a>
		            <!-- </li>
		            <li class="paginate_item page-item">
		               <a class="paginate_button page-link" aria-controls="datatable" data-dt-idx="2" tabindex="0">2</a>
		            </li>
		            <li class="paginate_item page-item">
		               <a class="paginate_button page-link" aria-controls="datatable" data-dt-idx="2" tabindex="0">3</a>
		            </li>
		            <li class="paginate_item page-item">
		               <a class="paginate_button page-link" aria-controls="datatable" data-dt-idx="2" tabindex="0">4</a>
		            </li> 
		            <li class="paginate_item page-item">
		               <a class="paginate_button next page-link" aria-controls="datatable" data-dt-idx="3" tabindex="0" id="datatable_next">
		               <span aria-hidden="true">Next</span></a> -->
			        <li class="paginate_item page-item disabled">
			        <a class="paginate_button next page-link" aria-controls="datatable" data-dt-idx="3" tabindex="0" id="datatable_next">
			        <span aria-hidden="true">Next</span></a>
		            </li>
		         </ul>
			</div>
		</div>
	</div>

</body>
</html>