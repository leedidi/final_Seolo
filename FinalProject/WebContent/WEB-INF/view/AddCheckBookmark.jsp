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
<title>AddCheckBookmark.jsp</title>

<!-- 통일하기로 한 부트스트랩 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" 
integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" 
integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>

<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">

	$(function()
	{
		// alert("확인");
		
		// 북마크 완료 버튼 클릭시 제목 입력 확인
		$("#done").click(function check()	
		{
		
			alert("북마크가 완료되었습니다. 나의 체크리스트에서 확인해보세요.");
			location.href="mychecklistform.action";
			
		});
		
		// 기존의 스티커를 보낼 때
		$(".sticker1").click(function()
		{
			$(location).attr("href", "addCheckSticker.action?stickerNo=" + $(this).val() 
							+ "&checkNo=${checkNo}&title=" + $("#title").val());
		});
		
		
		// 새로운 스티커를 보낼 때
		$("#sticker2").click(function()
		{
			$(location).attr("href","addNewCheckSticker.action?checkNo=${checkNo}&title=" + $("#title").val()
							+ "&newSticker=" + $("#newSticker").val());
			
		});
		
	});
	
</script>
</head>
<body>
<%-- 
<div>
	<c:import url="MenuNavbar_new.jsp"></c:import>
</div> --%>
<br><br><br><br><br>

<main role="main" class="container">
  
	<div class="card mb-4 shadow-sm">
		<div class="card-header">
			<h2 class="my-0 font-weight-bold" style="text-align: center;">이 체크리스트를 북마크하시겠습니까?</h2><br>
			<p class="lead" style="text-align: center;">'나의 체크리스트'에서 같은 스티커를 붙여둔 리스트를 모아서 확인할 수 있습니다.</p>
		</div>
      
       
        <div class="card-body">
        	
        	<h3 class="card-title">북마크 제목</h3>
        	<div class="input-group mb-3">
				  <input type="text" class="form-control" name="title" id="title"
				   placeholder="15자 이내로 가장 먼저 작성해주세요." maxlength="15">
			</div><br>
        
        	<h3 class="card-title">기존의 스티커에서 선택하기</h3>
        	<p class="card-text" >
			<c:forEach var="li" items="${list }">
				<%-- 
				<a id="sticker${li.stickerNo }" role="button" class="btn btn-link"
				href="addCheckSticker.action?stickerNo=${li.stickerNo }&checkNo=${checkNo }">#${li.content }</a> 
				--%>
				<button id="sticker${li.stickerNo }" value="${li.stickerNo }" class="btn btn-link sticker1">#${li.content }</button>
			</c:forEach>        		
        	</p>
        	
        	
        	<h3 class="card-title">스티커 새로 작성하기</h3>
		        <!-- <form id="addNewCheckSticker" action="addNewCheckSticker.action" method="get"> --> 
		        
			    	<!-- 북마크시 체크리스트 고유번호 넘겨줘야 함 -->
					<%-- <input type="hidden" name="checkNo" id="checkNo" value="${checkNo }"> --%>
					
		        	<div class="input-group mb-3">
						  <input type="text" class="form-control" name="newSticker" id="newSticker"
						   placeholder="15자 이내로 작성해주세요." maxlength="15">
						  <div class="input-group-append">
						    <button class="btn btn-outline-secondary" type="button" id="sticker2">추가하기</button>
						    <!-- <button class="btn btn-outline-secondary" type="button" onclick="sendIt2()">추가하기</button> -->
						  </div>
					</div>
				<!-- </form> -->
        	<br>
        	
        	<h3 class="card-title">추가된 스티커 목록</h3>
        	<div class="input-group mb-3">
			  <div class="input-group-append">
				  <c:forEach var="st" items="${sticker }">
					<a id="stkcer${st.stickerNo }" href="removesticker.action?stickerNo=${st.stickerNo }"
					role="button" class="btn btn-link">#${st.content }</a>
				  </c:forEach> 
			 </div>
			</div>
        	<br>
        
			<div class="text-center mb-4">
		    	<button class="btn btn-lg btn-primary" id="done" 
		    	onclick="return check()">북마크 완료</button>
	    	</div>
	    	
	    	
     	</div>
	</div>
</main>

</body>
</html>