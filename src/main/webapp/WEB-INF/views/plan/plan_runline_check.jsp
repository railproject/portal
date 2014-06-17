<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% 
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>核查编制图定开行</title>
<!-- Bootstrap core CSS -->
<link href="<%=basePath %>/assets/css/cross/custom-bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>/assets/css/cross/cross.css" rel="stylesheet">  
<!--font-awesome-->
<link href="<%=basePath %>/assets/css/datepicker.css" rel="stylesheet">
<link  type="text/css" rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.min.css"/>
<link  type="text/css" rel="stylesheet" href="<%=basePath %>/assets/css/datepicker.css">
<!-- Custom styles for this template -->
<link href="<%=basePath %>/assets/css/style.css" rel="stylesheet">
<script src="<%=basePath %>/assets/js/jquery.js"></script>
<script src="<%=basePath %>/assets/js/html5.js"></script>
<script src="<%=basePath %>/assets/js/bootstrap.min.js"></script> 
<script src="<%=basePath %>/assets/js/respond.min.js"></script>
<script src="<%=basePath %>/assets/js/jquery.dataTables.js"></script>
<script src="<%=basePath %>/assets/js/jquery.gritter.min.js"></script>
<script src="<%=basePath %>/assets/js/datepicker.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/common.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/knockout.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/jquery.freezeheader.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/js/trainplan/plan/plan_runline_check.js"></script>
<script src="<%=basePath %>/assets/js/trainplan/knockout.pagemodle.js"></script> 
<script type="text/javascript">
var basePath = "<%=basePath %>";
</script>
<script type="text/html" id="tablefooter-short-template">  
 <table style="width:100%;height:20px;">
    <tr style="width:100%;height:20px;">
     <td style="width:60%;height:20px;">
  		<span class="pull-right" style="margin-right:10px">共<span data-bind="html: totalCount()"></span>条  当前<span data-bind="html: totalCount() > 0 ? (currentIndex() + 1) : '0'"></span>到<span data-bind="html: endIndex()"></span>条   共<span data-bind="text: pageCount()"></span>页</span> 								 
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
</head>
<body class="Iframe_body">
<input id="basePath_hidden" type="hidden" value="<%=basePath %>">
<!--以上为必须要的-->

<ol class="breadcrumb">
  <span><i class="fa fa-anchor"></i>当前位置：</span>
  <li><a href="#">基本图查询</a></li>
</ol> 
<div class="pull-left" style="width:63%;"> 
    <!--分栏框开始-->
    <div id="plan_view_div_palnDayDetail" class="panel panel-default">
       <div class="row" style="margin:15px 0 10px 0;"> 
		      <form class="form-horizontal" role="form">
		     
		        <div class="pull-left">
		        		<label for="exampleInputEmail3" class="control-label pull-left" style="margin-left:10px">
										方案:&nbsp;</label> 
						<div class="pull-left">
							<select style="width:230px" id="input_cross_chart_id"
								class="form-control" data-bind="options:searchModle().charts, value: searchModle().chart, optionsText: 'name', optionsCaption:''">
							</select>
						</div>  
				  <label for="exampleInputEmail3" class="control-label pull-left" style="margin-left:15px"> 
										始发局:&nbsp;</label> 
						<div class="pull-left">
							<select style="width: 40px" id="input_cross_chart_id"
								class="form-control" data-bind="options:searchModle().startBureaus(), value: searchModle().startBureau,  optionsValue: 'shortName', optionsText: 'shortName', optionsCaption: ''">
							</select>
						</div>   
				     <label for="exampleInputEmail3" class="control-label pull-left" style="margin-left:15px">
										终到局:&nbsp;</label> 
						<div class="pull-left">
							<select style="width:40px" id="input_cross_chart_id"
								class="form-control" data-bind="options:searchModle().endBureaus(), value: searchModle().endBureau, optionsValue: 'shortName', optionsText: 'shortName', optionsCaption: ''">
							</select> 
						</div>
				
		          <div class="form-group" style="float:left;margin-left:20px;margin-bottom:0;">
		          	<label for="exampleInputEmail3" class="control-label pull-left"> 车次：&nbsp;</label>
		            <div class="pull-left">
                    	<input type="text" class="form-control" style="width:80px;" data-bind="value: searchModle().trainNbr, event:{keyup: trainNbrChange}" id="plan_construction_input_trainNbr">
				    </div> 
		          </div>
		            <div class="pull-left">
					<input type="checkbox" class="pull-left" class="form-control"
						value="1" data-bind="checked: searchModle().fuzzyFlag(), event:{change: fuzzyChange}"
						style="width: 20px; margin-left: 25px; margin-top: 5px"
						class="form-control">
				</div>
				<label for="exampleInputEmail5" class="control-label pull-left">
					模糊</label>    
		        </div> 
				<a type="button"  style="margin-left:30px" class="btn btn-success" data-toggle="modal" data-target="#" id="plan_construction_createRunLine"  data-bind="click: loadTrains">查询</a>
				 
		        <!--col-md-3 col-sm-4 col-xs-4-->
		      </form> 
		    </div> 
		     <div class="row" style="margin:15px 0 10px 0;"> 
		     	<div class="pull-right" data-bind="template: { name: 'tablefooter-short-template', foreach: trainRows }" style="margin-right:10px; width:500px"></div>
		     </div>
		     <div class="row" style="margin:15px 10px 10px 10px;height:800px; overflow-y:auto"> 
			     <div class="table-responsive" >
			          <table class="table table-bordered table-striped table-hover" id="plan_runline_table_trainInfo"> 
					        <thead>
					        <tr>
					          <th style="width:5%">序号</th>
			                  <th style="width:15%">车次</th>
			                  <th style="width:5%">始发局</th>
			                  <th style="width:15%">始发站</th>
			                  <th style="width:10%">始发时间</th>
			                  <th style="width:5%">终到局</th>
			                  <th style="width:15%">终到站</th>
			                  <th style="width:10%">终到时间</th>  
			                  <th style="width:10%">经由局</th>
			                  <th style="width:8%">运行天数</th>
			                 </tr>
					        </thead> 
					        <tbody data-bind="foreach: trainRows.rows">
					             <tr data-bind="click: $parent.showTrainTimes, style:{color: $parent.currentTrain() != null && $parent.currentTrain().name == name ? 'blue':''}">
					               <!-- data-bind="event:{change: $parent.checkboxSelect}, checked: selected" -->
								    <td align="center" data-bind="text: $parent.trainRows.currentIndex()+$index()+1"></td>
									<td data-bind=" text: name"></td>
									<td  align="center" data-bind="text: startBureau"></td>
									<td data-bind="text: startStn , attr:{title: startStn}"></td>
									<td align="center" data-bind="text: sourceTime"></td>
									<td align="center" data-bind="text: endBureau"></td>
									<td data-bind="text: endStn , attr:{title: endStn}"></td>
									<td align="center" data-bind="text: targetTime"></td>
									<td data-bind="text: routingBureau, attr:{title: routingBureau}"></td>
									<td align="center" data-bind="text: runDays"></td>
									 
								 </tr> 
					        </tbody>
					      </table> 
			        </div> 
		       </div>
		  </div> 
      </div> 
  <div class="pull-right" style="width:36%;"> 
    <!--分栏框开始-->
    <div id="plan_view_div_palnDayDetail" class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title" style="float: left;"> <i class="fa fa fa-folder-open"></i>详情时刻表  <span style="margin-left:5px" data-bind="text:currentTrain() == null ? '' : '车次:' + currentTrain().name"></span><label id="plan_view_div_palnDayDetail_title"></label></h3>
      </div>
      <!--panle-heading-->
      <div class="panel-body" style="padding:10px;">
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
                  <th style="width:15%">股道</th> 
                 </tr>
		        </thead>
		        <tbody data-bind="foreach: trainLines">
		           <tr>  
					<td align="center" data-bind=" text: $index() + 1"></td>
					<td data-bind="text: stnName, attr:{title: stnName}"></td>
					<td align="center" data-bind="text: bureauShortName"></td>
					<td align="center" data-bind="text: sourceTime"></td>
					<td align="center" data-bind="text: targetTime"></td>
					<td align="center" data-bind="text: stepStr"></td>
					<td align="center" data-bind="text: runDays"></td>
					<td align="center" data-bind="text: trackName"></td>
		        	</tr>
		        </tbody>
		      </table>
        </div>  
        </div>  
      </div>
      <!--panel-body--> 
    </div>
    <!--分栏框结束--> 
  </div>
  <!--分栏框结束--> 
</div> 
</body>
</html>