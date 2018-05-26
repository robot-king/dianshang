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
<script type="text/javascript" src="js/admin/message_list.js?t=<%=new Date().getTime()%>"></script>

</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>

	<div id="tool_message">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
	</div>

	<div id="message_dialog" class="easyui-dialog" style="width: 100%; max-width: 600px; padding: 30px 60px;" buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:false">
			<div style="margin-bottom: 20px">
				<label class="label-top">标题:</label>
				<input class="easyui-textbox" data-options="prompt:'输入标题',required:true" name="title" id="title" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">内容:</label>
				<input class="easyui-textbox" data-options="prompt:'输入内容',required:true,multiline:true" name="content" id="content" style="width: 100%;height: 80px;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">类型:</label>
				<select id="_type" class="easyui-combobox" data-options="prompt:'选择类型',required:true,editable:false" name="type" style="width: 100%;">
					<option value="1">促销</option>
					<option value="2">公告</option>
				</select>
			</div>
		</form>

	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>

</body>
</html>

