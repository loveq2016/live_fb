<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<%--以最高级别的可用模式显示内容，破坏“锁定”模式；如果支持Google Chrome Frame：GCF，则使用GCF渲染；--%>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>好视通直播会议</title>
		<link href="/static/css/login.min.css" rel="stylesheet">
	</head>

	<body>

		<!-- 背景图层 -->
		<div class="logo">
			<img src="/static/images/login-bg.png" alt="好视通Live" />
		</div>
		<!-- 背景图层 -->

		<div id="main-wrap">
			<div id="main">
				<div class="content">

					<!-- 判断房间地址0为游客登录 -->
					<c:if test="${room.verifyMode==0}">
						<div class="login-wrap">
					</c:if>
					<!-- 判断房间地址0为游客登录 -->

					<!-- 判断房间地址1为邀请码登录 -->
					<c:if test="${room.verifyMode==1}">
						<div class="login-wrap-invitation">
					</c:if>
					<!-- 判断房间地址1为邀请码登录 -->

					<p class="login-title">
						<strong class="login-title-one">加入直播</strong>
					</p>
					<p class="login-subtitle">"${room.liveName}"</p>

					<c:if test="${room.verifyMode==1}">
						<input name="inviteCode" class="invitation-code" type="text" placeholder="邀请码" maxlength="9">
					</c:if>
					<div class="error-wrap">
						<input name="nickName" id="nickName" type="text" placeholder="昵称" minlength="1" maxlength="64" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;">
						<p class="error-message"></p>
					</div>
					<input type="hidden" id="ticket" name="ticket" value="${ticket}">
					<input type="button" id="joinID" value="加入直播">

					</div>

					</div>
				</div>

				<!-- loading加载层 -->
				<div class="load-layout">
					<div class="load-layout-div">
						<img src="/static/images/joining.gif">
						<p>正在加入直播<br/>"好视通直播产品发布会"</p>
					</div>
				</div>

				<!-- loading加载失败层 -->
				<div class="load-layout-fail">
					<div class="load-layout-fail-div">
						<img src="/static/images/connect-fail.png">
						<p>连接服务器失败，请重试</p>
						<div class="load-layout-fail-btn">
							<input type="button" value="返回" id="back-btn">
							<input type="button" value="重试" id="retry-btn">
						</div>
					</div>
				</div>

			</div>

			<footer>
				<p>©2016深圳银澎云计算有限公司版权所有</p>
			</footer>

			<script src="/static/javascript/public/jquery-1.8.3.min.js"></script>
			<script src="/static/javascript/login.min.js"></script>
			<script src="/static/javascript/login-effect.js"></script>
			<script>
				var liveURI = "${room.liveURI}"; // 域名地址
				var proxyAddrUrl = "${loginMsg.data.proxyAddr}"; // 代理服务器地址
				var token = "${loginMsg.data.token}"; // token
				var userId = "${loginMsg.data.userId}"; // userId
				var liveRoomId = "${loginMsg.data.liveRoomId}"; // roomId
				var ticket = "${ticket}"; // ticket
			</script>
	</body>

</html>