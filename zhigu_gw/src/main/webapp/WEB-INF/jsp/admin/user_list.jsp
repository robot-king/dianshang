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
<meta http-equiv="expires" content="0">

<script type="text/javascript" src="js/admin/user_list.js?t=<%=new Date().getTime()%>"></script>

</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>

	<div id="tool_user">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.fengjin();">封禁</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.jiefeng();">解封</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		<br/>
		<label>昵称&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入昵称'" name="nickName" id="s_nickName" style="width: 120px;">&nbsp;&nbsp;
		<label>真实姓名&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入真实姓名'" name="name" id="s_name" style="width: 120px;">&nbsp;&nbsp;
		<label>手机号&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入手机号'" name="mobile" id="s_mobile" style="width: 120px;">&nbsp;&nbsp;
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="obj.search();">查询</a>
        <a href="javascript:;" class="easyui-linkbutton" onclick="obj.searchClear();">清空</a>
	</div>


	<div id="user_dialog" class="easyui-dialog" style="width: 100%; max-width: 600px; padding: 30px 60px;" buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:false">
			<div style="margin-bottom: 20px">
				<label class="label-top">用户名:</label>
				<input class="easyui-textbox" data-options="prompt:'输入昵称',required:true" name="nickName" id="nickName" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">姓名:</label>
				<input class="easyui-textbox" data-options="prompt:'输入姓名',required:true" name="name" id="name" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">手机号:</label>
				<input class="easyui-textbox" data-options="prompt:'输入手机号',required:true" name="identifier" id="identifier" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">签名:</label>
				<input class="easyui-textbox" data-options="prompt:'输入签名',required:false,multiline:true" name="signature" id="signature" style="width: 100%; height: 80px;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">资源:</label>
				<input class="easyui-textbox" data-options="prompt:'输入资源',required:false,multiline:true" name="resource" id="resource" style="width: 100%; height: 80px;">
			</div>
		</form>

	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>


	<div id="user_dialog2" class="easyui-dialog" style="width: 100%; max-width: 600px; padding: 30px 60px;" buttons="#dlg-buttons2" data-options="closed:true,modal : true,cache : false">
		<form id="fm2" class="easyui-form" method="post" data-options="novalidate:false">
			<table cellpadding="5">
				<!-- <tr>
					<td>输入密码:</td>
					<td><input type="hidden" name="userId" id="userId"> <input class="easyui-textbox" type="password" name="credential" id="credential" data-options="required:true"></input></td>
				</tr> -->
				<div style="margin-bottom: 20px">
					<label class="label-top">输入密码:</label>
					<input type="hidden" name="userId" id="userId">
					<input class="easyui-textbox" data-options="prompt:'输入密码',required:true" type="password" name="credential" style="width: 100%;">
				</div>
			</table>
		</form>

	</div>
	<div id="dlg-buttons2">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save2();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close2();">取消</a>
	</div>

</body>
</html>

