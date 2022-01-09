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
<title>Myinfo.jsp</title>
<link rel="stylesheet" href="<%=cp %>/css/CreateAccountInsert.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
crossorigin="anonymous">
<style type="text/css">
	p { font-size: small; color: red; }
	.errMsg { font-size: small; color: red; }
	.okMsg { font-size: small; color: blue; }
	
	input { pointer-events: none; }

</style>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript">


		$(function()
		{
			$("#updateBtn").click(function()
			{
				$(location).attr("href", "infoupdateconfirmform.action");
			});
			
			$("#pwdUpdateBtn").click(function()
			{
				$(location).attr("href", "pwdchangeform.action");
			});
			
			$("#withdrawalBtn").click(function()
			{
				$(location).attr("href", "withdrawalconfirm.action");
			});
			
			// 나의 신고리스트로 이동 버튼 추가
			$("#myInfoReportBtn").click(function()
			{
				$(location).attr("href", "myInfoReportList.action");
			});
	
			//myInfoReportList.action
		});
		
		$(function()
		{
			// 취소 버튼 클릭
			$(".btn-secondary").click(
			function()
			{
				if (confirm("해당 신고를 정말 취소하시겠습니까?"))
				{
					$(location).attr("href", "myInfoReportMDelete.action?rpcheck_no=" + $(this).val());
				}
			});

		});
	
</script>



</head>
<body>

	<!-- 메뉴 영역 -->
	<div>
	<c:import url="/nav.action"></c:import>
	</div>

	<!-- 콘텐츠 영역 -->
	<div class="wrap wd668">
	<!-- <div class="container" style="width:50%; text-align: left;"> -->
        <div class="form_txtInput">
        <br>
          <h2 class="sub_tit_txt">내 정보 조회</h2>
          <hr>
		<!-- 프로필사진 업로드구역 -->
		<div class="col-md-4 mx-auto">
			<img src="<%=cp %>/images/profile.jpg" width="250px"><br><br>
			<div style="text-align: center;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-primary">사진 바꾸기</button>
			</div>
		</div>
        
        <hr style="margin: 30px 0;">
          
          <div class="join_form">
          <form action="" method="post" id="personalInsertForm">
            <table>
              <colgroup>
                <col width="30%"/>
                <col width="auto"/>
              </colgroup>
              <tbody>
                <tr>
                  <th>아이디</th>
                  <td><input type="text" class="send_number" id="id" name="id" maxlength="12"
                  style="width: 550px; background-color: #e9ecef;" value="${user.pe_Id }" readonly="readonly">
                      <span id="idErrMsg" class="errMsg"></span>
                  </td>
                </tr>
                <tr>
                  <th>이름</th>
                  <td><input type="text" id="name" name="name" class="name"maxlength="10" value="${user.name }" readonly="readonly"
                  style="background-color: #e9ecef;"></td>
                </tr>
                <tr>
                	<th></th>
                	<td><span id="nameErrMsg" class="errMsg"></span></td>
                </tr>
                <!-- ★ 소연 수정 -->
                <tr>
                  <th>닉네임</th>
                  <td>
					<input type="text" class="nickName" id="nickName" name="nickName" maxlength="10" value="${user.nickName }" readonly="readonly"
					style="background-color: #e9ecef;">
                  </td>
                </tr>
                <tr>
                	<th></th>
                	<td><span id="nickErrMsg" class="errMsg"></span><span id="nickOkMsg" class="okMsg"></span></td>
                </tr>
                <tr class="email">
                  <th>이메일</th>
                  <td>
                    <input type="text" id="email" name="email" class="email" value="${user.email }" readonly="readonly"
                    style="background-color: #e9ecef;">
                  </td>
                <tr>
                	<th></th>
                	<td><span id="emailErrMsg" class="errMsg"></span></td>
                </tr>  
                <tr>
                  <th>휴대전화</th>
                  <td><input type="text" id="tel" name="tel" class="phone" maxlength="11"
                  value="${user.tel }" readonly="readonly" style="background-color: #e9ecef;">
                  </td>
                </tr>
                <tr>
                	<th></th>
                	<td><!-- <p>이미 가입했거나, 현재 가입이 불가능한 번호입니다.</p> -->
						<span id="telErrMsg" class="errMsg"></span></td>
                </tr>
                <tr>
                	<th></th>
                	<td><span id="idnErrMsg" class="errMsg"></span><span id="idnOkMsg" class="okMsg"></span></td>
                </tr>
                <tr>
                  <th>주소</th>
                  <td>					
					<input type="text" id="roadAddr" name="roadAddr" readonly="readonly"
					value="${user.roadAddr } ${user.detailAddr }" style="background-color: #e9ecef;"><br>
				  </td>
                </tr>
                <tr>
                	<th></th>
                	<td><span id="addrErrMsg" class="errMsg"></span></td>
                </tr>  
                <tr>
              </tbody>
            </table>
            </form>
           
          </div><!-- join_form E  -->
          <div class="text-center">
          	<span id="flagMsg" class="errMsg"></span>
          </div>
          <div class="btn_wrap">
			<button type="button" class="btn btn-primary" id="updateBtn" style="font-size: 13px;">수정하기</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-primary" id="pwdUpdateBtn" style="font-size: 13px;">비밀번호 변경</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-danger" id="withdrawalBtn" style="font-size: 13px;">탈퇴하기</button>
			<!-- ＠ 자바스크립트에서 secondary 버튼 사용해야해서...! 이부분만 danger로 수정할게요! -->
          </div>
           
          <!-- 나의 신고리스트 추가 -->
          <!-- 최근 3개 신고만 보이도록 함 -->
          
          <div class="report_form">
           <hr style="margin: 30px 0;"><br>
           <!-- <h2 class="sub_tit_txt">나의 신고리스트</h2> -->
           <h4>나의 신고리스트<button type="button" class="btn btn-light" id="myInfoReportBtn" style="float: right;">바로가기▶</button></h4>
           <br>
          <div class="table-responsive">
      	 <table class="table table-hover">
         	<thead>
            <tr class="table-primary">
               <th>신고 번호</th>
               <th>대상 게시물</th>
               <th style="text-align: center;" width="20%;">신고사유</th>
               <th>신고 상태</th>
               <th>신고일시</th>
               <th></th>
            </tr> 
         </thead>
        
         <c:choose>
         	<%-- 신고 내용이 없을 때 --%>
         	<c:when test="${empty myinfoList}">
               <td colspan='6' style="text-align: center;">신고한 내용이 없습니다.</td>
         	</c:when>
         	
         	<%-- 신고 내용이 존재할 때 --%>
         	<c:otherwise>
         		<tbody>
          	<c:forEach var="myinfoList" items="${myinfoList }">
            <tr>
               <th scope="row" style="text-align: center">${myinfoList.rpcheck_no }</th>
               <td style="text-align: center">
               <button type="button" class="btn btn-light" style="width:38pt; height:23pt; font-size:12px;" onclick="location.href='readcheck.action?checkNo=${myinfoList.check_no}'">
               이동</button></td>
               <td style="text-align:center;">${myinfoList.title }</td>
               
               <!-- 신고 상태가 미해결일 시에는 붉은 글씨, 해결된 상태일때는 파란 글씨로 보이게 함 -->
               <c:choose>
	               <c:when test="${myinfoList.statusname eq '미해결'}">
	               		<td style="color:red;font-weight: bold;text-align:center;">[${myinfoList.statusname }]</td>
	               </c:when>
	               <c:otherwise>
	               		<td style="color:blue;font-weight: bold;text-align:center;">[${myinfoList.statusname }]</td>
	               </c:otherwise>
	               </c:choose>
               <td>${myinfoList.reportdate }</td>
               
               <!-- 취소 버튼은 미해결 일때만 클릭할 수 있게 함 -->
                <c:choose>
	               <c:when test="${myinfoList.statusname eq '미해결'}">
	               		<td><button type="button" class="btn btn-secondary" id="cancleBtn" value="${myinfoList.rpcheck_no }" 
	               		style="width:38pt; height:23pt; font-size:12px;">취소</button></td>
	               </c:when>
	               <c:otherwise>
	               		<td><button type="button" class="btn btn-secondary" id="cancleBtn" value="${myinfoList.rpcheck_no }" 
	               		style="width:38pt; height:23pt; font-size:12px;" disabled="disabled">취소</button></td>
	               </c:otherwise>
	               </c:choose>
            </tr>  
            </c:forEach>
         </tbody>
         	</c:otherwise>
         </c:choose>
         
          
         
      </table>
      </div> <!-- report_form E -->
      <br><br>
      
          
        </div> <!-- form_txtInput E -->
      </div><!-- content E-->
    </div> <!-- container E -->
    

</body>
</html>