<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commonTag.jsp"%>
<%@ include file="common.jsp"%>
<head>
<TITLE></TITLE>
<base href="${baseurl }">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/admin/employee_list.js?t=<%= new Date().getTime() %>"></script>

</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>

	<div id="tool_employee">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
	</div>

	<div id="employee_dialog" class="easyui-dialog" style="width:100%;max-width:600px;padding:30px 60px;" buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:false">
			<div style="margin-bottom: 20px">
				<label class="label-top">昵称:</label>
				<input class="easyui-textbox" data-options="prompt:'输入昵称',required:true" name="nickName" id="nickName" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">密码:</label>
				<input class="easyui-textbox" data-options="prompt:'输入密码',required:true" type="password" name="pwd" id="pwd" style="width: 100%;">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>

	<div id="employee_dialog2" class="easyui-dialog" style="width: 800px; height: 500px;" buttons="#dlg-buttons2" data-options="closed:true,modal : true,cache : false">

		<table id="dg2" data-options="fit:true,border:false"></table>
	</div>
	<div id="dlg-buttons2">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="dispatchSave();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dispatchClose();">取消</a>
	</div>

	<div id="employee_dialog3" class="easyui-dialog" style="width: 800px; height: 500px;" buttons="#dlg-buttons3" data-options="closed:true,modal : true,cache : false">

		<table id="dg3" data-options="fit:true,border:false"></table>
	</div>

	<div id="dlg-buttons3">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteMemnu();">删除权限</a>
	</div>

</body>
</html>

