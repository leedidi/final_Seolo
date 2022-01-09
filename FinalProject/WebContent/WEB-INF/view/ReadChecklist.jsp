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
<title>ReadChecklist.jsp</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
	integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
	crossorigin="anonymous">
	
<style type="text/css">
.text-essential 
{
	color: #FFA7A7;
}
</style>

<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript" 
src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8debbf5b35bae9b060adac364d027afd&libraries=services"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>



<script>
	
	$(document).ready(function()
	{
		$("#repBtn").click(function()
		{
			$("#readCheck").attr("method", "POST");
			$("#readCheck").attr("action", "reportform.action?checkNo=" + $(this).val() + "&acNo=" + $("#acNo").val());
			
		});

		

		$("#bookBtn").click(function()
		{
			$("#readCheck").attr("method", "GET");
			$("#readCheck").attr("action", "main.action");
			
		});
		
			
	});
		

	// 지도 출력
	window.onload = function()
    {
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
            mapOption = {
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };  
    
        // 지도를 생성합니다    
        var map = new kakao.maps.Map(mapContainer, mapOption); 
    
        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();
       
        var address = $("#roadAddr").val();
        
        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(address, function(result, status) {
    
            // 정상적으로 검색이 완료됐으면 
             if (status === kakao.maps.services.Status.OK) {
    
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                var xAddr = result[0].y;
                var yAddr = result[0].x;
    
                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });
             
                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
                
                $("#gangnam").html(Math.round(distance(xAddr, yAddr, 37.49799683930331, 127.0276305970426)) + "m");
                $("#uljiro").html(Math.round(distance(xAddr, yAddr, 37.56618007714531, 126.9826675362824)) + "m");
                $("#yeouido").html(Math.round(distance(xAddr, yAddr, 37.5216020501244, 126.92432977120859)) + "m");
                $("#hongdae").html(Math.round(distance(xAddr, yAddr, 37.55810688620485, 126.92596020132932)) + "m");
                $("#gadi").html(Math.round(distance(xAddr, yAddr, 37.48153537206139, 126.88264549660947)) + "m");
                $("#jamsil").html(Math.round(distance(xAddr, yAddr, 37.5146855894903, 127.10433616425807)) + "m");
            } 
        });
        
    }
	
	function distance(x, y, subX, subY)
	{
    	var polyline=new daum.maps.Polyline({
    		/* map:map, */
    		path : [
    		new daum.maps.LatLng(x,y),
    		new daum.maps.LatLng(subX, subY)
    		],
    	 strokeWeight: 2,
    	 strokeColor: '#FF00FF',
    	 strokeOpacity: 0.8,
    	 strokeStyle: 'dashed'
    	});
    	
    	return polyline.getLength();
	}
	
	
	
	
	
	
</script>

</head>
<body>
	<!-- 체크리스트 작성 뷰 페이지 -->
	
	<!-- 메뉴바 영역 -->
	<div>
		<c:import url="MenuNavbar_new.jsp"></c:import>
	</div>

	<!-- 본문 영역 -->
	<div class="container">
		<div class="py-5 text-center">
			<h1 style="margin-top: 50px;">체크리스트 조회</h1>
			<br>
			
		</div>

		<!-- 폼 시작 -->
		<form class="needs-validation" novalidate=""  action="" method="post" id="readCheck">
			<div class="row">
				<div class="col-md-8">
					<h4 class="mb-3">
						작성일 : ${checklist.checkDate }
					</h4>
				</div>
				<c:if test="${user eq 'writer' }">
				<div class="col-md-4 text-center">
					<button class="btn btn-secondary">수정하기</button>
					<button class="btn btn-secondary">삭제하기</button>
				</div>
				</c:if>
			</div>
			
			
			<h4 class="mb-3">
				거주여부 <span class="text-essential">(*)</span>
			</h4>
			<div class="d-block my-3">
				<c:choose>
					<c:when test="${checklist.resNo eq 1}">
						<div class="custom-control custom-radio">
							<input id="resNo1" name="resNo" type="radio" value=1
								class="custom-control-input" checked="checked"> 
								<label class="custom-control-label" for="resNo1">거주</label>
						</div>
					</c:when>
					<c:when test="${checklist.resNo eq 2}">
						<div class="custom-control custom-radio">
							<input id="resNo2" name="resNo" type="radio" value=2
								class="custom-control-input" checked="checked"> <label
								class="custom-control-label" for="resNo2">거주예정</label>
						</div>
					</c:when>
					<c:otherwise>
						<div class="custom-control custom-radio">
							<input id="resNo3" name="resNo" type="radio" value=3
								class="custom-control-input" checked="checked"> <label
								class="custom-control-label" for="resNo3">비거주</label>
						</div>
					</c:otherwise>
				</c:choose>

			</div>

			<hr class="mb-4">
			<c:if test="${user eq 'writer' }">
				<div class="mb-3">
					<label for="title"><h4 class="mb-3">제목<span class="text-essential">(*)</span></h4></label>
					<input type="text" class="form-control" id="title" name="title"
					 maxlength="25" required="required" readonly="readonly" value="${checklist.title }">
				</div>
			<hr class="mb-4">
			</c:if>
			<c:if test="${user eq 'bookmarker' }">
				<div class="mb-3">
					<label for="title"><h4 class="mb-3">북마크 제목<span class="text-essential">(*)</span></h4></label>
					<input type="text" class="form-control" id="title" name="title"
					 maxlength="25" required="required" readonly="readonly" value="${bookMark.title }">
				</div>
			<hr class="mb-4">
			</c:if>

			<div class="row">
				<!-- 본문 우측 영역 -->
				<div class="col-md-4 order-md-2 mb-4">
					
					<h4 class="mb-3">지역분류</h4>
					<div class="row mb-3" style="justify-content: center;">
						<div class="col-md-5">
							<input type="text" class="form-control" id="gu" name="gu" readonly="readonly" value="${checklist.guName }">
						</div>
						<div class="col-md-7">
							<input type="text" class="form-control" id="dong" name="dong" readonly="readonly" value="${checklist.dongName }">
						</div>
					</div>
					
					<hr class="mb-4">
					
					<c:if test="${user eq 'writer' }">
					<h4 class="d-flex justify-content-between align-items-center mb-3">
						<span class="mb-3">주소 위치확인</span>
					</h4>
					
					
					<!-- 지도 출력 -->
					
					<div id="map" style="width:100%;height:500px;"></div>
					
					
					<hr class="mb-4">
					<h4 class="d-flex justify-content-between align-items-center mb-3">
						<span class="mb-3">주요 역까지 거리</span>
					</h4>
					<ul class="list-group mb-3">
						<li class="list-group-item d-flex justify-content-between lh-condensed">
							<div>
								<h6 class="my-0">2호선 강남역 까지</h6>
							</div> <span class="badge badge-secondary" id="gangnam"></span>
						</li>
						<li class="list-group-item d-flex justify-content-between lh-condensed">
							<div>
								<h6 class="my-0">2호선 을지로입구역 까지</h6>
							</div> <span class="badge badge-secondary" id="uljiro"></span>
						</li>
						<li class="list-group-item d-flex justify-content-between lh-condensed">
							<div>
								<h6 class="my-0">9호선 여의도역 까지</h6>
							</div> <span class="badge badge-secondary" id="yeouido"></span>
						</li>
						<li class="list-group-item d-flex justify-content-between lh-condensed">
							<div>
								<h6 class="my-0">공항철도 홍대입구역 까지</h6>
							</div> <span class="badge badge-secondary" id="hongdae"></span>
						</li>
						<li class="list-group-item d-flex justify-content-between lh-condensed">
							<div>
								<h6 class="my-0">1호선 가산디지털단지역 까지</h6>
							</div> <span class="badge badge-secondary" id="gadi"></span>
						</li>
						<li class="list-group-item d-flex justify-content-between lh-condensed">
							<div>
								<h6 class="my-0">8호선 잠실역 까지</h6>
							</div> <span class="badge badge-secondary" id="jamsil"></span>
						</li>
					</ul>
					<hr class="mb-4">
					</c:if>
					
					<!-- 북마크 추가 -->
					<c:if test="${user eq 'viewer' }">
					<button class="btn btn-primary" id="bookBtn" style="width: 100%;">북마크 추가하기</button>
					<br><br>
					<button class="btn btn-danger" id="repBtn" style="width: 100%;"
					value="${checklist.checkNo}">신고하기</button>							
					</c:if>
					
					<!-- 북마크 삭제 -->
					<c:if test="${user eq 'bookmarker' }">
					<button class="btn btn-secondary" style="width: 100%;">북마크 삭제하기</button>
					<br><br>
					<button class="btn btn-danger" id="repBtn" style="width: 100%;"
					value="${checklist.checkNo}">신고하기</button>							
					</c:if>
			

				</div><!-- 본문 우측 영역 끝 -->




				<!-- 본문 좌측 영역 -->
				<div class="col-md-8 order-md-1">
					<c:if test="${user eq 'writer' }">
					<div class="mb-3">
						 
						<h4 class="mb-3">주소<span class="text-essential">(*)</span></h4>

						<input type="text" id="roadAddr" name="roadAddr" placeholder="주소" class="form-control" readonly="readonly"
						value="${checklist.roadAddr }"><br>
						
						<!-- 동 코드 확인 후 ajax 처리되는 버튼 -->
						
						<input type="hidden" id="bcode" name="bcode" placeholder="동고유번호" value="${checklist.dongNo }">						
					<hr class="mb-4">
					</div>
					</c:if>

					
					<h4 class="mb-3">주거비</h4>
					<h6 class="mb-3">(단위: 만원)</h6>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="deposit" style="font-weight: bold;">월세 보증금</label> 
							<input type="text" class="form-control" id="deposit" name="deposit" value="${checklist.deposit }" readonly="readonly">
						</div>
						<div class="col-md-6 mb-3">
							<label for="mWolse" style="font-weight: bold;">월세</label> 
							<input type="text" class="form-control" id="mWolse" name="mWolse" value="${checklist.mWolse }" readonly="readonly">
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="mJeonse" style="font-weight: bold;">전세 보증금</label> 
							<input type="text" class="form-control" id="mJeonse" name="mJeonse" value="${checklist.mJeonse }" readonly="readonly">
						</div>
					</div>

					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="mMaemae" style="font-weight: bold;">매매가</label> 
							<input type="text" class="form-control" id="mMaemae" name="mMaemae" value="${checklist.mMaemae }" readonly="readonly">
						</div>
					</div>

					<hr class="mb-4">
					<h4 class="mb-3">치안</h4>
					<div class="row">
						<div class="col-md-3 mb-3">
							<label for="sec_score" style="font-weight: bold;">점수</label>
							<c:choose>
								<c:when test="${checklist.security_scoreNo ne 0 }">
									<c:forEach var="score" items="${scores }">
										<c:if test="${checklist.security_scoreNo eq score.scoreNo }">
											<input type="text" class="form-control w-100" id="security_scoreNo" name="security_scoreNo" readonly="readonly" value="${score.scoreName }">
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control w-100" id="security_scoreNo" name="security_scoreNo" readonly="readonly" value="">
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-9 mb-3">
							<textarea class="form-control" name="security_co" id="security_co"
							 rows="3" readonly="readonly" maxlength="150">${checklist.security_co }</textarea>
						</div>
					</div>

					<hr class="mb-4">
					<h4 class="mb-3">대중교통</h4>
					<div class="row">
						<div class="col-md-3 mb-3">
							<label for="trans_score" style="font-weight: bold;">점수</label>
							<c:choose>
								<c:when test="${checklist.transport_scoreNo ne 0 }">
									<c:forEach var="score" items="${scores }">
										<c:if test="${checklist.transport_scoreNo eq score.scoreNo }">
											<input type="text" class="form-control w-100" id="transport_scoreNo" name="transport_scoreNo" readonly="readonly" value="${score.scoreName }">
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control w-100" id="transport_scoreNo" name="transport_scoreNo" readonly="readonly" value="">
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-9 mb-3">
							<textarea class="form-control" name="transport_co" id="transport_co"
								rows="3" readonly="readonly" maxlength="150">${checklist.transport_co }</textarea>
						</div>
					</div>

					<h4 class="mb-3">교통혼잡도</h4>
					<div class="row">
						<div class="col-md-3 mb-3">
							<label for="honjap_score" style="font-weight: bold;">점수</label>
							<c:choose>
								<c:when test="${checklist.honjap_scoreNo ne 0 }">
									<c:forEach var="score" items="${scores }">
										<c:if test="${checklist.honjap_scoreNo eq score.scoreNo }">
											<input type="text" class="form-control w-100" id="honjap_scoreNo" name="honjap_scoreNo" readonly="readonly" value="${score.scoreName }">
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control w-100" id="honjap_scoreNo" name="honjap_scoreNo" readonly="readonly" value="">
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-9 mb-3">
							<textarea class="form-control" name="honjap_co" id="honjap_co"
								rows="3" readonly="readonly" maxlength="150">${checklist.honjap_co }</textarea>
						</div>
					</div>

					<hr class="mb-4">
					<h4 class="mb-3">생활편의시설</h4>
					<div class="row mb-3 input-group" style="align-items: center; display: flex; justify-content: center;">
					
					
						<ul class="col list-group col-md-8 themed-grid-col">
							<li class="list-group-item" style="height: 43pt; font-weight: bold;">대형마트/소형마트/시장/편의점</li>
							<li class="list-group-item" style="height: 43pt; font-weight: bold;">세탁소/빨래방/수선집</li>
							<li class="list-group-item" style="height: 43pt; font-weight: bold;">동네병원/대형병원/약국</li>
							<li class="list-group-item" style="height: 43pt; font-weight: bold;">음식점/카페/배달</li>
							<li class="list-group-item" style="height: 43pt; font-weight: bold;">문화시설(영화관, 헬스장 등)</li>
							<li class="list-group-item" style="height: 43pt; font-weight: bold;">공원/산책로/자연환경</li>
						</ul>
						<ul class="col list-group col-md-2 themed-grid-col">
							<li class="list-group-item">
							<c:choose>
								<c:when test="${checklist.mart eq '0'}">
									<input type="radio" name="mart" id="mart1" value="0" checked onclick="return(false);"><label for="mart1">유</label> 
									<input type="radio" name="mart" id="mart2" value="1" onclick="return(false);"><label for="mart2">무</label>
								</c:when>
								<c:when test="${checklist.mart eq '1'}">
									<input type="radio" name="mart" id="mart1" value="0" onclick="return(false);"><label for="mart1">유</label> 
									<input type="radio" name="mart" id="mart2" value="1" checked onclick="return(false);"><label for="mart2">무</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="mart" id="mart1" value="0" onclick="return(false);"><label for="mart1">유</label> 
									<input type="radio" name="mart" id="mart2" value="1" onclick="return(false);"><label for="mart2">무</label>
								</c:otherwise>
							</c:choose>
							</li>
							<li class="list-group-item">
							<c:choose>
								<c:when test="${checklist.laundry eq '0'}">
									<input type="radio" name="laundry" id="laundry1" value="0" checked onclick="return(false);"><label for="laundry1">유</label> 
									<input type="radio" name="laundry" id="laundry2" value="1" onclick="return(false);"><label for="laundry2">무</label>
								</c:when>
								<c:when test="${checklist.laundry eq '1'}">
									<input type="radio" name="laundry" id="laundry1" value="0" onclick="return(false);"><label for="laundry1">유</label> 
									<input type="radio" name="laundry" id="laundry2" value="1" checked onclick="return(false);"><label for="laundry2">무</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="laundry" id="laundry1" value="0" onclick="return(false);"><label for="laundry1">유</label> 
									<input type="radio" name="laundry" id="laundry2" value="1" onclick="return(false);"><label for="laundry2">무</label>
								</c:otherwise>
							</c:choose>
							</li>
							<li class="list-group-item">
							<c:choose>
								<c:when test="${checklist.hospital eq '0'}">
									<input type="radio" name="hospital" id="hospital1" value="0" checked onclick="return(false);"><label for="hospital1">유</label> 
									<input type="radio" name="hospital" id="hospital2" value="1" onclick="return(false);"><label for="hospital2">무</label>
								</c:when>
								<c:when test="${checklist.hospital eq '1'}">
									<input type="radio" name="hospital" id="hospital1" value="0" onclick="return(false);"><label for="hospital1">유</label> 
									<input type="radio" name="hospital" id="hospital2" value="1" checked onclick="return(false);"><label for="hospital2">무</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="hospital" id="hospital1" value="0" onclick="return(false);"><label for="hospital1">유</label> 
									<input type="radio" name="hospital" id="hospital2" value="1" onclick="return(false);"><label for="hospital2">무</label>
								</c:otherwise>
							</c:choose>
							</li>
							<li class="list-group-item">
							<c:choose>
								<c:when test="${checklist.food eq '0'}">
									<input type="radio" name="food" id="food1" value="0" checked onclick="return(false);"><label for="food1">유</label> 
									<input type="radio" name="food" id="food2" value="1" onclick="return(false);"><label for="food2">무</label>
								</c:when>
								<c:when test="${checklist.food eq '1'}">
									<input type="radio" name="food" id="food1" value="0" onclick="return(false);"><label for="food1">유</label> 
									<input type="radio" name="food" id="food2" value="1" checked onclick="return(false);"><label for="food2">무</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="food" id="food1" value="0" onclick="return(false);"><label for="food1">유</label> 
									<input type="radio" name="food" id="food2" value="1" onclick="return(false);"><label for="food2">무</label>
								</c:otherwise>
							</c:choose>
							</li>
							<li class="list-group-item">
							<c:choose>
								<c:when test="${checklist.culture eq '0'}">
									<input type="radio" name="culture" id="culture1" value="0" checked onclick="return(false);"><label for="culture1">유</label> 
									<input type="radio" name="culture" id="culture2" value="1" onclick="return(false);"><label for="culture2">무</label>
								</c:when>
								<c:when test="${checklist.culture eq '1'}">
									<input type="radio" name="culture" id="culture1" value="0" onclick="return(false);"><label for="culture1">유</label> 
									<input type="radio" name="culture" id="culture2" value="1" checked onclick="return(false);"><label for="culture2">무</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="culture" id="culture1" value="0" onclick="return(false);"><label for="culture1">유</label> 
									<input type="radio" name="culture" id="culture2" value="1" onclick="return(false);"><label for="culture2">무</label>
								</c:otherwise>
							</c:choose>
							</li>
							<li class="list-group-item">
							<c:choose>
								<c:when test="${checklist.park eq '0'}">
									<input type="radio" name="park" id="park1" value="0" checked onclick="return(false);"><label for="park1">유</label> 
									<input type="radio" name="park" id="park2" value="1" onclick="return(false);"><label for="park2">무</label>
								</c:when>
								<c:when test="${checklist.park eq '1'}">
									<input type="radio" name="park" id="park1" value="0" onclick="return(false);"><label for="park1">유</label> 
									<input type="radio" name="park" id="park2" value="1" checked onclick="return(false);"><label for="park2">무</label>
								</c:when>
								<c:otherwise>
									<input type="radio" name="park" id="park1" value="0" onclick="return(false);"><label for="park1">유</label> 
									<input type="radio" name="park" id="park2" value="1" onclick="return(false);"><label for="park2">무</label>
								</c:otherwise>
							</c:choose>
							</li>
						</ul>
					</div>  
					
					<div class="row mb-3" style="align-items: center; display: flex; justify-content: center;">
						<textarea class="form-control col list-group col-md-11 themed-grid-col" name="conv_co" id="conv_co"
								rows="3" readonly="readonly" maxlength="150">${checklist.conv_co }</textarea>
					</div>

					<hr class="mb-4">
					<h4 class="mb-3">반려동물</h4>
					<div class="row">
						<div class="col-md-3 mb-3">
							<label for="pet_score" style="font-weight: bold;">점수</label>
							<c:choose>
								<c:when test="${checklist.pet_scoreNo ne 0 }">
									<c:forEach var="score" items="${scores }">
										<c:if test="${checklist.pet_scoreNo eq score.scoreNo }">
											<input type="text" class="form-control w-100" id="pet_scoreNo" name="pet_scoreNo" readonly="readonly" value="${score.scoreName }">
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control w-100" id="pet_scoreNo" name="pet_scoreNo" readonly="readonly" value="">
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-9 mb-3">
							<textarea class="form-control" name="pet_co" id="pet_co" 
							rows="3" readonly="readonly" maxlength="150">${checklist.pet_co }</textarea>
						</div>
					</div>
				</div>
			</div><!-- 본문 좌측 영역 끝 -->

			<hr class="mb-4">
			<h4 class="mb-3">코멘트</h4>
			<div class="col-md-12 mb-3">
				<label for="good" style="font-weight: bold;">장점</label>
				<textarea class="form-control" name="good" id="good" rows="3" readonly="readonly" maxlength="150">${checklist.good }</textarea>

				<label for="bad" style="font-weight: bold;">단점</label>
				<textarea class="form-control" name="bad" id="bad" rows="3" readonly="readonly" maxlength="150">${checklist.bad }</textarea>

				<label for="etc" style="font-weight: bold;">기타</label>
				<textarea class="form-control" name="etc" id="etc" rows="3" readonly="readonly" maxlength="150">${checklist.etc }</textarea>
			</div>
			
			<c:if test="${user eq 'writer' }">
			<hr class="mb-4">	
			<h4 class="mb-3">비밀 코멘트</h4>
			<div class="col-md-12 mb-3">
				<textarea class="form-control" name="secret_co" id="secret_co" rows="3" readonly="readonly" maxlength="150">${checklist.secret_co }</textarea>
			</div>
			</c:if>
			<input type="hidden" name="checkNo" value="${checklist.checkNo}">
			<input type="hidden" id="acNo" value="${ulogAcNo}">
			<hr class="mb-4">

		</form>
	</div>
	
	
	<!-- 본문2 영역 -->
	<div class="container">

		<div class="py-5 text-center">
			<h2 style="margin-top: 10px;">체크리스트 추가항목</h2>
		</div>

		<!-- SecondTimeInsertForm.jsp -->
		<c:if test="${user eq 'writer' }">
		<a href="secondtimeinsertform.action" role="button" class="btn btn-secondary" 
		id="btnAdd" style="vertical-align: bottom;">시간 관련항목 수정하기</a>
		
		<!-- SecondScoreInsertForm.jsp -->
		<a href="secondscoreinsertform.action" role="button" class="btn btn-secondary" 
		id="btnAdd" style="vertical-align: bottom;">점수 관련항목 수정하기</a>
		</c:if>
		

		<br>
		<br>

		<table class="table table-striped table-condensed table-hover">
			<tr class="text-center">
				<th>추가항목명</th>
				<th>코멘트</th>
				<th>점수 / 시간</th>
			</tr>
			<c:if test="${!empty timeList }">
			<c:forEach var="time" items="${timeList }">
			<tr class="text-center">
				<td>${time.timeTitle }</td>
				<td>${time.timeComments }</td>
				<td>${time.time } 분</td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${!empty scoreList }">
			<c:forEach var="score" items="${scoreList }">
			<tr class="text-center">
				<td>${score.scoreTitle }</td>
				<td>${score.scoreComments }</td>
				<c:forEach var="scores" items="${scores }">
					<c:if test="${score.scoreNo eq scores.scoreNo }">
						<td>${scores.scoreName } 점</td>
					</c:if>
				</c:forEach>
			</tr>
			</c:forEach>
			</c:if>
		</table>


	</div>
	
	
	<div class="container" style="margin-top: 50px;">
	<hr class="mb-4">
		<c:if test="${(user eq 'writer') or (user eq 'bookmarker') }">
		<div class="card mb-4 shadow-sm" style="margin-top: 50px;">
			<div class="card-header"><h4 class="my-0 font-weight-normal">스티커</h4></div>
	      
	        <div class="card-body">
	        	<p class="card-text">
	        	<c:choose>
	        		<c:when test="${!empty stickerList }">
	        			<c:forEach var="sticker" items="${stickerList }">
			        		<button type="button" class="btn btn-link">#${sticker }</button>
			        	</c:forEach>
	        		</c:when>
	        		<c:otherwise>
	        			작성한 스티커가 없습니다.
	        		</c:otherwise>
	        	</c:choose>
	        	</p>
	        	
	        	<br>
	     	</div>
		</div>
		</c:if>
		<div class="text-center mb-4" style="margin: 50px 0;">
			<button class="btn btn-secondary mx-auto" onclick="javascript:history.back();">뒤로가기</button>
    	</div>
    	<div class="text-right mb-4" style="margin: 20px 0;">
	    	<a href="#">▲ TOP</a>
    	</div>
	</div>
	

	<!-- footer 영역 -->
	<div>
	</div>

</body>
</html>