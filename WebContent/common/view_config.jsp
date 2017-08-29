<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.data.redis.core.ValueOperations" %>
<%@ page import="haron.aimshoot.vo.*" %>

<!-- 基本設定 -->
<input type="hidden" id="view_name" name="userVo.view_name">
<input type="hidden" id="encryptUser_id" name="userVo.user_id">
<input type="hidden" id="encryptUser_pwd" name="userVo.user_pwd">
<input type="hidden" id="encryptUser_repwd" name="userVo.user_repwd">
<input type="hidden" id="encryptUser_change_pwd" name="userVo.user_change_pwd">

<input type="hidden" id="category_cnt" name="categoryVo.category_cnt" value="${categoryVo.category_cnt}">
<textarea id="category_list" name="categoryVo.category_list" style="display:none;">${categoryVo.category_list}</textarea>

<%
ValueOperations<String,String> msgOps = (ValueOperations<String,String>)request.getAttribute("msgOps");
LangVO langVo = (LangVO)request.getAttribute("langVo");
AuthVO authVo = (AuthVO)request.getAttribute("authVo");
%>
