<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commonTag.jsp" %>
<%@ include file="common.jsp"%>

<head>
<TITLE></TITLE>
<base href="${baseurl }">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/admin/kehu_list.js?t=<%=new Date().getTime() %>"></script>

</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>
	
	<div id="tool_kehu">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<br>
		<label>姓名&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入姓名'" name="xingming" id="s_xingming" style="width: 120px;">&nbsp;&nbsp;
		<label>手机&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入手机'" name="shouji" id="s_shouji" style="width: 120px;">&nbsp;&nbsp;
		<label>QQ&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入QQ'" name="qq" id="s_qq" style="width: 120px;">&nbsp;&nbsp;
		<br>
		<div style="padding: 5px;"></div>
		<label>微信&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入微信'" name="weixin" id="s_weixin" style="width: 120px;">&nbsp;&nbsp;
		<label>地区&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入地区'" name="diqu" id="s_diqu" style="width: 120px;">&nbsp;&nbsp;
		<label>备注&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入备注'" name="beizhu" id="s_beizhu" style="width: 120px;">&nbsp;&nbsp;
        <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="obj.search();">查询</a>
        <a href="javascript:;" class="easyui-linkbutton" onclick="obj.searchClear();">清空</a>
	</div>
	
	<div id="kehu_dialog" class="easyui-dialog" style="width:100%;height: 600px;max-width:600px;padding:30px 60px;"
		 buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
			<form id="fm"  class="easyui-form" method="post" data-options="novalidate:false">
			    	<div style="margin-bottom:20px">
			            <label class="label-top">姓名:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入姓名',required:true" name="xingming" id="xingming" style="width:100%;">
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">手机:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入手机',required:true" name="shouji" id="shouji" style="width:100%;">
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">QQ:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入QQ',required:false" name="qq" id="qq" style="width:100%;">
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">微信:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入微信',required:false" name="weixin" id="weixin" style="width:100%;">
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">地区:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入地区',required:false" name="diqu" id="diqu" style="width:100%;">
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">备注:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入备注',required:false,multiline:true" name="beizhu" id="beizhu" style="width:100%;height: 100px;">
			        </div>
		    </form>
		    
	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>
	
</body>
</html>

