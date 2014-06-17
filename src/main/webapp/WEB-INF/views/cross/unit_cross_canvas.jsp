<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getContextPath();
Object jlData =  request.getAttribute("myJlData");
Object grid =  request.getAttribute("gridData");


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>交路单元</title>
<jsp:include page="/assets/commonpage/global.jsp" flush="true" />
</head>
<body >
<!--分栏框开始-->
<div class="row" >
  <div >
    <!--分栏框开始-->
    <div class="panel panel-default">
    
	   	<div class="row" style="margin:5px 0 10px 0;">
	      <form class="form-inline">
	         <div class="row" style="margin:5px 0 10px 90px;"> 
	              <button type="button" class="btn btn-success" id="canvas_event_btn_x_magnification"><i class="fa fa-search-plus"></i>X+</button>
	              <button type="button" class="btn btn-success" id="canvas_event_btn_x_shrink"><i class="fa fa-search-minus"></i>X-</button>
	              <button type="button" class="btn btn-success" id="canvas_event_btn_y_magnification"><i class="fa fa-search-plus"></i>Y+</button>
	              <button type="button" class="btn btn-success" id="canvas_event_btn_y_shrink"><i class="fa fa-search-minus"></i>Y-</button>
	              
	                                                当前比例倍数：｛X:<label id="canvas_event_label_xscale">1</label>倍；Y:<label id="canvas_event_label_yscale">1</label>倍｝
	         	<input type="checkbox" id="canvas_checkbox_trainTime"  value=""/>显示停站时刻
	         </div>
	      </form>
	    </div>
   
      <div style="width:100%;height:100%">
        <div class="table-responsive" style="overflow: auto;width:100%;height:100%">
        	<canvas id="unit_cross_canvas">您的浏览器不支持HTML5</canvas>
        </div>
        
      </div>
      <!--panel-body--> 
    </div>
    <!--分栏框结束--> 
    <!--分栏框开始-->
    <!--分栏框结束--> 
  </div>
  
  
</div>



<script type="text/javascript">
var canvasData = {};
canvasData.grid = <%=grid%>;
canvasData.jlData = <%=jlData%>;//交路数据
</script>



<script src="<%=basePath %>/assets/js/trainplan/util/fishcomponent.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/util/canvas.util.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/util/canvas.component.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/cross/unit_cross_canvas.js"></script>

</body>
</html>
