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
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
	integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
	crossorigin="anonymous"></script>

<script type="text/javascript"
	src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	$(function()
	{

		// 글쓰기 버튼 클릭
		$("#writeBtn").click(function()
		{
			// 테스트
			//alert("글쓰기 버튼 클릭");

			$(location).attr("href", "faqinsertform.action");

		});

		/* // 수정 버튼 클릭 - 안되는 버전. 폐지
		$("#updateBtn").click(function()
		{
			// 테스트
			alert("수정 버튼 클릭");
			alert($(this).val());
			
			//$(location).attr("href", "faqupdateform.action?faq_no=" + $(this).val());
			
			if (confirm("해당 게시물을 정말 수정하시겠습니까?"))
				{
					$(location).attr("href", "faqupdateform.action?faq_no=" + $(this).val());
				}
			}); */

		// 수정 버튼 클릭
		$(".btn-info").click(
				function()
				{
					$(location).attr("href",
							"faqupdateform.action?faq_no=" + $(this).val());
				});

		// 삭제 버튼 클릭
		$(".btn-danger").click(
				function()
				{
					if (confirm("해당 게시물을 정말 삭제하시겠습니까?"))
					{
						$(location).attr("href",
								"faqdelete.action?faq_no=" + $(this).val());
					}
				});
	});
</script>

</head>
<body>

	<!-- 내비바 -->
<%-- 
	<div>
		<c:import url="MenuNavbar_admin.jsp"></c:import>
	</div>
 --%>
 
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
		<br>
		<br>
		<%-- 
		질문 분류
		<ul class="nav nav-tabs page-header-tabs" id="categori" role="tablist">
			<li class="nav-item"><a class="nav-link" href="faqlist.action?faq_check=1">체크리스트1</a></li>
			<li class="nav-item"><a class="nav-link" href="faqlist.action?faq_check=2">공동구매1</a></li>
			<li class="nav-item"><a class="nav-link" href="faqlist.action?faq_check=3">회원관리1</a></li>
			<li class="nav-item"><a class="nav-link" href="faqlist.action?faq_check=4">기타1</a></li>
		</ul>
		  --%>
		  
		
		 <!-- 카테고리 분류 -->
	   <ul class="nav nav-tabs page-header-tabs" id="categori" role="tablist">
	       <c:forEach var="cateNameList" items="${cateNameList }">
	       <li class="nav-item">
	         <a class="nav-link" href="faqlist.action?faq_check=${cateNameList.qs_no }">${cateNameList.name }</a>
	       </li>
	       </c:forEach> 
	    </ul>
		
		<!-- collapse/노드 확장  -->
		<div class="accordion" id="accordionExample">
			<div class="card">

				<%-- 
				<!-- 첫번째 노드는 페이지 오픈 시 늘 확장되어 있도록 함 -->
				
				<div id="collapseOne" class="collapse show"
					aria-labelledby="headingOne" data-parent="#accordionExample">
					<div class="card-body">
						Some placeholder content for the first accordion panel. This panel
						is shown by default, thanks to the
						<code>.show</code>
						class.
						내가 북마크 해 놓은 체크리스트에 내가 표시하고 싶은 단어를 스티커처럼 붙일 수 있습니다.<br>
						한 번 만들어 놓은 스티커는 내 체크리스트에서 확인 가능하며, 다른 북마크한 체크리스트에 사용 할 수 있습니다.<br> 
						또한, 내 체크리스트에서 특정 스티커가 사용 된 체크리스트들을 확인할 수 있습니다.
						
						<!-- 수정하기/삭제하기 버튼은 관리자에게만 나타남 -->
						<br><br>
						<button type="submit" class="btn btn-primary">수정하기</button>
						<button type="submit" class="btn btn-primary">삭제하기</button>
					</div>
				</div>
			</div>
			  --%>

				<c:forEach var="list" items="${list }">
					<div class="card">
						<div class="card-header" id="heading${list.faq_no }">
							<h2 class="mb-0">
								<button class="btn btn-link btn-block text-left collapsed"
									type="button" data-toggle="collapse"
									data-target="#collapse${list.faq_no }" aria-expanded="false"
									aria-controls="collapse${list.faq_no }">
									<b>[${list.faqsort_name }]</b>&nbsp;&nbsp;${list.faq_title }
								</button>
							</h2>
						</div>
						<div id="collapse${list.faq_no }" class="collapse"
							aria-labelledby="heading${list.faq_no }"
							data-parent="#accordionExample">
							<%-- <div class="card-body">${list.faq_content } --%>
							<div class="card-body">${fn:replace(list.faq_content, replaceChar, "<br/>") }
								<br>
								<br>
								<%-- <button type="submit" class="btn btn-primary" id="updateBtn" value="${list.faq_no }">수정하기</button> --%>
								<div style="float: right;">
									<c:if test="${!empty adminLogin}">
										<button type="submit" class="btn btn-info" id="updateBtn"
											value="${list.faq_no }">수정하기</button>
										<button type="submit" class="btn btn-danger"
											value="${list.faq_no }">삭제하기</button>
									</c:if>
								</div>
								<br>
								<br>
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
			<br>
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