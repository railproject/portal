<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="org.apache.shiro.SecurityUtils,org.railway.com.trainplan.service.ShiroRealm,java.util.List" %>
<% 
/* ShiroRealm.ShiroUser user = (ShiroRealm.ShiroUser)SecurityUtils.getSubject().getPrincipal();

boolean isZgsUser = false;	//当前用户是否为总公司用户
if (user!=null && user.getBureau()==null) {
	isZgsUser = true;
} */
/* String currentUserBureau = "";
List<String> permissionList = "";
String userRolesString = "";
for(String p : permissionList){
	userRolesString += userRolesString.equals("") ? p : "," + p;
}  */
String basePath = request.getContextPath();
%>
<!DOCTYPE HTML>
<html lang="en">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>高铁车底计划审核</title>
<!-- Bootstrap core CSS -->

<!--font-awesome-->
<link href="<%=basePath %>/assets/css/datepicker.css" rel="stylesheet">
<link href="<%=basePath %>/assets/easyui/themes/default/easyui.css"
	rel="stylesheet">
<link href="<%=basePath %>/assets/easyui/themes/icon.css"
	rel="stylesheet">
<link href="<%=basePath %>/assets/css/cross/custom-bootstrap.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/assets/css/cross/custom-bootstrap.css">
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/assets/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/assets/css/datepicker.css">
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/assets/css/style.css"> 
	
<link href="<%=basePath %>/assets/easyui/themes/icon.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.min.css" />
 
<!-- Custom styles for this template --> 
<link href="<%=basePath %>/assets/css/cross/cross.css" rel="stylesheet">  
<link href="<%=basePath %>/assets/css/style.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/assets/css/rightmenu.css">



   

<script src="<%=basePath %>/assets/js/trainplan/knockout.pagemodle.js"></script> 
<script type="text/javascript">
var basePath = "<%=basePath %>";
var all_role = "";
var _isZgsUser = true;//当前用户是否为总公司用户
var currentUserBureau = "";
</script>
<!--#include virtual="assets/js/trainplan/knockout.pagefooter.tpl"-->
 <style type="text/css">
.pagination > li > a, .pagination > li > span {
	position: relative;
	float: left; 
	line-height: 1.428571429;
	text-decoration: none;
	background-color: #ffffff;
	border: 1px solid #dddddd;
	margin-left: -1px;
} 
.ckbox.disabled{
	cursor: not-allowed;
	pointer-events: none;
	opacity: 0.65;
	filter: alpha(opacity=65);
	-webkit-box-shadow: none;
	box-shadow: none;
}

 </style>

 
</head>
<body class="Iframe_body"  >
	
	<ol class="breadcrumb">
		<span><i class="fa fa-anchor"></i>当前位置:</span>
		<li><a href="#">高铁车底计划审核</a></li>
	</ol>  
	    <div class="row" style="margin: 10px 10px 10px 10px;">   
		    <!--分栏框开始-->
		    <div id="plan_view_div_palnDayDetail" class="panel panel-default">
		       <div class="row" style="margin:15px 0 10px 0;"> 
				      <form class="form-horizontal" role="form">
				     
				        <div class="pull-left">  
						 <label for="exampleInputEmail3" class="control-label pull-left" style="margin-left: 15px;">
															日期:&nbsp;</label>
							<div class="pull-left" style="margin-left: 5px;">
								<input type="text" class="form-control" style="width:75px;" placeholder="" id="runplan_input_startDate"  name="startDate" data-bind="value: searchModle().planStartDate" />
							</div>
						  <a type="button"  style="margin-left:5px" class="btn btn-success" data-toggle="modal" data-target="#"  data-bind="click: loadCrosses">刷新</a>
						  <a type="button"  style="margin-left:30px" class="btn btn-success" data-toggle="modal" data-target="#" data-bind="click: updateHighLineCrosses">提交动车交路计划</a>
						  <a type="button"  style="margin-left:30px" class="btn btn-success" data-toggle="modal" data-target="#" data-bind="click: checkHighLineCrosses">审核动车交路计划</a>
						  
						 </div>
				        <!--col-md-3 col-sm-4 col-xs-4-->
				      </form> 
				    </div>  
				     <div class="row" style="margin:15px 10px 10px 10px;overflow-y:auto"> 
					     <div class="table-responsive" >
					          <table class="table table-bordered table-striped table-hover" style="margin-left:5px; margin-right:5px; width:98%"
															id="cross_table_crossInfo">
									<thead>
										<tr style="height: 25px"> 
											<!-- <th style="width: 10%" align="center"><input type="checkbox" style="margin-top:0" value="1" data-bind="checked: crossAllcheckBox, event:{change: selectCrosses}"></th> -->
											<th style="width: 37px" align="center"><input type="checkbox" style="margin-top:0" value="1" data-bind="checked: crossAllcheckBox, event:{change: selectCrosses}"></th>
											<th style="width: 37px" align="center">序号</th> 
											<th align="center">  
												    <label for="exampleInputEmail5" style="font-weight: bold;vertical-align: bottom;">交路全车次</label> 
													<select class="form-control" style="width: 56px;display:inline-block;" id="input_cross_filter_showFlag"
														 data-bind="options: [{'code': 2, 'text': '全称'},{'code': 1, 'text': '简称'}], value: searchModle().shortNameFlag, optionsText: 'text', optionsValue: 'code'">
													</select>  
											</th> 
											<th style="width: 8%" align="center">车底1</th>
											<th style="width: 8%" align="center">车底2</th>
											<th style="width: 8%" align="center">接续车次</th> 
											<th style="width: 8%" align="center">出库所/始发站</th>
											<th style="width: 8%" align="center">入库所/终到站</th>
											<th style="width: 8%" align="center">车辆担当所</th>
											<th style="width: 8%" align="center">是否热备交路</th>
											<th style="width: 8%" align="center">命令号</th>
											<th style="width: 6%" align="center">审核</th>
										</tr>
									</thead>
									<tbody style="padding:0">
										 <tr style="padding:0">
										   <td colspan="12" style="padding:0">
												 <div id="plan_train_panel_body" style="height: 650px; overflow-y:auto;"> 
													<table class="table table-bordered table-striped table-hover" >
														<tbody data-bind="foreach: highLineCrossRows">
															<tr data-bind=" visible: visiableRow, style:{color: $parent.currentCross().highLineCrossId == highLineCrossId ? 'blue':''}" >
																 <!-- <td align="center" style="width: 10%"><input type="checkbox" value="1" data-bind="attr:{class: activeFlag() == 1  || checkActiveFlag() == 1?  '' : 'ckbox disabled'},event:{change: $parent.selectCross}, checked: selected"></td> -->
														         <td align="center" style="width: 37px"><input type="checkbox" value="1" data-bind="<!-- attr:{class: activeFlag() == 0 ? 'ckbox disabled' : ''},  -->event:{change: $parent.selectCross}, checked: selected"></td>
														         <td style="width: 36px" data-bind="text: $index() + 1"></td> 
															     <td data-bind="text: $parent.searchModle().shortNameFlag() == 1 ? shortName : crossName, attr:{title: crossName}, click: $parent.showTrains" ></td>
															     <td style="width: 8%"><input type="text" data-bind="value: vehicle1, event:{change: $parent.vehicle1Change}"></td>
															     <td style="width: 8%"><input type="text" data-bind="value: vehicle2, event:{change: $parent.vehicle2Change}"></td>
															     <td style="width: 8%" data-bind="text: ''"></td> 
															     <td style="width: 8%" data-bind="text: startStn"></td>
															     <td style="width: 8%" data-bind="text: endStn"></td>
															     <td style="width: 8%" data-bind="text: tokenVehBureauShowValue"></td>
															     <td style="width: 8%" data-bind="text: spareFlag() == 2 ? '是' : '否'"></td>
															     <td style="width: 8%" data-bind="text: ''"></td>
															     <td style="width: 5%" align="center" data-bind="style:{color:checkFlag() == 1 ? 'green' : ''},  text: checkFlag() == 1 ? '已' : '未' "></td>
															</tr> 
														</tbody> 
													</table> 
											 	</div>
											</td>
										</tr>
									</tbody>				 
								</table>
					        </div> 
				       </div>
				  </div>  
	    </div>  
</body>  
 <script type="text/html" id="tablefooter-short-template"> 
  <table style="width:100%;height:20px;">
    <tr style="width:100%;height:20px;">
     <td style="width:60%;height:20px;">
  		<span class="pull-left">共<span data-bind="html: totalCount()"></span>条  当前<span data-bind="html: totalCount() > 0 ? (currentIndex() + 1) : '0'"></span>到<span data-bind="html: endIndex()"></span>条   共<span data-bind="text: pageCount()"></span>页</span> 								 
  	 </td>
     <td style="width:40%;height:20px;padding:0px;pading-bottom:-14">   
		<span data-bind="attr:{class:currentPage() == 0 ? 'disabed': ''}"><a style="cursor:pointer;background-color: #ffffff;border: 1px solid #dddddd;margin-right:-5px;padding:0px 5px;" data-bind="text:'<<', click: currentPage() == 0 ? null: loadPre"></a>
	    <input type="text"  style="padding-left:8px;margin-bottom:0px;padding-bottom:0;width:30px;height: 19px;background-color: #ffffff;border: 1px solid #dddddd;" data-bind="value: parseInt(currentPage())+1, event:{keyup: pageNbrChange}"/>
		<a style="cursor:pointer;background-color: #ffffff;border: 1px solid #dddddd;margin-left:-5px;padding:0px 5px;" data-bind="text:'>>', click: (currentPage() == pageCount()-1 || totalCount() == 0) ? null: loadNext"  style="padding:0px 5px;"></a>
       </ul> 
	 
     </td >
  </tr>
</table> 
</script> 


<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/html5.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/respond.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/easyui/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=basePath %>/assets/js/knockout.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.freezeheader.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/ajaxfileupload.js"></script> 
<script type="text/javascript" src="<%=basePath %>/assets/js/trainplan/highLine/highLine_cross_vehicleCheck.js"></script>  
<script type="text/javascript" src="<%=basePath %>/assets/js/datepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.gritter.min.js"></script> 
<script type="text/javascript" src="<%=basePath %>/assets/js/trainplan/common.js"></script> 
<script type="text/javascript" src="<%=basePath%>/assets/js/respond.min.js"></script>
<script src="<%=basePath %>/assets/js/moment.min.js"></script>
<script src="<%=basePath %>/assets/lib/fishcomponent.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>/assets/js/trainplan/common.security.js"></script> --%> 

<script src="<%=basePath %>/assets/js/trainplan/util/fishcomponent.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/util/canvas.util.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/util/canvas.component.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/runPlan/canvas_rightmenu.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/runPlan/canvas_event_getvalue.js"></script>
<script type="text/javascript">
var basePath = "<%=basePath %>";
</script>

<script  type="text/html" id="runPlanTableDateHeader"> 
    <!-- ko if: $index() == 0 -->
 	<td></td>
 	<!-- /ko --> 
 	<td align='center' data-bind="text: day"></td>
</script>
<script  type="text/html" id="runPlanTableVlaues">
 <tr data-bind="foreach: runPlans">
    <!-- ko if: $index() == 0 --> 
 	<td data-bind="text: $parent.trainNbr"></td>
 	<!-- /ko -->  
 	<td  align='center' data-bind="text: runFlag, style:{'color': color}"></td>
 </tr> 
</script>
</html>
