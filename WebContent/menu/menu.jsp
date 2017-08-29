<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.springframework.data.redis.core.ValueOperations" %>
<%@ page import="haron.aimshoot.vo.*" %>
<%
ValueOperations<String,String> msgOps_menu = (ValueOperations<String,String>)request.getAttribute("msgOps");
LangVO langVo_menu = (LangVO)request.getAttribute("langVo");
%>

<div class="tab">
  <button class="tablinks" onclick="openCity(event, 'London')"><%= msgOps_menu.get("category_" + langVo_menu.getLang_type())%></button>
  <button class="tablinks" onclick="openCity(event, 'Paris')"><%= msgOps_menu.get("schedule_" + langVo_menu.getLang_type())%></button>
  <button class="tablinks" onclick="openCity(event, 'Tokyo')"><%= msgOps_menu.get("household_" + langVo_menu.getLang_type())%></button>
</div>

<div id="category" class="menu">
	<%@ include file="../menu/category/category.jsp" %>
</div>

<div id="schedule" class="menu">
	<%@ include file="../menu/schedule/schedule.jsp" %>
</div>

<div id="household" class="menu">
	<%@ include file="../menu/household/household.jsp" %>
</div>

<div id="main" class="menu">
	<%@ include file="../main/recent_list.jsp" %>
</div>