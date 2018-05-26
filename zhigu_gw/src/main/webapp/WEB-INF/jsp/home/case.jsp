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
	 			<h3>案例展示</h3>
	 		</div>
 			<div class="row t-center" id="data_grid">
 			</div>
 			<!-- 分页按钮 -->
	 		<ul class="pager" id="page">
			</ul>
 		</div>	
 	</section>
 	<!-- contact-->
	<!-- footer -->
	<jsp:include page="foot.jsp"></jsp:include>
	<!-- footer -->
	
	<script src="js/home/pageClick.js"></script>     
	<script src="js/home/case_list_1.js"></script>     

</body>
</html>


