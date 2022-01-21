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
		$("#exit").click(function()
		{
			window.opener.location.reload();
			window.close();
		});
		
		$(".stickerDelete").click(function()
		{
			var result = confirm("해당 스티커를 삭제하시겠습니까?");
			if (result)
			{
				$(location).attr("href", "stickerdelete.action?tagNo=" + $(this).val() 
						+ "&type=" + $("#type").val() + "&checkNo=" + $("#checkNo").val());
			}
			
		});
	});

</script>
</head>
<body>
	<div class="card mb-4 shadow-sm" style="margin: 50px 20px 0 20px;">
		<input type="hidden" id="checkNo" name="checkNo" value="${checkNo }">
		<input type="hidden" id="type" name="type" value="${type }">
		<div class="card-header">
			<h4 class="my-0 font-weight-normal">스티커 목록</h4>
		</div>
        <div class="card-body">
        	<p class="card-text">
				<c:choose>
	        		<c:when test="${!empty checkStickerList }">
	        			<table class="table">
	        				<thead>
	        					<tr>
	        						<th>스티커명</th>
	        						<th>삭제</th>
	        					</tr>
	        				</thead>
	        			<c:forEach var="sticker" items="${checkStickerList }">
	        				<tbody>
	        					<tr>
	        						<td>#${sticker.content }</td>
	        						<td><button type="button" class="btn btn-secondary stickerDelete" value="${sticker.tagNo }">삭제</button></td>
	        					</tr>
	        				</tbody>
			        	</c:forEach>
			        	</table>
	        		</c:when>
	        		<c:otherwise>
	        			붙어있는 스티커가 없습니다.
	        		</c:otherwise>
	        	</c:choose>
        	</p>
     	</div>
	</div>
	<div class="text-center" style="margin: 20px 0;">
		<button class="btn btn-secondary mx-auto" id="exit">닫기</button>
	</div>
</body>
</html>