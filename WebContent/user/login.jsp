<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AimShoot</title>
</head>
<body onload="initView('view_name' ,'loginView', '${userVo.action_code}', '${userVo.msg_value}');">
<form name="loginView" method="post">
<%@ include file="../common/package.jsp" %>
<%@ include file="../common/lang_list.jsp" %>


<table>
	<tr>
		<td colspan="2"><%= msgOps.get("login_" + langVo.getLang_type())%></td>
	</tr>
	<tr>
		<td><%= msgOps.get("id_" + langVo.getLang_type())%></td>
		<td><input type="text" id="user_id"  value="${userVo.user_id}"></td>
	</tr>
	<tr>
		<td><%= msgOps.get("pwd_" + langVo.getLang_type())%></td>
		<td><input type="password" id="user_pwd"></td>
	</tr>
	<tr>
		<td><input type="button" value="<%= msgOps.get("login_" + langVo.getLang_type())%>" onclick="encrypt('<%= authVo.getPublicKeyModulus() %>', '<%= authVo.getPublicKeyExponent() %>');execAction('login');"></td>
		<td><input type="button" value="<%= msgOps.get("join_" + langVo.getLang_type())%>" onclick="execAction('joinView');"></td>
	</tr>
</table>

<input type="button" value="flushAllRedis" onclick="execAction('flushAllRedis');">
</form>
</body>
</html>