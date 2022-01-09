<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.88.1">
<meta charset="UTF-8">
<title>WithdrawalForm.jsp</title>
<link href="<%=cp%>/css/bootstrap.min.css" rel="stylesheet">
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

#errMsg {
	color: red;
	font-weight: bold;
}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">

	$(document).ready(function()
	{ 
		// 신고 페이지 
		if (!$("input:radio[name=why_No]").is(":checked"))
		{
			$("#why").attr("disabled", true);
		}
		
		$("input:radio[name=why_No]").click(function()
		{
			
			if ($("input:radio[name=why_No]:checked").val() == "5")
			{
				$("#why").attr("disabled", false);
			}
			else
			{
				$("#why").attr("disabled", true);
			}
		});
		
		
		// 신고하기 버튼클릭
		$("#submitBtn").click(function()
		{
			if (!$("input:radio[name=why_No]").is(":checked"))
			{
                $("#errMsg").html("신고 사유를 선택해주세요.");
                return;				
			}
			
			if ($("#repNo5").is(":checked") && $("#why").val() == "")
			{
			 	$("#errMsg").html("신고 상세 사유를 작성해주세요.");
			 	return;
			 	
			}
			
			$("#reportForm").submit();
			
		});
		
		
		$("#returnBtn").click(function()
		{
            // 해당 체크리스트 조회로 돌아가기
            $(location).attr("href", "readcheck.action?checkNo=" + $("#check_No").val());
		});
		
		
	});
	

</script>
</head>
<body>

	<!-- 메뉴바 영역 -->
	<div>
		<c:import url="/nav.action"></c:import>
	</div>
	<div class="container" style="margin-top: 80px; margin-bottom: 80px;">
        <h2 class="sub_tit_txt" style="text-align: center;">신고 및 블라인드</h2>
        <hr>
		
		<div style="margin-top: 30px;">
            <form action="report.action" id="reportForm" method="post">
			<span style="color: red;">※ 허위 신고 시 신고자가 제재를 받을 수 있습니다.</span><br><br>
				
				<h3 class="h3 mb-3 font-weight-normal">신고 사유 리스트</h3>
				<div class="card">
				  <div class="card-body">
				    <div class="form-check">
					  <input class="form-check-input" type="radio" name="why_No" id="repNo1" value="1">
					  <label class="form-check-label" for="wirNo1">영리목적/홍보</label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="radio" name="why_No" id="repNo2" value="2">
					  <label class="form-check-label" for="wirNo2">도배</label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="radio" name="why_No" id="repNo3" value="3">
					  <label class="form-check-label" for="wirNo3">음란성/선정성</label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="radio" name="why_No" id="repNo4" value="4">
					  <label class="form-check-label" for="wirNo4">욕설/인신공격</label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="radio" name="why_No" id="repNo5" value="5">
					  <label class="form-check-label" for="wirNo5">기타</label>
					</div>
					
					<div style="margin: 30px 0;">
						<h5 class="h5 font-weight-normal">신고 상세 사유</h5>
						<textarea class="form-control" id="why" name="why" rows="8" placeholder="신고 상세 사유를 상세히 작성해주세요."></textarea>
					</div>

				  </div>
				</div>
				
				<div class="text-center" style="margin-top: 30px;">
					<span id="errMsg"></span><br>
					<button type="button" class="btn btn-primary btn-lg" id="returnBtn">돌아가기</button>
					<button type="button" class="btn btn-secondary btn-lg" id="submitBtn">신고하기</button>
				</div>
				<input type="hidden" id="check_No" name="check_No" value="${checkNo }">
				<input type="hidden" id="ac_No" name="ac_No" value="${acNo }">
				
			</form>
		</div>
		
	</div>

</body>
</html>