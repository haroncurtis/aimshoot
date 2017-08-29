<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.data.redis.core.ValueOperations" %>
<%@ page import="haron.aimshoot.vo.LangVO" %>

<select id="lang" name="langVo.lang_type" onchange="execAction('changeLang')"> 
<s:iterator value="langAR" id="langAR">
	
	<s:if test='%{lang_type == Lang_type_view}'>
		<option value="<s:property value="lang_type"/>" selected="selected"><s:property value="lang_display"/>
	</s:if>
	<s:else>
		<option value="<s:property value="lang_type"/>"><s:property value="lang_display"/>
	</s:else>
</s:iterator>
</select>