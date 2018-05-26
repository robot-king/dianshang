<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commonTag.jsp" %>
<%@ include file="../commonJs.jsp"%>
<head>
<TITLE></TITLE>
<base href="${baseurl }">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/admin/role_manage_list.js"></script>

</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'west',border:true,split:true" title="角色"
		style="width: 50%;">
		<table id="dg" data-options="fit:true,border:false"></table>
	
		<div id='tool_role_manage'>
			<div>
			
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.add();">新增</a>
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.edit();">修改</a>
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.remove();">删除</a>
				
			</div>
		</div>
		
		<div id="role_manage_dialog" class="easyui-dialog" style="width:500px;height:auto;"
		 buttons="#dlg-buttons" data-options="closed:true,modal : true,cache : false">
			<form id="fm"  class="easyui-form" method="post">
		    	<table cellpadding="5">
		    		<tr>
		    			<td>角色名称:</td>
		    			<td><input class="easyui-textbox" type="text" name="name" id="role_manage_name" data-options="required:true" ></input></td>
		    		</tr>
		    	</table>
		    </form>
		    
		</div>
		<div id="dlg-buttons">
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.save();">保存</a>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.close();">取消</a>
		</div>
	</div>
	
	<div data-options="region:'center',border:true,split:true" title="角色用户">
		<table id="dg_user" data-options="fit:true,border:false"></table>
	
		<div id='tool_role_user' style="display: none;">
			<div>
			
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="obj.addRu();">新增</a>
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="obj.editRu();">修改</a>
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="obj.removeRu();">删除</a>
				
			</div>
		</div>
		
		<div id="role_user_dialog" class="easyui-dialog" style="width:500px;height:auto;"
		 buttons="#dlg-buttons2" data-options="closed:true,modal : true,cache : false">
			<form id="fm2"  class="easyui-form" method="post">
		    	<table cellpadding="5">
		    		<tr>
		    			<td>真实姓名:</td>
		    			<td>
		    				<input class="easyui-textbox" type="text" name="realname" id="realname" data-options="required:true,editable:false,height:25" ></input>
							<input type="hidden" name="sUserId" id="user_id" value=""></td>
							<input type="hidden" name="sRoleId" id="role_id" value=""></td>
		    			</td>
		    			<td>
		    				<a href="javascript:chooseUser();" class="easyui-linkbutton" data-options="iconCls:'icon-add',width:100,height:25">选择用户</a>
		    			</td>
		    		</tr>
		    	</table>
		    </form>
			    
		</div>
		<div id="dlg-buttons2">
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="obj.saveRu();">保存</a>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="obj.closeRu();">取消</a>
		</div>
		
		<div id="add_ru_dialog" class="easyui-dialog"
			style="width: 40%; height: 50%; padding: 2px 2px" closed="true"
			 modal="true">
			 	<iframe scrolling="auto" id='add_iframe' frameborder="0"  src="" style="width:100%;height:99%;"></iframe>
		</div>
	</div>
	
	
</body>
</html>
