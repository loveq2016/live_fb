<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
%>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>后台管理系统登录界面</title>

	
	<link href="${resourceRoot}/img/favicon.ico" rel="shortcut icon">
	<link href="${resourceRoot}/css/bootstrap.min.css" rel="stylesheet">
	<link href="${resourceRoot}/css/font-awesome.min.css" rel="stylesheet">
	<link href="${resourceRoot}/css/animate.min.css" rel="stylesheet">
	<link href="${resourceRoot}/css/style.min.css" rel="stylesheet">
	<link href="${resourceRoot}/css/whole.css" rel="stylesheet" />

	<script>
		var baseUrl = "<%=path%>";
		if(window.top !== window.self) {
			window.top.location = window.location;
		}
	</script>
</head>

<body class="gray-bg">
	
	<div class="middle-box text-center loginscreen  animated fadeInDown">
		<div>
			<div>
				<h1 class="logo-name">FS</h1>
			</div>
			<h3>欢迎使用好视通基础服务平台</h3>

			<form class="m-t" role="form">
				<div class="form-group">
					<input type="text" name="userName" class="form-control" placeholder="用户名" required="" oninvalid="setCustomValidity('请输入用户名')" oninput="setCustomValidity('')">
				</div>
				<div class="form-group">
					<input type="password" name="pwd" class="form-control" placeholder="密码" required="" oninvalid="setCustomValidity('请输入密码')" oninput="setCustomValidity('')">
				</div>
				<button id="login_btn" type="button" class="btn btn-primary block full-width m-b">登 录</button>

				<!--<p class="text-muted text-center">
					<a href="#"><small>忘记密码了？</small></a> |
					<a href="register.html">注册一个新账号</a>
				</p>-->

			</form>
		</div>
	</div>
</body>
<script src="${resourceRoot}/js/jquery.min.js"></script>
<script src="${resourceRoot}/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#login_btn").click(function(){
		var userName = $("form input[name='userName']").val();
		var password = $("form input[name='pwd']").val();
		if (!userName) {
			alert("用户名为空");
			return;
		}
		if (!password) {
			alert("密码为空");
			return;
		}
		
		$.ajax({
			type : "POST",
			url : baseUrl + "/secure/auth_token",
			data : {userName: userName, pwd: password},
			dataType : "json",
			success : function(data) {
				alert("DEBUG:" + JSON.stringify(data));
				if (data.status == 0) {
					// 登录成功
					window.location.href = baseUrl + "/index";
				}
				else {
					alert("ERROR:"+data.error.message);
				}
			}
			
		});
		
		
	});
	
	
	
});


</script>
</html>