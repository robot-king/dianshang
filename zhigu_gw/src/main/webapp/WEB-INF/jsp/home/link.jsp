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
	 			<h3>联系我们</h3>
	 		</div>
 			<div class="row">
 				<div class="col-lg-12">
					<p>
						<span class="glyphicon glyphicon-home"></span>
						&nbsp;&nbsp;地址：&nbsp;北京市朝阳区十八里店
					</p>
					<p>
                        <span class="glyphicon glyphicon-phone-alt"></span>
          					&nbsp;&nbsp;电话：&nbsp;18910621537/18001184528 
          			</p>
                    <p>
                       	<span class="glyphicon glyphicon-envelope"></span>
						&nbsp;&nbsp;QQ：&nbsp;1254953990
					</p>
 				</div>
 				<!-- <div class="col-sm-4 col-md-4 col-lg-12">
 					<div class="thumbnail">
			            <img src="images/240450-140HZSQ7100.jpg" alt="">
			        </div>
 				</div> -->
 				<div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
 					<div class="thumbnail">
			            <img src="images/weixin_zhang.png" alt="">
			            <div class="caption t-center">
			                <p>智谷科技</p>
			            </div>
			        </div>
 				</div>
 				<div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
					<div class="thumbnail">
			            <img src="images/weixin_cao.jpg" alt="">
			            <div class="caption t-center">
			                <p>风林孤雨</p>
			            </div>
			        </div>
 				</div>
 			</div>
 		</div>	
 	</section>
 	<!-- contact-->
	<!-- footer -->
	<jsp:include page="foot.jsp"></jsp:include>
	<!-- footer -->

</body>
</html>


