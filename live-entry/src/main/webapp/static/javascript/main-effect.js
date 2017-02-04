// 初始化实例
$(function() {
	start();
})

// 小窗口视频特效
$(".small-video").mouseover(function() {
	$(".down-layer").slideDown(500);
});
$(".small-video").mouseleave(function() {
	$(".down-layer").slideUp(500);
});

// 发送消息按钮特效
function start() {
	$("#btnSend").addClass("not-allow");
}
$('#show').bind('input propertychange', function() {
	var showMsg = $("#show").val();
	if(showMsg != "") {
		$("#btnSend").removeClass("not-allow");
		$("#btnSend").attr("disabled", false);
		$("#btnSend").css({
			"cursor": "pointer",
			"background": "#1577f5",
		});

	} else {
		$("#btnSend").attr("disabled", true);
		$("#btnSend").css({
			"cursor": "not-allowed",
			"background": "#d3dde6",
		});
	}
});

// 回车发送消息
$(document).keyup(function(event) {
	if(event.keyCode == 13) {
		var showMsg = $("#show").val();
		if(showMsg != "") {
			sendMsg(socket);
			// 消息列表滚动条在最底部
			scrollEvent();
		}
	}
});

// 消息列表滚动条在最底部
function scrollEvent() {
	var scrollHeight = $('.chat-content')[0].scrollHeight;
	if(scrollHeight > 442) {
		$('.chat-content').scrollTop($('.chat-content')[0].scrollHeight);
	}
}

// 退出按钮特效
$("#exit").mouseover(function() {
	$(".icon").addClass("icon-hover");
	$(".title").addClass("title-hover");
});
$("#exit").mouseleave(function() {
	$(".icon").removeClass("icon-hover");
	$(".title").removeClass("title-hover");
});

// 转16进制颜色
function StringToColor(strCL) {
	var sexadecimalCL = parseInt(strCL).toString(16);
	while(sexadecimalCL.length < 6) {
		sexadecimalCL = "0" + sexadecimalCL;
	}
	return "#" + sexadecimalCL;
}