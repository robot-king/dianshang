<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commonTag.jsp"%>
<%@ include file="common.jsp"%>
<%
	request.setAttribute("decorator", "none");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<head>
<TITLE></TITLE>
<base href="${baseurl }">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/admin/danzi_list.js?t=<%=new Date().getTime()%>"></script>
<script type="text/javascript">
	var action = '${action}';
	var kehu_id = '${kehu_id}';
</script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>

	<div id="tool_danzi">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
		&nbsp;&nbsp;&nbsp;&nbsp;

		<label>客户&nbsp;&nbsp;:&nbsp;&nbsp;</label>
		<input id="s_kehuId" style="width: 120px;">
		&nbsp;&nbsp;
		<label>单子状态&nbsp;&nbsp;:&nbsp;&nbsp;</label>
		<select class="easyui-combobox" data-options="prompt:'选择类型',required:false,editable:false" id="s_danziStatus" style="width: 120px;">
			<option value=""></option>
			<option value="1">验证客户</option>
			<option value="2">需求确定</option>
			<option value="3">方案报价</option>
			<option value="4">谈判审核</option>
			<option value="5">赢单</option>
			<option value="6">输单</option>
			<option value="7">托管</option>
			<option value="8">正在开发</option>
			<option value="9">开发完成</option>
			<option value="10">改bug</option>
			<option value="11">付款</option>
		</select>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="obj.search();">查询</a>
		<a href="javascript:;" class="easyui-linkbutton" onclick="obj.searchClear();">清空</a>
	</div>

	<div id="danzi_dialog" class="easyui-dialog" style="width: 100%; height: 600px; max-width: 600px; padding: 30px 60px;" buttons="#dlg-buttons"
		data-options="closed:true,modal : true,cache : false">
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:false">
			<div style="margin-bottom: 20px">
				<label class="label-top">客户:</label>
				<input name="kehuId" id="kehuId" style="width: 100%;">
				<input name="kehuName" id="kehuName" type="hidden" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">需求描述:</label>
				<input class="easyui-textbox" data-options="prompt:'输入需求描述',required:true,multiline:true" name="des" id="des" style="width: 100%; height: 100px;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">来源:</label>
				<input name="laiyuan" id="laiyuan" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">意向强弱:</label>
				<select class="easyui-combobox" data-options="prompt:'选择类型',required:true,editable:false" name="yixiangStatus" id="yixiangStatus" style="width: 100%;">
					<option value="1">强</option>
					<option value="2">一般</option>
					<option value="3">弱</option>
				</select>
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">单子状态:</label>
				<select class="easyui-combobox" data-options="prompt:'选择类型',required:true,editable:false" name="danziStatus" id="danziStatus" style="width: 100%;">
					<option value="1">验证客户</option>
					<option value="2">需求确定</option>
					<option value="3">方案报价</option>
					<option value="4">谈判审核</option>
					<option value="5">赢单</option>
					<option value="6">输单</option>
					<option value="7">托管</option>
					<option value="8">正在开发</option>
					<option value="9">开发完成</option>
					<option value="10">改bug</option>
					<option value="11">付款</option>
				</select>
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">工作开始时间:</label>
				<input class="easyui-datetimebox" data-options="prompt:'输入工作开始时间',required:true" name="startTime" id="startTime" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">工作结束时间:</label>
				<input class="easyui-datetimebox" data-options="prompt:'输入工作结束时间',required:true" name="endTime" id="endTime" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">报价:</label>
				<input class="easyui-textbox" data-options="prompt:'输入报价',required:false" name="baojiaMoney" id="baojiaMoney" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">成交金额:</label>
				<input class="easyui-textbox" data-options="prompt:'输入成交金额',required:false" name="chengjiaoMoney" id="chengjiaoMoney" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">实际工作结束时间:</label>
				<input class="easyui-datetimebox" data-options="prompt:'输入实际工作结束时间',required:false" name="shijiEndTime" id="shijiEndTime" style="width: 100%;">
			</div>
		</form>

	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>


	<div id="genjin_jilu_dialog" class="easyui-dialog" style="width: 100%; height: 600px; max-width: 600px; padding: 30px 60px;" buttons="#dlg-buttons2"
		data-options="closed:true,modal : true,cache : false">
		<form id="fm2" class="easyui-form" method="post" data-options="novalidate:false">
			<div style="margin-bottom: 20px">
				<label class="label-top">描述:</label>
				<input type="hidden" name="danziId" id="danziId" style="width: 100%;">
				<input class="easyui-textbox" data-options="prompt:'输入描述',required:true,multiline:true" name="des" id="des" style="width: 100%; height: 100px;">
			</div>
		</form>

	</div>
	<div id="dlg-buttons2">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save2();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close2();">取消</a>
	</div>

</body>
</html>

