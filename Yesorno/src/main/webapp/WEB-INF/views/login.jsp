<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<a class="btn btn-block btn-social btn-facebook" onclick="document.facebook.submit();">
	<span class="fa fa-facebook""></span>Facebook 계정으로 로그인하기</a>
	<a class="btn btn-block btn-social btn-google" onclick="document.google.submit();">
	<span class="fa fa-google"></span> Google 계정으로 로그인하기</a><!-- hidden scope-->
	<form action="/auth/facebook" name="facebook">
	<input type="hidden" name="scope" value="email,user_friends"/>
	</form>
	<form action="/auth/google" name="google">
	<input type="hidden" name="scope" value="https://www.googleapis.com/auth/plus.login" />
	</form>
</body>
</html>