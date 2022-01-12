<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	pageContext.setAttribute("replaceChar", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FaqListNode.jsp</title>

<!-- 통일하기로 한 부트스트랩 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>

<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	$(function()
	{

		// 글쓰기 버튼 클릭
		$("#writeBtn").click(function()
		{
			$(location).attr("href", "faqinsertform.action");

		});

		// 수정 버튼 클릭
		$(".btn-info").click(function()
		{
			$(location).attr("href", "faqupdateform.action?faq_no=" + $(this).val());
		});

		// 삭제 버튼 클릭
		$(".btn-danger").click(function()
		{
			if(confirm("해당 게시물을 정말 삭제하시겠습니까?"))
			{
				$(location).attr("href", "faqdelete.action?faq_no=" + $(this).val());
			}
		});
	});
</script>

</head>
<body>

	<!-- 내비바 -->
	<div>
		<c:choose>
			<c:when test="${!empty adminLogin }">
				<c:import url="MenuNavbar_admin.jsp"></c:import>
			</c:when>
			<c:otherwise>
				<c:import url="/nav.action"></c:import>
			</c:otherwise>
		</c:choose>
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>

	<div class="container">
		<!-- 게시판 메인 -->
		<div class="page-header">
			<h1 class="page-heading">자주 묻는 질문</h1>
		</div>
		<br> <br>

		<!-- 카테고리 분류 -->
		<ul class="nav nav-tabs page-header-tabs" id="categori" role="tablist">
			<li class="nav-item"><a class="nav-link" href="faqlist.action">전체</a></li>
			<c:forEach var="cateNameList" items="${cateNameList }">
				<li class="nav-item"><a class="nav-link" href="faqlist.action?faq_check=${cateNameList.qs_no }">${cateNameList.name }</a></li>
			</c:forEach>
		</ul>

		<!-- collapse/노드 확장  -->
		<div class="accordion" id="accordionExample">
			<div class="card">

				<c:forEach var="list" items="${list }">
					<div class="card">
						<div class="card-header" id="heading${list.faq_no }">
							<h2 class="mb-0">
								<button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapse${list.faq_no }" aria-expanded="false" aria-controls="collapse${list.faq_no }">
									<b>[${list.faqsort_name }]</b>&nbsp;&nbsp;${list.faq_title }
								</button>
							</h2>
						</div>
						<div id="collapse${list.faq_no }" class="collapse" aria-labelledby="heading${list.faq_no }" data-parent="#accordionExample">
							<div class="card-body">${fn:replace(list.faq_content, replaceChar, "<br/>") }
								<br> <br>
								<div style="float: right;">
									<c:if test="${!empty adminLogin}">
										<button type="submit" class="btn btn-info" id="updateBtn" value="${list.faq_no }">수정하기</button>
										<button type="submit" class="btn btn-danger" value="${list.faq_no }">삭제하기</button>
									</c:if>
								</div>
								<br> <br>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

			<br>
			<!-- 글쓰기 버튼 -->
			<div class="d-flex justify-content-end">
				<!-- 이 글쓰기 버튼은 관리자에게만 나타나는 버튼! -->
				<c:if test="${!empty adminLogin}">
					<button type="submit" class="btn btn-primary" id="writeBtn">글쓰기</button>
				</c:if>
			</div>
			<br> <br>

			<!-- 페이징 처리 부분 -->
			<div class="dataTables_paginate paging_simple_numbers" style="text-align: center;"datatable_paginate">
				<span style="text-align: center;">${pageIndexList }</span>
			</div>
			
			<%-- 부트스트랩이 적용된 페이징 처리 --%>
			<%--
            <br><br>
            <div class="dataTables_paginate paging_simple_numbers" style="text-align: center;"datatable_paginate">
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
            --%>
		</div>
	</div>
</body>
</html>