 window.onload=function () {
 	weixinShow();
 }
 function weixinShow() {
 	var mask=$('.mask');
    var weixin=$('.weixin');
    weixin.click(function (ev) {
        ev.stopPropagation();
        mask.fadeIn();
        if (mask.fadeIn()) 
        {
           $(document).click(function () {
              mask.fadeOut()
           })
        }
    });
    
 }
