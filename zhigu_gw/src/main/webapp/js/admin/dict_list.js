var obj;
var url;
var ids = [];
var sub_status = true;// 防止重复提交标识
$(function() {
	obj = {
		search : function() {
			$('#dg').datagrid('load', {
				name : $.trim($('#_name').val()),
				code : $.trim($('#_code').val())
			});
		},
		searchClear : function() {
			$("#dg").datagrid("load", {}); // 重新加载数据，无填写数据，向后台传递值则为空
			$("#dg2").datagrid("load", {});
			$("#tool_dict").find("input").val(""); 
		},
		add : function() {
			// 防止重复验证默认通过
			sub_status = true;
			// 默认提交之前不验证
			$('#fm').form({
				novalidate : true
			});
			$('#dict_dialog').dialog('open').dialog('setTitle', '新增');
			$('#dict_dialog').form('clear');
			$('#parent_name').textbox('readonly', true);
			url = baseurl + 'dict/save.do';
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
					$('#dict_dialog').dialog('open').dialog('setTitle', '编辑dict');
					$('#fm').form('load', row);
					$('#parent_name').textbox('setValue', row.parentName);
					url = baseurl + 'dict/update.do?id=' + row.id;
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
						$.post(baseurl + 'dict/delete.do', {
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
							$('#dict_dialog').dialog('close'); // close the
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
			$('#dict_dialog').dialog('close');
		}
	};

	$('#dg').datagrid(
			{
				url : baseurl + 'dict/queryList.do',
				loadMsg : '加载中……',
				toolbar : '#tool_dict',
				pageSize : 20,
				columns : [ [
						{
							field : 'id',
							title : '序号',
							width : 100
						},
						{
							field : 'name',
							title : '名称',
							width : 100
						},
						{
							field : 'code',
							title : '编码',
							width : 100
						},
						/*
						 * { field : 'iconUrl', title : '', width : 100 },
						 */
						{
							field : 'parentName',
							title : '父节点',
							width : 100
						},
						{
							field : 'createTime',
							title : '创建时间',
							width : 100
						},
						{
							field : 'click',
							title : '操作',
							width : 120,
							formatter : function(value, row) {
								var buttonStr = "";
								buttonStr += "<a class='add_type' href='javascript:addChildrenType(\"" + row.name
										+ "\",\"" + row.code + "\");'>添加分类</a>";
								return buttonStr;
							}
						} ] ],
				pagination : true,
				onClickRow : function(index, row) {
					childGrid(row.code);
				},
				onLoadSuccess : function(data) {
					$(".add_type").linkbutton({
						text : '添加子菜单',
						plain : true,
						iconCls : 'icon-add'
					});
				}
			});
});

// 添加子分类窗口
function addChildrenType(parentName, code) {
	$('#dict_dialog').dialog('open').dialog('setTitle', '添加子菜单');
	$('#fm').form('clear');
	document.getElementById('fm').reset();
	// 添加父菜单信息
	if ('' != parentName && 0 != code) {
		// 显示父菜单内容
		$('#_parent').show();
		// 防止重复验证默认通过
		sub_status = true;
		// 默认提交之前不验证
		$('#fm').form({
			novalidate : true
		});
		$('#parent_name').textbox('setValue', parentName);
		$('#parent_name').textbox('readonly', true);
		$('#parentCode').val(code);
	}
	url = baseurl + 'dict/save.do';
}

// 子分类
function childGrid(parentCode) {
	$('#dg2').datagrid(
			{
				url : baseurl + 'dict/queryList.do?parentCode=' + parentCode,
				loadMsg : '加载中……',
				singleSelect : true,
				columns : [ [
						{
							field : 'id',
							title : '序号',
							width : 100
						},
						{
							field : 'name',
							title : '名称',
							width : 100
						},
						{
							field : 'code',
							title : '编码',
							width : 100
						},
						/*
						 * { field : 'iconUrl', title : '', width : 100 },
						 */
						{
							field : 'parentName',
							title : '父节点',
							width : 100
						},
						{
							field : 'createTime',
							title : '创建时间',
							width : 100
						},
						{
							field : 'click',
							title : '操作',
							width : 100,
							formatter : function(value, row) {
								var buttonStr = "";
								buttonStr += "<a href='javascript:addChildrenType(\"" + row.name + "\",\"" + row.code
										+ "\");'>添加分类</a>";
								return buttonStr;
							}
						} ] ],
				pagination : true
			});
}