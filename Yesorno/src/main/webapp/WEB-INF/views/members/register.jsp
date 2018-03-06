<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="members.register.title"/></title>
</head>
<body>
	<h1><spring:message code="members.register.title"/></h1>
<%--
 	<form action="../members" method="post">
		<p><spring:message code="members.register.email"/> : <input type="email" name="memberEmail" placeholder="<spring:message code="members.register.email.placeholder"/>"/></p>
		<p><spring:message code="members.register.password"/> : <input type="password" name="password" placeholder="<spring:message code="members.register.password.placeholder"/>"/></p>
		<p><spring:message code="members.register.passwordconfirm"/>: <input type="password" name="passwordConfirm"/></p>
		<p><spring:message code="members.register.nickname"/> : <input type="text" name="memberNickname" placeholder="<spring:message code="members.register.nickname.placeholder"/>"/></p>
		<p><input type="submit" value="<spring:message code="input.button.ok"/>"/></p>
	</form>
-->
--%>

	<form:form action="members" method="post" modelAttribute="registerMember">
		<p>
			<spring:message code="members.register.email"/> : 
			<form:input path="memberEmail" placeholder='<spring:message code="members.register.email.placeholder"/>'/>
			<form:errors path="memberEmail"/>
		</p>
		<p><spring:message code="members.register.password"/> : <input type="password" name="password" placeholder="<spring:message code="members.register.password.placeholder"/>"/></p>
		<p><spring:message code="members.register.passwordConfirm"/>: <input type="password" name="passwordConfirm"/></p>
		<p>
			<spring:message code="members.register.nickname"/> : 
			<form:input path="memberNickname" placeholder='<spring:message code="members.register.nickname.placeholder"/>'/>
			<form:errors path="memberNickname"/>	
		</p>
		<p><input type="submit" value="<spring:message code="input.button.ok"/>"/></p>
	</form:form>

</body>
</html>