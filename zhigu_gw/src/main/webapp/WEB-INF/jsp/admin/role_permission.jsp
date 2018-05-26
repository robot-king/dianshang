<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commonTag.jsp" %>
<%@ include file="../commonJs.jsp"%>
<head>
<TITLE></TITLE>
<base href="${baseurl }">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/admin/role_permission_list.js"></script>
<script type="text/javascript" src="js/plugin/jquery.pin.js"></script>
<style>
<%--浮动层例子--%>
<%--     .main{--%>
<%--         width: 230px;--%>
<%--         height: 1200px;--%>
<%--         background: red;--%>
<%--         position: relative;--%>
<%--     }--%>
<%--     .container{--%>
<%--         width: 230px;--%>
<%--         height: 230px;--%>
<%--         background: #ffc;--%>
<%--         padding:20px;--%>
<%--         position: fixed;--%>
<%--         box-shadow: 5px 5px 7px rgba(33,33,33,.7);--%>
<%--         -webkit-transform: rotate(-6deg);--%>
<%--         -moz-transform: rotate(-6deg);--%>
<%--         -ms-transform: rotate(-6deg);--%>
<%--         transform: rotate(-6deg);--%>
<%--     }--%>
     
<%--    <div class="main">--%>
<%--     <div class="container">--%>
<%--          我们来看看这个有没有被钉住哦？！--%>
<%--     </div>        --%>
<%--	</div>--%>
</style>
</head>

<body class="easyui-layout" >
	<div data-options="region:'west',border:true,split:true" title="角色"
		style="width: 50%;">
		<table id="dg" data-options="fit:true,border:false"></table>
	</div>
	
	<div data-options="region:'center',border:true,split:true" title="角色授权">

		<div class="right-container" style="display: none;">
			<div class="pinned">
				<a href="javascript:;" class="easyui-linkbutton" data-options="width:150,height:30" onclick="obj.getCheckedNodeNewId();">保存</a>
			</div>	
		</div>
			
		<ul id="dg_user" data-options="fit:true,border:false"></ul>
	</div>
	
</body>
</html>
