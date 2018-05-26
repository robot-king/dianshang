var obj;
var url;
var ids = [];
var sub_status = true;// 防止重复提交标识
$(function() {
	obj = {
		search : function() {
			$('#dg').datagrid('load', {
				id : $.trim($('#s_id').val()),
				title : $.trim($('#s_title').val()),
				coverUrl : $.trim($('#s_coverUrl').val()),
				price : $.trim($('#s_price').val()),
				type : $.trim($('#s_type').val()),
				webType : $.trim($('#s_webType').val()),
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
			// 清空图片
			$("img[id^='img']").each(function() {
				$(this).attr("src", "");
			});
			// 清空表单
			$('#web_site_dialog').form('clear');
			// 防止重复验证默认通过
			sub_status = true;
			// 默认提交之前不验证
			$('#fm').form({
				novalidate : true
			});
			$('#web_site_dialog').dialog('open').dialog('setTitle', '新增');

			url = baseurl + 'webSite/save.do';

			loadType();

		},
		edit : function() {
			// 清空表单
			$('#web_site_dialog').form('clear');
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
					$('#web_site_dialog').dialog('open').dialog('setTitle', '编辑');
					$('#fm').form('load', row);
					url = baseurl + 'webSite/update.do?id=' + row.id;

					loadType();
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
						$.post(baseurl + 'webSite/delete.do', {
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
						$.messager.progress({
							text : "进行中...."
						});
						return status;
					},
					success : function(res) {
						$.messager.progress("close");
						var result = eval('(' + res + ')');
						if (result.success) {
							$.messager.show({
								title : '提示',
								msg : '操作成功',
								timeout : 5000,
								showType : 'slide'
							});
							$('#web_site_dialog').dialog('close'); // close the dialog
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
			$('#web_site_dialog').dialog('close');
		}
	};

	$('#dg').datagrid({
		url : baseurl + 'webSite/queryList.do',
		loadMsg : '加载中……',
		toolbar : '#tool_web_site',
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100
		}, {
			field : 'title',
			title : '标题',
			width : 100
		}, {
			field : 'coverUrl',
			title : '缩略图地址',
			width : 200
		}, {
			field : 'accessUrl',
			title : '访问地址',
			width : 300
		}, {
			field : 'feiAccessUrl',
			title : '非本网访问地址',
			width : 200
		}, {
			field : 'price',
			title : '价格',
			width : 100
		}, {
			field : 'type',
			title : '类型',
			width : 100,
			formatter : function(value, row, index) {
				var str = "";
				if (value == 1) {
					str = "案例";
				} else if (value == 2) {
					str = "模板";
				}
				return str;
			}
		}, {
			field : 'webType',
			title : '网站类型',
			width : 100,
			formatter : function(value, row, index) {
				console.log(value);
				return window.parent.dictValue(value);
			}
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 180
		} ] ],
		pagination : true
	});
});
// 加载分类
function loadType() {
	// 网站分类
	$('#webType').combobox({
		url : baseurl + 'dict/queryList.do?rows=100',
		queryParams : {
			"parentCode" : "webType",
		},
		valueField : 'code',
		textField : 'name',
		loadFilter : function(req) {
			var data = req.list;
			if (null != data) {
				for (var i = 0; i < data.length; i++) {
					var obj = data[i];
				}
			}
			return data;
		}
	});
}

// 当上传时调用该方法
function changePic() {
	previewImage($("input[name='coverUrlFile']")[0], 'img2', 'img');
}
// 展示图片
function previewImage(fileObj, imgPreviewId, divPreviewId) {

	// 指定预览图片宽度
	// $('#' + imgPreviewId).width("300px");

	var allowExtention = ".jpg,.bmp,.gif,.png"; // 允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;
	var extention = fileObj.value.substring(fileObj.value.lastIndexOf(".") + 1).toLowerCase();
	var browserVersion = window.navigator.userAgent.toUpperCase();
	if (allowExtention.indexOf(extention) > -1) {
		if (fileObj.files) { // HTML5实现预览，兼容chrome、火狐7+等
			if (window.FileReader) {
				var reader = new FileReader();
				reader.onload = function(e) {
					document.getElementById(imgPreviewId).setAttribute("src", e.target.result);
				}
				reader.readAsDataURL(fileObj.files[0]);
			} else if (browserVersion.indexOf("SAFARI") > -1) {
				alert("不支持Safari6.0以下浏览器的图片预览!");
			}
		} else if (browserVersion.indexOf("MSIE") > -1) {
			if (browserVersion.indexOf("MSIE 6") > -1) { // ie6
				document.getElementById(imgPreviewId).setAttribute("src", fileObj.value);
			} else { // ie[7-9]
				fileObj.select();
				if (browserVersion.indexOf("MSIE 9") > -1)
					fileObj.blur(); // 不加上document.selection.createRange().text在ie9会拒绝访问
				var newPreview = document.getElementById(divPreviewId + "New");
				if (newPreview == null) {
					newPreview = document.createElement("div");
					newPreview.setAttribute("id", divPreviewId + "New");
					newPreview.style.width = document.getElementById(imgPreviewId).width + "px";
					newPreview.style.height = document.getElementById(imgPreviewId).height + "px";
					newPreview.style.border = "solid 1px #d2e2e2";
				}
				newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";
				var tempDivPreview = document.getElementById(divPreviewId);
				tempDivPreview.parentNode.insertBefore(newPreview, tempDivPreview);
				tempDivPreview.style.display = "none";
			}
		} else if (browserVersion.indexOf("FIREFOX") > -1) { // firefox
			var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
			if (firefoxVersion < 7) { // firefox7以下版本
				document.getElementById(imgPreviewId).setAttribute("src", fileObj.files[0].getAsDataURL());
			} else { // firefox7.0+
				document.getElementById(imgPreviewId).setAttribute("src", window.URL.createObjectURL(fileObj.files[0]));
			}
		} else {
			document.getElementById(imgPreviewId).setAttribute("src", fileObj.value);
		}
	} else {
		alert("仅支持" + allowExtention + "为后缀名的文件!");
		fileObj.value = ""; // 清空选中文件
		if (browserVersion.indexOf("MSIE") > -1) {
			fileObj.select();
			document.selection.clear();
		}
		console.log(fileObj.name);
		$('#' + fileObj.name).filebox('reset');
		$('#' + imgPreviewId).removeAttr("src");
	}
}