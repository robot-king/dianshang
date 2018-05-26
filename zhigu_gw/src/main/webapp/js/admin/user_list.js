var obj;
var url;
var ids = [];
var sub_status = true; // 防止重复提交标识
$(function() {
	obj = {
		search : function() {
			$('#dg').datagrid('load', {
				nickName : $.trim($('#s_nickName').val()),
				name : $.trim($('#s_name').val()),
				mobile : $.trim($('#s_mobile').val())
			});
		},
		searchClear : function() {
			$("#dg").datagrid("load", {}); // 重新加载数据，无填写数据，向后台传递值则为空
			$("#tool_user").find("input").val(""); 
		},
		add : function() {
			// 防止重复验证默认通过
			sub_status = true;
			// 默认提交之前不验证
			$('#fm').form({
				novalidate : true
			});
			$('#user_dialog').dialog('open').dialog('setTitle', '新增用户');
			$('#user_dialog').form('clear');
			url = baseurl + 'user/save.do';
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
					$('#user_dialog').dialog('open').dialog('setTitle', '编辑用户');
					$('#fm').form('load', row);
					url = baseurl + 'user/update.do?id=' + row.id;
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
						$.post(baseurl + 'user/delete.do', {
							ids : ids.toString()
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
		fengjin : function() {
			ids = [];
			var rows = $('#dg').datagrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			if (ids.length == 1) {
				$.post(baseurl + 'user/fengjin.do', {
					id : ids.toString()
				}, function(result) {
					if (result.success) {
						$.messager.show({
							title : '提示',
							msg : result.msg,
							timeout : 5000,
							showType : 'slide'
						});
						$("#dg").datagrid("clearSelections");
						$('#dg').datagrid('reload');
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
					msg : '只能选择一个'
				});
			}
		},
		jiefeng : function() {
			ids = [];
			var rows = $('#dg').datagrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			if (ids.length == 1) {
				$.post(baseurl + 'user/jiefeng.do', {
					id : ids.toString()
				}, function(result) {
					if (result.success) {
						$.messager.show({
							title : '提示',
							msg : result.msg,
							timeout : 5000,
							showType : 'slide'
						});
						$("#dg").datagrid("clearSelections");
						$('#dg').datagrid('reload');
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
					msg : '只能选择一个'
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
								msg : '操作成功,' + result.msg,
								timeout : 5000,
								showType : 'slide'
							});
							$('#user_dialog').dialog('close'); // close the
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
			$('#user_dialog').dialog('close');
		},
		save2 : function() {
			if (sub_status) {
				$('#fm2').form('submit', {
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
						if (!result.success) {
							$.messager.show({
								title : '提示',
								msg : result.msg
							});
							sub_status = true;
						} else {
							$.messager.show({
								title : '提示',
								msg : '操作成功',
								timeout : 5000,
								showType : 'slide'
							});
							$('#user_dialog2').dialog('close'); // close the
							// dialog
							$('#dg').datagrid('reload');

						}
					}
				});
			}
		},
		close2 : function() {
			$('#user_dialog2').dialog('close');
		},
		exportExcel : function() {
			window.location.href = baseurl + 'user/exportExcel.do';
		},
		displayColumn : function() {
			// 显示列
			$('#dg').datagrid('showColumn', 'pushArticleCount');
			$('#dg').datagrid('showColumn', 'readArticleCount');
			$('#dg').datagrid('showColumn', 'readRate');
			$('#dg').datagrid('showColumn', 'avgReadLenght');
			$('#dg').datagrid('showColumn', 'collectArticleCount');
			$('#dg').datagrid('showColumn', 'offlineArticleCount');
			$('#dg').datagrid('showColumn', 'shareArticleCount');
		},
		hideColumn : function() {
			// 隐藏列
			$('#dg').datagrid('hideColumn', 'pushArticleCount');
			$('#dg').datagrid('hideColumn', 'readArticleCount');
			$('#dg').datagrid('hideColumn', 'readRate');
			$('#dg').datagrid('hideColumn', 'avgReadLenght');
			$('#dg').datagrid('hideColumn', 'collectArticleCount');
			$('#dg').datagrid('hideColumn', 'offlineArticleCount');
			$('#dg').datagrid('hideColumn', 'shareArticleCount');
		},
		_mobileTypeVer : function() {
			$('#_mobileTypeVer').combobox({
				url : baseurl + 'dict/queryList.do',
				queryParams : {
					"parentCode" : "mobileTypeVer",
				},
				valueField : 'code',
				textField : 'name',
				loadFilter : function(req) {
					var data = req.list;
					return data;
				}
			});
		},
		_mobileType : function() {
			$('#_mobileType').combobox({
				url : baseurl + 'dict/queryList.do',
				queryParams : {
					"parentCode" : "mobileType"
				},
				valueField : 'code',
				textField : 'name',
				loadFilter : function(req) {
					var data = req.list;
					return data;
				}
			});
		}
	};

	$('#dg').datagrid({
		url : baseurl + 'user/queryList.do?t=' + new Date().getTime(),
		loadMsg : '加载中……',
		cache : false,
		toolbar : '#tool_user',
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100
		}, {
			field : 'nickName',
			title : '昵称',
			width : 100
		}, {
			field : 'name',
			title : '真实姓名',
			width : 100
		}, {
			field : 'mobile',
			title : '手机号',
			width : 160
		}, {
			field : 'iconUrl',
			title : '头像',
			width : 100
		}, {
			field : 'vipType',
			title : '会员类型',
			width : 100
		}, {
			field : 'loginStatus',
			title : '登录状态',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				if (value == 1) {
					str = "在线";
				} else if (value == 2) {
					str = "离线";
				} else if (value == 3) {
					str = "封禁";
				}
				return str;
			}
		}, {
			field : 'comment',
			title : '备注',
			width : 100
		}, {
			field : 'money',
			title : '账户余额',
			width : 100
		}, {
			field : 'balancePwd',
			title : '余额密码',
			width : 100
		}, {
			field : 'signature',
			title : '签名',
			width : 100
		}, {
			field : 'resource',
			title : '资源',
			width : 100
		}, {
			field : 'employeeId',
			title : '员工ID',
			width : 100
		}, {
			field : 'passedName',
			title : '曾用名',
			width : 100
		}, {
			field : 'national',
			title : '国家地区',
			width : 100
		}, {
			field : 'province',
			title : '省份',
			width : 100
		}, {
			field : 'customeUrl',
			title : '自定义URL',
			width : 100
		}, {
			field : 'des',
			title : '描述',
			width : 100
		}, {
			field : 'infoStatus',
			title : '资料状态',
			width : 100
		}, {
			field : 'messageStatus',
			title : '留言状态',
			width : 100
		}, {
			field : 'inventoryStatus',
			title : '库存状态',
			width : 100
		}, {
			field : 'integration',
			title : '积分',
			width : 100
		}, {
			field : 'loginTime',
			title : '登录时间',
			width : 100
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 100
		}, {
			field : 'click1',
			title : '重置密码',
			width : 120,
			formatter : function(value, row) {
				var buttonStr = "";
				buttonStr += "<a class='add_type' href='javascript:resetPwdDialog(\"" + row.id + "\");'>重置密码</a>";
				return buttonStr;
			}
		} ] ],
		pagination : true,
		onLoadSuccess : function(data) {
			$(".add_type").linkbutton({
				text : '重置密码',
				plain : true
			});
			$(".add_type1").linkbutton({
				text : '查看简历',
				plain : true
			});
			$(".add_type2").linkbutton({
				text : '查看招聘',
				plain : true
			});
			$(".add_type3").linkbutton({
				text : '查看企业认证',
				plain : true
			});
		}
	});

	obj._mobileTypeVer();
	obj._mobileType();

});
//
function resetPwdDialog(userId) {

	$('#user_dialog2').dialog('open').dialog('setTitle', '重置密码');
	$('#fm2').form('clear');
	document.getElementById('fm2').reset();
	sub_status = true;
	// 默认提交之前不验证
	$('#fm').form({
		novalidate : true
	});
	$("#userId").val(userId);
	url = baseurl + 'userAuth/resetPwd.do';
}

// 查看简历
function viewResume(userId, name, nickName) {
	var title = name == 'null' ? nickName : name;
	title += "的简历";
	var url = "resume/resume_list.do?userId=" + userId;
	window.parent.addTab(title, url);
}
// 查看招聘
function viewJobs(userId, name, nickName) {
	var title = name == 'null' ? nickName : name;
	title += "的招聘";
	var url = "jobs/jobs_list.do?userId=" + userId;
	window.parent.addTab(title, url);
}

// 查看企业认证
function viewCompany(userId, name, nickName) {
	var title = name == 'null' ? nickName : name;
	title += "的企业认证";
	var url = "jobs/jobs_list.do?userId=" + userId;
	window.parent.addTab(title, url);
}
