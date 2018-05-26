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

<script type="text/javascript" src="plugin/photoClip/iscroll-zoom-min.js"></script>
<script type="text/javascript" src="plugin/photoClip/hammer.min.js"></script>
<script type="text/javascript" src="plugin/photoClip/lrz.all.bundle.js"></script>
<script type="text/javascript" src="plugin/photoClip/PhotoClip.js"></script>

<script type="text/javascript" src="js/admin/lun_bo_tu_list.js?t=<%=new Date().getTime()%>"></script>
<style>
#clipArea {
	width: 1000px;
	height: 100%;
}
#view {
	margin: 0 auto;
	width: 428px;
	height: 144px;
}
</style>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<table id="dg" data-options="fit:true,border:false"></table>

	<div id="tool_lun_bo_tu">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
		&nbsp;&nbsp;&nbsp;&nbsp;

		<!--
		<br/>
		<label>id&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入id'" name="id" id="s_id" style="width: 120px;">&nbsp;&nbsp;
		<label>员工id&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入员工id'" name="employeeId" id="s_employeeId" style="width: 120px;">&nbsp;&nbsp;
		<label>轮播图地址&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入轮播图地址'" name="bannerUrl" id="s_bannerUrl" style="width: 120px;">&nbsp;&nbsp;
		<label>{"1":"启用","2":"禁用","3":"删除"}&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入{"1":"启用","2":"禁用","3":"删除"}'" name="status" id="s_status" style="width: 120px;">&nbsp;&nbsp;
		<label>创建时间&nbsp;&nbsp;:&nbsp;&nbsp;</label><input class="easyui-textbox" data-options="prompt:'输入创建时间'" name="createTime" id="s_createTime" style="width: 120px;">&nbsp;&nbsp;
        <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="obj.search();">查询</a>
        <a href="javascript:;" class="easyui-linkbutton" onclick="obj.searchClear();">清空</a>
        -->
	</div>

	
	<div id="lun_bo_tu_dialog" class="easyui-dialog" style="width: 100%; height: 600px; max-width: 600px; padding: 30px 60px;" buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:false">
			<div style="margin-bottom: 20px">
				<label class="label-top">轮播图地址:</label>
				<input class="easyui-filebox" data-options="prompt:'选择文件',buttonText:'&nbsp;选&nbsp;择&nbsp;',required:true,editable:false" name="bannerUrlFile" id="bannerUrlFile" style="width: 100%;">
				<input type="hidden" name="bannerBase64" id="bannerBase64">
			</div>
			<div style="margin-bottom: 20px" id="view"></div>
			<div style="margin-bottom: 20px">
				<label class="label-top">目标链接:</label>
				<input class="easyui-textbox" data-options="prompt:'输入目标链接,例如：http://www.mozji.com/shop/shop_detail.action?id=2',required:true" name="targetUrl" id="targetUrl" style="width: 100%;">
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">状态:</label>
				<select id="status" class="easyui-combobox" data-options="prompt:'选择类型',required:true,editable:false" name="status" style="width: 100%;">
					<option value="1">启用</option>
					<option value="2">禁用</option>
				</select>
			</div>
		</form>

	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
	</div>

	

</body>



<!-- <div id="clipArea"></div> -->
<div id="upload_tu_dialog" class="easyui-dialog" style="width: 100%; height: 100%; max-width: 1000px;overflow: hidden;" buttons="#dlg-buttons2" data-options="closed:true,modal : true,cache : false">
		<div id="clipArea"></div>
	</div>
	<div id="dlg-buttons2">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" id="clipBtn">截取</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close2();">取消</a>
	</div>
</html>

