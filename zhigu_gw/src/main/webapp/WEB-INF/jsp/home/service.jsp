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
<link href="css/home/other.css" rel="stylesheet">
</head>

<body>
	<!-- 头部 -->
	<jsp:include page="head.jsp"></jsp:include>
	<!-- contact -->
 	<div class="fengge-img">
		<img src="images/1517538218.jpg" alt="">
 	</div>
 	<section class="anli-box">
 		<div class="container">
 			<div class="title">
	 			<h3>服务项目</h3>
	 		</div>
 			<div class="row t-center">
 				<div class="col-lg-12 sv-iconbox">
	 				<div class="col-xs-4 col-md-4 t-center">
	 					<img src="images/web.png" alt="" class="img-responsive block-center">
	 					<h4>网站开发</h4>
	 					<p class="hidden-xs">PC网站、移动端网站、响应式网站</p>
	 				</div>
	 				<div class="col-xs-4 col-md-4">
	 					<img src="images/app.png" alt="" class="img-responsive block-center">
	 					<h4>APP开发</h4>
	 					<p class="hidden-xs">iOS开发、Android开发</p>
	 				</div>
					<div class="col-xs-4 col-md-4">
						<img src="images/xchx.png" alt="" class="img-responsive block-center">
						<h4>小程序开发</h4>
	 					<p class="hidden-xs">微信公众号开发、小程序开发</p>
					</div>
				</div>
				<div class="clear"></div>
 			</div>
 			<!-- 套餐列表 -->
 			<h4 class="t-center mg-tb20 them-bg lh-34px c-fff">关于模板</h4>
 			<div class="taocan">
				<div class="taocan-list col-lg-12">
					<div class="col-lg-2 them-color">
						<h4>基础套餐</h4>
						<h4>300元</h4>
					</div>
					<div class="col-lg-10">
						<p>展示型网站，带后台，pc、手机、平板电脑三合一网站</p>
					</div>
				</div>
				<div class="taocan-list col-lg-12">
					<div class="col-lg-2 them-color">
						<h4>带后台套餐</h4>
						<h4>500元</h4>
					</div>
					<div class="col-lg-10">
						<p>展示型网站，带后台，带后台，pc、手机、平板电脑、微信、微站五合一网站</p>
					</div>
				</div>
				<div class="taocan-list col-lg-12">
					<div class="col-lg-2 them-color">
						<h4>高端定制</h4>
						<h4>1000元起</h4>
					</div>
					<div class="col-lg-10">
						<p>依照客户需求，打造与众不同的网站。定制类网站整体按功能模块来收费</p>
					</div>
 				</div>
 				<div class="clear"></div>
 			</div>
 			<!-- 服务说明 -->
 			<h4 class="t-center mg-tb20 them-bg lh-34px c-fff">服务说明</h4>
 			<div class="taocan">
				<div class="taocan-list col-lg-12">
					<div class="col-lg-2 them-color">
						<h4>后台操作教程</h4>
					</div>
					<div class="col-lg-10">
						<p>提供后台操作教程，小白均可轻松上手。</p>
					</div>
				</div>
				<div class="taocan-list col-lg-12">
					<div class="col-lg-2 them-color">
						<h4>代办备案</h4>
					</div>
					<div class="col-lg-10">
						<p>通常备案周期为十天左右，我们为您免费代办备案</p>
					</div>
				</div>
				<div class="taocan-list col-lg-12">
					<div class="col-lg-2 them-color">
						<h4>送服务器、域名</h4>
					</div>
					<div class="col-lg-10">
						<p>享一年免费服务器、域名，期满客户自己缴费</p>
					</div>
 				</div>
 				<div class="taocan-list col-lg-12">
					<div class="col-lg-2 them-color">
						<h4>技术服务支持</h4>
					</div>
					<div class="col-lg-10">
						<p>免费维护网站，额外添加模块酌情收费</p>
					</div>
 				</div>
 				<div class="clear"></div>
 			</div>
 		</div>	
 	</section>
 	<!-- contact-->
	<!-- footer -->
	<jsp:include page="foot.jsp"></jsp:include>
	<!-- footer -->


</body>
</html>


