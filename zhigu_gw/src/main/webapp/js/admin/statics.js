$(function() {

	var dflt1 = {
		min : 0,
		max : 2000,
		donut : true,
		gaugeWidthScale : 0.6,
		counter : true,
		hideInnerShadow : true
	}
	var dflt2 = {
		min : 0,
		max : 10000,
		donut : true,
		gaugeWidthScale : 0.6,
		counter : true,
		hideInnerShadow : true
	}
	var dflt3 = {
		min : 0,
		max : 50000,
		donut : true,
		gaugeWidthScale : 0.6,
		counter : true,
		hideInnerShadow : true
	}
	var dflt4 = {
		min : 0,
		max : 1000,
		donut : true,
		gaugeWidthScale : 0.6,
		counter : true,
		hideInnerShadow : true
	}
	var gg1 = new JustGage({
		id : 'gg1',
		defaults : dflt1
	});

	var gg2 = new JustGage({
		id : 'gg2',
		defaults : dflt2
	});
	var gg3 = new JustGage({
		id : 'gg3',
		defaults : dflt3
	});
	var gg4 = new JustGage({
		id : 'gg4',
		defaults : dflt4
	});

});

$(function() {
	var data;
	var xyData = [];
	var expireDate;
	var date = new Date();
	var nowTime = date.pattern("yyyy-MM-dd");
	var passTime = getPreMonth(nowTime);
	expireDate = passTime + "~~" + nowTime;
	$.ajax({
		type : "POST",
		dataType : "json",
		url : baseurl + "accessRecord/queryList.do",
		async : false,
		success : function(res) {
			if (res.success) {
				data = res.data;
				for (var i = 0; i < data.length; i++) {
					var obj = data[i];
					var someDate = new Date(Date.parse(obj.createTime));
					var tms = Date.UTC(someDate.getFullYear(), someDate.getMonth(), someDate.getDate(), someDate
							.getHours(), someDate.getMinutes());
					xyData.push([ tms, obj.accessCount ]);
				}
			}
		},
		error : function(data) {
		}
	});
	$('#charts').highcharts({
		credits : {
			enabled : false
		},
		chart : {
			type : 'spline',
			events : {
				load : function() {

				}
			}
		},
		title : {
			text : '一个月内每天访问量'
		},
		subtitle : {
			text : expireDate
		},
		xAxis : {
			type : 'datetime',
			tickInterval : 1 * 24 * 3600 * 1000, // 坐标轴刻度间隔为一天
			gridLineWidth : 1,
			dateTimeLabelFormats : {
				day : '%Y-%m-%d'
			}
		},
		yAxis : {
			title : {
				text : '每天访问量'
			},
			min : 0,
			minorGridLineWidth : 0,
			gridLineWidth : 1,
			alternateGridColor : null,
			labels : {
				step : 2
			}
		},
		tooltip : {
			valueSuffix : ' 次/天',
			formatter : function() {
				return Highcharts.dateFormat('%Y-%m-%d', this.x) + '<br/> ' + this.y + ' 次/天';
			}
		},
		plotOptions : {
			spline : {
				lineWidth : 4,
				states : {
					hover : {
						lineWidth : 5
					}
				},
				marker : {
					enabled : false
				},
				pointInterval : 3600000, // one hour
				pointStart : Date.UTC(2009, 9, 6, 0, 0, 0)
			}
		},
		series : [ {
			name : '访问量',
			data : xyData
		} ],
		navigation : {
			menuItemStyle : {
				fontSize : '10px'
			}
		}
	});
});

document.addEventListener("DOMContentLoaded", function() {
	// 完成所有页面处理后
	// 重置宽度
	var chart = $('#charts').highcharts();
	chart.reflow();
});
// 设置员工信息弹框
function editEmployee() {
	$('#nickName').textbox('setValue', _nickName);
	$('#iconUrlFile').filebox('setText', _iconUrl);
	$('#employee_dialog').dialog('open').dialog('setTitle', '设置用户信息');
}
// 关闭弹框
function closeEmployee() {
	$('#employee_dialog').dialog('close');
}
// 保存员工信息
function save() {
	$('#fm').form('submit', {
		url : baseurl + 'employee/updateIconUrl.do',
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
// 当上传时调用该方法
function changePic() {
	PreviewImage($("input[name='iconUrlFile']")[0], 'img2', 'img');
}
// 展示图片
function PreviewImage(fileObj, imgPreviewId, divPreviewId) {

	// 指定预览图片宽度
	$('#' + imgPreviewId).width("300px");

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
				newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='"
						+ document.selection.createRange().text + "')";
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
		// fileObj.outerHTML = fileObj.outerHTML;
		$('#coverUrlFile').filebox('reset');
		// $('#coverUrlFile').filebox('setText', "");
		$('#img2').removeAttr("src");
	}
}