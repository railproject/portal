<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% 
String basePath = request.getContextPath();
System.out.println(basePath);
%>
<!DOCTYPE HTML>
<html lang="en">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>客运计划管理</title>
<!-- Bootstrap core CSS -->
<link href="<%=basePath %>/assets/css/cross/custom-bootstrap.css" rel="stylesheet">
<!--font-awesome-->
<link href="<%=basePath %>/assets/css/datepicker.css" rel="stylesheet">
<link href="<%=basePath %>/assets/easyui/themes/default/easyui.css"
	rel="stylesheet"> 
<link href="<%=basePath %>/assets/easyui/themes/icon.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/assets/css/cross/custom-bootstrap.css">
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/assets/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/assets/css/datepicker.css">
<link rel="stylesheet" type="text/css" media="screen" href="<%=basePath%>/assets/css/style.css">
	
<link href="<%=basePath %>/assets/easyui/themes/icon.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.min.css" />
 
<!-- Custom styles for this template --> 
<link href="<%=basePath %>/assets/css/cross/cross.css" rel="stylesheet">  
<link href="<%=basePath %>/assets/css/style.css" rel="stylesheet">



   

<script src="<%=basePath %>/assets/js/trainplan/knockout.pagemodle.js"></script> 
<script type="text/javascript">
var basePath = "<%=basePath %>";
</script>
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

 
</head>
<body class="Iframe_body"  >
	
	<ol class="breadcrumb">
		<span><i class="fa fa-anchor"></i>当前位置:</span>
		<li><a href="#">客运计划管理</a></li>
	</ol> 
   	<div id="plan_view_div_palnDayDetail" class="panel panel-default">
	   <div class="row" style="margin: 10px 10px 10px 10px;"> 
	
	<!--分栏框开始-->
		<div class="pull-left" style="width: 27%;height:100%">
			<!--分栏框开始-->  
			    <div class="row" style="margin: 5px 10px 10px 10px;">
				    <section class="panel panel-default">
				        <div class="panel-heading"><i class="fa fa-table"></i>车底交路列表</div>
				        	<div class="panel-body">
								<form class="form-horizontal" role="form"> 
									<div class="row" style="margin-top: 5px;">
									        <div class="row"  style="margin-top: 5px;">
											    <div class="form-group" style="float: left; margin-left: 0px; margin-top: 0px;width: 100%">  
														<label for="exampleInputEmail3" class="control-label pull-left">
																		方案:&nbsp;</label> 
														<div class="pull-left">
															<select style="width: 273px" id="input_cross_chart_id"
																class="form-control" data-bind="options:searchModle().charts, value: searchModle().chart, optionsText: 'name', optionsCaption: ''">
															</select>
														</div>  
												</div> 
											</div>
											<div class="row"  style="margin-top: 5px;">
											    <label for="exampleInputEmail3" class="control-label pull-left">
													车次:&nbsp;</label>
												<div class="pull-left">
													<input type="text" class="form-control" style="width: 100px;"
														placeholder="车次" id="input_cross_filter_trainNbr" data-bind=" value: searchModle().filterTrainNbr">
												</div>  
												<div class="pull-left" style="margin-left: 16px;">
													<a type="button" class="btn btn-success" data-toggle="modal"
														data-target="#" id="btn_cross_search"  data-bind="click: loadCrosses">查询</a>
												</div> 
											</div> 
									</div>
									<div class="row" style="margin-top:10px">
										   <div class="pull-left" style="width: 60%;">
										        <section class="panel panel-default">
										          <div class="panel-heading">车底交路名</div>
										        	<div class="panel-body" style="height: 450px; overflow-y:auto "> 
														<div class="table-responsive"> 
															<table class="table table-bordered table-striped table-hover" 
																id="cross_table_crossInfo"> 
																<tbody data-bind="foreach: crossRows.rows">
																	<tr data-bind=" visible: visiableRow, style:{color: $parent.currentCross().planCrossId == planCrossId ? 'blue':''}, click: $parent.showTrains" >
																		<td data-bind=" text: crossName , attr:{title: crossName}"></td>
																	</tr> 
																</tbody>  					 
															</table>
														</div> 
												   </div>
											   </section>
											</div>
											<div class="pull-right" style="width: 40%;">  
											 <section class="panel panel-default">
										          <div class="panel-heading">车次</div>
										        	<div class="panel-body" style="height: 450px; overflow-y:auto "> 
												      <div class="table-responsive" > 
															<table class="table table-bordered table-striped table-hover"
																id="cross_trainInfo" > 
																<tbody data-bind="foreach: trains" >
																	<tr  data-bind="click: $parent.showTrainTimes, style:{color: $parent.currentTrain() != null && $parent.currentTrain().trainNbr == trainNbr ? 'blue':''}">
																		<td style="width: 60px" data-bind="text: trainNbr, attr:{title: trainNbr}"></td>
																	</tr>
																</tbody>
															</table> 
													   </div>
											      </div>  
											    </section>
										</div> 
								  </div> 
							 </form>
						 </div>
				   </section>
				</div>   
		</div>
		 <div class="pull-right" style="width: 72.5%;"> 
		   
		<!-- Nav tabs -->
		<div class="pull-left" style="width: 100%;"> 
					<ul class="nav nav-tabs" >
					  <li class="active"><a href="#home" data-toggle="tab">车底交路</a></li>
					  <li><a href="#profile" data-toggle="tab">交路基本信息</a></li> 
					</ul> 
					<!-- Tab panes -->
					<div class="tab-content" >
					  <div class="tab-pane active" id="home"> 
					       <div class="pull-left" style="width: 70%;"> 
							 <div class="panel-body" >
							      	<div class="row" style="margin:5px 0 10px 0;">
								      <form class="form-inline" role="form">
							              <div class="input-group">
							                  <label class="margin-right-10">开始日期:</label>
							                  <input type="text" class="form-control" style="width:120px;" placeholder="" id="canvas_runplan_input_startDate"  name="startDate" data-bind="value: searchModle().planStartDate()" />
							                  <label class="margin-right-10">&nbsp;&nbsp;截至日期:</label>
							                  <input type="text" class="form-control" style="width:120px;" placeholder="" id="canvas_runplan_input_endDate"  name="endDate" data-bind="value: searchModle().planEndDate()" />
								              <button class="btn btn-primary" type="button" id="canvas_event_btnQuery"><i class="fa fa-search"></i>查询</button>
							              </div>
							              <div class="row" style="margin:5px 0 10px 200px;">
							                  <button type="button" class="btn btn-success" id="canvas_event_btn_x_magnification"><i class="fa fa-search-plus"></i>X+</button>
								              <button type="button" class="btn btn-success" id="canvas_event_btn_x_shrink"><i class="fa fa-search-minus"></i>X-</button>
								              <button type="button" class="btn btn-success" id="canvas_event_btn_y_magnification"><i class="fa fa-search-plus"></i>Y+</button>
								              <button type="button" class="btn btn-success" id="canvas_event_btn_y_shrink"><i class="fa fa-search-minus"></i>Y-</button>
								              
								                                                当前比例倍数：｛X:<label id="canvas_event_label_xscale">1</label>倍；Y:<label id="canvas_event_label_yscale">1</label>倍｝
								          </div>
							          </form>
								    </div> 
							        <div class="table-responsive" style="width:100%;height:450px;overflow-x:auto; overflow-y:auto;">
							        	<canvas id="canvas_event_getvalue"></canvas>
							        </div> 
							      </div> 
						</div>
						 <div class="pull-right" style="width: 30%; margin-top:5px"> 
						        <div id="plan_view_div_palnDayDetail" class="panel panel-default">
							      <div class="panel-heading">
							        <h3 class="panel-title" style="float: left;"> <i class="fa fa fa-folder-open"></i>详情时刻表  <span style="margin-left:5px" data-bind="text:currentTrain() == null ? '' : '车次:' + currentTrain().trainNbr"></span><label id="plan_view_div_palnDayDetail_title"></label></h3>
							      </div>
							      <!--panle-heading-->
							      <div class="panel-body" style="padding:10px;">
							      	<div class="table-responsive" > 
							          <table class="table table-bordered table-striped table-hover" id="plan_runline_table_trainLine">
									        <thead>
									        <tr >
									          <th style="width:5%">序号</th>
							                  <th style="width:20%">站名</th> 
							                  <th style="width:15%">到时</th>
							                  <th style="width:15%">发时</th> 
							                 </tr>
									        </thead>
									      <tbody data-bind="foreach: stns">
									           <tr>  
												<td align="center" data-bind=" text: $index"></td>
												<td data-bind="text: stnName, attr:{title: stnName}"></td> 
												<td align="center" data-bind="text: arrTime"></td>
												<td align="center" data-bind="text: dptTime"></td> 
									        	</tr>
									        </tbody>
									      </table>
							        </div>  
							        </div>  
							      </div>
						</div> 
				  </div>
				  <div class="tab-pane" id="profile">
				      <div class="row" style="margin: 10px 10px 10px 10px;">
						   <section class="panel panel-default">
						        <div class="panel-heading"><i class="fa fa-table"></i>交路信息</div>
						          <div class="panel-body">
									<div class="row" >
										<form class="form-horizontal" role="form" data-bind="with: currentCross">  
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
													id="cross_train_save" data-bind="click: $parent.saveCrossInfo"> 保存</a>
											</div>  
										</div>
										<!--col-md-3 col-sm-4 col-xs-4-->
									</form>
								    </div>
								  </div>
							   </section>
							</div>
				  	 </div>
				  </div>  
			 </div> 
		</div>
     </div>
   	   <div class="row" style="margin: 10px 10px 10px 10px;" data-bind="click: dragRunPlan">
 	   	    <ul class="nav nav-tabs" >
			  <li class="active"><a href="#runPlan" data-toggle="tab">开行情况</a></li>
			</ul> 
			<!-- Tab panes -->
			<div class="tab-content" >
			  <div class="tab-pane active" id="runPlan"> 
			  		 <div id="plan_view_div_palnDayDetail" class="panel panel-default"> 
							      <!--panle-heading-->
							      <div class="panel-body" style="padding:10px;overflow-y: auto">
							      	<div class="table-responsive" > 
							          <table class="table table-bordered table-striped table-hover" id="run_plan_table">
								      <thead> 
								      	<tr data-bind="template: { name: 'runPlanTableDateHeader', foreach: planDays }"></tr>
								      </thead>
								      <tbody data-bind="template: { name: 'runPlanTableVlaues', foreach: trainPlans }"> 
								      </tbody> 
									  </table>
							        </div>  
							        </div>  
							      </div>
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


<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/html5.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/respond.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/easyui/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=basePath %>/assets/js/knockout.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.freezeheader.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/ajaxfileupload.js"></script> 
<script type="text/javascript" src="<%=basePath %>/assets/js/trainplan/cross/run_plan.js"></script>  
<script type="text/javascript" src="<%=basePath %>/assets/js/datepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.gritter.min.js"></script> 
<script type="text/javascript" src="<%=basePath %>/assets/js/trainplan/common.js"></script> 
<script type="text/javascript" src="<%=basePath%>/assets/js/respond.min.js"></script>
<script src="<%=basePath %>/assets/js/moment.min.js"></script>
<script src="<%=basePath %>/assets/lib/fishcomponent.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>/assets/js/portal/common.security.js"></script> --%>
<script src="<%=basePath %>/assets/js/trainplan/common.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/util/canvas.util.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/util/canvas.component.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/runPlan/canvas_event_getvalue_data.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/runPlan/canvas_event_getvalue.js"></script>
<script type="text/javascript">
var basePath = "<%=basePath %>";
</script>

<script  type="text/html" id="runPlanTableDateHeader"> 
    <!-- ko if: $index() == 0 -->
 	<td></td>
 	<!-- /ko --> 
 	<td data-bind="text: day"></td>
</script>
<script  type="text/html" id="runPlanTableVlaues">
 <tr data-bind="foreach: runPlans">
    <!-- ko if: $index() == 0 --> 
 	<td data-bind="text: $parent.trainNbr"></td>
 	<!-- /ko -->  
 	<td><input type="checkbox" value="1" data-bind="checked: runFlag, event:{change: $root.trainRunPlanChange} , attr:{name: $parent.trainNbr + ',' + day}"></td>
 </tr> 
</script>
</html>
