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
<title>NoticeWrite.jsp</title>
<!-- 신고받은 것을 클릭시 보이는 상세 페이지 -->

<!-- 통일하기로 한 부트스트랩 -->
<link rel="stylesheet"
   href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
   integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
   crossorigin="anonymous">
<script
   src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
   integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
   crossorigin="anonymous"></script>


<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">     
            
	$(function()
	{
		// 승인 버튼 클릭
		$("#approvalBtn").click(function()
		{
			
			if (confirm("해당 신고를 승인 처리하시겠습니까?"))
			{
				//@ 해당 버튼 값에 넣어준 번호로 update 처리 해보기!
				//$(location).attr("href", "reportapproval.action?no_no=");
				$(location).attr("href", "reportapproval.action?rpcheck_no=" + $(this).val());
			}
			
		});
		
		// 반려 버튼 클릭
		$("#rejectBtn").click(function()
		{
			
			if (confirm("해당 신고를 반려 처리하시겠습니까?"))
			{
				$(location).attr("href", "reportreject.action?rpcheck_no=" + $(this).val());
			}
		});
		
		// 허위신고 버튼 클릭
		$("#fakeBtn").click(function()
		{
			
			if (confirm("해당 신고를 허위신고 처리하시겠습니까?"))
			{
				$(location).attr("href", "reportrefake.action?rpcheck_no=" + $(this).val());
			}
		});
	});


</script>

</head>
<body>

   <!-- 내비바 -->
   <div>
      <c:import url="MenuNavbar_admin.jsp"></c:import>
   </div>

   <br>
   <br>
   <br>
   <br>
   <br>

   <div class="container">
      <!-- 게시판 메인 -->
      <div class="page-header">
         <h1 class="page-heading">신고 및 블라인드</h1>
         <hr>

         <br>
         <table class="table table-striped table-bordered">
            <tr>

               <tr>
            <!-- 신고 고유 번호 -->
               <th width="150px;">신고 번호</th>
               <td>${view.rpcheck_no}</td>
               <th width="150px;">신고 카테고리</th>
               <td>${view.title }</td>
            </tr><tr>
               <th width="150px;">신고자</th>
               <td>${view.reportername }</td>
               <th width="150px;">신고 대상자</th>
               <td>${view.writername }</td>
            </tr>
            <tr>
               <th>작성일시</th>
               <td colspan="3">${view.reportdate }</td>
              <!-- <th width="150px;">작성 시간</th>
               <td>13:50</td> -->
            </tr>

            <tr>
               <th height="150px;">내용</th>
               <!-- <td>{{ article.content }}</td> -->
               <td colspan="3"><br><br>
               <%-- 신고 당한 곳 주소 : http://seoro/List/${view.check_no }.com  --%>
               신고 당한 곳 주소 : <a href="readcheck.action?checkNo=${view.check_no}">http://localhost:8090/FinalProject/readcheck.action?checkNo=${view.check_no}</a>
               <br>신고된 체크리스트 고유번호 : ${view.check_no }
               </td>
               <!-- <br> 신고 당한 곳 주소 : http://seoro/List/131.com</td> -->
               <!-- 주소 추후 추가. 아마 게시판 주소 + 해당 고유번호 넣으면 되지 않을까.. 일단은 고유번호 넣어놓기 -->
            </tr>
            <tr>
               <th height="100px;">신고 상세 사유</th>
               <!-- 회원이 입력한 내용 -->
               <td colspan="3"><br>${view.why}
               </td>
            </tr>

         </table>




         <!-- 글쓰기 버튼 -->
         <div class="d-flex justify-content-end">
            <!-- 이 글쓰기 버튼은 관리자에게만 나타나는 버튼! -->
            <%-- <button type="submit" class="btn btn-primary" value="${view.no_no }">수정하기</button> --%>
            <button type="button" class="btn btn-primary" id="approvalBtn" value="${view.rpcheck_no }">승인</button>
            &nbsp;
            <button type="button" class="btn btn-primary" id="rejectBtn" value="${view.rpcheck_no }">반려</button>
            &nbsp;
            <button type="button" class="btn btn-primary" id="fakeBtn" value="${view.rpcheck_no }">허위신고</button>
            &nbsp;
            <button type="button" class="btn btn-secondary" onclick="location.href='reportlist.action'">목록으로</button>
         </div>
         <br> <br>


      </div>
   </div>

</body>
</html>