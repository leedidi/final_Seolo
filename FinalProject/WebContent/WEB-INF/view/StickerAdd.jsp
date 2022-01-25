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
<title>StickerModify.jsp</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" 
	integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function()
	{
		$(".addStk").click(function()
		{
			$(location).attr("href", "stickeradd.action?stickerNo=" + $(this).val() 
					+ "&type=" + $("#type").val() + "&checkNo=" + $("#checkNo").val());
		});
		
		$(".addLocalStk").click(function()
		{
			$(location).attr("href", "stickeradd.action?stickerNo=" + $(this).val() 
					+ "&type=" + $("#type").val() + "&dongNo=" + $("#dongNo").val());
		});
		
		$("#exit").click(function()
		{
			window.opener.location.reload();
			window.close();
		});
	});

</script>
</head>
<body>
	<form action="addnewsticker.action" method="post">
		<div class="card mb-4 shadow-sm" style="margin: 50px 20px 0 20px;">
			<input type="hidden" id="checkNo" name="checkNo" value="${checkNo }">
			<input type="hidden" id="dongNo" name="dongNo" value="${dongNo }">
			<input type="hidden" id="type" name="type" value="${type }">
			<div class="card-header">
				<h4 class="my-0 font-weight-normal">붙어있는 스티커 목록</h4>
			</div>
	        <div class="card-body">
	        	<p class="card-text">
					<c:choose>
		        		<c:when test="${!empty checkStickerList }">
		        			<c:forEach var="sticker" items="${checkStickerList }">
				        		<button type="button" class="btn btn-link">#${sticker }</button>
				        	</c:forEach>
		        		</c:when>
		        		<c:when test="${!empty localStickerList }">
		        			<c:forEach var="sticker" items="${localStickerList }">
		        				<button type="button" class="btn btn-link">#${sticker }</button>
		        			</c:forEach>
		        		</c:when>
		        		<c:otherwise>
		        			붙어있는 스티커가 없습니다.
		        		</c:otherwise>
		        	</c:choose>
	        	</p>
	     	</div>
		</div>
		<div class="card mb-4 shadow-sm" style="margin: 50px 20px 0 20px;">
			<div class="card-header">
				<h4 class="my-0 font-weight-normal">기존 스티커 목록에서 추가하기</h4>
			</div>
	        <div class="card-body">
	        	<p class="card-text">
					<c:choose>
						<c:when test="${(type eq 1 || type eq 2) && !empty stickerList }">
		        			<c:forEach var="sticker" items="${stickerList }">
				        		<button type="button" class="btn btn-link addStk" value="${sticker.stickerNo }">#${sticker.content }</button>
				        	</c:forEach>
		        		</c:when>
		        		<c:when test="${(type eq 3) && !empty stickerList }">
		        			<c:forEach var="sticker" items="${stickerList }">
				        		<button type="button" class="btn btn-link addLocalStk" value="${sticker.stickerNo }">#${sticker.content }</button>
				        	</c:forEach>
		        		</c:when>
		        		<c:otherwise>
		        			기존에 작성된 스티커가 존재하지 않습니다.
		        		</c:otherwise>
					</c:choose>
	        	</p>
	     	</div>
		</div>
		<div class="card mb-4 shadow-sm" style="margin: 50px 20px 0 20px;">
			<div class="card-header">
				<h4 class="my-0 font-weight-normal">새 스티커 추가</h4>
			</div>
	        <div class="card-body">
	        	<p class="card-text">
					<input type="text" class="form-control" id="newSticker" name="newSticker"> <button type="submit" class="btn btn-primary">추가</button>
	        	</p>
	     	</div>
		</div>
	</form>
	
	<div class="text-center" style="margin: 20px 0;">
		<button class="btn btn-secondary mx-auto" id="exit">닫기</button>
	</div>
</body>
</html>