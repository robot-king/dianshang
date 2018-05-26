var obj;
var url;
var ids = [];
var sub_status = true; // 防止重复提交标识
$(function () {
	obj = {
		search: function () {
			$('#dg').datagrid('load', {
				id: $.trim($('#s_id').val()),
				des: $.trim($('#s_des').val()),
				kehuId: $.trim($('#s_kehuId').val()),
				laiyuan: $.trim($('#s_laiyuan').val()),
				yixiangStatus: $.trim($('#s_yixiangStatus').val()),
				danziStatus: $.trim($('#s_danziStatus').val()),
				startTime: $.trim($('#s_startTime').val()),
				endTime: $.trim($('#s_endTime').val()),
				baojiaMoney: $.trim($('#s_baojiaMoney').val()),
				chengjiaoMoney: $.trim($('#s_chengjiaoMoney').val()),
				fukuanTime: $.trim($('#s_fukuanTime').val()),
				shijiEndTime: $.trim($('#s_shijiEndTime').val()),
				createTime: $.trim($('#s_createTime').val())
			});
		},
		searchClear: function () {
			$("#dg").datagrid("load", {}); // 重新加载数据，无填写数据，向后台传递值则为空
			obj.clearForm();
		},
		clearForm: function () {
			$('#s_danziStatus').combobox('setValue', "");
			$("label + input").each(function () {
				var _id = $(this).attr("id");
				console.log(_id);
				$('#' + _id).textbox('setValue', '');
			});
		},
		add: function () {
			// 清空表单
			$('#danzi_dialog').form('clear');
			// 防止重复验证默认通过
			sub_status = true;
			// 默认提交之前不验证
			$('#fm').form({
				novalidate: true
			});
			$('#danzi_dialog').dialog('open').dialog('setTitle', '新增');

			url = baseurl + 'danzi/save.do';

			var curr_time = new Date();
			$("#startTime").datetimebox("setValue", curr_time.Format('yyyy-MM-dd HH:mm:ss'));

			var end_time = curr_time.setDate(curr_time.getDate() + 10);
			$("#endTime").datetimebox("setValue", curr_time.Format('yyyy-MM-dd HH:mm:ss'));

			loadType();
			if ('' != kehu_id) {
				$('#kehuId').combobox('setValue', kehu_id);
			}
		},
		edit: function () {
			// 清空表单
			$('#danzi_dialog').form('clear');
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
						novalidate: true
					});
					$('#danzi_dialog').dialog('open').dialog('setTitle', '编辑');
					$('#fm').form('load', row);
					url = baseurl + 'danzi/update.do?id=' + row.id;

					loadType();

					$('#kehuId').combobox('setValue', row.kehuId);

					$('#laiyuan').combobox('setValue', row.laiyuan);
				} else {
					$.messager.show({
						title: '提示',
						msg: '只能选择一个'
					});
				}
			} else {
				$.messager.show({
					title: '提示',
					msg: '请选择一个'
				});
			}
		},
		remove: function () {
			ids = [];
			var rows = $('#dg').datagrid('getSelections');
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			if (ids.length > 0) {
				$.messager.confirm('确定', '你确定删除这些数据吗？', function (r) {
					if (r) {
						$.post(baseurl + 'danzi/delete.do', {
							ids: ids.toString()
						}, function (result) {
							if (result.success) {
								$.messager.show({
									title: '提示',
									msg: '操作成功',
									timeout: 5000,
									showType: 'slide'
								});
								$("#dg").datagrid("clearSelections");
								$('#dg').datagrid('reload');
							} else {
								$.messager.show({ // show error message
									title: '错误',
									msg: result.msg
								});
							}
						}, 'json');
					}
				});
			} else {
				$.messager.show({
					title: '提示',
					msg: '请至少选择一个'
				});
			}
		},
		save: function () {
			// 判断kehuId是否为汉字，如果为汉字则置空
			var kehuId = $('#kehuId').combobox('getValue');
			if (!isNumber(kehuId)) {
				$('#kehuId').combobox('setValue', "");
				$('#kehuName').val(kehuId);
			}

			if (sub_status) {
				$('#fm').form('submit', {
					url: url,
					onSubmit: function () {
						var status = false;
						if ($(this).form('enableValidation').form('validate')) {
							status = true;
							sub_status = false;
						}
						return status;
					},
					success: function (res) {
						var result = eval('(' + res + ')');
						if (result.success) {
							$.messager.show({
								title: '提示',
								msg: '操作成功',
								timeout: 5000,
								showType: 'slide'
							});
							$('#danzi_dialog').dialog('close'); // close the dialog
							$('#dg').datagrid('reload');
						} else {
							$.messager.show({
								title: '错误',
								msg: result.msg
							});
							sub_status = true;
						}
					}
				});
			}
		},
		close: function () {
			$('#danzi_dialog').dialog('close');
		},
		save2: function () {
			$('#fm2').form('submit', {
				url: url2,
				onSubmit: function () {
					var status = false;
					if ($(this).form('enableValidation').form('validate')) {
						status = true;
						sub_status = false;
					}
					$.messager.progress({
						text: "进行中...."
					});
					return status;
				},
				success: function (res) {
					var result = eval('(' + res + ')');
					if (result.success) {
						$.messager.progress("close");
						$.messager.show({
							title: '提示',
							msg: '操作成功',
							timeout: 5000,
							showType: 'slide'
						});
						$('#genjin_jilu_dialog').dialog('close'); // close the dialog
						$('#dg').datagrid('reload');
					} else {
						$.messager.show({
							title: '错误',
							msg: result.msg
						});
						sub_status = true;
					}
				}
			});
		},
		close2: function () {
			$('#genjin_jilu_dialog').dialog('close');
		}
	};

	$('#dg').datagrid({
		url: baseurl + 'danzi/queryList.do',
		loadMsg: '加载中……',
		toolbar: '#tool_danzi',
		singleSelect: true,
		columns: [[{
					field: 'id',
					title: 'id'
				}, {
					field: 'kehuName',
					title: '客户'
				}, {
					field: 'des',
					title: '需求描述',
					width:100
				}, {
					field: 'laiyuan',
					title: '来源',
					formatter: function (value, row, index) {
						return window.parent.dictValue(value);
					}
				}, {
					field: 'yixiangStatus',
					title: '意向强弱',
					formatter: function (value, row, index) {
						var str = "";
						if (value == 1) {
							str = "强";
						} else if (value == 2) {
							str = "一般";
						} else if (value == 3) {
							str = "弱";
						}
						return str;
					}

				}, {
					field: 'danziStatus',
					title: '单子状态',
					formatter: function (value, row, index) {
						var str = "";
						if (value == 1) {
							str = "验证客户";
						} else if (value == 2) {
							str = "需求确定";
						} else if (value == 3) {
							str = "方案报价";
						} else if (value == 4) {
							str = "谈判审核";
						} else if (value == 5) {
							str = "赢单";
						} else if (value == 6) {
							str = "输单";
						} else if (value == 7) {
							str = "托管";
						} else if (value == 8) {
							str = "正在开发";
						} else if (value == 9) {
							str = "开发完成";
						} else if (value == 10) {
							str = "改bug";
						} else if (value == 11) {
							str = "付款";
						}
						return str;
					}

				}, {
					field: 'startTime',
					title: '工作开始时间'
				}, {
					field: 'endTime',
					title: '工作结束时间'
				}, {
					field: 'baojiaMoney',
					title: '报价'
				}, {
					field: 'chengjiaoMoney',
					title: '成交金额'
				}, {
					field: 'fukuanTime',
					title: '付款时间'
				}, {
					field: 'shijiEndTime',
					title: '实际工作结束时间'
				}, {
					field: 'createTime',
					title: '创建时间'
				}, {
					field: 'click',
					title: '操作',
					width: 260,
					formatter: function (value, row) {
						var buttonStr = "";
						buttonStr += "<a href='javascript:addGenjin(" + row.id + ");' class='add_type'></a>";
						buttonStr += "<a href='javascript:seeGenjin(" + row.id + ");' class='see_type'></a>";
						$.parser.parse(buttonStr);
						return buttonStr;
					}
				}
			]],
		pagination: true,
		onLoadSuccess: function (data) {

			$(".add_type").linkbutton({
				text: '添加跟进记录',
				plain: true,
				iconCls: 'icon-add'
			});
			
			$(".see_type").linkbutton({
				text: '查看跟进记录',
				plain: true,
				iconCls: 'icon-more'
			});
			
			$('tr td[field="des"]').click(function() {
				// console.log($(this).html());
				if ("" != $(this).text()) {
					var _content = '<div style="padding:20px;word-wrap:break-word;">' + $(this).text() + '</div>';
					layer.open({
						type : 1,
						title:false,
						skin : 'layer-ext-yw', // 样式类名
						closeBtn : 0, // 不显示关闭按钮
						anim : 2,
						shadeClose : true, // 开启遮罩关闭
						content : _content
					});
				}
			});
		}
	});

	if ('' != action && action == "add") {
		obj.add();
	}

	$('#s_kehuId').combobox({
		url: 'kehu/queryList.do?rows=10000',
		valueField: 'id',
		textField: 'xingming',
		loadFilter: function (req) {
			var data = req.list;
			return data;
		}
	});
});
function loadType() {
	$('#kehuId').combobox({
		url: 'kehu/queryList.do?rows=10000',
		valueField: 'id',
		textField: 'xingming',
		loadFilter: function (req) {
			var data = req.list;
			return data;
		}
	});

	$('#laiyuan').combobox({
		url: 'dict/queryList.do?rows=100',
		queryParams: {
			"parentCode": "laiyuan_type",
		},
		valueField: 'code',
		textField: 'name',
		loadFilter: function (req) {
			var data = req.list;
			return data;
		}
	});
}
Date.prototype.Format = function (fmt) { // author: meizz
	var o = {
		"M+": this.getMonth() + 1, // 月份
		"d+": this.getDate(), // 日
		"H+": this.getHours(), // 小时
		"m+": this.getMinutes(), // 分
		"s+": this.getSeconds(), // 秒
		"q+": Math.floor((this.getMonth() + 3) / 3), // 季度
		"S": this.getMilliseconds() // 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
function isNumber(val) {

	var regPos = /^\d+(\.\d+)?$/; // 非负浮点数
	var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; // 负浮点数
	if (regPos.test(val) || regNeg.test(val)) {
		return true;
	} else {
		return false;
	}
}
var url2 = "";
function addGenjin(id) {
	// 清空表单
	$('#genjin_jilu_dialog').form('clear');
	// 防止重复验证默认通过
	sub_status = true;
	// 默认提交之前不验证
	$('#fm').form({
		novalidate: true
	});
	$('#genjin_jilu_dialog').dialog('open').dialog('setTitle', '新增');
	$('#danziId').val(id);

	url2 = baseurl + 'genjinJilu/save.do';
}
function seeGenjin(id) {
	window.parent.addTab("跟进记录", "genjinJilu/genjin_jilu_list.do?danziId=" + id);
}