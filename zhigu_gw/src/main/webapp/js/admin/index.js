// 菜单url
var menu_url = 'menu/menuData.do';
var dict_list = [];
var menus_data = [];
$(function() {
	$('#tabs').tabs('add', {
		title : '欢迎使用',
		content : createFrame(baseurl + 'employee/statics.do')
	});

	loadLeftMenu(); // 动态加载 导航条，根据后台菜单类型配置
	dictList();
	login();
});
function loadLeftMenu() {
	$.post(baseurl + menu_url, {}, function(result) {
		if (result.success) {
			menus_data = result.data;
			// console.log(JSON.stringify(menus_data));
			initMenu(menus_data);
		} else {
			$.messager.show({ // show error message
				title : '提示',
				msg : result.msg
			});
		}
	}, 'json');

}

// 将后台获取的json菜单数据，组织成html
function initMenu(menus_data) {

	// 循环处理json的菜单数据，组织成html
	$.each(menus_data, function(i, n) { // 外层循环处理一级菜单
		var menulist = '';
		menulist += '<ul class="easyui-datalist" data-options="border:false,fit:true">';
		$.each(n.children, function(j, o) { // 二层处理二级菜单
			// 这里自定义了一些属性存放菜单的内容：title存放菜单名称rel存放菜单地址，这些在属性在点击菜单 时要取出值使用
			menulist += '<li>' + o.text + '</li> ';
		});
		menulist += '</ul>'; // 生成了菜单的html

		var target_id = "nav_" + n.id;
		menulist = '<div id="' + target_id + '"></div>';

		// 自动创建菜单
		$('#nav').accordion('add', {
			title : n.text,
			content : menulist
		});
		// 业务管理管理默认展开
		$('#nav').accordion('select', 2);

		$('#' + target_id).datalist({
			data : n.children,
			cls : 'easyui-datalist',
			onClickRow : function(index, row) {
				addTab(row.text, row.url);
			}
		});

	});
}

// 添加选项卡
function addTab(subtitle, url) {
	if (subtitle == "") {
		$.messager.alert('错误信息', '无法创建空标签页');
		return;
	}
	// 如果不存在tab
	if (!$('#tabs').tabs('exists', subtitle)) {
		var tabs = $('#tabs').tabs('add', {
			title : subtitle,
			content : createFrame(url),
			cache : false,
			closable : true,
			width : $('#center').width() - 10,
			height : $('#center').height() - 26
		});
	} else {
		// 当重新选中,重新加载一遍
		// 根据标题获取tab对象
		var currTab = $('#tabs').tabs('getTab', subtitle);
		$('#tabs').tabs('update', {
			tab : currTab,
			options : {
				content : createFrame(url)
			}
		});
		$('#tabs').tabs('select', subtitle);
	}

}

function createFrame(url) {
	if (url.indexOf("?") > 0) {
		url += "&t=" + new Date().getTime();
	} else {
		url += "?t=" + new Date().getTime();
	}
	var s = '<iframe id="myframe" name="myframe" onload="window.status=\'成功加载完毕\'" scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:98%;"></iframe>';
	return s;
}

function dictList() {
	$.post(baseurl + 'dict/list.do', {}, function(result) {
		if (result.success) {
			dict_list = result.data;
		} else {
			$.messager.show({ // show error message
				title : '提示',
				msg : result.msg
			});
		}
	}, 'json');
}

// 通过code查询名称
function dictValue(key) {
	var name = "";
	if (null != dict_list) {
		for (var i = 0; i < dict_list.length; i++) {
			var dict = dict_list[i];
			if (dict.code == key) {
				console.log(dict.name);
				name = dict.name;
				break;
			}
		}
	}
	return name;
}

// 通过code查询父节点code
function dictParentCode(key) {
	var code = "";
	if (null != dict_list) {
		for (var i = 0; i < dict_list.length; i++) {
			var dict = dict_list[i];
			if (dict.code == key) {
				code = dict.parentCode;
				break;
			}
		}
	}
	return code;
}

function login() {
	$('#fm').submit(function() {
		$.ajax({
			type : "POST",
			dataType : "html",
			url : baseurl + "employee/login.do",
			data : $('#fm').serialize(),
			success : function(res) {
				var result = eval('(' + res + ')');
				if (result.success) {
					$.messager.show({
						title : '提示',
						msg : result.msg,
						timeout : 5000,
						showType : 'slide'
					});
					var index = layer.load(0, {
						shade : false
					}); // 0代表加载的风格，支持0-2
					setTimeout(function() {
						parent.window.location.href = baseurl + "employee/index.do"
					}, 2000);
				} else {
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				}
			},
			error : function(data) {
			}
		});
		return false; // 阻止表单自动提交事件
	});
}
