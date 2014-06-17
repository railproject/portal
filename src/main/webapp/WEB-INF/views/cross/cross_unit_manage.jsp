<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="org.apache.shiro.SecurityUtils,org.railway.com.portal.service.ShiroRealm,java.util.List" %>
<% 
	ShiroRealm.ShiroUser user = (ShiroRealm.ShiroUser)SecurityUtils.getSubject().getPrincipal(); 
	List<String> permissionList = user.getPermissionList();
	String userRolesString = "";
	for(String p : permissionList){
		userRolesString += userRolesString.equals("") ? p : "," + p;
	} 
	String basePath = request.getContextPath();
	System.out.println(basePath);
%>
<!DOCTYPE HTML>
<html lang="en">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>基本交路单元管理</title>
<!-- Bootstrap core CSS -->

<!--font-awesome-->
<link href="<%=basePath %>/assets/css/datepicker.css" rel="stylesheet">
<link href="<%=basePath %>/assets/easyui/themes/default/easyui.css"
	rel="stylesheet">
<link href="<%=basePath %>/assets/css/cross/custom-bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>/assets/css/cross/cross.css" rel="stylesheet"> 
<link href="<%=basePath %>/assets/easyui/themes/icon.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.min.css" /> 
<!-- Custom styles for this template --> 
 
<link href="<%=basePath %>/assets/css/style.css" rel="stylesheet"> 
<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/html5.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/respond.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/easyui/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=basePath %>/assets/js/knockout.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.freezeheader.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/ajaxfileupload.js"></script> 
<script type="text/javascript" src="<%=basePath %>/assets/js/trainplan/cross/crossunit.js"></script>  
<script type="text/javascript" src="<%=basePath %>/assets/js/datepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.gritter.min.js"></script> 
<script type="text/javascript" src="<%=basePath %>/assets/js/trainplan/common.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/knockout.pagemodle.js"></script> 
<!--#include virtual="assets/js/portal/knockout.pagefooter.tpl"-->
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
<script type="text/javascript">
var basePath = "<%=basePath %>";
var all_role = "<%=userRolesString %>";
console.log(all_role);
</script>
 

</head>
<body class="Iframe_body"  >
	
	<ol class="breadcrumb">
		<span><i class="fa fa-anchor"></i>当前位置:</span>
		<li><a href="#">基本交路单元管理</a></li>
	</ol> 
    <div id="plan_view_div_palnDayDetail" class="panel panel-default">
	<div class="row" style="margin: 10px 10px 10px 10px;">     
	<!--分栏框开始-->
	<div class="pull-left" style="width: 28%;">
		<!--分栏框开始--> 
			<div class="row" style="margin: 10px 10px 10px 10px;">
			    <section class="panel panel-default">
			        <div class="panel-heading"><i class="fa fa-table"></i>车底交路列表</div>
			        <div class="panel-body">
						<form class="form-horizontal" role="form"> 
							<div class="row" style="margin-top: 5px;">
									<div class="form-group"
										style="float: left; margin-left: 0px; margin-top: 0px;width: 100%"> 
										 <div class="row" style="width: 100%">
												<label for="exampleInputEmail3" class="control-label pull-left">
																方案:&nbsp;</label> 
												<div class="pull-left">
													<select style="width: 260px" id="input_cross_chart_id"
														class="form-control" data-bind="options:searchModle().charts, value: searchModle().chart, optionsText: 'name'">
													</select>
												</div>  
										   </div> 
										<div class="row"  style="margin-top: 5px;">
											<label for="exampleInputEmail3" class="control-label pull-left" >
												车辆担当局:</label>
											<div class="pull-left" style="margin-left: 5px;">
												<select style="width:60px" class="form-control" data-bind="options:searchModle().bureaus, value: searchModle().bureau, optionsText: 'shortName', optionsValue:'code', optionsCaption: '' ,event:{change: bureauChange}"></select>
											</div>
											<label for="exampleInputEmail3" class="control-label pull-left" style="margin-left: 40px;">
												始发路局:</label>
											<div class="pull-left" style="margin-left: 5px; ">
											<select style="width: 60px" class="form-control" data-bind="options:searchModle().startBureaus, value: searchModle().startBureau, optionsText: 'shortName', optionsValue:'code', optionsCaption: ''"></select>
											</div> 
										</div>    
										<div class="row"  style="margin-top: 5px;">
											<label for="exampleInputEmail3" class="control-label pull-left" >
												铁路线类型:</label>
											<div class="pull-left" style="margin-left: 5px;">
											    <select  style="width:60px" class="form-control" data-bind="options: searchModle().highlingFlags, value: searchModle().highlingFlag, optionsText: 'text' , optionsCaption: ''"></select>
											</div>
											 <label for="exampleInputEmail3" class="control-label pull-left" style="margin-left: 40px;">
												 审核状态:</label>
											<div class="pull-left" style="margin-left: 5px;">
												<select style="width:60px" id="input_cross_sure_flag"
													class="form-control" data-bind="options: searchModle().checkFlags, value: searchModle().checkFlag, optionsText: 'text' , optionsCaption: ''">
												</select>
											</div>
											
										</div>  
										
										<div class="row"  style="margin-top: 5px;">
										    <label for="exampleInputEmail3" class="control-label pull-left">
												车次:&nbsp;</label>
											<div class="pull-left">
												<input type="text" class="form-control" style="width: 100px;"
													 id="input_cross_filter_trainNbr" data-bind=" value: searchModle().filterTrainNbr, event:{keyup: trainNbrChange}">
											</div> 
											 <label for="exampleInputEmail3" class="control-label pull-left" style="margin-left: 40px;" >
												生成状态:</label>
											<div class="pull-left" style="margin-left: 5px;">
												<select style="width:60px" id="input_cross_sure_flag"
													class="form-control" data-bind="options: searchModle().unitCreateFlags, value: searchModle().unitCreateFlag, optionsText: 'text' , optionsCaption: '' ">
												</select>
											</div> 
										</div>
										<div class="row"  style="margin-top: 5px;">
											<div class="pull-left" style="margin-left: 20px;">
												<a type="button" class="btn btn-success" data-toggle="modal"
													data-target="#" id="btn_cross_search"  data-bind="click: loadCrosses">查询</a>
											</div> 
										</div>
										<hr style="margin-top: 8px;margin-bottom: 8px">
										<div>
											<label for="exampleInputEmail3" class="control-label pull-left" style="margin-left: 20px;">
												交路名:&nbsp;</label>
											<div class="pull-left">
												<select style="width: 66px" id="input_cross_filter_showFlag"
													class="form-control" data-bind="options: [{'code': 1, 'text': '简称'},{'code': 2, 'text': '全称'}], value: searchModle().shortNameFlag, optionsText: 'text', optionsValue: 'code'">
												</select>
											</div>   
										</div>
										<div class="pull-left" style="margin-left: 32px">
											<input type="checkbox" class="pull-left" class="form-control"
												value="1" data-bind=" event:{change: showCrossMapDlg}, checked: searchModle().showCrossMap"
												style="width: 20px; margin-left: 5px; margin-top: 5px"
												class="form-control">
										</div>
										<label for="exampleInputEmail5" class="control-label pull-left">
										 显示交路图</label>  
									</div>
								</div>
								<div class="row" >
									<div class="table-responsive">
										<div class="form-group"
											style="margin-left: 20px;">
											<a type="button" class="btn btn-success" data-toggle="modal"
												data-target="#" id="btn_cross_sure" data-bind="attr:{class: searchModle().activeFlag() == 1 ? 'btn btn-success' : 'btn btn-success disabled'}, click: checkCrossInfo">审核</a>
											<a type="button" class="btn btn-success" data-toggle="modal"
												data-target="#" id="btn_cross_delete" style="margin-left: 2px;" data-bind="attr:{class: searchModle().activeFlag() == 1 ? 'btn btn-success' : 'btn btn-success disabled'}, click: deleteCrosses">删除</a>
											<a type="button" class="btn btn-success" data-toggle="modal" style="margin-left: 2px;"
												data-target="#" id="btn_cross_createCrossUnit" data-bind="attr:{class: searchModle().activeFlag() == 1 ? 'btn btn-success' : 'btn btn-success disabled'}, click: createUnitCrossInfo">生成基本交路</a>
											
										</div>
										<table class="table table-bordered table-striped table-hover" 
												id="cross_table_crossInfo">
												<thead>
													<tr style="height: 25px">
														<th style="width: 10%" align="center"><input type="checkbox" style="margin-top:0" value="1" data-bind="checked: crossAllcheckBox, event:{change: selectCrosses}"></th>
														<th style="width: 8%" align="center">序号</th>
														<th style="width: 59%" align="center">车底交路名</th>
														<th style="width: 8%" align="center">审核</th>
														<th style="width: 15%" align="center" colspan="2">生成</th> 
													</tr>
												</thead>
												<tbody style="padding:0">
													 <tr style="padding:0">
													   <td colspan="6" style="padding:0">
															 <div style="height: 450px; overflow-y:auto;"> 
																<table class="table table-bordered table-striped table-hover" >
																	<tbody data-bind="foreach: crossRows.rows">
																		<tr data-bind=" visible: visiableRow, style:{color: $parent.currentCross().unitCrossId == unitCrossId ? 'blue':''}" >
																			<td  align="center" style="width: 10%"><input type="checkbox" value="1" data-bind="attr:{class: activeFlag() == 0 ? 'ckbox disabled' : ''}, event:{change: $parent.selectCross}, checked: selected"></td>
																			<td style="width: 10%" data-bind=" text: $parent.crossRows.currentIndex()+$index()+1 , click: $parent.showTrains"></td>
																			<td style="width: 58%" data-bind="text: $parent.searchModle().shortNameFlag() == 1 ? shortName : crossName, click: $parent.showTrains , attr:{title: crossName()}"></td>
																			<td  style="width:10%"  align="center" data-bind="style:{color:checkFlag() == 1 ? 'green' : ''},  text: checkFlag() == 1 ? '已' : '未' "></td>
																			<td  style="width: 11%" align="center" data-bind="style:{color:unitCreateFlag() == 1 ? 'green' : ''}, text: unitCreateFlag() == 1 ? '已' : '未' "></td>
																		</tr> 
																	</tbody> 
																</table> 
														 	</div>
														</td>
													</tr>
												</tbody>				 
											</table>  	 
										<div data-bind="template: { name: 'tablefooter-short-template', foreach: crossRows }"></div>
									</div>
								</div> 
						</form>
					 </div>
			   </section>
			</div> 
	</div>
	
	<div class="pull-right" style="width: 72%;">
		 
			<div class="row" style="margin: 10px 10px 10px 10px;">
			   <section class="panel panel-default">
			        <div class="panel-heading"><i class="fa fa-table"></i>交路信息</div>
			          <div class="panel-body">
						<div class="row" >
							<form class="form-horizontal" role="form" data-bind="with: currentCross"> 
							<!-- <table>
								<tr>
									<td><label for="exampleInputEmail3"
										class="control-label pull-left"> 车次:&nbsp;</label></td>
									<td colspan="3"><div class="pull-left">
											<input type="text" class="form-control" style="width: 380px;"
												placeholder="输入车次查询" id="plan_construction_input_trainNbr">
										</div></td> 
								</tr>
								<tr>
									<td><label for="exampleInputEmail3"
											class="control-label pull-left"> 交路名:&nbsp;</label></td>
									<td><div class="pull-left">
											<input type="text" class="form-control" style="width: 100px;"
												id="plan_construction_input_trainNbr">
										</div></td>
									<td><label for="exampleInputEmail3"
										class="control-label pull-left"> 组数:&nbsp;</label></td>
									<td><div class="pull-left">
											<input type="text" class="form-control" style="width: 100px;"
												id="plan_construction_input_trainNbr">
										</div></td>
								</tr>
								 <tr>
									<td><label for="exampleInputEmail3"
											class="control-label pull-left"> 交路名:&nbsp;</label></td>
									<td style="width: 250px;">adfaa</td>
									<td><label for="exampleInputEmail3"
										class="control-label pull-left"> 组数:&nbsp;</label></td>
									<td style="width: 250px;"> afad</td>
								</tr>
								<tr>
									<td><label for="exampleInputEmail3"
											class="control-label pull-left"> 交路名:&nbsp;</label></td>
									<td><div class="pull-left">
											<input type="text" class="form-control" style="width: 100px;"
												id="plan_construction_input_trainNbr">
										</div></td>
									<td><label for="exampleInputEmail3"
										class="control-label pull-left"> 组数:&nbsp;</label></td>
									<td><div class="pull-left">
											<input type="text" class="form-control" style="width: 100px;"
												id="plan_construction_input_trainNbr">
										</div></td>
								</tr>
							</table> -->
							<div class="pull-left" id="left_div">
								<div class="row" style="margin: 0px 0 5px 0;"> 
										<label for="exampleInputEmail3"
											class="control-label pull-left"> 车底交路名:&nbsp;</label>
										<div class="pull-left" style="margin-left: 26px;">
											<input type="text" class="form-control" style="width: 470px;" data-bind="value: crossName"
												id="plan_construction_input_trainNbr">
										</div> 
										<label for="exampleInputEmail5" class="control-label pull-left" style="margin-left:40px">
											线路线型:</label> 
										<div class="pull-left">
											<input type="radio" class="pull-left" class="form-control" 
												style="width: 20px; margin-left: 5px; margin-top: 5px"
												class="form-control" data-bind="checked: highlineFlag" value="0">
										</div>
										<label for="exampleInputEmail5" class="control-label pull-left">
											普线</label> 
										<div class="pull-left">
											<input type="radio" class="pull-left" class="form-control" 
												style="width: 20px; margin-left: 5px; margin-top: 5px"
												class="form-control" value="1" data-bind="checked: highlineFlag">
										</div>
										<label for="exampleInputEmail5" class="control-label pull-left">
											高线</label> 
										<div class="pull-left">
											<input type="radio" class="pull-left" class="form-control" 
												style="width: 20px; margin-left: 5px; margin-top: 5px"
												class="form-control" value="2" data-bind="checked: highlineFlag" >
										</div>
										<label for="exampleInputEmail5" class="control-label pull-left">
											混合</label> 
								</div>
								<div class="row" style="margin: 5px 0 0px 0;">
								    <label for="exampleInputEmail3"
											class="control-label pull-left"> 备用套跑交路名:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 470px;" data-bind="value: crossSpareName">
									</div> 
									<label for="exampleInputEmail5" style="margin-left: 40px;" class="control-label pull-left">
										开行状态:</label>
											
									<div class="pull-left">
										<input type="radio" class="pull-left" class="form-control"
											value="1" data-bind="checked: spareFlag"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										开行</label>

									<div class="pull-left">
										<input type="radio" class="pull-left" class="form-control"
											value="2" data-bind="checked: spareFlag"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										备用</label>
									<div class="pull-left">
										<input type="radio" class="pull-left" class="form-control"
											value="0" data-bind="checked: spareFlag"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										停运</label> 
								</div>
								<div class="row" style="margin: 5px 0 0px 0;"> 
									<label for="exampleInputEmail3"
										class="control-label pull-left"> 组数:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 40px;" data-bind="value: groupTotalNbr">
									</div> 
									<label for="exampleInputEmail2" style="margin-left: 23px;" class="control-label pull-left">对数:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 40px;" data-bind="value: pairNbr">
									</div> 
									
									
										
									<div class="pull-left">
										<input type="checkBox" class="pull-left" class="form-control"
											value="1" data-bind="checked: cutOld"
											style="width: 20px; margin-left: 23px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										截断原交路</label>
									
									<label for="exampleInputEmail5"  style="margin-left: 40px;" class="control-label pull-left">
										开始日期:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 140px;"
											placeholder=""  data-bind="value: crossStartDate">
									</div>
									
									<label for="exampleInputEmail5"  style="margin-left: 10px;" class="control-label pull-left">
										结束日期:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 140px;"
											placeholder=""  data-bind="value: crossEndDate">
									</div>
									
								</div>
								<div class="row" style="margin: 5px 0 0px 0;">
									<label for="exampleInputEmail5" class="control-label pull-left">
										普线开行规律:</label>
									<div class="pull-left">
										<input type="radio" class="pull-left" class="form-control"
											value="1" data-bind="checked: commonlineRule"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										每日</label>
									<div class="pull-left">
										<input type="radio" class="pull-left" class="form-control"
											value="2" data-bind="checked: commonlineRule"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										隔日</label>
									
									<label for="exampleInputEmail5" style="margin-left: 30px;" class="control-label pull-left">
										高线开行规律:</label>	
									<div class="pull-left">
										<input type="radio" class="pull-left" class="form-control"
											value="1" data-bind="checked: highlineRule"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										日常</label>
									<div class="pull-left">
										<input type="radio" class="pull-left" class="form-control"
											value="2" data-bind="checked: highlineRule"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										周末</label> 
									<div class="pull-left">
										<input type="radio" class="pull-left" class="form-control"
											value="3" data-bind="checked: highlineRule"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										高峰</label>  
									<label for="exampleInputEmail5" style="margin-left: 30px;" class="control-label pull-left">
										指定星期:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 71px;"
											placeholder=""   data-bind="value: appointWeek">
									</div>
									
									<label for="exampleInputEmail5"  style="margin-left: 10px;" class="control-label pull-left">
										指定日期:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 140px;"
											placeholder=""  data-bind="value: appointDay">
									</div>
									 
									 
								</div>
								
								<div class="row" style="margin: 5px 0 0px 0;">
									<label class="control-label pull-left"> 车辆担当局:&nbsp;</label>
										<div class="pull-left">
											<select style="width: 50px" class="form-control" data-bind="options: $parent.gloabBureaus, value: tokenVehBureau, optionsText: 'shortName', optionsValue:'code' , optionsCaption: ''"></select>
											<!-- <input type="text" class="form-control" style="width: 30px;"  data-bind="value: tokenVehBureau"> -->
										</div>
									<label  class="control-label pull-left" style=" margin-left: 20px;"> 车辆段/动车段:&nbsp;</label>
										<div class="pull-left">
											<input type="text" class="form-control" style="width: 100px;" data-bind="value: tokenVehDept">
										</div>
									<label  class="control-label pull-left" style=" margin-left: 20px;" > 动车所:&nbsp;</label>
										<div class="pull-left">
											<input type="text" class="form-control" style="width: 100px;" data-bind="value: tokenVehDepot">
										</div>
									<label for="exampleInputEmail3"
											class="control-label pull-left" style=" margin-left: 30px;" > 客运担当局:&nbsp;</label>
									<div class="pull-left">
										<!-- <input type="text" class="form-control" style="width: 30px;" data-bind="value: tokenPsgDept"> -->
										
										<select style="width: 50px" class="form-control" data-bind="options: $parent.gloabBureaus, value: tokenPsgBureau, optionsText: 'shortName', optionsValue:'code', optionsCaption: ''"></select>
									</div>
									<label for="exampleInputEmail3"
										class="control-label pull-left" style=" margin-left: 20px;" > 客运段:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 100px;" data-bind="value: tokenPsgDept">
									</div>
								</div>
								<div class="row" style="margin: 5px 0 0px 0;"> 
									<label for="exampleInputEmail3"
										class="control-label pull-left" > 运行区段:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 200px;" data-bind="value: crossSection">
									</div>
									<label for="exampleInputEmail3" style=" margin-left: 20px;" 
											class="control-label pull-left"> 机车类型:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 50px;" data-bind="value: locoType">
									</div>
									<label for="exampleInputEmail3"
											class="control-label pull-left" style=" margin-left: 13px;"  > 动车组车型:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 80px;" data-bind="value: crhType">
									</div>
									<div class="pull-left">
										<input type="checkbox" class="pull-left" class="form-control"
											value="1" data-bind="checked: elecSupply"
											style="width: 20px; margin-left: 25px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										供电</label>
									<div class="pull-left">
										<input type="checkbox" class="pull-left" class="form-control"
											value="1" data-bind="checked: dejCollect"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										集便</label>
									<div class="pull-left">
										<input type="checkbox" class="pull-left" class="form-control"
											value="1" data-bind="checked: airCondition"
											style="width: 20px; margin-left: 5px; margin-top: 5px"
											class="form-control">
									</div>
									<label for="exampleInputEmail5" class="control-label pull-left">
										空调</label> 
								
								<!-- <div class="pull-left">
									<input type="checkBox" class="pull-left" class="form-control"
										name="exampleInputEmail5"
										style="width: 20px; margin-left: 10px; margin-top: 5px"
										class="form-control">
								</div>
								<label for="exampleInputEmail5" class="control-label pull-left">
									是否切断久交路图</label> <label for="exampleInputEmail2"
									class="control-label pull-left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;调整:&nbsp;</label>
								<div class="pull-left">
									<input type="text" class="form-control" style="width: 50px;"
										placeholder="" id="plan_construction_selectdate">
								</div> -->
							</div>
							<div class="row" style="margin: 5px 0 0px 0;">
							    <label for="exampleInputEmail3"
										class="control-label pull-left" > 经由线:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 214px;" data-bind="value: throughline">
									</div> 
								<label for="exampleInputEmail3"   style=" margin-left: 19px;" 
											class="control-label pull-left"> 备注:&nbsp;</label>
									<div class="pull-left">
										<input type="text" class="form-control" style="width: 400px;" data-bind="value: note">
									</div> 
									  <a type="button" style="margin-left: 15px"
										class="btn btn-success" data-toggle="modal" data-target="#"
										id="cross_train_save" data-bind="attr:{class: $parent.searchModle().activeCurrentCrossFlag() == 1 ? 'btn btn-success' : 'btn btn-success disabled'},click: $parent.saveCrossInfo"> 保存</a>
								</div>  
							</div>
							<!--col-md-3 col-sm-4 col-xs-4-->
						</form>
					    </div>
					  </div>
				   </section>
				</div>
				<div class="row" style="margin: 15px 10px 10px 10px;">
					<div class="pull-left" style="width: 100%;">
					<section class="panel panel-default" >
				         <div class="panel-heading"><i class="fa fa-table"></i>列车信息</div>
				          <div class="panel-body" style="overflow-y:auto">
							<div class="table-responsive">
								<div class="form-group"
									style="margin-left: 10px; margin-top: 5px;">
									  <a type="button"
										class="btn btn-success" data-toggle="modal" data-target="#"
										id="cross_train_add" data-bind="attr:{class: searchModle().activeCurrentCrossFlag() == 1 ? 'btn btn-success' : 'btn btn-success disabled'},click: showCrossTrainDlg">添加</a> <a type="button"
										class="btn btn-success" data-toggle="modal" data-target="#"
										id="cross_train_save" data-bind="attr:{class: searchModle().activeCurrentCrossFlag() == 1 ? 'btn btn-success' : 'btn btn-success disabled'}, click: showCrossTrainDlg"> 修改</a> <a type="button"
										class="btn btn-success" data-toggle="modal" data-target="#" data-bind="attr:{class: searchModle().activeCurrentCrossFlag() == 1 ? 'btn btn-success' : 'btn btn-success disabled'}"
										id="cross_train_delete">删除</a> 
										 <a type="button"
										class="btn btn-success" data-toggle="modal" data-target="#"
										id="cross_train_delete" data-bind="click: showCrossTrainTimeDlg">时刻表</a> 
								</div>
								<table class="table table-bordered table-striped table-hover"
									id="cross_trainInfo">
									<thead>
										<tr> 	
											<th style="width: 5%">组序</th> 
											<th style="width: 5%">车序</th>
											<th style="width: 8%">车次</th>  
											<th style="width: 7%">始发日期</th>
											<th style="width: 7%">终到日期</th>
											<th style="width: 8%">发站</th>
											<th style="width: 5%">发局</th>
											<th style="width: 8%">到站</th>
											<th style="width: 5%">到局</th>
											<th style="width: 5%">线型</th>
											<th style="width: 5%">间隔</th>
											<th style="width: 5%">状态</th> 
											<th style="width: 5%">备用套跑</th>
											<th style="width: 5%">高线规律</th>
											<th style="width: 5%">普线规律</th>
											<th style="width: 10%">特殊规律</th>
											<th style="width: 5%">交替车次</th>
											<th style="width: 9%">交替时间</th>
										</tr>
									</thead> 
									<tbody data-bind="foreach: trains" >
										<tr  data-bind="click: $parent.setCurrentTrain, style:{color: $parent.currentTrain() != null && $parent.currentTrain().trainNbr == trainNbr ? 'blue':''}">
											<td style="width: 5px" data-bind="text: groupSerialNbr"></td> 
											<td style="width: 5px" data-bind="text: trainSort"></td>
											<td style="width: 60px" data-bind="text: trainNbr"></td> 
											<td style="width: 60px" data-bind="text: runDate"></td> 
											<td style="width: 60px" data-bind="text: endDate"></td> 
											<td style="width: 100px" data-bind="text: startStn, attr:{title: startStn}"></td>
											<td style="width: 50px" data-bind="text: startBureau"></td>
											<td style="width: 100px" data-bind="text: endStn, attr:{title: endStn}"></td>
											<td style="width: 50px" data-bind="text: endBureau"></td>
											<td style="width: 50px" data-bind="text: highlineFlag"></td>
											<td style="width: 50px" data-bind="text: dayGap"></td>
											<td style="width: 50px" data-bind="text: spareFlag"></td> 
											<td style="width: 50px" data-bind="text: spareApplyFlage"></td>
											<td style="width: 50px" data-bind="text: highlineRule"></td>
											<td style="width: 50px" data-bind="text: commonLineRule"></td>
											<td style="width: 50px" data-bind="text: otherRule"></td>
											<td style="width: 50px" data-bind="text: alertNateTrainNbr"></td>
											<td style="width: 50px" data-bind="text: alertNateTime"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div> 
					</section>
					</div>
					<!-- <div class="pull-right" style="width: 28%;margin: 0px 10px 10px 10px;"> -->
				
				 
				</div>
			</div>  
	 <!--交路图--> 
	<div id="cross_map_dlg" class="easyui-dialog" title="交路图"
		data-options="iconCls:'icon-save'"
		style="width: 800px; height: 400px;">
		 <iframe style="width: 100%; height: 350px;border: 0;" src=""></iframe>
	</div> 
	
	 <!--列车新增和修改--> 
	<div id="cross_train_dlg" class="easyui-dialog" title="列车基本信息编辑"
		data-options="iconCls:'icon-save'"
		style="width: 400px; height: 500px; padding: 10px">
		  
	</div> 
	 
	<!--详情时刻表--> 
	<div id="cross_train_time_dlg" class="easyui-dialog" title="时刻表"
		data-options="iconCls:'icon-save'"
		style="width: 600px; height: 500px; padding: 10px;"> 
			      <!--panle-heading-->
			      <div class="panel-body" style="padding:10px;margin-right:10px;">
			      	<div class="table-responsive" >  
			          <table class="table table-bordered table-striped table-hover" id="plan_runline_table_trainLine">
					        <thead>
					        <tr >
					          <th style="width:5%">序号</th>
			                  <th style="width:20%">站名</th>
			                  <th style="width:5%">路局</th>
			                  <th style="width:15%">到达时间</th>
			                  <th style="width:15%">出发时间</th>
			                  <th style="width:15%">停留时间</th>
			                  <th style="width:10%">天数</th> 
			                  <th style="width:15%" colspan="2">股道</th>  
			                 </tr>
					        </thead>
					        <tbody style="padding:0">
								 <tr style="padding:0">
								   <td colspan="9" style="padding:0">
										 <div style="height: 400px; overflow-y:auto;"> 
											<table class="table table-bordered table-striped table-hover" >
												 <tbody data-bind="foreach: times">
										           <tr>  
													<td style="width:6%" align="center" data-bind=" text: $index() + 1"></td>
													<td style="width:19%" data-bind="text: stnName, attr:{title: stnName}"></td>
													<td style="width:7%" align="center" data-bind="text: bureauShortName"></td>
													<td style="width:15%" align="center" data-bind="text: sourceTime"></td>
													<td style="width:15%" align="center" data-bind="text: targetTime"></td>
													<td style="width:15%" align="center" data-bind="text: stepStr"></td>
													<td style="width:10%" align="center" data-bind="text: runDays"></td>
													<td style="width:15%" align="center" data-bind="text: trackName"></td>
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
</html>
