 window.onload=function () {
 	loading();
 }
 function loading() {
 	var loading=document.getElementById('loading');
 	loading.style.display="block";
 	var start_time = new Date();
    var end_time = "" ;
    var t = setInterval(function(){
        if(document.readyState=="complete"){aa()}
    },500)
 
    function aa(){
        end_time = new Date();
        var time=end_time.getTime() - start_time.getTime()
        setTimeout(function () {
        	loading.style.display="none";
        },time)
        // alert(end_time.getTime() - start_time.getTime() );
        clearInterval(t);
    }
 }
