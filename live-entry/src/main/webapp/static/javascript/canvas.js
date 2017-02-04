// 直线
function drawingLine(canv, lineCap,lineWidth,strokeStyle,points){
	canv.beginPath();
	canv.lineCap =lineCap;   // 线型
	canv.lineWidth = lineWidth;  // 线宽
	canv.strokeStyle = strokeStyle;	 // 线颜色	
	for(var j = 0; j < points.length - 1; j++) {
		canv.moveTo(points[j].X, points[j].Y + 0.5);
		canv.lineTo(points[j + 1].X, points[j + 1].Y + 0.5);
	}	
	canv.stroke();
	canv.closePath();
}

// 箭头
function drawingArrows(canv, lineCap,lineWidth,strokeStyle,points){
	canv.beginPath();
	canv.lineCap =lineCap;   // 线型
	canv.lineWidth = lineWidth;  // 线宽
	canv.strokeStyle = strokeStyle;	 // 线颜色
	for(var j = 0; j < points.length - 1; j++) {
		canv.moveTo(points[j].X, points[j].Y);
		canv.lineTo(points[j + 1].X, points[j + 1].Y);
		canv.translate(points[j + 1].X, points[j + 1].Y);
		var ang = (points[j + 1].X, points[j + 1].Y / points[j].X, points[j].Y);
		ang = Math.atan(ang);
		if(points[j + 1].Y - points[j].Y >= 0) {
			canv.rotate(-ang);
		} else {
			canv.rotate(Math.PI - ang); //加个180度，反过来  
		}
		canv.lineTo(4, 4);
		canv.lineTo(-5, -16);
		canv.lineTo(-5, 10);
		canv.lineTo(0, 0);
		canv.fill(); //箭头是个封闭图形
	}
	canv.stroke();
	canv.closePath();
}

// 手写笔
function drawingPen(canv, lineCap,lineWidth,strokeStyle,points){
	canv.beginPath();
	canv.lineCap =lineCap;   // 线型
	canv.lineWidth = lineWidth;  // 线宽
	canv.strokeStyle = strokeStyle;	 // 线颜色
	for(var j = 0; j < points.length - 1; j++) {
		canv.moveTo(points[j].X, points[j].Y + 0.5);
		canv.lineTo(points[j + 1].X, points[j + 1].Y + 0.5);
	}
	canv.stroke();
	canv.closePath();
}

// 荧光笔
function drawingLightPen(canv, lineCap,lineWidth,strokeStyle,points){
	canv.beginPath();
	canv.lineCap =lineCap;   // 线型
	canv.lineWidth = lineWidth;  // 线宽
	canv.strokeStyle = strokeStyle;	 // 线颜色
	for(var j = 0; j < points.length - 1; j++) {
		canv.moveTo(points[j].X, points[j].Y + 0.5);
		canv.lineTo(points[j + 1].X, points[j + 1].Y + 0.5);
	}
	canv.stroke();
	canv.closePath();
}

// 矩形
function drawingRectangle(canv, lineCap,lineWidth,strokeStyle,fillStyle,points){
	canv.beginPath();
	canv.lineCap =lineCap;   // 线型
	canv.lineWidth = lineWidth;  // 线宽
	canv.strokeStyle = strokeStyle;	 // 线颜色
	canv.fillStyle = fillStyle;   // 块颜色
	for(var j = 0; j < points.length - 1; j++) {
		canv.fillRect(points[j].X, points[j].Y + 0.5, (points[j + 1].X-points[j].X), (points[j + 1].Y + 0.5-points[j].Y + 0.5));
		canv.strokeRect(points[j].X, points[j].Y + 0.5, (points[j + 1].X-points[j].X), (points[j + 1].Y + 0.5-points[j].Y + 0.5)); 
	}
	canv.stroke();
	canv.closePath();
}

// 圆角矩形
function drawingRoundedRectangle(canv, lineCap,lineWidth,strokeStyle,fillStyle,points){
	canv.beginPath();
	canv.lineCap =lineCap;   // 线型
	canv.lineWidth = lineWidth;  // 线宽
	canv.strokeStyle = strokeStyle;	 // 线颜色
	canv.fillStyle = fillStyle;   // 块颜色
	var x1 = parseInt(points[0].X);
	var y1 = parseInt(points[0].Y + 0.5);
	var x2 = parseInt(points[1].X);
	var y2 = parseInt(points[1].Y + 0.5);
	var r = 10;
	canv.moveTo(x1+r, y1);
    canv.arcTo(x2, y1, x2, y1+r, r);
    canv.arcTo(x2, y2, x2-r, y2, r);
    canv.arcTo(x1, y2, x1, y2-r, r);
    canv.arcTo(x1, y1, x1+r, y1, r);
    canv.fill();
	canv.stroke();
	canv.closePath();
}

// 椭圆
function drawingEllipse(canv, lineCap,lineWidth,strokeStyle,fillStyle,points){
	canv.beginPath();
	canv.lineCap =lineCap;   // 线型
	canv.lineWidth = lineWidth;  // 线宽
	canv.strokeStyle = strokeStyle;	 // 线颜色
	canv.fillStyle = fillStyle;   // 块颜色
	for(var j = 0; j < points.length - 1; j++) {
		var a = (points[j + 1].X-points[j].X)/2;
		var b = ((points[j + 1].Y + 0.5) - (points[j].Y + 0.5))/2;
		var x = points[j + 1].X/2;
		var y = (points[j + 1].Y + 0.5)/2;
		//选择a、b中的较大者作为arc方法的半径参数 
		var r = (a > b) ? a : b;
		var ratioX = a / r;        //横轴缩放比率
		var ratioY = b / r;       //纵轴缩放比率 
		canv.scale(ratioX, ratioY); //进行缩放（均匀压缩）        
		//从椭圆的左端点开始逆时针绘制 
//		canv.moveTo((x + a) / ratioX, y / ratioY);
		canv.arc(x / ratioX, y / ratioY, r, 0, 2 * Math.PI);
		canv.fill();
	}
	canv.stroke();
	canv.closePath();
}

// 文字
function drawingWord(canv, lineCap,Height,strokeStyle,points,texts){
	canv.beginPath();
	canv.lineCap =lineCap;   // 线型
	canv.lineWidth = Height;  // 线宽
	canv.strokeStyle = strokeStyle;	 // 线颜色
	for(var j = 0; j < points.length - 1; j++) {
		canv.fillText(texts, points[j].X, points[j].Y + 0.5, points[j + 1].X, points[j + 1].Y + 0.5);
	}
	canv.stroke();
	canv.closePath();
}