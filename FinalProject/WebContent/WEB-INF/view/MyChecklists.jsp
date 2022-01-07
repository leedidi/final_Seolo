<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<title>MyChecklists.jsp</title>
</head>

<body>
	<!-- 나의 체크리스트F 목록 -->
	<!-- 메뉴바 영역 -->
	<div>
		<c:import url="MenuNavbar_new.jsp"></c:import>
	</div>

	<!-- 본문 영역 -->
	<div class="container">
		<div class="py-5 text-center">
			<h1 style="margin-top: 70px;">나의 체크리스트</h1>
			<br> <a href="checkfirstinsertform.action" class="btn btn-primary my-2">새 체크리스트 작성</a> <a href="mychecklistform.action" class="btn btn-primary my-2">체크리스트 목록 설정</a>
		</div>

		<!-- 본문 하단영역(체크리스트 목록) 출력 -->
		<div class="table-responsive">
			<table class="card-text table table-striped">
				<thead>
					<tr>
						<th>분류</th>
						<th>지역</th>
						<th>제목</th>
						<c:if test="${wolse }">
							<th>보증금</th>
							<th>월세</th>
						</c:if>
						<c:if test="${jeonse }">
							<th>전세</th>
						</c:if>
						<c:if test="${maemae }">
							<th>매매</th>
						</c:if>
						<c:if test="${security }">
							<th>치안</th>
						</c:if>
						<c:if test="${transportation }">
							<th>대중교통</th>
						</c:if>
						<c:if test="${honjap }">
							<th>교통혼잡도</th>
						</c:if>
						<c:if test="${conv }">
							<th>생활편의시설</th>
						</c:if>
						<c:if test="${pet }">
							<th>반려동물</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<%-- 데이터가 존재하지 않을 때 --%>
						<c:when test="${fn:length(viewList)==0}">
							<tr>
								<td colspan="12"><br>
									<p class="text-center">체크리스트가 존재하지 않습니다.</p></td>
							</tr>
						</c:when>

						<%-- 데이터가 존재할 때 --%>
						<c:otherwise>
							<c:forEach var="view" items="${viewList }">
								<%-- tr 구성을 위한 분기: type 속성에 따라 다른 링크 --%>
								<c:choose>
									<c:when test="${view.type == '북마크 지역정보' }">
										<tr onclick="location.href='readlocal.action?dongNo=${view.listNo}'">
									</c:when>
									<c:otherwise>
										<tr onclick="location.href='readcheck.action?checkNo=${view.listNo}'">
									</c:otherwise>
								</c:choose>
								
								<%-- td 구성 시작 --%>
								<td>${view.type }</td>
								<td>${view.guName }&nbsp;${view.dongName }</td>
								<td>${view.title }</td>
								<c:if test="${wolse }">	<%-- wolse 가 선택됐다면... --%>
									<td><c:if test="${view.deposit != -1}">
											<fmt:formatNumber value="${view.deposit }" pattern="#,###" />만원
											</c:if></td>
									<td><c:if test="${view.wolse != -1}">
											<fmt:formatNumber value="${view.wolse }" pattern="#,###" />만원
											</c:if></td>
								</c:if>
								<c:if test="${jeonse }">
									<td><c:if test="${view.jeonse != -1}">
											<fmt:formatNumber value="${view.jeonse }" pattern="#,###" />만원
											</c:if></td>
								</c:if>
								<c:if test="${maemae }">
									<td><c:if test="${view.maemae != -1}">
											<fmt:formatNumber value="${view.maemae }" pattern="#,###" />만원
											</c:if></td>
								</c:if>
								<c:if test="${security }">
									<td>${view.security != -1? view.security : '' }</td>
								</c:if>
								<c:if test="${transportation }">
									<td>${view.transport != -1? view.transport : '' }</td>
								</c:if>
								<c:if test="${honjap }">
									<td>${view.honjap != -1? view.honjap : '' }</td>
								</c:if>
								<c:if test="${conv }">
									<td>${view.convenience != -1? view.convenience += '/6' : '' }</td>
								</c:if>
								<c:if test="${pet }">
									<td>${view.pet != -1? view.pet : '' }</td>
								</c:if>
								</tr>
								<tr>
									<td colspan="12"><c:choose>
											<c:when test="${fn:length(view.stickerList)==0}">
											등록된 스티커가 없습니다.
										</c:when>
											<c:otherwise>
											나의 스티커
											<c:forEach var="sticker" items="${view.stickerList }">
													<span class="badge rounded-pill bg-light text-dark">#${sticker.content }</span>
												</c:forEach>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>

		<hr class="mb-4">

		<div style="margin-bottom: 50px;"></div>
	</div>

	<!-- footer 영역 -->
	<div></div>

</body>
</html>