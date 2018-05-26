//
$(function() {
	loadData();
});

// 案例
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
						type : 1,
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
								if(obj.coverUrl != null) {
									moren_img = obj.coverUrl;
								}
								arr
										.push('<a href="javascript:;" target="_blank" class="thul-img"><img src="'+moren_img+'" alt=""></a>');
								arr.push('<div class="caption">');
								arr.push('<h4>'+obj.title+'</h4>');
								var moren_access = "javascript:;";
								moren_access = obj.accessUrl==null?obj.feiAccessUrl:obj.accessUrl;
								arr
										.push('<a href="'+moren_access+'" target="_blank" class="them-btn them-bg">进入浏览</a>');
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
	arr.push('<li class="yeshu"><span><b>' + req.pageNum
			+ '</b>/<b>' + req.pages
			+ '</b></li>');
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
