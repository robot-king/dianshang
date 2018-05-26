<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="commonTag.jsp"%>
<%@ include file="common.jsp"%>

<head>
<base href="${baseurl }">
<title>后台管理页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="plugin/layer/layer.js"></script>
<script type="text/javascript" src="js/admin/update_pwd_page.js?t=<%= new Date().getTime() %>"></script>
<%-- ?t=<%= new Date().getTime() %> --%>
<script type="text/javascript">
var _nickName = '${admin_3306.nickName}';
var _iconUrl = '${admin_3306.iconUrl}';
</script>
</head>

<body>

	<!-- 修改用户信息 -->
	<div id="employee_dialog" class="easyui-dialog" style="width: 100%; max-width: 600px; padding: 30px 60px;" buttons="#dlg-buttons" data-options="modal : true,cache : false,title:'修改密码'">
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:false">
			<input type="hidden" name="id" value="${admin_3306.id }"/>
			<div style="margin-bottom: 20px">
				<label class="label-top">原密码:</label>
				<input class="easyui-textbox" data-options="prompt:'输入原密码',required:true" type="password" name="oldPwd" id="oldPwd" style="width: 100%;"></input>
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">新密码:</label>
				<input class="easyui-textbox" data-options="prompt:'输入新密码',required:true" type="password" name="newPwd" id="newPwd" style="width: 100%;">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeEmployee();">取消</a>
	</div>
</body>