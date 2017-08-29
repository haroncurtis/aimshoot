<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AimShoot</title>
</head>
<body>
<body onload="initView('view_name' ,'joinView', '${userVo.action_code}', '${userVo.msg_value}');">
<form name="joinView">
<%@ include file="../common/package.jsp" %>
<%@ include file="../common/lang_list.jsp" %>

<%= msgOps.get("join_" + langVo.getLang_type())%>

<table>
	<!-- 名前 -->
	<tr>
		<td><%= msgOps.get("name_" + langVo.getLang_type())%></td>
	</tr>
	<tr>
		<td><input type="text" id="last_name" name="userVo.last_name" value="${userVo.last_name}" placeholder="<%= msgOps.get("last_name_" + langVo.getLang_type())%>"></td>
		<td><input type="text" id="first_name" name="userVo.first_name" value="${userVo.first_name}" placeholder="<%= msgOps.get("first_name_" + langVo.getLang_type())%>"></td>
	</tr>
	
	<!-- 性別 -->
	<tr>
		<td><%= msgOps.get("sex_" + langVo.getLang_type())%><td>
	</tr>
	<tr>
		<td>
			<s:if test="userVo.sex == 1"><input type="radio" id="sex" value="1" name="userVo.sex" checked="checked"></s:if>
			<s:else><input type="radio" id="sex" value="1" name="userVo.sex"></s:else>
			<%= msgOps.get("sex_man_" + langVo.getLang_type())%>
		</td>
		<td>
			<s:if test="userVo.sex == 2"><input type="radio" id="sex" value="2" name="userVo.sex" checked="checked"></s:if>
			<s:else><input type="radio" id="sex" value="2" name="userVo.sex"></s:else>
			<%= msgOps.get("sex_woman_" + langVo.getLang_type())%>
		</td>

	</tr>

	<!-- 生年月日 -->
	<tr>
		<td><%= msgOps.get("birth_" + langVo.getLang_type())%></td>
	</tr>
	<tr>
		<td>
		<select id="birth_year" name="userVo.birth_year">
			<s:iterator value="yearAR">
				<s:if test='%{birth_year == birth_year_view}'>
					<option value="<s:property value="birth_year"/>" selected="selected"><s:property value="birth_year"/>
				</s:if>
				<s:else>
					<option value="<s:property value="birth_year"/>"><s:property value="birth_year"/>
				</s:else>
			</s:iterator>
		</select>
		<select id="birth_month" name="userVo.birth_month">
			<s:iterator value="monthAR">
				<s:if test='%{birth_month == birth_month_view}'>
					<option value="<s:property value="birth_month"/>" selected="selected"><s:property value="birth_month"/>
				</s:if>
				<s:else>
					<option value="<s:property value="birth_month"/>"><s:property value="birth_month"/>
				</s:else>
			</s:iterator>
		</select>
		<select id="birth_day" name="userVo.birth_day">
			<s:iterator value="dayAR">
				<s:if test='%{birth_day == birth_day_view}'>
					<option value="<s:property value="birth_day"/>" selected="selected"><s:property value="birth_day"/>
				</s:if>
				<s:else>
					<option value="<s:property value="birth_day"/>"><s:property value="birth_day"/>
				</s:else>
			</s:iterator>
		</select>
		<td>
	</tr>

	<!-- ID -->
	<tr>
		<td><%= msgOps.get("id_" + langVo.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="text" id="user_id" value="${userVo.user_id}"></td>
	</tr>
	
	<!-- パスワード -->
	<tr>
		<td><%= msgOps.get("pwd_" + langVo.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="password" id="user_pwd"></td>
	</tr>
	
	<!-- パスワード確認 -->
	<tr>
		<td><%= msgOps.get("repwd_" + langVo.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="password" id="user_repwd"></td>
	</tr>
	
	<!-- メール -->
	<tr>
		<td><%= msgOps.get("email_" + langVo.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="text" id="email" name="userVo.email" value="${userVo.email}"></td>
	</tr>
	
	<!-- 連絡先1(自宅) -->
	<tr>
		<td><%= msgOps.get("call1_" + langVo.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="text" id=call1" value="${userVo.call1}"></td>
	</tr>	
	<!-- 連絡先2(携帯) -->
	<tr>
		<td><%= msgOps.get("call2_" + langVo.getLang_type())%><td>
	</tr>
	<tr>
		<td><input type="text" id="call2"  value="${userVo.call2}"></td>
	</tr>
	<tr>
		<td><input type="button" value="<%= msgOps.get("join_" + langVo.getLang_type())%>" onclick="encrypt('<%= authVo.getPublicKeyModulus() %>', '<%= authVo.getPublicKeyExponent() %>');execAction('join');"></td>
	</tr>
</table>
</form>
</body>
</html>