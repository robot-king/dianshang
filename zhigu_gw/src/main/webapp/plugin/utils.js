// js函数代码：字符串转换为时间戳
function getDateTimeStamp(dateStr) {
    return Date.parse(dateStr.replace(/-/gi, "/"));
}

/**
 * 获取上一个月
 * 
 * @date 格式为yyyy-mm-dd的日期，如：2014-01-25
 */
function getPreMonth(date) {
    var arr = date.split('-');
    var year = arr[0]; // 获取当前日期的年份
    var month = arr[1]; // 获取当前日期的月份
    var day = arr[2]; // 获取当前日期的日
    var days = new Date(year, month, 0);
    days = days.getDate(); // 获取当前日期中月的天数
    var year2 = year;
    var month2 = parseInt(month) - 1;
    if (month2 == 0) {
	year2 = parseInt(year2) - 1;
	month2 = 12;
    }
    var day2 = day;
    var days2 = new Date(year2, month2, 0);
    days2 = days2.getDate();
    if (day2 > days2) {
	day2 = days2;
    }
    if (month2 < 10) {
	month2 = '0' + month2;
    }
    var t2 = year2 + '-' + month2 + '-' + day2;
    return t2;
}

/**
 * 获取下一个月
 * 
 * @date 格式为yyyy-mm-dd的日期，如：2014-01-25
 */
function getNextMonth(date) {
    var arr = date.split('-');
    var year = arr[0]; // 获取当前日期的年份
    var month = arr[1]; // 获取当前日期的月份
    var day = arr[2]; // 获取当前日期的日
    var days = new Date(year, month, 0);
    days = days.getDate(); // 获取当前日期中的月的天数
    var year2 = year;
    var month2 = parseInt(month) + 1;
    if (month2 == 13) {
	year2 = parseInt(year2) + 1;
	month2 = 1;
    }
    var day2 = day;
    var days2 = new Date(year2, month2, 0);
    days2 = days2.getDate();
    if (day2 > days2) {
	day2 = days2;
    }
    if (month2 < 10) {
	month2 = '0' + month2;
    }

    var t2 = year2 + '-' + month2 + '-' + day2;
    return t2;
}

/**
 * 对Date的扩展，将 Date 转化为指定格式的String 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
 * 可以用 1-2 个占位符 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) eg: (new
 * Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 (new
 * Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04 (new
 * Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04 (new
 * Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04 (new
 * Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */        
Date.prototype.pattern=function(fmt) {         
    var o = {         
    "M+" : this.getMonth()+1, // 月份
    "d+" : this.getDate(), // 日
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, // 小时
    "H+" : this.getHours(), // 小时
    "m+" : this.getMinutes(), // 分
    "s+" : this.getSeconds(), // 秒
    "q+" : Math.floor((this.getMonth()+3)/3), // 季度
    "S" : this.getMilliseconds() // 毫秒
    };         
    var week = {         
    "0" : "/u65e5",         
    "1" : "/u4e00",         
    "2" : "/u4e8c",         
    "3" : "/u4e09",         
    "4" : "/u56db",         
    "5" : "/u4e94",         
    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
}       

/*$(function(){
    var date = new Date();        
    window.alert(date.pattern("yyyy-MM-dd hh:mm:ss"));  
});*/