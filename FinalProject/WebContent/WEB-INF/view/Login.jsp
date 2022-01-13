<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   request.setCharacterEncoding("UTF-8");
   String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.88.1">
<title>Login.jsp</title>
<link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/sign-in/">

<!-- Bootstrap core CSS -->
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
</style>

<!-- Custom styles for this template -->
<link href="<%=cp%>/css/signin.css" rel="stylesheet">
<script type="text/javascript">


</script>

</head>
<body>
   <!-- 메뉴바 영역 -->
   <div>
      <c:import url="/nav.action"></c:import>
   </div>
   
   <!-- 로그인 영역 -->
   <div class="container text-center">   
   
      <form action="login.action" class="form-signin" id="loginForm">
      
      <c:if test="${errMsg == 1}">
         <div class="alert alert-primary alert-dismissible fade show" role="alert" id="errMsg">
            <strong>로그인 정보가 <br>올바르지 않습니다!</strong>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
               <span aria-hidden="true">&times;</span>
            </button>
         </div>
      </c:if>
      <c:if test="${errMsg == 2}">
         <div class="alert alert-danger alert-dismissible fade show" role="alert" id="errMsg">
            <strong>10회 이상 경고 누적으로<br>로그인이 제한되었습니다.</strong>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
               <span aria-hidden="true">&times;</span>
            </button>
         </div>
      </c:if>
      
         <h1 class="h3 mb-3 font-weight-normal">로그인</h1>
         <label for="id" class="sr-only">ID</label>
         <input type="text" id="id" name="id" class="form-control" placeholder="User ID" required autofocus>
         <label for="pwd" class="sr-only">Password</label>
         <input type="password" id="pwd" name="pwd" class="form-control" placeholder="Password" required>
         
         <!-- 로그인 상태 유지 기능 -->
         <div class="checkbox mb-3">
            <label>
               <input type="checkbox" name="rememberMe" value="rememberMe"> 로그인 상태 유지
            </label>
         </div>
         <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
         <p></p>
         
         <p class="mt-5 mb-3 text-muted"><a href="idsearchform.action">아이디 찾기</a> / <a href="pwdsearchoneform.action">비밀번호 찾기</a> /  <a href="adminloginform.action">ADMIN</a></p>
         <p></p>
      </form>
      
      
      
   </div>
</body>
</html>