var obj;
var url;
var ids = [];
var sub_status = true;// 防止重复提交标识

$(function() {
	obj = {
		search : function() {
			$('#dg').datagrid('load', {
				id : $.trim($('input[name="id"]').val()),
				nickName : $.trim($('input[name="nickName"]').val()),
				iconUrl : $.trim($('input[name="iconUrl"]').val()),
				createTime : $.trim($('input[name="createTime"]').val()),
				status : $.trim($('input[name="status"]').val()),
				loginTime : $.trim($('input[name="loginTime"]').val()),
				pwd : $.trim($('input[name="pwd"]').val())
			});
		},
		searchClear : function() {
			$("#dg").datagrid("load", {}); // 重新加载数据，无填写数据，向后台传递值则为空
			$("input[id^='s_']").each(function() {
				var _id = $(this).attr("id");
				$('#' + _id).textbox('setValue', '');
			}); 
		},
		add : function() {
			// 防止重复验证默认通过
			sub_status = true;
			// 默认提交之前不验证
			$('#fm').form({
				novalidate : true
			});
			$('#employee_dialog').dialog('open').dialog('setTitle', '新增');
			$('#employee_dialog').form('clear');
			url = baseurl + 'employee/save.do';
		},
		edit : function() {
			ids = [];
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				var rows = $('#dg').datagrid('getSelections');
				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				if (ids.length == 1) {
					console.log(JSON.stringify(row));
					// 防止重复验证默认通过
					sub_status = true;
					// 默认提交之前不验证
					$('#fm').form({
						novalidate : true
					});
					$('#employee_dialog').dialog('open').dialog('setTitle', '编辑');
					$('#fm').form('load', row);
					$('#pwd').textbox('setValue', "");
					$('#pwd').textbox('setText', "");
					url = baseurl + 'employee/update.do?id=' + row.id;
				} else {
					$.messager.show({
						title : '提示',
						msg : '只能选择一个'
					});
				}
			} else {
				$.messager.show({
					title : '提示',
					msg : '请选择一个'
				});
			}
		},
		remove : function() {
			ids = [];
			var rows = $('#dg').datagrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			if (ids.length > 0) {
				$.messager.confirm('确定', '你确定删除这些数据吗？', function(r) {
					if (r) {
						$.post(baseurl + 'employee/delete.do', {
							id : ids.toString()
						}, function(result) {
							if (result.success) {
								$("#dg").datagrid("clearSelections");
								$('#dg').datagrid('reload');
							} else {
								$.messager.show({ // show error message
									title : '提示',
									msg : result.msg
								});
							}
						}, 'json');
					}
				});
			} else {
				$.messager.show({
					title : '提示',
					msg : '请至少选择一个'
				});
			}
		},
		save : function() {
			if (sub_status) {
				$('#fm').form('submit', {
					url : url,
					onSubmit : function() {
						var status = false;
						if ($(this).form('enableValidation').form('validate')) {
							status = true;
							sub_status = false;
						}
						return status;
					},
					success : function(res) {
						var result = eval('(' + res + ')');
						if (result.success) {
							$.messager.show({
								title : '提示',
								msg : '操作成功',
								timeout : 5000,
								showType : 'slide'
							});
							$('#employee_dialog').dialog('close'); // close the
							// dialog
							$('#dg').datagrid('reload');
						} else {
							$.messager.show({
								title : '提示',
								msg : result.msg
							});
							sub_status = true;
						}
					}
				});
			}
		},
		close : function() {
			$('#employee_dialog').dialog('close');
		}
	};

	$('#dg').datagrid({
		url : baseurl + 'employee/queryList.do',
		loadMsg : '加载中……',
		toolbar : '#tool_employee',
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 100
		}, {
			field : 'nickName',
			title : '昵称',
			width : 100
		},
		// {
		// field : 'iconUrl',
		// title : '头像',
		// width : 100
		// },
		{
			field : 'loginTime',
			title : '登录时间',
			width : 100
		}, {
			field : 'isManager',
			title : '是否管理员',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				if (value == "1") {
					str = "管理员";
				} else {
					str = "员工";
				}
				return str;
			}
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 180
		}, {
			field : 'click2',
			title : '操作',
			width : 240,
			formatter : function(value, row) {
				var buttonStr = "";
				buttonStr += "<a class='dispatch_per' href='javascript:dispatchMemnu(\"" + row.id + "\");'></a>";
				buttonStr += "&nbsp;&nbsp;<a class='view_per' href='javascript:viewMemnu(\"" + row.id + "\");'></a>";
				return buttonStr;
			}
		} ] ],
		pagination : true,
		onLoadSuccess : function(data) {
			$(".dispatch_per").linkbutton({
				text : '分配权限',
				plain : true
			});
			$(".view_per").linkbutton({
				text : '查看权限',
				plain : true
			});
		}
	});
});

var employeeId;
var menuIds = [];
function dispatchMemnu(id) {
	employeeId = id;
	$('#employee_dialog2').dialog('open').dialog('setTitle', '分配权限');

	$('#dg2').datagrid({
		url : baseurl + 'menu/queryList.do',
		loadMsg : '加载中……',
		toolbar : '#tool_menu',
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 100
		}, {
			field : 'code',
			title : '编码',
			width : 100
		}, {
			field : 'name',
			title : '名称',
			width : 100
		} ] ],
		pagination : true
	});
}

function dispatchSave() {
	menuIds = [];
	var rows = $('#dg2').datagrid('getSelections');
	for (var i = 0; i < rows.length; i++) {
		menuIds.push(rows[i].id);
	}
	if (menuIds.length > 0) {
		$.post(baseurl + 'employeeMenu/bindMenu.do', {
			ids : menuIds.toString(),
			employeeId : employeeId
		}, function(result) {

			if (result.success) {
				$.messager.show({
					title : '提示',
					msg : '操作成功',
					timeout : 5000,
					showType : 'slide'
				});
				dispatchClose();
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
			}
		}, 'json');
	} else {
		$.messager.show({
			title : '提示',
			msg : '请至少选择一个'
		});
	}
}

function dispatchClose() {
	$('#employee_dialog2').dialog('close');
	$('#employee_dialog3').dialog('close');
}

function viewMemnu(id) {
	employeeId = id;
	$('#employee_dialog3').dialog('open').dialog('setTitle', '分配权限');

	$('#dg3').datagrid({
		url : baseurl + 'menu/queryListByEmployeeId.do?employeeId=' + id,
		loadMsg : '加载中……',
		toolbar : '#tool_menu',
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 100
		}, {
			field : 'code',
			title : '编码',
			width : 100
		}, {
			field : 'name',
			title : '名称',
			width : 100
		} ] ],
		pagination : true
	});
}

// 删除菜单
function deleteMemnu() {
	menuIds = [];
	var rows = $('#dg3').datagrid('getSelections');
	for (var i = 0; i < rows.length; i++) {
		menuIds.push(rows[i].id);
	}
	if (menuIds.length > 0) {
		$.post(baseurl + 'employeeMenu/unBindMenu.do', {
			ids : menuIds.toString(),
			employeeId : employeeId
		}, function(result) {

			if (result.success) {
				$.messager.show({
					title : '提示',
					msg : '操作成功',
					timeout : 5000,
					showType : 'slide'
				});
				$('#dg3').datagrid('reload');
			} else {
				$.messager.show({ // show error message
					title : '提示',
					msg : result.msg
				});
			}
		}, 'json');
	} else {
		$.messager.show({
			title : '提示',
			msg : '请至少选择一个'
		});
	}
}