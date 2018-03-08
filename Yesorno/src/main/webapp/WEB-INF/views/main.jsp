<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
	<title><spring:message code="name.page.main"/></title>
</head>
<body>
	<h1><spring:message code="name.page.main"/></h1>
	<p><a href="members/register">[<spring:message code="members.register.title"/>]</a>
	<p><a href="members/login">[<spring:message code="members.login.title"/>]</a>
	<p><a href="members/logout">[<spring:message code="members.logout.title"/>]</a>
	<p><a href="members/changeinfo">[<spring:message code="members.changeinfo.title"/>]</a>
	<p>memberId : ${memberDto.memberId}</p>
	<p>memberEmail : ${memberDto.memberEmail}</p>
	<p>memberNickname : ${memberDto.memberNickname}</p>
	<p>memberTokenCookie : ${memberDto.memberToken}</p>
</body>
</html>