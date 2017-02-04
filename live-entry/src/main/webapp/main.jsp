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
		<link rel="stylesheet" href="/static/css/public/video-js.css" />
		<link rel="stylesheet" href="/static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="/static/css/main.min.css" />
		<script src="/static/javascript/public/jquery-1.8.3.min.js"></script>
	</head>

	<body onbeforeunload="return roomDestroy()">
		<!-- <div id="aboutID"></div>
<button id="btn">播放</button> -->

		<header>
			<div class="logo">
				<img src="/static/images/logo.png" alt="好视通Live" />
			</div>

			<div class="user-exit">
				<ul>
					<!-- <li class="m-r-30">
                    <p class="icon">
                        <i class="fa fa-user"></i>
                    </p>
                    <p class="title" id="nickName"></p>
                </li> -->
					<li id="exit">
						<p class="icon">
							<i class="fa fa-power-off"></i>
						</p>
						<p class="title">退出</p>
					</li>
				</ul>
			</div>
		</header>
		<div id="main-wrap">
			<!-- 白板画布 -->
			<div class="main-video" id="mainVideoWrap">

				<!-- <div id="drawController">
			<img src="/static/images/pencil.png" class="img border_choose" onclick="draw_graph('pencil',this)" title="铅笔"><br>
			<img src="/static/images/line.png" class="img border_nochoose" onclick="draw_graph('line',this);" title="画直线"><br>
			<img src="/static/images/cancel.png" class="img border_nochoose" onclick="cancel(this)" title="撤销上一个操作"><br>
			<img src="/static/images/next.png" class="img border_nochoose" onclick="next(this)" title="重做上一个操作"><br>
			<input id="chooseColor" type="button" class="i1 border_nochoose" onclick="showColor(this)" title="选择颜色"><br>
			<img src="/static/images/square.png" class="img border_nochoose" onclick="draw_graph('square',this)" title="方形"><br>
			<img src="/static/images/circle.png" class="img border_nochoose" onclick="draw_graph('circle',this)" title="圆"><br>
			<img src="/static/images/handwriting.png" class="img border_nochoose" onclick="draw_graph('handwriting',this)" title="涂鸦"><br>
			<img src="/static/images/rubber.png" id="chooseSize" class="img border_nochoose" onclick="draw_graph('rubber',this)" title="橡皮擦"><br>
			<img src="/static/images/line_size_1.png" id="chooseSize" class="img border_nochoose" onclick="showLineSize(this)" title="线条大小"><br>
			<img src="/static/images/xx.png" class="img border_nochoose" onclick="clearContext('1')" title="清屏"><br>
			<a href="#" download="picture.png" id="downloadImage_a">
				<img src="/static/images/download.png" class="img border_nochoose" title="下载" onclick="downloadImage();">
			</a><br>
		</div> -->
				<!--<div class="canvas-wrap-div">-->
					<!--<canvas id="canvas">您的浏览器不支持Canvas标签，请升级或更换浏览器</canvas>
					<canvas id="canvas_bak" style="z-index: 1;">您的浏览器不支持Canvas标签，请升级或更换浏览器</canvas>-->
				<!--</div>-->

				<!--<div id="color" class="color">
					<input class="i1" type="button">
					<input class="i2" type="button">
					<input class="i3" type="button">
					<input class="i4" type="button">
					<input class="i5" type="button">
					<input class="i6" type="button">
					<input class="i7" type="button">
					<input class="i8" type="button">
					<input class="i9" type="button">
				</div>

				<div id="line_size" class="border_nochoose" style="z-index:99">
					<img src="/static/images/line_size_1.png" width="80px;" height="12px;" onclick="chooseLineSize(1)"><br>
					<img src="/static/images/line_size_3.png" width="80px;" height="12px;" onclick="chooseLineSize(3)"><br>
					<img src="/static/images/line_size_5.png" width="80px;" height="12px;" onclick="chooseLineSize(5)"><br>
					<img src="/static/images/line_size_7.png" width="80px;" height="12px;" onclick="chooseLineSize(7)">
				</div>-->

			</div>
			<!-- 白板画布 -->
			<div class="video-chat">
				<div class="small-video">
					<div class="down-layer">
						<div class="down-layer-name">产品部</div>
						<div class="icon">
							<i class="fa fa-expand down-layer-icon2" title="全屏"></i>
							<i class="down-layer-icon1" title="声音"></i>
						</div>
					</div>
				</div>
				<div class="chat">
					<div class="chat-h2">
						<h2 class="chat-ask">提问</h2>
					</div>
					
					<div class="chat-content" id="js-chat">
						<!-- <ul id="logID"></ul> -->
						<!-- 聊天对话 -->
						<!-- <div class="chat-panel">                    
	                    <div class="content-detail">
		                    <p class="name">Gino</p>
		                    <span>&nbsp;:</span>
		                    <p class="time">18:53</p>
	                    </div>
	                    <div class="content">好视通产品？好视通产品？好视通产品？好视通产品？好视通产品？好视通产品？好视通产品？好视通产品？</div>
	                </div>
	                <div class="chat-panel">
	                    <div class="content-detail">
		                    <p class="name">Gino</p>
		                    <span>&nbsp;对&nbsp;</span>
		                    <p class="name">Davis</p>
		                    <span>&nbsp;说&nbsp;</span>
		                    <span>:</span>
		                    <p class="time">18:53</p>
	                    </div>
	                    <div class="content">好视通产品？好视通产品？好视通产品？好视通产品？好视通产品？好视通产品？</div>
	                </div> -->
						<!-- 聊天对话 -->
					</div>
					<div class="chat-edit">
						<textarea id="show" rows="1" maxlength="1000" placeholder="输入提问信息"></textarea>
						<input id="btnSend" disabled="disabled" value="发送" type="submit" />
					</div>
				</div>
			</div>
		</div>
		<footer>
			<p>©2016深圳银澎云计算有限公司版权所有</p>
			<ul class="footer-menu">
				<li class="border-none">
					<a href="javascript:void(0)"> 技术支持 : 400-9900-967</a>
				</li>
			</ul>
		</footer>

		<script>
//			// 撤销的array
//			var cancelList = new Array();
//			// 撤销的次数
//			var cancelIndex = 0;
//
//			$(function() {
//				initCanvas();
//				initDrag();
//				$("img")[0].click();
//				$("#color input").click(chooseColor);
//				$('.border_choose').trigger("click");
//			});
//
//			// 初始化
//			var initCanvas = function(e) {
//				canvas = document.getElementById("canvas");
//				canvas.width = canvasWidth;
//				canvas.height = canvasHeight;
//				context = canvas.getContext('2d');
//				canvasTop = $(canvas).offset().top;
//				canvasLeft = $(canvas).offset().left;
//
//				canvas_bak = document.getElementById("canvas_bak");
//				canvas_bak.width = canvasWidth;
//				canvas_bak.height = canvasHeight;
//				context_bak = canvas_bak.getContext('2d');
//			}
//
//			// 下载图片
//			var downloadImage = function() {
//				$("#downloadImage_a")[0].href = canvas.toDataURL();
//				$("#downloadImage_a").click();
//			}
//
//			// 展开颜色选择器
//			var showColor = function(obj) {
//					var top = $(obj).offset().top;
//					var left = $(obj).offset().left;
//					$("#color")[0].style.left = left + "px";;
//					$("#color")[0].style.top = top + "px";
//					$("#color").show();
//
//				}
//				// 展开线条大小选择器
//			var showLineSize = function(obj) {
//
//				if($("#line_size").is(":hidden")) {
//					var top = $(obj).offset().top;
//					var left = $(obj).offset().left;
//					$("#line_size")[0].style.left = left + $(obj).width() + 5 + "px";
//					$("#line_size")[0].style.top = top + "px";
//					$("#line_size").show();
//				} else {
//					$("#line_size").hide();
//				}
//			}
//
//			// 选择颜色
//			var chooseColor = function(obj) {
//				var objClass = $(this).attr("class");
//				$("#chooseColor").attr("class", "");
//				$("#chooseColor").addClass(objClass).addClass('border_nochoose');
//				color = $(this).css('background-color');
//				$("#color").hide();
//
//			}
//
//			// 选择大小
//			var chooseLineSize = function(_size) {
//				$("#chooseSize").attr("src", "/static/images/line_size_" + _size + ".png");
//				size = _size;
//				$("#line_size").hide();
//			}
//
//			// 撤销上一个操作
//			var cancel = function() {
//				cancelIndex++;
//				context.clearRect(0, 0, canvasWidth, canvasHeight);
//				var image = new Image();
//				var index = cancelList.length - 1 - cancelIndex;
//				var url = cancelList[index];
//				image.src = url;
//				image.onload = function() {
//					context.drawImage(image, 0, 0, image.width, image.height, 0, 0, canvasWidth, canvasHeight);
//				}
//			};
//
//			// 重做上一个操作
//			var next = function() {
//				cancelIndex--;
//				context.clearRect(0, 0, canvasWidth, canvasHeight);
//				var image = new Image();
//				var index = cancelList.length - 1 - cancelIndex;
//				var url = cancelList[index];
//				image.src = url;
//				image.onload = function() {
//					context.drawImage(image, 0, 0, image.width, image.height, 0, 0, canvasWidth, canvasHeight);
//				}
//			};
//
//			// 保存历史 用于撤销
//			var saveImageToAry = function() {
//				cancelIndex = 0;
//				var dataUrl = canvas.toDataURL();
//				cancelList.push(dataUrl);
//			}
//
//			// 处理文件拖入事件，防止浏览器默认事件带来的重定向  
//			function handleDragOver(evt) {
//				evt.stopPropagation();
//				evt.preventDefault();
//			}
//
//			// 判断是否是图片  
//			function isImage(type) {
//				switch(type) {
//					case 'image/jpeg':
//					case 'image/png':
//					case 'image/gif':
//					case 'image/bmp':
//					case 'image/jpg':
//						return true;
//					default:
//						return false;
//				}
//			}
//
//			// 处理拖放文件列表  
//			function handleFileSelect(evt) {
//				evt.stopPropagation();
//				evt.preventDefault();
//
//				var files = evt.dataTransfer.files;
//
//				for(var i = 0, f; f = files[i]; i++) {
//					var t = f.type ? f.type : 'n/a';
//					reader = new FileReader();
//					isImg = isImage(t);
//
//					// 处理得到的图片  
//					if(isImg) {
//						reader.onload = (function(theFile) {
//							return function(e) {
//								var image = new Image();
//								image.src = e.target.result;
//								image.onload = function() {
//									context.drawImage(image, 0, 0, image.width, image.height, 0, 0, canvasWidth, canvasHeight);
//								}
//
//							};
//						})(f)
//						reader.readAsDataURL(f);
//					}
//				}
//			}
//
//			//初始化拖入效果
//			var initDrag = function() {
//				var dragDiv = document.getElementById("canvas_bak");
//				dragDiv.addEventListener('dragover', handleDragOver, false);
//				dragDiv.addEventListener('drop', handleFileSelect, false);
//			}

			var liveURI = "${loginMsg.data.room.liveURI}"; // 域名地址
			var proxyAddrUrl = "${loginMsg.data.proxyAddr}"; // 代理服务器地址
			var token = "${loginMsg.data.token}"; // token
			var userId = "${loginMsg.data.userId}"; // userId
			var liveRoomId = "${loginMsg.data.room.id}"; // roomId

			// 房间销毁
			/* function roomDestroy(){
				return "直播房间已关闭！";
			} */
		</script>
		<!-- <script type="text/javascript" src="/static/javascript/public/video.min.js"></script> -->
		<script type="text/javascript" src="/static/javascript/main.min.js"></script>
		<script type="text/javascript" src="/static/javascript/main-effect.js"></script>
		<script type="text/javascript" src="/static/javascript/canvas.js"></script>
		<script type="text/javascript" src="/static/javascript/login.min.js"></script>
		<script type="text/javascript" src="/static/javascript/draw.js"></script>
		<script type="text/javascript" src="/static/javascript/storage.js"></script>

	</body>

</html>