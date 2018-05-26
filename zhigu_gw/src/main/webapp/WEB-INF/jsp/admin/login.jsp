<!DOCTYPE html>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commonTag.jsp"%>
<%@ include file="common.jsp"%>

<head>
<base href="${baseurl }">
<title>后台管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="css/admin/login.css">
<script type="text/javascript" src="${baseurl }js/admin/index.js"></script>
</head>


<body class="login" mycollectionplug="bind">
	<div class="login_m">
		<div class="login_logo">
			<img src="images/admin/logo.png" width="196" height="46">
		</div>
		<div class="login_boder">

			<form action="" method="post" id="fm">
				<div class="login_padding" id="login_model">

					<h2>用户名</h2>
					<label> <input type="text" id="username" name="nickName"
						class="txt_input txt_input2" value="">
					</label>
					<h2>密码</h2>
					<label> <input type="password" name="pwd"
						id="userpwd" class="txt_input" value="">
					</label>

					<p class="forgot">
						<a id="iforget" href="javascript:void(0);"> </a>
					</p>
					<div class="rem_sub">
						<div class="rem_sub_l">
							<input type="checkbox" name="checkbox" id="save_me"> <label
								for="checkbox">记住</label>
						</div>
						<label> <input type="submit" class="sub_button"
							name="button" id="sub_button" value="登录" style="opacity: 0.7;">
						</label>
					</div>
				</div>
			</form>
			
			<div id="forget_model" class="login_padding" style="display: none">
				<br>

				<h1>Forgot password</h1>
				<br>
				<div class="forget_model_h2">(Please enter your registered
					email below and the system will automatically reset users’ password
					and send it to user’s registered email address.)</div>
				<label> <input type="text" id="usrmail"
					class="txt_input txt_input2">
				</label>

				<div class="rem_sub">
					<div class="rem_sub_l"></div>
					<label> <input type="submit" class="sub_buttons"
						name="button" id="Retrievenow" value="Retrieve now"
						style="opacity: 0.7;"> <input type="submit"
						class="sub_button" name="button" id="denglou" value="Return"
						style="opacity: 0.7;">

					</label>
				</div>
			</div>


			<!--login_padding  Sign up end-->
		</div>
		<!--login_boder end-->
	</div>
	<!--login_m end-->
	<br>
	<br>



</body>
</html>
