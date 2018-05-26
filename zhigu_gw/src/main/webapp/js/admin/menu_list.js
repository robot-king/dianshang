var obj;
var url;
var ids = [];
var sub_status = true;// 防止重复提交标识
$(function() {
	obj = {
		search : function() {
			$('#dg').treegrid('load', {
				id : $.trim($('input[name="id"]').val()),
				code : $.trim($('input[name="code"]').val()),
				name : $.trim($('input[name="name"]').val()),
				url : $.trim($('input[name="url"]').val()),
				parentCode : $.trim($('input[name="parentCode"]').val()),
				status : $.trim($('input[name="status"]').val()),
				createTime : $.trim($('input[name="createTime"]').val())
			});
		},
		searchClear : function() {
			$("#dg").treegrid("load", {}); // 重新加载数据，无填写数据，向后台传递值则为空
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
			$('#menu_dialog').dialog('open').dialog('setTitle', '新增');
			$('#menu_dialog').form('clear');
			url = baseurl + 'menu/save.do';
		},
		edit : function() {
			ids = [];
			var row = $('#dg').treegrid('getSelected');
			if (row) {
				var rows = $('#dg').treegrid('getSelections');
				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				if (ids.length == 1) {
					$('#menu_dialog').form('clear');
					console.log(JSON.stringify(row));
					// 防止重复验证默认通过
					sub_status = true;
					// 默认提交之前不验证
					$('#fm').form({
						novalidate : true
					});
					$('#menu_dialog').dialog('open').dialog('setTitle', '编辑');
					$('#fm').form('load', row);
					var _parentName = "";
					var obj = row.attributes;
					if (null != obj.parentName) {
						_parentName = obj.parentName;
					}
					$('#parent_name').textbox('setValue', _parentName);

					var _code = "";
					if (null != obj.code) {
						_code = obj.code;
					}
					$('#code').textbox('setValue', _code);
					$('#name').textbox('setValue', row.text);
					url = baseurl + 'menu/update.do?id=' + row.id;
					$('#parentCode').val("");
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
			var rows = $('#dg').treegrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			if (ids.length > 0) {
				$.messager.confirm('确定', '你确定删除这些数据吗？', function(r) {
					if (r) {
						$.post(baseurl + 'menu/delete.do', {
							ids : ids.toString()
						}, function(result) {
							if (result.success) {
								$.messager.show({
									title : '提示',
									msg : '操作成功',
									timeout : 5000,
									showType : 'slide'
								});
								$("#dg").treegrid("clearSelections");
								$('#dg').treegrid('reload');
							} else {
								$.messager.show({ // show error message
									title : 'Error',
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
							$('#menu_dialog').dialog('close'); // close
							// the
							// dialog
							$('#dg').treegrid('reload');
						} else {
							$.messager.show({
								title : 'Error',
								msg : result.msg
							});
							sub_status = true;
						}
					}
				});
			}
		},
		close : function() {
			$('#menu_dialog').dialog('close');
		}
	};

	$('#dg').treegrid(
			{
				url : baseurl + 'menu/treeList.do',
				loadMsg : '加载中……',
				idField : 'id',
				treeField : 'text',
				toolbar : '#tool_menu',
				columns : [ [
						{
							field : 'id',
							title : '序号',
							width : 100
						},
						{
							field : 'text',
							title : '名称',
							width : 150
						},
						{
							field : 'url',
							title : 'URL',
							width : 300
						},
						{
							field : 'code',
							title : '编码',
							width : 100,
							formatter : function(value, row, index) {
								var _name = "";
								var obj = row.attributes;
								if (null != obj.code) {
									_name = obj.code;
								}
								return _name;
							}
						},
						{
							field : 'parentCode',
							title : '父节点',
							width : 100,
							formatter : function(value, row, index) {
								var _name = "";
								var obj = row.attributes;
								if (null != obj.parentName) {
									_name = obj.parentName;
								}
								return _name;
							}
						},
						{
							field : 'sortNum',
							title : '排序数字',
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
								var _code = "";
								var obj = row.attributes;
								if (null != obj.code) {
									_code = obj.code;
								}
								buttonStr += "<a href='javascript:addChildrenType(\"" + row.text + "\",\"" + _code
										+ "\");' class='add_type'></a>";
								$.parser.parse(buttonStr);
								return buttonStr;
							}
						} ] ],
				onLoadSuccess : function(data) {

					$(".add_type").linkbutton({
						text : '添加子菜单',
						plain : true,
						iconCls : 'icon-add'
					});
				},
				loadFilter : function(data, parentId) {
					return data.data;
				}
			});
});
// 添加子分类窗口
function addChildrenType(parentName, code) {
	$('#menu_dialog').dialog('open').dialog('setTitle', '添加子菜单');
	$('#fm').form('clear');
	document.getElementById('fm').reset();
	// 添加父菜单信息
	if ('' != code) {
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
	url = baseurl + 'menu/save.do';
}