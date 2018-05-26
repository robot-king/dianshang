var obj;
var url;
var ids = [];
var sub_status = true; // 防止重复提交标识
$(function () {
	obj = {
		search: function () {
			$('#dg').datagrid('load', {
				id: $.trim($('#s_id').val()),
				employeeId: $.trim($('#s_employeeId').val()),
				bannerUrl: $.trim($('#s_bannerUrl').val()),
				status: $.trim($('#s_status').val()),
				createTime: $.trim($('#s_createTime').val())
			});
		},
		searchClear: function () {
			$("#dg").datagrid("load", {}); // 重新加载数据，无填写数据，向后台传递值则为空
			$("input[id^='s_']").each(function(){
				var _id = $(this).attr("id");
				$('#'+ _id).textbox('setValue','');
			}); 
		},
		add: function () {
			$.messager.show({
				title: '提示',
				msg: '请上传宽度为855像素和高度为288像素的图片'
			});
			// 防止重复验证默认通过
			sub_status = true;
			// 默认提交之前不验证
			$('#fm').form({
				novalidate: true
			});
			$('#lun_bo_tu_dialog').dialog('open').dialog('setTitle', '新增');
			$('#lun_bo_tu_dialog').form('clear');
			url = baseurl + 'lunBoTu/save.do';

			// 清空"#view"
			$("#view").css("background-image", "");
			clip();
		},
		edit: function () {
			$.messager.show({
				title: '提示',
				msg: '请上传宽度为855像素和高度为288像素的图片'
			});
			ids = [];
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				var rows = $('#dg').datagrid('getSelections');
				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				if (ids.length == 1) {
					// 防止重复验证默认通过
					sub_status = true;
					// 默认提交之前不验证
					$('#fm').form({
						novalidate: true
					});
					$('#lun_bo_tu_dialog').dialog('open').dialog('setTitle', '编辑');
					$('#fm').form('load', row);
					url = baseurl + 'lunBoTu/update.do?id=' + row.id;

					if (null != row.bannerUrl && '' != row.bannerUrl) {
						$('#bannerUrlFile').filebox("setText", row.bannerUrl);
					}
					clip();
					var image = new Image();
					image.crossOrigin = '';
					image.src = row.bannerUrl;
					image.onload = function () {
						var base64 = getBase64Image(image);
						$('#view').css('background-image', "url(" + base64 + ")");
					}
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
						$.post(baseurl + 'lunBoTu/delete.do', {
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
							$('#lun_bo_tu_dialog').dialog('close'); // close the
							// dialog
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
			$('#lun_bo_tu_dialog').dialog('close');
		},
		close2: function () {
			$('#upload_tu_dialog').dialog('close');
		}
	};

	$('#dg').datagrid({
		url: baseurl + 'lunBoTu/queryList.do',
		loadMsg: '加载中……',
		toolbar: '#tool_lun_bo_tu',
		singleSelect: true,
		columns: [[{
					field: 'id',
					title: 'id',
					width: 100
				},
// {
// field: 'employeeId',
// title: '员工id',
// width: 100
// },
				{
					field: 'bannerUrl',
					title: '轮播图地址',
					width: 100
				},{
					field: 'targetUrl',
					title: '目标地址',
					width: 100
				}, {
					field: 'status',
					title: '状态',
					width: 100,
					formatter: function (value, row, index) {
						var str = "";
						if (value == 1) {
							str = "启用";
						} else if (value == 2) {
							str = "禁用";
						} else if (value == 3) {
							str = "删除";
						}
						return str;
					}
				}, {
					field: 'createTime',
					title: '创建时间',
					width: 100
				}
			]],
		pagination: true
	});
});
var pc = null;
function clip() {
	if (null != pc) {
		pc.destroy();
	}

	pc = new PhotoClip('#clipArea', {
			size: [855, 288],
			outputSize: [855, 288],
			// adaptive: ['60%', '80%'],
			file: "#filebox_file_id_1",
			view: '#view',
			ok: '#clipBtn',
			// img : 'img/mm.jpg',
			loadStart: function () {
				console.log('开始读取照片');
				// 由于在加载图片到clipArea时，clipArea区域未完成加载，因此用了延时
				setTimeout(() => {
					$('#upload_tu_dialog').dialog('open').dialog('setTitle', '截取图片');
				}, 500);
			},
			loadComplete: function () {
				console.log('照片读取完成');
			},
			done: function (dataURL) {
				// console.log(dataURL);
				$('#upload_tu_dialog').dialog('close');
				$('#view').css('background-size', '100% 100%');
				dataURL = dataURL.substring(dataURL.indexOf("64,") + 3, dataURL.length);
				$('#bannerBase64').val(dataURL);
			},
			fail: function (msg) {
				alert(msg);
			}
		});

	// 加载的图片必须要与本程序同源，否则无法截图
	// pc.load('img/mm.jpg');
}
// 将图片转化成base64
function getBase64Image(img) {
	var canvas = document.createElement("canvas");
	canvas.width = img.width;
	canvas.height = img.height;

	var ctx = canvas.getContext("2d");
	ctx.drawImage(img, 0, 0, img.width, img.height);
	var ext = img.src.substring(img.src.lastIndexOf(".") + 1).toLowerCase();
	var dataURL = canvas.toDataURL("image/" + ext);
	return dataURL;
}
