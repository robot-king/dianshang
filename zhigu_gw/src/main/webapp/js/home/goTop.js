$(function () {
	goTop();
})
var goTop=function (){
	var Top=$('.go-top');
	Top.click(function () {
		$('body,html').animate({'scrollTop':0});
	});
}