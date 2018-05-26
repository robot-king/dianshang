// 设置员工信息弹框
function editEmployee() {
    $('#employee_dialog').dialog('open').dialog('setTitle', '设置用户信息');
    $('#nickName').textbox('setValue',_nickName);
    $('#img2').attr("src",_iconUrl);
}
// 关闭弹框
function closeEmployee() {
    $('#employee_dialog').dialog('close');
}
// 保存员工信息
function save() {
    $('#fm').form('submit', {
	url : baseurl + 'employee/updatePwd.do',
	onSubmit : function() {
	    var status = false;
	    if ($(this).form('enableValidation').form('validate')) {
		status = true;
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
		var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
		setTimeout(function(){
		    parent.window.location.href = baseurl + 'employee/exit.do';
		}, 2000);
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
