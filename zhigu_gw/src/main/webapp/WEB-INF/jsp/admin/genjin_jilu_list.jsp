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
<script type="text/javascript" src="js/admin/genjin_jilu_list.js?t=<%=new Date().getTime() %>"></script>
<script type="text/javascript">
var danziId = '${danziId}';
</script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>
	
	<div id="tool_genjin_jilu">
		<!-- <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a> -->
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		<label>单子ID&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入单子ID'" name="danziId" id="s_danziId" style="width: 120px;">&nbsp;&nbsp;
        <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="obj.search();">查询</a>
        <a href="javascript:;" class="easyui-linkbutton" onclick="obj.searchClear();">清空</a>
	</div>
	
	<div id="genjin_jilu_dialog" class="easyui-dialog" style="width:100%;height: 600px;max-width:600px;padding:30px 60px;"
		 buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
			<form id="fm"  class="easyui-form" method="post" data-options="novalidate:false">
			    	<div style="margin-bottom:20px">
			            <label class="label-top">单子ID:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入单子ID',required:true" name="danziId" id="danziId" style="width:100%;">
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">描述:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入描述',required:true" name="des" id="des" style="width:100%;">
			        </div>
		    </form>
		    
	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>
	
</body>
</html>

