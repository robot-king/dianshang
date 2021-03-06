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
<script type="text/javascript" src="js/admin/menu_list.js?t=<%=new Date().getTime()%>"></script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>
	<div id="tool_menu">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
	</div>
	<div id="menu_dialog" class="easyui-dialog" style="width: 100%; max-width: 600px; padding: 30px 60px;" buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:false">
			<div style="margin-bottom: 20px">
				<label class="label-top">编码:</label>
				<input class="easyui-textbox" data-options="prompt:'输入编码',required:true" name="code" id="code" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">名称:</label>
				<input class="easyui-textbox" data-options="prompt:'输入名称',required:true" name="name" id="name" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">地址:</label>
				<input class="easyui-textbox" data-options="prompt:'输入地址',required:true" name="url" id="url" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">排序数字:</label>
				<input class="easyui-textbox" data-options="prompt:'输入排序数字',required:false" name="sortNum" id="sortNum" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">父节点:</label>
				<input type="hidden" name="parentCode" id="parentCode"></input>
				<input class="easyui-textbox" type="text" data-options="readonly:true" id="parent_name" style="width: 100%;"></input>
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>
</body>
</html>
