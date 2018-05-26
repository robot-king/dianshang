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
<script type="text/javascript" src="js/admin/seo_tag_list.js?t=<%=new Date().getTime() %>"></script>

</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>
	
	<div id="tool_seo_tag">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		<!--
		<br/>
		<label>id&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入id'" name="id" id="s_id" style="width: 120px;">&nbsp;&nbsp;
		<label>标题&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入标题'" name="title" id="s_title" style="width: 120px;">&nbsp;&nbsp;
		<label>创建时间&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入创建时间'" name="createTime" id="s_createTime" style="width: 120px;">&nbsp;&nbsp;
        <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="obj.search();">查询</a>
        <a href="javascript:;" class="easyui-linkbutton" onclick="obj.searchClear();">清空</a>
        -->
	</div>
	
	<div id="seo_tag_dialog" class="easyui-dialog" style="width:100%;height: 600px;max-width:600px;padding:30px 60px;"
		 buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
			<form id="fm"  class="easyui-form" method="post" data-options="novalidate:false">
			    	<div style="margin-bottom:20px">
			            <label class="label-top">标题:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入标题',required:true" name="title" id="title" style="width:100%;">
			        </div>
		    </form>
		    
	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>
	
</body>
</html>

