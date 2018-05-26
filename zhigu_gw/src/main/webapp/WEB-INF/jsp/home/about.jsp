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
	 			<h3>关于我们</h3>
	 		</div>
 			<div class="row">
 				<div class="col-lg-12">
	 				<a href="#" class="thumbnail no-border">
			            <img class="img-responsive" src="images/1517537536133945.jpg" alt="">
			        </a>
		        </div>
 			</div>	
 			<div class="">
 				<div class="col-lg-3"></div>
 				<div class="col-lg-6">
					<h4 class="media-heading t-center lh-34px">智谷故事</h4>
					<p>也许雾霾便是北京的特色之一吧。</p>
					<p>
						这里汇聚了全国很多优秀的资源，IT行业便是其中一个。
					</p>
					<p>
						张和Java语言打交道已经有七八个年头了，这个行业有着改不完的bug，理不完的逻辑，加不完的班，还有听不完的死亡新闻。是的，能在程序代码堆里摸爬滚打这么多年的，或许是高薪或许是兴趣吧。
					</p>
					<p>
						张说：“我们有的是技术，为什么不创业呢？”。</br>
						创业这个词承载着多少人的梦想。有人成功，家财万贯。有人失败，嘲笑和鼓励纷至沓来。有人还在徘徊，其中滋味只有经历者自知。
						</p>
					<p>
						从成立到现在，“智谷科技”团队有收获，有失落，但不忘初心，以专注专业的精神稳步前行。</br>
						选择智谷，您不会失望
					</p>		
 				</div>
 			</div>
 		</div>	
 	</section>
 	<!-- contact-->
	<!-- footer -->
	<jsp:include page="foot.jsp"></jsp:include>
	<!-- footer -->
	
	<script src="js/home/pageClick.js"></script>     

</body>
</html>


