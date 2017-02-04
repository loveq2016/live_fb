	// 手机拒绝访问网页
	var system = { 
	    win: false, 
	    mac: false, 
	    xll: false, 
	    ipad:false 
	}; 
	// 检测平台 
	var p = navigator.platform; 
	system.win = p.indexOf("Win") == 0; 
	system.mac = p.indexOf("Mac") == 0; 
	system.xll = (p == "Xll") || (p.indexOf("Linux") == 0); 
	system.ipad = (navigator.userAgent.match(/iPad/i) != null)?true:false; 
	if (system.win || system.mac || system.xll || system.ipad) { 
		
	}else{
		alert("手机端禁止访问");
	}
	
	// 回车确定	
	$(document).keyup(function(event) {
		if(event.keyCode == 13) {
			loginClickEvent();
		}
	});
	
	// 邀请码页面，邀请码focus
	function inviteCodeFocus(){
		$('.error-message').text('邀请码不能为空！');
	    $("#main").css({"height": "384px"});
	    $(".invitation-code").addClass("invitation-error-border");
	    $(".invitation-code").focus(function(){
	    	$(this).removeClass("invitation-error-border");
	    	$("#main").css({
	        	"height": "365px",
	        });
	    });
	}
	
	// 邀请码页面，昵称focus
	function nickNameFocus(){
		$("#nickName").focus(function(){
	    	$("#main").css({
	        	"height": "365px",
	        });
	    });
	}
	
	// 游客登录页面，昵称JS
	function nickNameEffect(){
		$('.error-message').text('请输入昵称！');
	    $("#main").css({
	    	"height": "384px",
	    });
	    $("#nickName").addClass("error-border");
	    $("#nickName").focus(function(){
	    	$(this).removeClass("error-border");
	    	$("#main").css({
	        	"height": "365px",
	        });
	    });
	}
	
	// 存储获取昵称
	function storeNickName(){
		sessionStorage.setItem("nickName",$("#nickName").val());
		getNickName = sessionStorage.getItem("nickName");
	}	
	
	// 存储获取邀请码	
	function storeInviteCode(){
		sessionStorage.setItem("inviteCode",$(".invitation-code").val());
		getInviteCode = sessionStorage.getItem("inviteCode");
		if(getInviteCode == "undefined" || getInviteCode == "null" || getInviteCode == "NaN"){
			getInviteCode = "";
		}
	}
		
	
	// 登录页确定按钮事件	
	function loginClickEvent(){
		// 存储获取昵称
    	storeNickName();
    	// 存储获取邀请码
    	storeInviteCode();
    	
    	// 判断昵称输入框是否为空
        var data = {};
        if(isUndefine(inviteCode.val())){
            data.inviteCode = '';
        }else{
            if(isNull(inviteCode.val())){
            	// 邀请码页面，邀请码focus
            	inviteCodeFocus();
            	// 邀请码页面，昵称focus
            	nickNameFocus();
                return;
            }else{
                data.inviteCode = inviteCode.val(); 
            }
        }
        if(isNull(nickName.val())){
        	// 游客登录页面，昵称JS
        	nickNameEffect();
            return;
        }else{
            data.nickName = nickName.val();
        }
        // ajax发送请求
        ajax();
	}
	
	
	// ajax发送请求
	function ajax(){
		
		var data = {
			nickName: getNickName,
			inviteCode: getInviteCode,
			ticket: ticket,
		};
	
		$.ajax({
	        type:"post",
	        dataType:"json",
	        url : "/live/"+liveURI+"/home",          
	        data: data,
	        beforeSend : function(){   
	            // loading动画
	        	function loading(){
	        		$("#joinID").click(function(){
	                	$('.load-layout').show();
	                });
	        	}    
	        },
	        success:function(data){
	    		// 请求成功后，取消loading动画,跳转到直播页面
	    		$(".load-layout").hide();
	    		window.location.href = "/live/"+liveURI+"/home";
	    		// 10s一次心跳（登录成功发送心跳）
	    		var heartMessage = '[{"CmdId":30011,"RoomId":'+liveRoomId+',"UserId":'+userId+',"UserName":'+getNickName+'}]';
	    		var sendHeartMessage = socket.send(heartMessage);
	    		setInterval(sendHeartMessage,10000);
	        },
	        error:function(){
	        	// 请求失败后，出现loading重试层
	        	$(".load-layout").hide();
	        	$(".load-layout-fail").show();
	        }
	    }); 
	}	