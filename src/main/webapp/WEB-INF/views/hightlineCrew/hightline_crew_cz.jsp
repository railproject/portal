<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% 
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>车长乘务计划上报</title>
<jsp:include page="/assets/commonpage/global.jsp" flush="true" />
</head>
<body class="Iframe_body">
<ol class="breadcrumb">
  <span><i class="fa fa-anchor"></i>当前位置：</span>
  <li><a href="<%=basePath %>/crew/page/sj">乘务计划->车长乘务计划上报</a></li>
</ol>
<!--以上为必须要的--> 

<div class="row" style="padding-top:10px;padding-bottom:10px;">
  <form class="form-horizontal" role="form">
    <div class="form-group" style="float:left;margin-left:20px;margin-bottom:0;">
      <label for="exampleInputEmail2" class="control-label pull-left">计划日期:&nbsp;</label>
      <div class="pull-left">
        <input id="crew_input_rundate" type="text" class="form-control" style="width:100px;" placeholder="" data-bind="value: searchModle().runDate">
      </div>
    </div>
    <div class="form-group" style="float:left;margin-left:30px;margin-bottom:0;">
      <label for="exampleInputEmail2" class="control-label pull-left"> 车次:&nbsp;</label>
      <div class="pull-left">
        <input id="crew_input_trainNbr" type="text" class="form-control" style="width:100px;" data-bind="value: searchModle().trainNbr">
      </div>
    </div>
    <a type="button" href="#" class="btn btn-success" data-bind="click : queryList" style="float:left;margin-left:20px;margin-bottom:0;"><i class="fa fa-search"></i>查询</a>
  	<button type="button" class="btn btn-success"  style="float:left;margin-left:5px;margin-bottom:0;" data-bind="click: checkCrew, enable: checkAndSendBtnEnable"><i class="fa fa-retweet"></i>校验</button>
  	<button type="button" class="btn btn-success"  style="float:left;margin-left:5px;margin-bottom:0;" data-bind="click: sendCrew, enable: checkAndSendBtnEnable"><i class="fa fa-external-link"></i>提交</button>
  </form>
</div>



<!--左右分开-->
<div class="row">
	  
  
  <!--乘务计划-->
  <div style="margin-right:-590px; float:left; width:100%;">
    <!--分栏框开始-->
    <div class="panel panel-default" style="margin-right:590px;">
      <div class="panel-heading" >
        <h3 class="panel-title" > <i class="fa fa-user-md"></i>车长乘务计划</h3>
        <!--        <div class="col-md-8 col-sm-6 col-xs-4  pull-right" style=" width: 10%; text-align:right;">  <a  class="panel-title" href="application-Status.html" >返回</a></div>--> 
      </div>
      <!--panle-heading-->
      
      <div class="panel-body">
        <div class="row" style="margin-bottom:10px;">
          <button type="button" class="btn btn-success" data-toggle="modal" data-bind="click : onAddOpen" data-target="#saveHightLineCrewModal"><i class="fa fa-plus"></i>添加</button>
          <button type="button" class="btn btn-success" data-toggle="modal" data-bind="click : onEditOpen" data-target="#saveHightLineCrewModal"><i class="fa fa-pencil-square-o"></i> 修改</button>
          <button type="button" class="btn btn-success" data-bind="click : deleteHightLineCrew"><i class="fa fa-minus-square"></i>删除</button>
          <button type="button" class="btn btn-success" data-bind="" data-toggle="modal" data-target="#uploadCrewExcelModal"><i class="fa fa-sign-in"></i>导入EXCEL</button>
          <button type="button" class="btn btn-success" data-bind="click : exportExcel"><i class="fa fa-sign-out"></i>导出EXCEL</button>
        </div>
        <div class="table-responsive table-hover">
          <table class="table table-bordered table-striped table-hover">
            <thead>
              <tr>
                <th rowspan="2" style="width:5%;"></th>
                <th rowspan="2" class="text-center" style="vertical-align: middle;width:40px;">序号</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">乘务交路</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">车队组号</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle">经由铁路线</th>
                <th colspan="3" class="text-center" style="vertical-align: middle">车长1</th>
                <th colspan="3" class="text-center" style="vertical-align: middle">车长2</th>
                <th rowspan="2" class="text-center" style="vertical-align: middle;width:40px">提交<br>状态</th>
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
              	<td><input name="crew_checkbox" type="checkbox" data-bind="value : crewHighlineId"></td>
                <td data-bind=" text: ($index() + 1)"></td>
                <td data-bind=" text: crewCross, attr:{title: crewCross}"></td>
                <td data-bind=" text: crewGroup, attr:{title: crewGroup}"></td>
                <td data-bind=" text: throughLine, attr:{title: throughLine}"></td>
                <td data-bind=" text: name1, attr:{title: name1}"></td>
                <td data-bind=" text: tel1, attr:{title: tel1}"></td>
                <td data-bind=" text: identity1, attr:{title: identity1}"></td>
                <td data-bind=" text: name2, attr:{title: name2}"></td>
                <td data-bind=" text: tel2, attr:{title: tel2}"></td>
                <td data-bind=" text: identity2, attr:{title: identity2}"></td>
                <td data-bind="html : submitTypeStr"></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!--panel-body--> 
      
    </div>
    
    <!--分栏框结束--> 
  </div>
  <!--乘务计划end--> 




  <!--列车开行计划-->
  <div class="pull-right" style="width:580px;"> 
    <!--分栏框开始-->
    <div class="panel panel-default">
      <div class="panel-heading" >
        <h3 class="panel-title" > <i class="fa fa-user-md"></i>列车开行计划</h3>
      </div>
      <!--panle-heading-->
      <div class="panel-body" style="padding:5px 5px;">
        <div class="table-responsive table-hover" style="height: 700px; overflow-y:auto;">
          <table border="0" class="table table-bordered table-striped table-hover">
            <thead>
              <tr>
                <th style="width:40px;">序号</th>
                <th style="width:120px;">车次</th>
                <th style="width:120px;">始发站</th>
                <th style="width:120px;">始发时间</th>
                <th style="width:120px;">终到站</th>
                <th style="width:120px;">终到时间</th>
              </tr>
            </thead>
			<tbody data-bind="foreach: planTrainRows.rows">
			  <tr data-bind="attr:{class : isMatch()==1? 'danger':''}">
                <td style="width:40px;" data-bind=" text: ($index() + 1)"></td>
                <td style="width:120px;" data-bind=" text: trainNbr, attr:{title: trainNbr}"></td>
                <td style="width:120px;" data-bind=" text: startStn, attr:{title: startStn}"></td>
                <td style="width:120px;" data-bind=" text: startTimeStr, attr:{title: startTimeStr}"></td>
                <td style="width:120px;" data-bind=" text: endStn, attr:{title: endStn}"></td>
                <td style="width:120px;" data-bind=" text: endTimeStr, attr:{title: endTimeStr}"></td>
              </tr>
            </tbody> 
          </table>
        </div>
      </div>
      <!--panel-body--> 
      
    </div>
    
    <!--分栏框结束--> 
  </div>
  <!--列车开行计划 end--> 
  
  
  
  
  
  
  
  

  
</div>
<!--左右分开--> 













<!--新增/修改弹出框-->
<div class="modal fade" id="saveHightLineCrewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" data-bind="text : hightLineCrewModelTitle"></h4>
      </div>
      
      <!--panel-heading-->
      <div class="panel-body row">
        <form id="hightLineCrewForm" class="bs-example form-horizontal" style="margin-top:10px;" data-bind="with : hightLineCrewModel">
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">乘务交路：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <input id="add_crewCross" type="text" class="form-control" data-bind="value : crewCross">
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">车队组号：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <input id="add_crewGroup" type="text" class="form-control" data-bind="value : crewGroup">
               </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">经由铁路线：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <input id="add_throughLine" type="text" class="form-control" data-bind="value : throughLine">
               </div>
          </div>
          
          
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">车长1姓名：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <input id="add_name1" type="text" class="form-control" data-bind="value : name1">
               </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">电话：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <input id="add_tel1" type="text" class="form-control" data-bind="value : tel1">
               </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">政治面貌：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <input id="add_identity1" type="text" class="form-control" data-bind="value : identity1">
               </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">车长2姓名：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <input id="add_name2" type="text" class="form-control" data-bind="value : name2">
               </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">电话：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <input id="add_tel2" type="text" class="form-control" data-bind="value : tel2">
               </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">政治面貌：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <input id="add_identity2" type="text" class="form-control" data-bind="value : identity2">
               </div>
          </div>
          <div class="form-group">
            <label class="col-md-3 col-sm-4 col-xs-4 control-label text-right">备注：</label>
            <div class="col-md-7 col-sm-7 col-xs-6">
              <textarea id="add_note" class="form-control" rows="4" data-bind="value : note"></textarea>
            </div>
          </div>
        </form>
        <!--        <p class="pull-right" style="margin:0;">说明：当您申请后需要等待管理员审批才能使用。</p>
--> </div>
      <!--panel-body-->
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-bind="click : saveHightLineCrew" data-dismiss="modal">确定</button>
        <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
      </div>
    </div>
    <!-- /.modal-content --> 
  </div>
  <!-- /.modal-dialog --> 
</div>
<!--新增/修改弹出框 end-->






<!--导入弹窗-->
<div class="modal fade" id="uploadCrewExcelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">导入车长乘务计划EXCEL</h4>
      </div>
      
      <!--panel-heading-->
      <div class="panel-body row">
      	<img id="loading" src="<%=basePath %>/assets/images/loading.gif" style="display:none;">
        <form  id="file_upload_crew" name="file_upload_crew" action="crew/highline/importExcel" method="post" enctype="multipart/form-data" class="bs-example form-horizontal" style="margin-top:10px;">
          <div class="form-group">
            <div class="col-md-12 col-sm-12 col-xs-12">
              <input id="crewExcelFile" type="file" size="45" name="crewExcelFile"/>
            </div>
          </div>
        </form>
        <!--        <p class="pull-right" style="margin:0;">说明：当您申请后需要等待管理员审批才能使用。</p>
--> </div>
      <!--panel-body-->
      <div class="modal-footer">
        <button id="btn_fileToUpload" type="button" class="btn btn-primary" data-bind="click : uploadExcel" data-dismiss="modal">导入</button>
        <button id="btn_fileToUpload_cancel" type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
      </div>
    </div>
    <!-- /.modal-content --> 
  </div>
  <!-- /.modal-dialog --> 
</div>
<!-- 导入excel弹窗 end -->






<script type="text/javascript" src="<%=basePath %>/assets/js/ajaxfileupload.js"></script> 
<script type="text/javascript"  src="<%=basePath %>/assets/js/trainplan/knockout.pagemodle.js"></script> 
<script type="text/javascript"  src="<%=basePath %>/assets/js/trainplan/hightlineCrew/hightline.crew.cz.js"></script> 
</body>
</html>