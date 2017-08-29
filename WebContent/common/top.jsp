<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/package.jsp" %>
<%= msgOps.get("name_" + langVo.getLang_type())%>：${userVo.last_name}${userVo.first_name}
<input type="button" value="<%= msgOps.get("logout_" + langVo.getLang_type())%>" onclick="logout('<%= msgOps.get("logout_confirm_" + langVo.getLang_type())%>');">
<input type="button" value="<%= msgOps.get("login_" + langVo.getLang_type())%>" onclick="execAction('scheduleView');">
<input type="button" value="<%= msgOps.get("setting_" + langVo.getLang_type())%>" onclick="execAction('accountSettingView');">