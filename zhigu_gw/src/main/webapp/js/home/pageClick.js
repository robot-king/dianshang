$(function (argument) {
	pageClick();
})
var pageClick=function (){
	var aA=$('.pager li').find('a');
	aA.each(function (index) {
		$(this).click(function () {
			aA.removeClass('active');
			aA.eq(index).addClass('active');
		});
	})
}