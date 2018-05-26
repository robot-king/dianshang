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
<script type="text/javascript" src="js/admin/web_site_list.js?t=<%=new Date().getTime() %>"></script>

</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>
	
	<div id="tool_web_site">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<br/>
		<label>id&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入id'" name="id" id="s_id" style="width: 120px;">&nbsp;&nbsp;
		<label>标题&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入标题'" name="title" id="s_title" style="width: 120px;">&nbsp;&nbsp;
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="obj.search();">查询</a>
        <a href="javascript:;" class="easyui-linkbutton" onclick="obj.searchClear();">清空</a>
		
		<!--
		
		<label>缩略图地址&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入缩略图地址'" name="coverUrl" id="s_coverUrl" style="width: 120px;">&nbsp;&nbsp;
		<label>价格&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入价格'" name="price" id="s_price" style="width: 120px;">&nbsp;&nbsp;
		<label>类型{"1":"案例","2":"模板"}&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入类型{"1":"案例","2":"模板"}'" name="type" id="s_type" style="width: 120px;">&nbsp;&nbsp;
		<label>网站类型&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入网站类型'" name="webType" id="s_webType" style="width: 120px;">&nbsp;&nbsp;
		<label>创建时间&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入创建时间'" name="createTime" id="s_createTime" style="width: 120px;">&nbsp;&nbsp;
        
        -->
	</div>
	
	<div id="web_site_dialog" class="easyui-dialog" style="width:100%;height: 600px;max-width:600px;padding:30px 60px;"
		 buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
			<form id="fm"  class="easyui-form" method="post" data-options="novalidate:false" enctype="multipart/form-data">
			    	<div style="margin-bottom:20px">
			            <label class="label-top">标题:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入标题',required:true" name="title" id="title" style="width:100%;">
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">缩略图地址:</label>
			            <!-- <input class="easyui-textbox" data-options="prompt:'输入缩略图地址',required:true" name="coverUrl" id="coverUrl" style="width:100%;"> -->
			            <input class="easyui-filebox" type="text" name="coverUrlFile" id="coverUrlFile" data-options="onChange:changePic,prompt:'选择文件',buttonText:'&nbsp;选&nbsp;择&nbsp;',required:false,editable:false" style="width: 100%;"></input>

						<div id="img" style="width: 300px; min-height: 10px;">
							<img id="img2" style="height: 60px; width: 60px;">
						</div>
			        </div>
			        <div style="margin-bottom:20px">
			            <label class="label-top">上传模板:</label>
			            <input class="easyui-filebox" type="text" name="templateFile" id="templateFile" data-options="onChange:changePic,prompt:'选择文件',buttonText:'&nbsp;选&nbsp;择&nbsp;',required:false,editable:false" style="width: 100%;"></input>
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">价格:</label>
			            <input class="easyui-numberbox" data-options="prompt:'输入价格',required:false" name="price" id="price" style="width:100%;">
			        </div>
			        <div style="margin-bottom:20px">
			            <label class="label-top">非本网访问地址:</label>
			            <input class="easyui-textbox" data-options="prompt:'输入非本网访问地址',required:false" name="feiAccessUrl" id="feiAccessUrl" style="width:100%;">
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">类型:</label>
			            <select id="type" class="easyui-combobox" name="type" data-options="prompt:'选择状态',required:true,editable:false" style="width: 100%;">
							<option value="1">案例</option>
							<option value="2">模板</option>
						</select>
			        </div>
			    	<div style="margin-bottom:20px">
			            <label class="label-top">网站类型:</label>
			            <input data-options="prompt:'输入网站类型',required:false" name="webType" id="webType" style="width:100%;">
			        </div>
		    </form>
		    
	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>
	
</body>
</html>

