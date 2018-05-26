/**
 * https://github.com/binnng/time.js
 * 毫秒转换友好的显示格式
 * 输出格式：21小时前
 * @param  {[type]} time [description]
 * @return {[type]}      [description]
 */
/*function dateStr(date){
    //获取js 时间戳
    var time=new Date().getTime();
    //去掉 js 时间戳后三位，与php 时间戳保持一致
    time=parseInt((time-date*1000)/1000);

    //存储转换值 
    var s;
    if(time<60*10){//十分钟内
        return '刚刚';
    }else if((time<60*60)&&(time>=60*10)){
        //超过十分钟少于1小时
        s = Math.floor(time/60);
        return  s+"分钟前";
    }else if((time<60*60*24)&&(time>=60*60)){ 
        //超过1小时少于24小时
        s = Math.floor(time/60/60);
        return  s+"小时前";
    }else if((time<60*60*24*3)&&(time>=60*60*24)){ 
        //超过1天少于3天内
        s = Math.floor(time/60/60/24);
        return s+"天前";
    }else{ 
        //超过3天
        var date= new Date(parseInt(date) * 1000);
        return date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
    }
}
*/
(function(WIN) {
  var DAY, DEFAULT_FORMAT, HOUR, MINUTE, MONTH, SECOND, YEAR, angularApp, entry, exports, getFullTime, map, replace, time, two, unify;
  YEAR = "year";
  MONTH = "month";
  DAY = "day";
  HOUR = "hour";
  MINUTE = "minute";
  SECOND = "second";
  DEFAULT_FORMAT = "%y-%M-%d %h:%m:%s";
  map = {
    "%y": YEAR,
    "%M": MONTH,
    "%d": DAY,
    "%h": HOUR,
    "%m": MINUTE,
    "%s": SECOND
  };
  unify = function(time) {
    time -= 0;
    if (("" + time).length === 10) {
      time *= 1000;
    }
    return time;
  };
  two = function(str) {
    var s;
    s = "" + str;
    if (s.length === 1) {
      s = "0" + s;
    }
    return s;
  };
  replace = function(str, src, dst) {
    var reg;
    reg = new RegExp(src, "g");
    return str.replace(reg, dst);
  };
  getFullTime = function(time) {
    var date;
    date = new Date(unify(time));
    return {
      year: date.getFullYear(),
      month: two(date.getMonth() + 1),
      day: two(date.getDate()),
      hour: two(date.getHours()),
      minute: two(date.getMinutes()),
      second: two(date.getSeconds())
    };
  };
  time = {
    "default": function(time, format) {
      var fullTime, ret, src;
      if (format && (typeof format) !== "string") {
        throw new Error("format must be a string.");
      }
      fullTime = getFullTime(time);
      ret = format || DEFAULT_FORMAT;
      for (src in map) {
        ret = replace(ret, src, fullTime[map[src]]);
      }
      return ret;
    },
    human: function(time) {
      var ago, curTime, diff, int;
      time = unify(time);
      int = parseInt;
      curTime = +new Date();
      diff = curTime - time;
      ago = "";
      if (1000 * 60 > diff) {
        ago = "刚刚";
      } else if (1000 * 60 <= diff && 1000 * 60 * 60 > diff) {
        ago = int(diff / (1000 * 60)) + "分钟前";
      } else if (1000 * 60 * 60 <= diff && 1000 * 60 * 60 * 24 > diff) {
        ago = int(diff / (1000 * 60 * 60)) + "小时前";
      } else if (1000 * 60 * 60 * 24 <= diff && 1000 * 60 * 60 * 24 * 30 > diff) {
        ago = int(diff / (1000 * 60 * 60 * 24)) + "天前";
      } else if (1000 * 60 * 60 * 24 * 30 <= diff && 1000 * 60 * 60 * 24 * 30 * 12 > diff) {
        ago = int(diff / (1000 * 60 * 60 * 24 * 30)) + "月前";
      } else {
        ago = int(diff / (1000 * 60 * 60 * 24 * 30 * 12)) + "年前";
      }
      return ago;
    }
  };
  entry = time["default"];
  entry.human = entry.ago = time.human;
  if (typeof module !== "undefined" && module.exports) {
    return module.exports = exports = entry;
  } else if (typeof WIN["define"] === "function") {
    return define(function(require, exports, module) {
      return module.exports = exports = function() {
        return entry;
      };
    });
  } else if (typeof WIN["angular"] === "object") {
    angularApp = angular.module("binnng/time", []);
    angularApp.factory("$time", function() {
      return entry;
    });
    angularApp.filter("ago", function() {
      return function(time) {
        return entry.ago(time);
      };
    });
    angularApp.filter("date", function() {
      return function(time) {
        return entry(time, "%y年%M月%d日");
      };
    });
    return angularApp.filter("datetime", function() {
      return function(time) {
        return entry(time, DEFAULT_FORMAT);
      };
    });
  } else {
    return WIN["Time"] = entry;
  }
})(window);