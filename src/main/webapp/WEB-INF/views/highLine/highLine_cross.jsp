<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% 
 
String basePath = request.getContextPath();
%>
<!DOCTYPE HTML>
<html lang="en">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>高铁交路计划审核</title>
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
		<li><a href="#">高铁交路计划审核</a></li>
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
						  <a type="button"  style="margin-left:30px" class="btn btn-success" data-toggle="modal" data-target="#" data-bind="click: createHighLineCrosses">加载图定</a>
						  <a type="button"  style="margin-left:30px" class="btn btn-success" data-toggle="modal" data-target="#" data-bind="click: showActiveHighLineCrossDlg">调整交路</a>
						  
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
										</tr>
									</thead>
									<tbody style="padding:0">
										 <tr style="padding:0">
										   <td colspan="11" style="padding:0">
												 <div id="plan_train_panel_body" style="height: 250px; overflow-y:auto;"> 
													<table class="table table-bordered table-striped table-hover" >
														<tbody data-bind="foreach: highLineCrossRows">
															<tr data-bind=" visible: visiableRow, style:{color: $parent.currentCross().highLineCrossId == highLineCrossId ? 'blue':''}" >
																<!-- <td align="center" style="width: 10%"><input type="checkbox" value="1" data-bind="attr:{class: activeFlag() == 1  || checkActiveFlag() == 1?  '' : 'ckbox disabled'},event:{change: $parent.selectCross}, checked: selected"></td> -->
														        <td style="width: 36px" data-bind="text: $index() + 1"></td> 
															     <td data-bind="text: $parent.searchModle().shortNameFlag() == 1 ? shortName : crossName, attr:{title: crossName}, click: $parent.showTrains" ></td>
															     <td style="width: 8%" data-bind="text: vehicle1"></td>
															     <td style="width: 8%" data-bind="text: vehicle2"></td>
															     <td style="width: 8%" data-bind="text: ''"></td> 
															     <td style="width: 8%" data-bind="text: startStn"></td>
															     <td style="width: 8%" data-bind="text: endStn"></td>
															     <td style="width: 8%" data-bind="text: tokenVehBureauShowValue"></td>
															     <td style="width: 8%" data-bind="text: spareFlag() == 2 ? '是' : '否'"></td>
															     <td style="width: 7%" data-bind="text: ''"></td>
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
	   <div class="row" style="margin: 10px 10px 10px 10px;">  
	        <!--分栏框开始-->
		    <div class="pull-left" style="width: 30%;height:100%">
			<!--分栏框开始-->   
			         <div class="panel panel-default"> 
				       <div class="panel-body"> 
							<table class="table table-bordered table-striped table-hover" style="margin-left:5px; margin-right:5px;"
											id="cross_table_crossInfo">
											<thead>
												<tr style="height: 26px"> 
													<th style="width: 20%" align="center">车次</th> 
													<th style="width: 20%" align="center">始发站名</th> 
													<th style="width: 20%" align="center">始发时间</th> 
													<th style="width: 20%" align="center">终到站名</th> 
													<th style="width: 20%" align="center">终到时间</th> 
												</tr>
											</thead>
											<tbody style="padding:0">
												 <tr style="padding:0">
												   <td colspan="5" style="padding:0">
														 <div id="plan_cross_panel_body" style="height: 220px; overflow-y:auto;"> 
															<table class="table table-bordered table-striped table-hover"
																id="cross_trainInfo" > 
																<tbody data-bind="foreach: trains" >
																	<tr  data-bind="click: $parent.showTrainTimes, style:{color: $parent.currentTrain() != null && $parent.currentTrain().trainNbr == trainNbr ? 'blue':''}">
																		<td style="width: 20%" data-bind="text: trainNbr, attr:{title: trainNbr}"></td>
																		<td style="width: 20%" data-bind="text: startStn, attr:{title: startStn}"></td>
																		<td style="width: 20%" data-bind="text: startTime, attr:{title: startTime}"></td>
																		<td style="width: 20%" data-bind="text: endStn, attr:{title: endStn}"></td>
																		<td style="width: 18%" data-bind="text: endTime, attr:{title: endTime}"></td>
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
			 
		        <div class="pull-right" style="width: 69%;">  
		        <div class="panel panel-default"> 
					 <div class="panel-body" >
					      	<div class="row" style="margin:5px 0 10px 0;">
						      <form class="form-inline" role="form">
					              <!-- <div class="input-group">
					                  <label class="margin-right-10">开始日期:</label>
					                  <input type="text" class="form-control" style="width:120px;" placeholder="" id="canvas_runplan_input_startDate"  name="startDate" data-bind="value: searchModle().planStartDate" />
					                  <label class="margin-right-10">&nbsp;&nbsp;截至日期:</label>
					                  <input type="text" class="form-control" style="width:120px;" placeholder="" id="canvas_runplan_input_endDate"  name="endDate" data-bind="value: searchModle().planEndDate" />
						              <button class="btn btn-primary" type="button" id="canvas_event_btnQuery"><i class="fa fa-search"></i>查询</button>
					              </div> -->
					              <div class="row" style="margin:5px 0 10px 50px;">
						         		<button type="button" class="btn btn-success btn-xs" id="canvas_event_btn_refresh"><i class="fa fa-refresh"></i>刷新</button>
									  <span><input type="checkbox" id="canvas_checkbox_stationType_jt" name="canvas_checkbox_stationType" checked="checked" style="margin-left:10px">简图</span>
						         	  <input type="checkbox" id="canvas_checkbox_trainTime" style="margin-left:10px;margin-top:2px"  value=""/>时刻
						         	   &nbsp;&nbsp;车底：<select id="canvas_select_groupSerialNbr"></select>
						              <button style="margin-left:10px" type="button" class="btn btn-success btn-xs" id="canvas_event_btn_x_magnification"><i class="fa fa-search-plus"></i>X+</button>
						              <button type="button" class="btn btn-success btn-xs" id="canvas_event_btn_x_shrink"><i class="fa fa-search-minus"></i>X-</button>
						              <button type="button" class="btn btn-success btn-xs" id="canvas_event_btn_y_magnification"><i class="fa fa-search-plus"></i>Y+</button>
						              <button type="button" class="btn btn-success btn-xs" id="canvas_event_btn_y_shrink"><i class="fa fa-search-minus"></i>Y-</button>
						                                                 比例：｛X:<label id="canvas_event_label_xscale">1</label>倍；Y:<label id="canvas_event_label_yscale">1</label>倍｝
						                       
						         </div>
					          </form>
						    </div> 
					        <div id="canvas_parent_div" class="table-responsive" style="width:100%;height:200px;overflow-x:auto; overflow-y:auto;">
					        	<canvas id="canvas_event_getvalue"></canvas>
					        </div> 
					      </div> 
				</div> 
			</div>
		</div>  
	  <!--详情时刻表--> 
	 <div id="run_plan_train_times" class="easyui-dialog" title="时刻表"
		data-options="iconCls:'icon-save'"
		style="width: 608px; height: 500px; padding: 10px;"> 
			      <!--panle-heading-->
			      <div class="panel-body" style="padding:10px;margin-right:10px;">
				       <ul class="nav nav-tabs" >
						  <li class="active"><a style="padding:3px 10px;" href="#simpleTimes" data-toggle="tab">简点</a></li> 
						  <li><a style="padding:3px 10px;" href="#allTimes" data-toggle="tab">详点</a></li> 
						  <li style="float:right" ><span style="font: -webkit-small-control;" data-bind="html: currentTrainInfoMessage()"></span></li>
						</ul> 
						<!-- Tab panes -->
						<div class="tab-content" >
						  <div class="tab-pane active" id="simpleTimes" > 
					      	<div class="table-responsive" > 
					            <table class="table table-bordered table-striped table-hover" id="plan_runline_table_trainLine">
							        <thead> 
							         <tr>
							          <th style="width:7.5%">序号</th>
					                  <th style="width:19%">站名</th>
					                  <th style="width:7%">路局</th>
					                  <th style="width:14.5%">到达</th>
					                  <th style="width:14.5%">出发</th>
					                  <th style="width:14%">停时</th>
					                  <th style="width:10%">天数</th> 
					                  <th style="width:15%" colspan="2">股道</th>  
					                 </tr>
							        </thead>
							        <tbody style="padding:0">
										 <tr style="padding:0">
										   <td colspan="9" style="padding:0">
												 <div id="simpleTimes_table" style="height: 400px; overflow-y:auto;"> 
													<table class="table table-bordered table-striped table-hover" >
														 <tbody data-bind="foreach: simpleTimes">
												           <tr data-bind="visible: stationFlag != 'BTZ'">  
															<td style="width:7.5%" align="center" data-bind=" text: $index() + 1"></td>
															<td style="width:19%" data-bind="text: stnName, attr:{title: stnName}"></td>
															<td style="width:7.5%" align="center" data-bind="text: bureauShortName"></td>
															<td style="width:14.3%" align="center" data-bind="text: sourceTime"></td>
															<td style="width:14.3%" align="center" data-bind="text: targetTime"></td>
															<td style="width:14%" align="center" data-bind="text: stepStr"></td>
															<td style="width:10%" align="center" data-bind="text: runDays"></td>
															<td style="width:10%" align="center" data-bind="text: trackName"></td>
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
			        	<div class="tab-pane" id="allTimes" > 
					      	<div class="table-responsive" > 
					            <table class="table table-bordered table-striped table-hover" id="plan_runline_table_trainLine">
							        <thead> 
							         <tr>
							          <th style="width:7.5%">序号</th>
					                  <th style="width:19%">站名</th>
					                  <th style="width:7%">路局</th>
					                  <th style="width:14.5%">到达</th>
					                  <th style="width:14.5%">出发</th>
					                  <th style="width:14%">停时</th>
					                  <th style="width:10%">天数</th> 
					                  <th style="width:15%" colspan="2">股道</th>  
					                 </tr> 
							        </thead>
							        <tbody style="padding:0">
										 <tr style="padding:0">
										   <td colspan="9" style="padding:0">
												 <div id="allTimes_table" style="height: 400px; overflow-y:auto;"> 
													<table class="table table-bordered table-striped table-hover" > 
														 <tbody data-bind="foreach: times">
												           <tr>  
															<td style="width:7.5%" align="center" data-bind=" text: $index() + 1"></td>
															<td style="width:19%" data-bind="text: stnName, attr:{title: stnName}"></td>
															<td style="width:7.5%" align="center" data-bind="text: bureauShortName"></td>
															<td style="width:14.3%" align="center" data-bind="text: sourceTime"></td>
															<td style="width:14.3%" align="center" data-bind="text: targetTime"></td>
															<td style="width:14%" align="center" data-bind="text: stepStr"></td>
															<td style="width:10%" align="center" data-bind="text: runDays"></td>
															<td style="width:10%" align="center" data-bind="text: trackName"></td>
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
	    <div id="active_highLine_cross_dialog" class="easyui-dialog"
	      title="交路调整"
		  data-options="iconCls:'icon-save'"
		   style="width: 1200px; height: 500px; padding: 10px;" >  
		      <div class="row" style="width:100%;height:90%"> 
		    	 <div class="pull-left" style="width: 27%;height:100%" >
				<!--分栏框开始-->    
					   <section class="panel panel-default" style="height:100%">
				        <div class="panel-heading">
				        	<span>
				              <i class="fa fa-table"></i>交路列表
						   </span>
						</div> 
				          <div class="panel-body" style="height:93%">
					         <div class="row" style="width:100%;height:100%"> 
						        	<select multiple="multiple" id="current_highLineCrosses" style="width:100%;height:100%" data-bind="options: highLineCrossRows, optionsText: 'crossName', selectedOptions:selectedHighLineCrossRows" >
						        	 
						        	</select>
							  </div>  
						</div>
						</section>
				</div>
				<div class="pull-left" style="width: 25%;height:100%" >
				<!--分栏框开始-->    
				  <section class="panel panel-default" style="height:100%">
				        <div class="panel-heading">
				        	<span>
				              <i class="fa fa-table"></i>交路组合列表
						   </span>
						</div> 
				          <div class="panel-body" style="height:93%">
				             <div class="row" style="width:100%;height:95%"> 
						          <div class="pull-left" style="width:90%;height:100%"> 
							        	<select multiple="multiple" style="width:100%;height:100%" data-bind="options: acvtiveHighLineCrosses, optionsText: 'crossName', selectedOptions: selectedActiveHighLineCrossRows, event:{change: selectedActiveHighLineCrossChange} "></select>
								  </div>
								  <div class="pull-left" style="width:10%;height:100%"> 
								          <div style="height:40%;" > 
									      </div> 
									      <div style="height:60%;" > 
									        <a data-bind="click: activeCrossUp" style="margin-left: 2px"><i class="fa fa-chevron-up blue"></i></a>
									        <a data-bind="click: activeCrossDown" style="margin-left:2px;"><i class="fa fa-chevron-down blue"></i></a>
									      </div> 
								  </div> 
							 </div>
							  <div class="row" style="margin-top:5px"> 
							    <a type="button"  style="margin-left:5px" class="btn btn-success" data-toggle="modal" data-target="#"  data-bind="click: cjHighLineCross">拆解</a>
								<a type="button"  style="margin-left:5px" class="btn btn-success" data-toggle="modal" data-target="#" data-bind="click: hbHighLineCross">组合</a>
							  </div> 
						</div>
					</section>
				</div>
				<div class="pull-left" style="width: 45%;height:100%" >
				<!--分栏框开始-->    
				   <section class="panel panel-default" style="height:100%">
				        <div class="panel-heading">
				        	<span>
				              <i class="fa fa-table"></i>时刻点单
						   </span>
						</div> 
				          <div class="panel-body" style="height:90%"> 
					        	<table class="table table-bordered table-striped table-hover" id="plan_runline_table_trainLine">
							        <thead> 
							         <tr>
							          <th style="width:7%">站序</th>
					                  <th style="width:19%">站名</th> 
					                  <th style="width:10%">停时</th>
					                  <th style="width:20%">到达时间</th> 
					                  <th style="width:20%">出发时间</th>  
					                 </tr> 
							        </thead>
							        <tbody style="padding:0">
										 <tr style="padding:0">
										   <td colspan="9" style="padding:0">
												 <div id="allTimes_table" style="height: 360px; overflow-y:auto;"> 
													<table class="table table-bordered table-striped table-hover" > 
														 <tbody data-bind="foreach: activeTimes">
												           <tr>  
															<td style="width:7.5%" align="center" data-bind=" text: stnSort"></td>
															<td style="width:20%" data-bind="text: stnName, attr:{title: stnName}"></td>
															<td style="width:10%" align="center" data-bind="text: stepStr"></td> 
															<td style="width:20%" align="center" data-bind="text: sourceTime"></td>
															<td style="width:17%" align="center" data-bind="text: targetTime"></td>
												        	</tr>
												        </tbody>
													</table> 
											 	</div>
											</td>
										</tr>
									</tbody> 
						        </table>
						  </div> 
						</section>
				</div>
			</div>
			<div class="row" style="width:100%;" align="center">  
			   <div class="panel panel-default" style="padding-top:10px;padding-bottom:5px"> 
			      <a type="button"  style="margin-left:5px" class="btn btn-success" data-toggle="modal" data-target="#"  data-bind="click: submitHighLineCross">确定交路</a>
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
<script type="text/javascript" src="<%=basePath %>/assets/js/trainplan/highLine/highLine_cross.js"></script>  
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
