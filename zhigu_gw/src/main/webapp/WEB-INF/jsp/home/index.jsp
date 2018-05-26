<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="commonTag.jsp"%>
<%@ include file="common.jsp"%>

<head>
<base href="${baseurl }">
<title>智谷科技工作室</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="${seo_tag }">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="icon" href="images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />

<!--[if lt IE 9]>
	<script src="js/home/html5shiv.js"></script>
	<script src="js/home/respond.min.js"></script>
	<![endif]-->

<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/home/global.css" rel="stylesheet">
<link href="css/home/index.css" rel="stylesheet">
<link href="css/home/mask.css" rel="stylesheet">
<link href="plugins/css/animate.css" rel="stylesheet">
</head>

<body>
	<!-- 头部 -->
	<jsp:include page="head.jsp"></jsp:include>
	<!-- home -->
	<section id="home">
		<div id="myCarousel" class="carousel slide">
			<!-- 轮播（Carousel）指标 -->
			<ol class="carousel-indicators hidden-xs">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
			</ol>
			<!-- 轮播（Carousel）项目 -->
			<div class="carousel-inner">
				<div class="item active">
					<img src="images/banner1.jpg" alt="First slide">
				</div>
				<div class="item">
					<img src="images/banner2.jpg" alt="Second slide">
				</div>
			</div>
			<!-- 轮播（Carousel）导航 -->
			<a class="left carousel-control" href="#myCarousel" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left"></span>
			</a> <a class="right carousel-control" href="#myCarousel"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right"></span>
			</a>
		</div>
	</section>
	<!-- home -->
	<!-- bbs-->
	<section id="bbs">
		<div class="container">
			<div class="title">
				<h3>服务项目</h3>
				<p>Service</p>
			</div>
			<div class="row wow fadeInRight">
				<div class="col-xs-4 col-md-4">
					<img src="images/web.png" alt="" class="img-responsive">
					<h3>网站开发</h3>
					<p class="hidden-xs">PC网站、移动端网站、响应式网站</p>
				</div>
				<div class="col-xs-4 col-md-4">
					<img src="images/app.png" alt="" class="img-responsive">
					<h3>APP开发</h3>
					<p class="hidden-xs">iOS开发、Android开发</p>
				</div>
				<div class="col-xs-4 col-md-4">
					<img src="images/xchx.png" alt="" class="img-responsive">
					<h3>小程序开发</h3>
					<p class="hidden-xs">微信公众号开发、小程序开发</p>
				</div>
			</div>

		</div>
	</section>
	<!-- bbs-->
	<!-- html5-->
	<section id="html5">
		<div class="container">
			<div class="title">
				<h3>案例展示</h3>
				<p>Case</p>
			</div>
			<div class="row">
				<div class="col-md-3 anli-left">
					<img src="images/anli.jpg" alt="" class="img-responsive">
				</div>
				<div class="col-md-6 anli-middle">
					<img src="images/anli.jpg" alt="" class="img-responsive">
				</div>
				<div class="col-md-3 anli-right">
					<img src="images/anli.jpg" alt="" class="img-responsive">
				</div>
				<div class="col-lg-12 t-center mg-tb20">
					<a href="case" class="them-btn them-bg">查看更多</a>
				</div>
			</div>
		</div>
	</section>
	<!-- html5-->
	<!-- course-->
	<section id="course">
		<div class="container">
			<div class="title">
				<h3>关于我们</h3>
				<p>About Us</p>
			</div>
			<div id="myCarousel2" class="carousel slide">
				<!-- 轮播（Carousel）项目 -->
				<div class="carousel-inner">
					<div class="item active">
						<div class="col-lg-12">
							<div class="col-lg-3"></div>
							<div class="col-lg-3">
								<img src="images/ui.png" alt="First slide"
									class="img-responsive">
							</div>
							<div class="col-lg-4">
								<h3 class="them-color">UI设计师</h3>
								<p>5年UI设计经验，精通网页设计，logo设计</p>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="col-lg-12">
							<div class="col-lg-4"></div>
							<div class="col-lg-3">
								<img src="images/qianduan.png" alt="First slide"
									class="img-responsive">
							</div>
							<div class="col-lg-4">
								<h3 class="them-color">前端工程师</h3>
								<p>从事前端4年，精通PC端、移动端、自适应网页</p>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="col-lg-12">
							<div class="col-lg-4"></div>
							<div class="col-lg-3">
								<img src="images/houduan.png" alt="First slide"
									class="img-responsive">
							</div>
							<div class="col-lg-4">
								<h3 class="them-color">后端工程师</h3>
								<p>从事后端开发7年，精通JAVA、PHP等后端语言</p>
							</div>
						</div>
					</div>
				</div>
				<!-- 轮播（Carousel）导航 -->
				<a class="left carousel-control" href="#myCarousel2"
					data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left"></span>
				</a> <a class="right carousel-control" href="#myCarousel2"
					data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right"></span>
				</a>
			</div>
		</div>
	</section>
	<!-- contact -->
	<section id="contact">
		<div class="lvjing">
			<div class="container">
				<div class="title">
					<h3 class="c-fff">联系我们</h3>
					<p>Link Us</p>
				</div>
				<div class="row contact-text">
					<div class="col-md-12 wow fadeInLeft">
						<p>
							智谷科技工作室由几个有些梦想但不甘于现状的程序员组成，在当今创业大潮的推动下，毅然放弃了机械化、固定化的上班模式，虽然年轻，但踏实能干。
						</p>
						<p>
							<span class="glyphicon glyphicon-home"></span>
							&nbsp;&nbsp;地址：&nbsp;北京市朝阳区十八里店
						</p>
						<p>
							<span class="glyphicon glyphicon-phone-alt"></span>
							&nbsp;&nbsp;联系电话：&nbsp;18910621537
						</p>
						<p>
							<span class="glyphicon glyphicon-envelope"></span>
							&nbsp;&nbsp;邮箱：&nbsp;1254953990@qq.com
						</p>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- contact-->
	<!-- footer -->
	<jsp:include page="foot.jsp"></jsp:include>
	<!-- footer -->
	<!-- 右侧浮动栏 -->
	<div class="zside-bar">
		<div class="qq">
			<a
				href="http://wpa.qq.com/msgrd?v=3&uin=1254953990&site=qq&menu=yes"
				target="_blank"> <img src="images/qq_icon.png" alt="QQ">
			</a>
		</div>
		<div class="weixin">
			<img src="images/weixin_icon.png" alt="微信">
		</div>
		<div class="go-top">
			<span class="glyphicon glyphicon-menu-up"></span>
		</div>
	</div>
	<!-- 微信图片 -->
	<div class="mask">
		<img src="images/weixin_zhang.png" alt="" class="wx-img">
	</div>

	<script src="plugins/js/wow.min.js"></script>
	<script src="js/home/weixinShow.js"></script>
	<script src="js/home/goTop.js"></script>
	<script>
		$(function() {
			new WOW().init();
			accessRecord();
		})
		// 访问记录
		function accessRecord() {
			$.post(baseurl + "accessRecord/accessRecordAdd.action", {},
					function(data, status) {
					}, "json");
		}
	</script>

</body>
</html>


