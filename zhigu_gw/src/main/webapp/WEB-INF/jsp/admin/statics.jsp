<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="commonTag.jsp"%>
<%@ include file="common.jsp"%>

<head>
<base href="${baseurl }">
<title>后台管理页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="plugin/utils.js"></script>
<script type="text/javascript" src="js/admin/statics.js?t=<%= new Date().getTime() %>"></script>
<style>
.gauge {
	width: 130px;
	height: 130px;
}
</style>
<script type="text/javascript">
var _nickName = '${admin_3306.nickName}';
var _iconUrl = '${admin_3306.iconUrl}';
</script>
</head>

<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" style="height: 40%">
			<div class="theme-user-info-panel">
				<div class="left">
					<img src="${admin_3306.iconUrl==null?'plugin/easyui/themes/insdep/images/portrait86x86.png':admin_3306.iconUrl }" width="86" height="86" onclick="editEmployee();">
				</div>
				<%-- <div class="right">

					<ul>
						<li>
							<div id="gg1" class="gauge" data-value="${record.userCount }"></div>
							<span>总用户数</span>
						</li>
						<li>
							<div id="gg2" class="gauge" data-value="${record.shopCount }"></div>
							<span>总商品数</span>
						</li>
						<li>
							<div id="gg3" class="gauge" data-value="${record.boxCount }"></div>
							<span>宝箱中作品总数</span>
						</li>
						<li>
							<div id="gg4" class="gauge" data-value="${record.tradeCount }"></div>
							<span>总交易数</span>
						</li>
					</ul>
				</div> --%>
				<div class="center">
					<h1>
						${admin_3306.nickName==null?'匿名':admin_3306.nickName }
						<!-- <span class="color-warning badge">未认证</span> -->
					</h1>
					<h2>管理员</h2>
					<!-- <dl>
						<dt>examples@insdep.com</dt>
						<dd>13000000000</dd>
					</dl> -->
				</div>

			</div>
		</div>
		<div data-options="region:'center',border:false">

			<div id="user-info-more" class="easyui-tabs theme-tab-blue-line theme-tab-body-unborder" data-options="tools:'#tab-tools',fit:true">

				<div title="统计图" id="charts-layout">
					<!--统计开始-->

					<div id="charts" style="height: 400px;"></div>

					<!--统计结束-->
				</div>
				<!-- <div title="帮助" data-options="closable:true" style="padding: 10px">This is the help content.</div> -->
			</div>



		</div>
		<!-- <div id="tab-tools">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-set'"></a>
		</div> -->
		<!--
            <span class="icons" style="font-size:28px;">&#xe601;</span>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'">测试</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-set'"></a>
            -->
	</div>

	<!-- 修改用户信息 -->
	<div id="employee_dialog" class="easyui-dialog" style="width: 100%; max-width: 600px; padding: 30px 60px;" buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:false" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${admin_3306.id }"/>
			<div style="margin-bottom: 20px">
				<label class="label-top">昵称:</label>
				<input class="easyui-textbox" data-options="prompt:'输入昵称',required:true" name="nickName" id="nickName" style="width: 100%;"></input>
			</div>
			<div style="margin-bottom: 20px">
				<label class="label-top">头像:</label>
				<input class="easyui-filebox" data-options="onChange:changePic,prompt:'选择头像',buttonText:'&nbsp;选&nbsp;择&nbsp;头&nbsp;像',required:true" name="iconUrlFile" id="iconUrlFile" style="width: 100%;">

				<div id="img" style="width: 300px; min-height: 10px;">
					<img id="img2" style="width:${admin_3306.iconUrl==null?'0':'300px' };height:${admin_3306.iconUrl==null?'0':'300px' } " src="${admin_3306.iconUrl==null?'':admin_3306.iconUrl }">
				</div>
			</div>
			<!-- <div style="margin-bottom: 20px">
				<label class="label-top">密码:</label>
				<input class="easyui-textbox" data-options="prompt:'输入密码',required:true" name="pwd" id="pwd" style="width: 100%;">
			</div> -->
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="save();">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeEmployee();">取消</a>
	</div>
</body>