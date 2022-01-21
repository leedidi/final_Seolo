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
<title>ReadLocal.jsp</title>
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
			<h1 style="margin-top: 50px;">지역정보 조회</h1>
			<br>
		</div>

		<!-- 폼 시작 -->
		<form class="needs-validation" novalidate=""  action="" method="post" id="readLocal">
			<div class="row">
				<!-- 본문 우측 영역 -->
				<div class="col-md-4 order-md-2 mb-4">
					
					<h4 class="mb-3">지역분류</h4>
					<div class="row mb-3" style="justify-content: center;">
						<div class="col-md-5">
							<input type="text" class="form-control" id="gu" name="gu" readonly="readonly" value="${localList.guName }">
						</div>
						<div class="col-md-7">
							<input type="text" class="form-control" id="dong" name="dong" readonly="readonly" value="${localList.dongName }">
						</div>
					</div>
					
					<c:if test="${user eq 'bookmarker' }">
						<button class="btn btn-secondary" style="width: 100%;">북마크 삭제하기</button>
					</c:if>
					<c:if test="${user eq 'viewer' }">
						<button class="btn btn-primary" style="width: 100%;">북마크 추가하기</button>
					</c:if>

				</div><!-- 본문 우측 영역 끝 -->
			

				<!-- 본문 좌측 영역 -->
				<div class="col-md-8 order-md-1">
					
					<h4 class="mb-3">주거비 평균</h4>
					<h6 class="mb-3">(단위: 만원)</h6>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="deposit" style="font-weight: bold;">월세 보증금</label>
							<c:choose>
								<c:when test="${localList.deposit eq -1 }">
									<input type="text" class="form-control" id="deposit" name="deposit" value="-" readonly="readonly">
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" id="deposit" name="deposit" value="${localList.deposit }" readonly="readonly">
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-6 mb-3">
							<label for="mWolse" style="font-weight: bold;">월세</label>
							<c:choose>
								<c:when test="${localList.mWolse eq -1 }">
									<input type="text" class="form-control" id="mWolse" name="mWolse" value="-" readonly="readonly">
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" id="mWolse" name="mWolse" value="${localList.mWolse }" readonly="readonly">
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="mJeonse" style="font-weight: bold;">전세 보증금</label>
							<c:choose>
								<c:when test="${localList.mJeonse eq -1 }">
									<input type="text" class="form-control" id="mJeonse" name="mJeonse" value="-" readonly="readonly">
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" id="mJeonse" name="mJeonse" value="${localList.mJeonse }" readonly="readonly">
								</c:otherwise>
							</c:choose>
						</div>
					</div>

					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="mMaemae" style="font-weight: bold;">매매가</label>
							<c:choose>
								<c:when test="${localList.mMaemae eq -1 }">
									<input type="text" class="form-control" id="mMaemae" name="mMaemae" value="-" readonly="readonly">
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" id="mMaemae" name="mMaemae" value="${localList.mMaemae }" readonly="readonly">
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					
					<hr class="mb-4">
					<div class="row">
						<div class="col-md-9">
							<label for="convenience_score" style="font-weight: bold;">생활편의시설</label>
							<c:choose>
								<c:when test="${localList.mMaemae eq -1 }">
									<input type="text" class="form-control" id="convenience_score" name="convenience_score" value="-" readonly="readonly">
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" id="convenience_score" name="convenience_score" value="${localList.convenience_score }" readonly="readonly">
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-3">
							<br>
							<strong> / 6점 만점</strong>
						</div>
					</div>
				</div>
			</div>
			
			<hr class="mb-4">
			<div class="row">
				<div class="col-md-6">
					<label for="security_score" style="font-weight: bold;">치안 (5점 만점)</label>
					<c:choose>
						<c:when test="${localList.security_score eq -1 }">
							<input type="text" class="form-control" id="security_score" name="security_score" value="-" readonly="readonly">
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control" id="security_score" name="security_score" value="${localList.security_score }" readonly="readonly">
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-6">
					<label for="pet_score" style="font-weight: bold;">반려동물 (5점 만점)</label>
					<c:choose>
						<c:when test="${localList.pet_score eq -1 }">
							<input type="text" class="form-control" id="pet_score" name="pet_score" value="-" readonly="readonly">
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control" id="pet_score" name="pet_score" value="${localList.pet_score }" readonly="readonly">
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<hr class="mb-4">
			<div class="row">
				<div class="col-md-6">
					<label for="transport_score" style="font-weight: bold;">대중교통 (5점 만점)</label> 
					<c:choose>
						<c:when test="${localList.transport_score eq -1 }">
							<input type="text" class="form-control" id="transport_score" name="transport_score" value="-" readonly="readonly">
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control" id="transport_score" name="transport_score" value="${localList.transport_score }" readonly="readonly">
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-6">
					<label for="honjap_score" style="font-weight: bold;">교통혼잡도 (5점 만점)</label>
					<c:choose>
						<c:when test="${localList.honjap_score eq -1 }">
							<input type="text" class="form-control" id="honjap_score" name="honjap_score" value="-" readonly="readonly">
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control" id="honjap_score" name="honjap_score" value="${localList.honjap_score }" readonly="readonly">
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form>
	</div>

	<div class="container" style="margin-top: 50px;">
	<hr class="mb-4">
		<c:if test="${user eq 'bookmarker' }">
		<div class="card mb-4 shadow-sm" style="margin-top: 50px;">
			<div class="card-header">
				<h4 class="my-0 font-weight-normal">스티커</h4>
			</div>
	        <div class="card-body">
	        	<p class="card-text">
	        	<a href="xxx.action" role="button" class="btn btn-secondary" id="stickerModify" style="vertical-align: bottom;">스티커 추가/삭제</a><br><br>
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