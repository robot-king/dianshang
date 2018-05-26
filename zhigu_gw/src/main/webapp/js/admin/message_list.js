var obj;
var url;
var ids = [];
var sub_status = true;// 防止重复提交标识
$(function() {
	obj = {
		search : function() {
			$('#dg').datagrid('load', {
				id : $.trim($('input[name="id"]').val()),
				title : $.trim($('input[name="title"]').val()),
				content : $.trim($('input[name="content"]').val()),
				type : $.trim($('input[name="type"]').val()),
				sendId : $.trim($('input[name="sendId"]').val()),
				receivedId : $.trim($('input[name="receivedId"]').val()),
				status : $.trim($('input[name="status"]').val()),
				createTime : $.trim($('input[name="createTime"]').val())
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
			$('#message_dialog').dialog('open').dialog('setTitle', '新增');
			$('#message_dialog').form('clear');
			url = baseurl + 'message/save.do';
		},
		edit : function() {
			ids = [];
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				if (row.type != 1 && row.type != 2) {
					$.messager.show({
						title : '提示',
						msg : '只能选择促销或公告类型的数据修改'
					});
					return;
				}
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
					$('#message_dialog').dialog('open').dialog('setTitle', '编辑');
					$('#fm').form('load', row);
					url = baseurl + 'message/update.do?id=' + row.id;
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
						$.post(baseurl + 'message/delete.do', {
							ids : ids.toString()
						}, function(result) {
							if (result.success) {
								$.messager.show({
									title : '提示',
									msg : '操作成功',
									timeout : 5000,
									showType : 'slide'
								});
								$("#dg").datagrid("clearSelections");
								$('#dg').datagrid('reload');
							} else {
								$.messager.show({ // show error message
									title : '错误',
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
							$('#message_dialog').dialog('close'); // close the
							// dialog
							$('#dg').datagrid('reload');
						} else {
							$.messager.show({
								title : '错误',
								msg : result.msg
							});
							sub_status = true;
						}
					}
				});
			}
		},
		close : function() {
			$('#message_dialog').dialog('close');
		}
	};

	$('#dg').datagrid({
		url : baseurl + 'message/queryList.do',
		loadMsg : '加载中……',
		toolbar : '#tool_message',
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100
		}, {
			field : 'sendId1',
			title : '发送人',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				var user = row.user;
				if (null != user) {
					str = user.nickName;
				}
				return str;
			}
		}, {
			field : 'receivedId1',
			title : '接收人',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				var user = row.receivedUser;
				if (null != user) {
					str = user.nickName;
				}
				return str;
			}
		}, {
			field : 'title',
			title : '标题',
			width : 100
		}, {
			field : 'content',
			title : '内容',
			width : 100
		}, {
			field : 'type',
			title : '类型',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				if (value == 1) {
					str = "促销";
				} else if (value == 2) {
					str = "公告";
				} else if (value == 3) {
					str = "留言";
				} else if (value == 4) {
					str = "私信";
				} else if (value == 5) {
					str = "邀请";
				} else if (value == 6) {
					str = "交易报价";
				} else if (value == 7) {
					str = "系统通知";
				}
				return str;
			}
		}, {
			field : 'status',
			title : '状态',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				if (value == 1) {
					str = "未读";
				} else if (value == 2) {
					str = "已读";
				}
				return str;
			}
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 100
		} ] ],
		pagination : true
	});
});
