<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Aimshoot</title>
<head>
<!-- 다운받은 fontello.css의 경로를 지정하여 준다. -->

</head>
<body onload="initView('view_name' ,'accountSettingView', '${userVo.action_code}', '${userVo.msg_value}');if('${viewVo.menu}' == 'category_setting111'){categoryListView();}">
  <!-- if('${viewVo.menu}' == 'category_setting'){cateogryParent(0, 'category1');}"> -->
<form name ="accountSettingView" method="post">
<%@ include file="../common/top.jsp" %>

<!--  
<div class="container">
    <div class="row">
        <nav class="navbar navbar-default navbar-fixed-top">

            <div class="navbar-header">
                <a href="" class="navbar-brand">Home</a>
            </div>

            <ul class="nav navbar-nav">
                <li class=""><a href=""  onc>menu1</a></li>
                <li class="active"><a href="">menu2</a></li>
                <li><a href="">menu3</a></li>
                <li><a href="">menu4</a></li>
            </ul>
        </nav>
    </div>
-->
 
    <!-- content -->
    <div>

        <div class="col-md-3">
            <div class="panel panel-default">
                <!-- <div class="panel-body"> -->
				<div class="panel-heading">
                   <%= msgOps.get("menu_" + langVo.getLang_type())%>
                </div>
                <ul class="nav nav-pills nav-stacked">
                	${viewVo.menu_view}
                </ul>
            </div>
        </div>

        <!-- main -->
        <div class="col-md-9">
            <!-- apply custom style -->
            <div class="page-header" style="margin-top:-30px;padding-bottom:0px;">
               ${viewVo.menu}
    
               <s:if test='%{viewVo.menu == "profile"}'>
               <h1><small><%= msgOps.get("menu_" + langVo.getLang_type())%></small></h1>
					<%@ include file="accountsetting/profile_setting.jsp" %>
               </s:if>
               
               <s:if test='%{viewVo.menu == "change_pwd"}'>
               		<h1><small><%= msgOps.get("menu_" + langVo.getLang_type())%></small></h1>
					<%@ include file="accountsetting/change_pwd.jsp" %>
               </s:if>
               
               <s:if test='%{viewVo.menu == "category_setting"}'>
					<h1><small><%= msgOps.get("menu_" + langVo.getLang_type())%></small></h1>
					<%@ include file="accountsetting/category_setting.jsp" %>
               </s:if>
             
            	
            </div>
        </div>
    </div>

    
 </form>
</body>
</html>
