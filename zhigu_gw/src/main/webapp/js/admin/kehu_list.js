var obj;
var url;
var ids = [];
var sub_status = true;// 防止重复提交标识
$(function() {
	obj = {
		search : function() {
			$('#dg').datagrid('load', {
				id : $.trim($('#s_id').val()),
				xingming : $.trim($('#s_xingming').val()),
				shouji : $.trim($('#s_shouji').val()),
				qq : $.trim($('#s_qq').val()),
				weixin : $.trim($('#s_weixin').val()),
				diqu : $.trim($('#s_diqu').val()),
				beizhu : $.trim($('#s_beizhu').val()),
				createTime : $.trim($('#s_createTime').val())
			});
		},
		searchClear : function() {
			$("#dg").datagrid("load", {}); // 重新加载数据，无填写数据，向后台传递值则为空
			obj.clearForm();
		},
		clearForm : function() {
			$("label + input").each(function() {
				var _id = $(this).attr("id");
				console.log(_id);
				$('#' + _id).textbox('setValue', '');
			});
		},
		add : function() {
			// 清空表单
			$('#kehu_dialog').form('clear');
			// 防止重复验证默认通过
			sub_status = true;
			// 默认提交之前不验证
			$('#fm').form({
				novalidate : true
			});
			$('#kehu_dialog').dialog('open').dialog('setTitle', '新增');

			url = baseurl + 'kehu/save.do';
		},
		edit : function() {
			// 清空表单
			$('#kehu_dialog').form('clear');
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
					$('#kehu_dialog').dialog('open').dialog('setTitle', '编辑');
					$('#fm').form('load', row);
					url = baseurl + 'kehu/update.do?id=' + row.id;
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
						$.post(baseurl + 'kehu/delete.do', {
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
							$('#kehu_dialog').dialog('close'); // close the dialog
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
			$('#kehu_dialog').dialog('close');
		}
	};

	$('#dg').datagrid({
		url : baseurl + 'kehu/queryList.do',
		loadMsg : '加载中……',
		toolbar : '#tool_kehu',
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100
		}, {
			field : 'xingming',
			title : '姓名',
			width : 100
		}, {
			field : 'shouji',
			title : '手机',
			width : 100
		}, {
			field : 'qq',
			title : 'QQ',
			width : 100
		}, {
			field : 'weixin',
			title : '微信',
			width : 100
		}, {
			field : 'diqu',
			title : '地区',
			width : 100
		}, {
			field : 'beizhu',
			title : '备注',
			width : 100
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 100
		}, {
			field : 'click',
			title : '操作',
			width : 120,
			formatter : function(value, row) {
				var buttonStr = "";
				buttonStr += "<a href='javascript:addDanzi(" + row.id + ");' class='add_type'></a>";
				$.parser.parse(buttonStr);
				return buttonStr;
			}
		} ] ],
		pagination : true,
		onLoadSuccess : function(data) {

			$(".add_type").linkbutton({
				text : '添加子菜单',
				plain : true,
				iconCls : 'icon-add'
			});
		}
	});
});
// 添加单子
function addDanzi(id) {
	window.parent.addTab("单子列表", "danzi/danzi_list.do?action=add&kehu_id=" + id);
}