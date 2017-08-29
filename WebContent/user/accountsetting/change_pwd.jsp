<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.data.redis.core.ValueOperations" %>
<%@ page import="haron.aimshoot.vo.*" %>
パスワード変更

<%
ValueOperations<String,String> msgOps_ChangePwd = (ValueOperations<String,String>)request.getAttribute("msgOps");
LangVO langVo_ChangePwd = (LangVO)request.getAttribute("langVo");
AuthVO authVo_ChangePwd = (AuthVO)request.getAttribute("authVo");
%>
<table>

	<!-- 現在パスワード -->
	<tr>
		<td><%= msgOps_ChangePwd.get("now_pwd_" + langVo_ChangePwd.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="password" id="user_pwd"></td>
	</tr>

	<!-- パスワード -->
	<tr>
		<td><%= msgOps_ChangePwd.get("pwd_" + langVo_ChangePwd.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="password" id=user_change_pwd></td>
	</tr>
	
	<!-- パスワード確認 -->
	<tr>
		<td><%= msgOps_ChangePwd.get("repwd_" + langVo_ChangePwd.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="password" id="user_repwd"></td>
	</tr>
	<tr>
		<td><input type="button" value="<%= msgOps_ChangePwd.get("change_pwd_" + langVo_ChangePwd.getLang_type())%>" onclick="encrypt('<%= authVo_ChangePwd.getPublicKeyModulus() %>', '<%= authVo_ChangePwd.getPublicKeyExponent() %>');execAction('changePwdEXE');">
	</tr>
</table>