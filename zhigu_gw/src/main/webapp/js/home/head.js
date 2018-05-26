$(function () {
	navClick();
})
var navClick=function (){
	// 当菜单被点击时
	$(".nav li").click(function(){
		
		$(this).find('a').addClass('curr');
		$(this).siblings().find('a').removeClass('curr');
		
	});
	
	// 当进入页面时，根据URL判断哪个显示选中状态
	var url = window.location.href;
	console.log(url);
	url = url.substring(url.lastIndexOf("/") + 1,url.length);
	console.log(url);
	if(url != undefined && url != '') {
		$(".nav li a").each(function(){
			var menu = $(this).attr("href");
			if(url == menu) {
				$(this).addClass('curr');
				$(this).siblings().removeClass('curr');
				return;
			}
		});
		
	} else {
		$(".nav li").eq(0).click();
	}
}