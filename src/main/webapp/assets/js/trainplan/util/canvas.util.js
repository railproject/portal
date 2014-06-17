/**
 * 产生随机颜色
 */
function getRandomColor() {
	return '#'+('00000'+(Math.random()*0x1000000<<0).toString(16)).slice(-6);
	//"rgba("+parseInt(10*Math.random())+","+parseInt(100*Math.random())+","+parseInt(50*Math.random())+","+Math.random()+")";
};

/**
 * 画竖线
 * @param context
 * @param lineWidth
 * @param color
 * @param x1
 * @param y1
 * @param y2
 */
function myCanvasDrawFullLineY(context,lineWidth, color, x1, y1, y2) {
	context.beginPath();
	context.strokeStyle = color;//"#eee";
	context.lineWidth = lineWidth;
	context.moveTo(x1, y1);
	context.lineTo(x1, y2);
	context.stroke();//绘画
	context.closePath();//关闭path
}



/**
 * 画线
 * @param context
 * @param lineWidth
 * @param color
 * @param fromX
 * @param fromY
 * @param toX
 * @param toY
 */
function myCanvasDrawLine(context,lineWidth, color, fromX, fromY, toX, toY) {
	context.lineWidth = lineWidth;
	context.moveTo(fromX, fromY);
	context.lineTo(toX, toY);
}


/**
 * 画竖虚线
 * @param context
 * @param lineWidth
 * @param color
 * @param fromX
 * @param fromY
 * @param toX
 * @param toY
 * @param pattern
 */
function myCanvasDrawDashLineY(context,lineWidth, color, fromX, fromY, toX, toY, pattern) {
	context.strokeStyle = color;//"#eee";
	context.lineWidth = lineWidth;
	context.dashedLineTo(fromX, fromY, toX, toY, pattern);//竖虚线
}




function windowToCanvas(canvas, x, y) {
	var bbox = canvas.getBoundingClientRect();
	return {
		x:(x-bbox.left)*(canvas.width/bbox.width),
		y:(y-bbox.top)*(canvas.height/bbox.height)
	};
};



function myCanvasFillTextWithColor(context, colorParam, textObj) {
	context.fillStyle = colorParam;
	// 设置字体
	if (textObj.font !=null && textObj.font!="undefine") {
		context.font = textObj.font;//"Bold 17px Arial";
	}
	
	// 设置对齐方式
	context.textAlign = "center";
	//context.textBaseline = "middle";	//垂直居中
	context.fillText(textObj.text, textObj.fromX, textObj.fromY);
};


function myCanvasFillText(context, textObj) {
	context.strokeStyle = "#000000";
	context.fillStyle = "#000000";
	// 设置字体
	if (textObj.font !=null && textObj.font!="undefine") {
		context.font = textObj.font;//"normal 12px Arial";	//normal Bold Bolder lighter
	}
	
	// 设置对齐方式
	context.textAlign = textObj.textAlign,//"right";
	//context.textBaseline = "middle";	//垂直居中
	context.fillText(textObj.text, textObj.fromX, textObj.fromY);
};




