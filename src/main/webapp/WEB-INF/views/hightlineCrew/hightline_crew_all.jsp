<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% 
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>高铁乘务计划查询</title>
<jsp:include page="/assets/commonpage/global.jsp" flush="true" />
</head>
<body class="Iframe_body">
<ol class="breadcrumb">
  <span><i class="fa fa-anchor"></i>当前位置：</span>
  <li><a href="<%=basePath %>/crew/page/sj">乘务计划->高铁乘务计划查询</a></li>
</ol>
<!--以上为必须要的--> 

<div class="row" style="padding-top:10px;padding-bottom:10px;">
  <form class="form-horizontal" role="form">
  	<div class="row">
  		<div class="pull-left">
	  		<div class="row">
	  			<div class="row" style="width: 100%; margin-top: 5px;">
			  		<div class="form-group" style="float:left;margin-left:20px;margin-bottom:0;">
			  			<label for="exampleInputEmail2" class="control-label pull-left">开始日期:&nbsp;</label>
					    <div class="pull-left">
					        <input id="crew_input_startDate" type="text" class="form-control" style="width:100px;" placeholder="">
						</div>
						<label for="exampleInputEmail2" class="control-label pull-left">&nbsp;&nbsp;截至日期:&nbsp;</label>
					    <div class="pull-left">
					        <input id="crew_input_endDate" type="text" class="form-control" style="width:100px;" placeholder="">
						</div>
						<label for="exampleInputEmail2" class="control-label pull-left">&nbsp;&nbsp;乘务类型:&nbsp;</label>
					    <div class="pull-left">
					    	<select class="form-control" style="width: 110px;display:inline-block;"
								 data-bind="options: [{'code': 'all', 'text': ''},{'code': '1', 'text': '车长'},{'code': '2', 'text': '司机'},{'code': '3', 'text': '机械师'}], value: searchModle().crewTypeOption, optionsText: 'text',optionsValue:'code'">
							</select>
						</div>
						<label for="exampleInputEmail3" class="control-label pull-left">&nbsp;&nbsp;路局:</label>
						<div class="pull-left" style="margin-left: 5px; ">
							<select style="width: 80px" class="form-control" data-bind="options:searchModle().bureauSelect, value: searchModle().bureauOption, optionsText: 'text'"></select>
						</div>
			  		</div>
			  	</div>
			  	<div class="row" style="width: 100%; margin-top: 5px;">
			  		<div class="form-group" style="float:left;margin-left:20px;margin-bottom:0;">
			  			
						<label for="exampleInputEmail2" class="control-label pull-left">部门:&nbsp;</label>
					    <div class="pull-left">
					        <select class="form-control" style="width:260px" data-bind="options:searchModle().orgSelect, value: searchModle().orgOption, optionsText: 'text'"></select>
						</div>
						<label for="exampleInputEmail2" class="control-label pull-left">&nbsp;&nbsp;车次:&nbsp;</label>
					    <div class="pull-left">
					        <input id="crew_input_trainNbr" type="text" class="form-control" style="width:100px;" data-bind="value: searchModle().trainNbr">
					    </div>
					    <label for="exampleInputEmail2" class="control-label pull-left">&nbsp;&nbsp;乘务员姓名:&nbsp;</label>
					    <div class="pull-left">
					        <input id="crew_input_crewPeopleName" type="text" class="form-control" style="width:110px;" data-bind="value: searchModle().crewPeopleName">
					    </div>
			  		</div>
			  	</div>
			  	
			  	
			  	
	  		</div>
  		</div>
  		<div style="float:left;margin-left:20px;margin-top: 25px;margin-bottom:0;vertical-align: middle">
  			<a type="button" href="#" class="btn btn-success" data-bind="click : queryList" style="float:left;margin-left:20px;margin-bottom:0;"><i class="fa fa-search"></i>查询</a>
    		<button type="button" class="btn btn-success" data-bind="click : exportExcel" style="float:left;margin-left:5px;margin-bottom:0;"><i class="fa fa-sign-out"></i>导出EXCEL</button>
  			
  		</div>
  	</div>
  	
  	
  	
  	
  </form>
</div>



<!--左右分开-->
<div class="row">
	  
  
  <!--乘务计划-->
  <div style="float:left; width:100%;">
    <!--分栏框开始-->
    <div class="panel panel-default">
      
      <div class="panel-body">
        <div class="table-responsive table-hover">
          <table class="table table-bordered table-striped table-hover">
            <thead>
              <tr>
                <th rowspan="2" class="text-center" style="vertical-align: middle;width:40px;">序号</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">乘务类型</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">日期</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">乘务交路</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">乘务组编号</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">经由铁路线</th>
                <th colspan="3" class="text-center" style="vertical-align: middle">乘务员1</th>
                <th colspan="3" class="text-center" style="vertical-align: middle">乘务员2</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">路局</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">部门</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">备注</th>
              </tr>
              <tr>
                <th class="text-center">姓名</th>
                <th class="text-center">电话</th>
                <th class="text-center">政治面貌</th>
                <th class="text-center">姓名</th>
                <th class="text-center">电话</th>
                <th class="text-center">政治面貌</th>
              </tr>
            </thead>
            <tbody data-bind="foreach: hightLineCrewRows.rows">
              <tr>
                <td data-bind=" text: ($index() + 1)"></td>
                <td data-bind=" text: crewTypeName"></td>
                <td data-bind=" text: crewDate"></td>
                <td data-bind=" text: crewCross, attr:{title: crewCross}"></td>
                <td data-bind=" text: crewGroup, attr:{title: crewGroup}"></td>
                <td data-bind=" text: throughLine, attr:{title: throughLine}"></td>
                <td data-bind=" text: name1, attr:{title: name1}"></td>
                <td data-bind=" text: tel1, attr:{title: tel1}"></td>
                <td data-bind=" text: identity1, attr:{title: identity1}"></td>
                <td data-bind=" text: name2, attr:{title: name2}"></td>
                <td data-bind=" text: tel2, attr:{title: tel2}"></td>
                <td data-bind=" text: identity2, attr:{title: identity2}"></td>
                <td data-bind=" text: crewBureau"></td>
                <td data-bind=" text: recordPeopleOrg, attr:{title: recordPeopleOrg}"></td>
                <td data-bind=" text: note, attr:{title: note}"></td>
              </tr>
            </tbody>
          </table>
			<div data-bind="template: { name: 'tablefooter-short-template', foreach: hightLineCrewRows }" style="margin-bottom: 5px"></div>
        </div>
      </div>
      <!--panel-body--> 
      
    </div>
    
    <!--分栏框结束--> 
  </div>
  <!--乘务计划end--> 




  
  
  
  
  
  
  
  

  
</div>
<!--左右分开--> 



















<script type="text/javascript" src="<%=basePath %>/assets/js/ajaxfileupload.js"></script> 
<script type="text/javascript"  src="<%=basePath %>/assets/js/trainplan/knockout.pagemodle.js"></script> 
<script type="text/javascript"  src="<%=basePath %>/assets/js/trainplan/hightlineCrew/hightline.crew.all.js"></script> 
 <script type="text/html" id="tablefooter-short-template"> 
  <table style="width:100%;height:20px;">
    <tr style="width:100%;height:20px;">
     <td style="width:60%;height:20px;">
		<div style="pull-left;">
  			<span class="pull-left">共<span data-bind="html: totalCount()"></span>条  当前<span data-bind="html: totalCount() > 0 ? (currentIndex() + 1) : '0'"></span>到<span data-bind="html: endIndex()"></span>条   共<span data-bind="text: pageCount()"></span>页</span> 		
		</div>						 
  	 </td>
     <td  align="right" style="width:40%;padding:0px;pading-bottom:-14">   
		<div style="pull-right;">
			<span data-bind="attr:{class:currentPage() == 0 ? 'disabed': ''}"><a style="cursor:pointer;background-color: #ffffff;border: 1px solid #dddddd;margin-right:-5px;padding:0px 5px;" data-bind="text:'<<', click: currentPage() == 0 ? null: loadPre"></a>
	    	<input type="text"  style="padding-left:8px;margin-bottom:0px;padding-bottom:0;width:30px;height: 19px;background-color: #ffffff;border: 1px solid #dddddd;" data-bind="value: parseInt(currentPage())+1, event:{keyup: pageNbrChange}"/>
			<a style="cursor:pointer;background-color: #ffffff;border: 1px solid #dddddd;margin-left:-5px;padding:0px 5px;" data-bind="text:'>>', click: (currentPage() == pageCount()-1 || totalCount() == 0) ? null: loadNext"  style="padding:0px 5px;"></a>
       		</ul> 
	 	</div>
     </td >
  </tr>
</table> 
</script>
</body>
</html>