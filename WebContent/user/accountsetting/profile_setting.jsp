<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.data.redis.core.ValueOperations" %>
<%@ page import="haron.aimshoot.vo.*" %>
<%
ValueOperations<String,String> msgOps_Profile = (ValueOperations<String,String>)request.getAttribute("msgOps");
LangVO langVo_Profile = (LangVO)request.getAttribute("langVo");
AuthVO authVo_Profile = (AuthVO)request.getAttribute("authVo");
%>

プロフィル設定

<table>
	<!-- 名前 -->
	<tr>
		<td><%= msgOps_Profile.get("name_" + langVo_Profile.getLang_type())%></td>
	</tr>
	<tr>
		<td><input type="text" id="last_name" name="userVo.last_name" value="${userVo.last_name}" readonly="readonly"></td>
		<td><input type="text" id="first_name" name="userVo.first_name" value="${userVo.first_name}" readonly="readonly"></td>
	</tr>
	
	<!-- 性別 -->
	<tr>
		<td><%= msgOps_Profile.get("sex_" + langVo_Profile.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="text" id="last_name" name="userVo.sex" value="${userVo.sex}" readonly="readonly"></td>
	</tr>

	<!-- 生年月日 -->
	<tr>
		<td><%= msgOps_Profile.get("birth_" + langVo_Profile.getLang_type())%></td>
	</tr>
	<tr>
			<td><input type="text" id="last_name" name="userVo.birth" value="${userVo.birth}" readonly="readonly"></td>
	</tr>

	<!-- ID -->
	<tr>
		<td><%= msgOps_Profile.get("id_" + langVo_Profile.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="text" id="user_id" value="${userVo.user_id}" readonly="readonly"></td>
	</tr>
	
	<!-- メール -->
	<tr>
		<td><%= msgOps_Profile.get("email_" + langVo_Profile.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="text" id="email" name="userVo.email" value="${userVo.email}" readonly="readonly"></td>
	</tr>
	
	<!-- 連絡先1(自宅) -->
	<tr>
		<td><%= msgOps_Profile.get("call1_" + langVo_Profile.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="text" id="call1" name="userVo.call1" value="${userVo.call1}" readonly="readonly"></td>
	</tr>	
	<!-- 連絡先2(携帯) -->
	<tr>
		<td><%= msgOps_Profile.get("call2_" + langVo_Profile.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="text" id="call2" name="userVo.call2" value="${userVo.call2}" readonly="readonly"></td>
	</tr>
	<tr>
		<td>
			<input type="button" id="profileEdit" value="<%= msgOps_Profile.get("edit_" + langVo_Profile.getLang_type())%>" onclick="profileEdita(0, '', '', '');">
			<input type="button" id="profileCancel" value="<%= msgOps_Profile.get("cancel_" + langVo_Profile.getLang_type())%>" style="display:none;" onclick="profileEdita(1, '${userVo.email}','${userVo.call1}', '${userVo.call2}');">
			<input type="button" id="profileConfirm" value="<%= msgOps_Profile.get("confirm_" + langVo_Profile.getLang_type())%>" style="display:none;" onclick="execAction('profileEditConfirm');">
		</td>
	</tr>
</table>
