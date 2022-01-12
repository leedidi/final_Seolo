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

<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">

<script type="text/javascript">
	$(document).ready(function()
	{
		//○ 스티커 선택 유효성 검사(1개 이상 선택)
		$("#listSubmitBtn").click(function()
		{
			$("#stickerErr").css("display", "none");

			// 스티커가 선택되지 않았을 경우 → 경고 메시지 출력
			if($("input:checkbox[name=sticker]:checked").length == 0)
			{
				$("#stickerErr").show();
				return;
			}

			$("#listFilter").submit();
		});

		
		//○ '구 이름' 이 선택되면 '동 이름'을 불러오는 ajax 처리
		$("#guSelect").on('change', function()
		{
			// data 구성
			var params = "guNo=" + $("#guSelect").val();
			var fromArri = $("#dongSelect");

			// jQuery 의 ajax() 함수 사용(호출)
			$.ajax(
			{
				type: "GET",
				url: "dongnameajax.action",
				data: params,
				dataType: "json",
				success: function(data)
				{
					$(fromArri).children().remove(); // selectBox 내의 데이터 삭제
					$(fromArri).append("<option value='0' selected class='text-center'>-- 전체 보기 --</option>");

					for(var i = 0; i < data.length; i++)
					{
						var dongNo = data[i].dongNo;
						var dongName = data[i].dongName;
						$(fromArri).append("<option value='" + dongNo + "'>" + dongName + "</option>");
					}

				},
				error: function(e)
				{
					alert(e.responseText);
				}
			});
			
			/*
			//○ 스티커 전체선택 버튼 클릭
			$("#allSticker").click(function()
			{
				if($("#allSticker").is(":checked"))
				{
					alert("실행");
					$("input[name=sticker]").prop("checked", true);
					$("#allStickerLabel").removeClass("btn-outline-primary").addClass("btn-primary");
					//$("stickerLabel").removeClass("btn-outline-primary").addClass("btn-primary");
				}
				else
					$("input[name=sticker]").prop("checked", false);
			});
			*/
			
		});

		//○ 스티커 컨트롤 관련
		/*
		$("input[name=sticker]").click(function()
		{
			var total = $("input[name=sticker]").length;
			var checked = $("input[name=sticker]:checked").length;

			if(total != checked)
				$("#allSticker").prop("checked", false);
			else
				$("#allSticker").prop("checked", true);
		});
		*/

	});

</script>


<title>MyChecklists.jsp</title>
</head>

<body>
	<!-- 나의 체크리스트 목록 -->

	<!-- 메뉴바 영역 -->
	<div>
		<c:import url="MenuNavbar_new.jsp"></c:import>
	</div>

	<!-- 본문 영역 -->
	<form action="mychecklist.action" method="post" id="listFilter">
		<div class="container">
			<div class="py-5 text-center">
				<h1 style="margin-top: 70px;">나의 체크리스트</h1>
				<p class="lead">조회할 항목을 선택해주세요.</p>
				<br> <a href="checkfirstinsertform.action" class="btn btn-primary my-2">새 체크리스트 작성</a>
				<button type="button" id="listSubmitBtn" class="btn btn-primary my-2">체크리스트 조회</button>
				<p class="lead" style="font-size: small; color: red; display: none;" id="stickerErr">조회할 스티커를 하나 이상 선택해주세요!</p>
			</div>

			<!-- 본문 상단영역(필터) 시작 -->
			<div class="card" style="margin-bottom: 20px;">
				<div class="card-header">알아볼 내용</div>
				<div class="card-body">
					<!-- <form action=""> -->
					<div class="form-row">
						<div class="form-group col-md-3">
							<label for="inputState">분류</label>
							<select id="typeSelect" class="form-control" name="type">
								<option value="%" selected class="text-center">-- 전체 보기 --</option>
								<option value="나의 체크리스트">나의 체크리스트</option>
								<option value="북마크 체크리스트">북마크 체크리스트</option>
								<option value="북마크 지역정보">북마크 지역정보</option>
							</select>
						</div>
						<div class="form-group col-md-3">
							<label for="inputState">지역 구</label>
							<select id="guSelect" class="form-control" name="guNo">
								<option value="0" selected class="text-center">-- 전체 보기 --</option>
								<c:forEach var="gu" items="${guList }">
									<option value="${gu.guNo }">${gu.guName }</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group col-md-3">
							<label for="inputState">지역 동</label>
							<select id="dongSelect" class="form-control" name="dongNo">
								<option value="0" selected class="text-center">-- 전체 보기 --</option>
							</select>
						</div>
					</div>
				</div>
			</div>

			<div class="card" style="margin-bottom: 20px;">
				<div class="card-header">비교할 내용</div>
				<div class="card-body">
					<div class="form-row">
						<div class="form-group col-md-1.5">
							<div class="custom-control custom-switch">
								<input type="checkbox" class="custom-control-input" id="wolse" name="bigyo" checked="checked" value="wolse">
								<label class="custom-control-label" for="wolse">월세</label>
							</div>
						</div>
						<div class="form-group col-md-1.5">
							<div class="custom-control custom-switch">
								<input type="checkbox" class="custom-control-input" id="jeonse" name="bigyo" checked="checked" value="jeonse">
								<label class="custom-control-label" for="jeonse">전세</label>
							</div>
						</div>
						<div class="form-group col-md-1.5">
							<div class="custom-control custom-switch">
								<input type="checkbox" class="custom-control-input" id="maemae" name="bigyo" checked="checked" value="maemae">
								<label class="custom-control-label" for="maemae">매매</label>
							</div>
						</div>
						<div class="form-group col-md-1.5">
							<div class="custom-control custom-switch">
								<input type="checkbox" class="custom-control-input" id="security" name="bigyo" checked="checked" value="security">
								<label class="custom-control-label" for="security">치안</label>
							</div>
						</div>
						<div class="form-group col-md-1.5">
							<div class="custom-control custom-switch">
								<input type="checkbox" class="custom-control-input" id="transportation" name="bigyo" checked="checked" value="transportation">
								<label class="custom-control-label" for="transportation">대중교통</label>
							</div>
						</div>
						<div class="form-group col-md-1.5">
							<div class="custom-control custom-switch">
								<input type="checkbox" class="custom-control-input" id="honjap" name="bigyo" checked="checked" value="honjap">
								<label class="custom-control-label" for="honjap">교통혼잡도</label>
							</div>
						</div>
						<div class="form-group col-md-1.5">
							<div class="custom-control custom-switch">
								<input type="checkbox" class="custom-control-input" id="conv" name="bigyo" checked="checked" value="conv">
								<label class="custom-control-label" for="conv">생활편의시설</label>
							</div>
						</div>
						<div class="form-group col-md-1.5">
							<div class="custom-control custom-switch">
								<input type="checkbox" class="custom-control-input" id="pet" name="bigyo" checked="checked" value="pet">
								<label class="custom-control-label" for="pet">반려동물</label>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="card" style="margin-bottom: 20px;">
				<div class="card-header">나의 스티커</div>
				<div class="card-body">
					<!-- 스티커가 존재하지 않을 때 -->
					<c:if test="${stickerList.size() == 0 }">
						<br>
						<p class="text-center">나의 스티커가 존재하지 않습니다.</p>
					</c:if>
					<!-- 스티커가 존재할 때 -->
					<c:if test="${stickerList.size() != 0 }">
						<div class="btn-group-toggle" data-toggle="buttons">
							<%--
							<label class="btn btn-outline-primary" id="allStickerLabel"> <input type="checkbox" id="allSticker"> 모두 선택
							</label>
							--%>
							<c:forEach var="sticker" items="${stickerList }">
								<label class="btn btn-outline-primary"> <input type="checkbox" value="${sticker.stickerNo }" name="sticker"> ${sticker.content }
								</label>
							</c:forEach>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</form>

	<!-- footer 영역 -->
	<div></div>

</body>
</html>