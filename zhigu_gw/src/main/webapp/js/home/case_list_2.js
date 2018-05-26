//
$(function() {
	loadData();
	conditionType();
});

// 案例
var web_type = "";
function loadData(page) {
	if (typeof (page) == 'undefined' || page == 0) {
		page = 1;
	}
	$('#data_grid').html("");
	$
			.post(
					baseurl + "webSite/queryList.action",
					{
						page : page,
						type : 2,
						webType:web_type,
						rows : 6
					},
					function(req, status) {

						var list = req.list;
						inventory_list = req.list;
						data_list = req.list;
						if (null != list) {

							for (var i = 0; i < list.length; i++) {
								var obj = list[i];

								var arr = new Array();

								arr
										.push('<div class="col-sm-6 col-md-4 col-lg-4">');
								arr.push('<div class="thumbnail show-thul">');
								var moren_img = "images/anli.jpg";
								if (obj.coverUrl != null) {
									moren_img = obj.coverUrl;
								}
								arr
										.push('<a href="javascript:;" target="_blank" class="thul-img"><img src="'+moren_img+'" alt=""></a>');
								arr.push('<div class="caption">');
								var title = obj.title;
								if(title.length > 6)　{
									title = title.substring(0,title.indexOf("模板") + 2);
									if(title.length > 16)　{
										title = title.substring(0,16) + "...";
									}
								}
								arr.push('<h4>' + title + '</h4>');
								var moren_access = "javascript:;";
								if (obj.accessUrl != null) {
									moren_access = obj.accessUrl;
								}
								arr
										.push('<a href="'
												+ moren_access
												+ '" target="_blank" class="them-btn them-bg">进入浏览</a>');
								var moren_price = "";
								if (obj.price != null) {
									moren_price = obj.price;
								}
								arr.push('<b class="fr them-color lh-34px">ID:'+obj.id+' &nbsp;￥'
										+ moren_price + '</b>');
								arr.push('</div>');
								arr.push('</div>');
								arr.push('</div>');

								$('#data_grid').append(arr.join(""));
							}
						}

						pagination(req, "loadData");
					}, "json");
}

// 分页
function pagination(req, pageData) {
	// pc版
	$('#page').html("");
	var arr = new Array();

	if (req.hasPreviousPage) {
		arr.push('<li><a href="javascript:;" onclick="' + pageData + '('
				+ req.prePage + ');">上一页</a></li>');
	}
	arr.push('<li class="yeshu"><span><b>' + req.pageNum + '</b>/<b>'
			+ req.pages + '</b></li>');
	if (req.hasNextPage) {
		arr.push('<li><a href="javascript:;" onclick="' + pageData + '('
				+ req.nextPage + ');">下一页</a></li>');
	}

	$('#page').append(arr.join(""));

	// 点击效果
	$('#page').find("li").each(function() {
		// console.log($(this).text());
		if ($(this).find("a").text() == req.pageNum) {
			$(this).addClass('active');
			return;
		}
	});
}
// 条件
function conditionType() {
	$.post(baseurl + 'dict/list.do', {
		"parentCode" : "webType"
	}, function(result) {
		if (result.success) {
			var list = result.data;
			if (null != list) {


				for (var i = 0; i < list.length; i++) {
					var obj = list[i];
					var arr = new Array();
					if(i == 0) {
						arr.push('<a href="javascript:;" class="active" onclick="conditionClick(\'\');">全部</a>');
					}

					arr.push('<a href="javascript:;" onclick="conditionClick(\''+obj.code+'\');">' + obj.name + '</a>');
					$('#condition_grid').append(arr.join(""));
				}

				searchClick();
				searchWidth();
			}
		}
	}, 'json');
}

// 点击条件
function conditionClick(p){
	web_type = p;
	console.log(p);
	loadData();
}