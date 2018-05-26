$(function (argument) {
//	searchClick();
//	searchWidth();
})
var searchClick=function (){
	var searchBtn=$('.chaxun-box .chaxun-list').find('a');
	searchBtn.each(function (index) {
		$(this).click(function () {
			searchBtn.removeClass('active');
			searchBtn.eq(index).addClass('active');
		});
	})
}
var searchWidth=function (){
	var chaxunList=$('.chaxun-list');
	var aA=chaxunList.find('a');
	var width=0;
	aA.each(function () {
		width+=parseInt($(this).outerWidth(true));	
	});
//	chaxunList.css({'width':width});
}