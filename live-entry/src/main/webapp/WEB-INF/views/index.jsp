<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<%
	String path = request.getContextPath();
%>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<title>后台管理系统</title>

	<link href="${resourceRoot}/img/favicon.ico" rel="shortcut icon" >
	<link href="${resourceRoot}/css/bootstrap.min.css" rel="stylesheet">
	<link href="${resourceRoot}/css/font-awesome.min.css" rel="stylesheet">
	<link href="${resourceRoot}/css/animate.min.css" rel="stylesheet">
	<link href="${resourceRoot}/css/style.min.css" rel="stylesheet">
	<link href="${resourceRoot}/css/whole.css" rel="stylesheet"/>
	<link href="${resourceRoot}/js/plugins/layer/skin/layer.css" rel="stylesheet"/>
	<link href="${resourceRoot}/js/plugins/layer/skin/layer.ext.css" rel="stylesheet"/>
</head>

<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">

		<!--顶部导航-->
		<nav class="navbar navbar-default">
			<div class="container-fluid">

				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand img-a" href="<%=path%>/index"></a>
				</div>
				<!-- 模块栏 -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<c:forEach items="${navigationBars}" var="bar">
							<li><a href="${bar.url}" class="li-a">${bar.title}</a></li>
						</c:forEach>
					</ul>
				</div>

			</div>
		</nav>
		<!--顶部导航-->

		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>
			<div class="sidebar-collapse">
				<ul class="nav" id="side-menu">
					<!-- 管理用户 -->
					<li class="nav-header">
						<div class="dropdown profile-element">
							<span>
								<img alt="image" class="img-circle" src="${resourceRoot}/img/profile_small.jpg" />
							</span> 
							<a data-toggle="dropdown" class="dropdown-toggle" href="#"> 
								<span class="clear"> 
									<span class="block m-t-xs">
									<strong class="font-bold">${currentUser.userName}</strong></span> 
									<span class="text-muted text-xs block">${currentUser.loginName}<b class="caret"></b></span>
								</span>
							</a>
							<ul class="dropdown-menu animated fadeInRight m-t-xs">
								<li><a class="J_menuItem" href="profile.html">个人资料</a></li>
								<li><a class="J_menuItem" href="contact.html">联系我们</a></li>
								<li class="divider"></li>
								<li><a id="logout" href="#">安全退出</a></li>
							</ul>
						</div>
						<div class="logo-element">FS</div>
					</li>


					<c:forEach items="${menus}" var="menu">
						<li id="${menu.path}"><a data-href="<%=path%>${menu.url}">
								<i class="${menu.image}"></i> <span class="nav-label"
								title="${menu.description}">${menu.title}</span> <c:if
									test="${!menu.leaf}">
									<span class="fa arrow"></span>
								</c:if>
						</a> <c:if test="${!menu.leaf}">
								<ul class="nav nav-second-level"></ul>
							</c:if></li>
					</c:forEach>
				</ul>
			</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<!-- 隐藏菜单按钮 -->
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
					<div class="navbar-header">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
							href="#"><i class="fa fa-bars"></i> </a>
					</div>
				</nav>
			</div>
			<!-- 视图导航栏 -->
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
					<div class="page-tabs-content">
						<a href="<%=path%>/index" class="active J_menuTab" data-id="<%=path%>/index">首页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a href="#" class="roll-nav roll-right J_tabExit"><i
					class="fa fa fa-sign-out"></i>退出</a>
			</div>

			<!-- 视图 -->
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="monitor-fsp-view-device-realtime.html" frameborder="0"
					data-id="monitor-fsp-view-device-realtime.html" seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">Copyright © 2013 Fsmeeting.com All Rights Reserved 深圳银澎云计算有限公司 版权所有 粤ICP备12013956号-3</div>
			</div>
		</div>
		<!--右侧部分结束-->
	</div>

</body>

<script src="${resourceRoot}/js/jquery.min.js"></script>
<script src="${resourceRoot}/js/bootstrap.min.js"></script>
<script src="${resourceRoot}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${resourceRoot}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${resourceRoot}/js/plugins/layer/layer.min.js"></script>
<script src="${resourceRoot}/js/hplus.min.js"></script>
<script src="${resourceRoot}/js/contabs.min.js" type="text/javascript" ></script>
<script type="text/javascript">
	var baseUrl = "<%=path%>";
	$(function(){
		var num2letter = {
			1 : "first",
			2 : "second",
			3 : "third",
			4 : "forth",
			5 : "fifth",
			6 : "sixth",
			7 : "seventh",
			8 : "eighth",
			9 : "ninth"
		};
		var subjectxhr = {};
		
		$("#logout").click(function(){
			$.ajax({
				type : "DELETE",
				url : baseUrl + "/secure/auth_token",
				dataType : "json",
				success : function(data) {
					alert("DEBUG:" + JSON.stringify(data));
					if (data.status == 0) {
						window.location.href = baseUrl + "/login";
					}
					else {
						alert("ERROR:"+data.error.message);
					}
				}
				
			});
			
		});
		
		
		$("#side-menu li").not("li[class*='nav-header']").click(function(event){
			var my = $(event.target).parent("li");
			var id = my.attr("id");
			var ul = my.find(">ul");
			var leafed = ul.length <= 0;
			var loaded = ul.find("li").length > 0;
			
			// 需要加载，先加载信息
			if (!leafed && !loaded) {
				if (subjectxhr[id]) return;
				else subjectxhr[id] = true;
				// 没有加载则加载
				$.ajax({
					type: "POST",
					url : baseUrl + "/subjects",
					data : {path : id},
					dataType : "json",
					success : function(data) {
						alert("DEBUG:" + JSON.stringify(data));
						if (data.status == 0) {
							var subjects = data.data;
							var attach = data.attach;
							if (subjects.length <= 0) {
								alert("DEBUG: subjects is empty!");
								return;
							}
							subjects.forEach(function(subject){
								var path = subject.path;
								var url = baseUrl + subject.url;
								var leaf = subject.leaf;
								var image = subject.image;
								// 获取数据加载
								var newLi = $("<li id=\""+path+"\"></li>");
								var newA = $("<a data-href=\""+url+"\">");
								if (leaf) {
									newA.attr("class", "J_menuItem");
								}
								newLi.append(newA);
								if (image) {
									newA.append("<i class=\""+image+"\"></i>");
								}
								newA.append("<span>"+subject.title+"</span>");
								if (!leaf) {
									newA.append("<span class=\"fa arrow\"></span>");
									var layer = subject.layer;
									newLi.append("<ul class=\"nav nav-"+num2letter[layer]+"-level\"></ul>");
								}
								ul.append(newLi);
							});
						}
						else {
							alert("ERROR:"+data.error.message);
						}
					},
					error: function(xhr,status,error) {
						alert("ERROR: status=" + status + ", error=" + error);
					},
					complete: function (){
						subjectxhr[id] = false;
					}
				});
			}
			// 叶子节点或有隐藏到显示时，进入
			if (leafed) {
				/* event.preventDefault();
				event.stopPropagation(); */
				// 叶子节点或子节点已经加载
				var link = my.find("> a:eq(0)");
				var href = link.data("href");
				if (href || href == baseUrl) {
					var frame = $("#content-main iframe");
					frame.attr("src", href);
					frame.data("id", id);
					return null;
				}
			}
			
		});
		
	});
	
</script>
</html>