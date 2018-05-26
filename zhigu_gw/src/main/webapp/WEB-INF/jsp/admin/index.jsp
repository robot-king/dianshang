<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="commonTag.jsp"%>
<%@ include file="common.jsp"%>

<!DOCTYPE html>
<html>
<head>
<base href="${baseurl }">
<title>后台管理页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript" src="${baseurl }js/admin/index.js?t=<%=new Date().getTime() %>"></script>
</head>

<body>
	<div class="easyui-layout" data-options="fit:true,border:false" style="width: 100%; height: 100%;">
		<!-- 头部 -->
		<div data-options="region:'north',border:false,bodyCls:'theme-header-layout'">
			<%-- <a href="${baseurl }employee/exit.do">退出</a> --%>
			<div class="theme-logo">智谷科技工作室后台管理系统</div>
			<div class="theme-navigate">
				<div class="right">
					<a href="${baseurl }employee/exit.do" class="easyui-linkbutton">退出</a>
				</div>
			</div>
		</div>

		<!--开始左侧菜单-->
		<div data-options="region:'west',border:false,bodyCls:'theme-left-layout'" style="width: 200px;">
			<!--正常菜单-->
			<div class="theme-left-normal">

				<!--start class="easyui-layout"-->
				<div class="easyui-layout" data-options="border:false,fit:true">
					<!--start region:'north'-->
					<div data-options="region:'north',border:false" style="height: 100px;">
						<!--start theme-left-user-panel-->
						<div class="theme-left-user-panel">
							<dl>
								<dt>
									<img src="${admin_3306.iconUrl==null?'plugin/easyui/themes/insdep/images/portrait86x86.png':admin_3306.iconUrl }" width="43" height="43" onclick="editEmployee()">
								</dt>
								<dd>
									<b class="badge-prompt">${admin_3306.nickName==null?'匿名':admin_3306.nickName } <!-- <i class="badge color-important">10</i> --></b> <!-- <span>examples</span> -->
									<p>
										安全等级：<i class="text-success">高</i>
									</p>
								</dd>

							</dl>
						</div>
						<!--end theme-left-user-panel-->
					</div>
					<!--end region:'north'-->

					<!--start region:'center'-->
					<div data-options="region:'center',border:false">
						<div id="nav" class="easyui-accordion" data-options="border:false,fit:true"></div>
					</div>
					<!--end region:'center'-->
				</div>
				<!--end class="easyui-layout"-->

			</div>

		</div>
		<!--结束左侧菜单-->

		<!-- 右侧主体 -->
		<div data-options="region:'center'" id="center">
			<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true"></div>
		</div>
	</div>
	
</body>
</html>
