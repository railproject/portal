<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>审核次日客车运行线</title>
    <link type="text/css" href="${ctx}/assets/css/custom-bootstrap.css" rel="stylesheet"/>
    <link type="text/css" href="${ctx}/assets/css/font-awesome.min.css" rel="stylesheet"/>
    <link type="text/css" href="${ctx}/assets/css/minified/jquery-ui.min.css" rel="stylesheet"/>
    <link type="text/css" href="${ctx}/assets/css/style.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/html5.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/fuelUX.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.knob.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.gritter.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.sparkline.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/jquery.freezeheader.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/minified/jquery-ui.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/datepicker.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/purl.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/knockout.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/moment.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/trainplan/common.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/trainplan/common.security.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/trainplan/planline_check.js"></script>
</head>
<body class="Iframe-body">
<ol class="breadcrumb">
    <span><i class="fa fa-anchor"></i>当前位置：</span>
    <li><a href="#">审核次日客车运行线</a></li>
</ol>
<section class="mainContent">
    <div class="row">
        <div class="col-xs-12 col-md-12 col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-home"></i>审核
                    <span class="pull-right">存在 <a style="color: #ff0000" data-bind="text: paramModel().unknownRunLine"></a> 条冗余运行线 | <span data-bind="text: checkStatus"></span></span>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-xs-12 col-md-12 col-lg-12">
                            <form class="form-inline" role="form">
                                <div class="input-group">
                                    <input type="text" class="form-control" style="width: 100px; margin-right: 10px; border-radius: 4px" placeholder="请选择日期" id="date_selector"/>
                                   	<shiro:hasPermission name="JHPT.RJH.KDSP"><!-- 客运调度审批 -->
                                        <button type="button" class="btn btn-primary" style="width: 100px; margin-right: 10px; border-radius: 4px" data-bind="click: autoCheck, enable: canCheckLev1">校验</button>
                                        <button type="button" class="btn btn-primary" style="width: 100px; margin-right: 10px; border-radius: 4px" data-bind="click: checkLev1, enable: canCheckLev1">客调审核</button>
                                    </shiro:hasPermission>
                                    
                                    <shiro:hasPermission name="JHPT.RJH.ZBZRSP"><!-- 值班主任审批权限 -->
                                        <button type="button" class="btn btn-primary" style="width: 100px; margin-right: 10px; border-radius: 4px" data-bind="click: checkLev2, enable: canCheckLev2">值班主任审核</button>
									</shiro:hasPermission>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="row paddingtop5">
                        <div class="panel panel-default marginbottom0">
                            <div class="panel-body paddingbottom0">
                                <%--
                                <p>客运计划共709条，其中停运8条，热备52条，需下达709条。</p>
                                <p>已生成运行线709条，未生成0条，调整0条。</p>
                                <p>客调已审核709条，值班主任已审核12条。</p>
                                --%>
                                <div class="row">
                                    <div class="col-xs-4 col-md-4  col-sm-4">
                                        <div id="chart_01" style="height:253px; margin: 0 auto;"></div>
                                    </div>
                                    <div class="col-xs-4 col-md-4  col-sm-4 ">
                                        <div id="chart_02" style="height:253px; margin:0 auto;"></div>
                                    </div>
                                    <shiro:hasPermission name="JHPT.RJH.KDSP">
                                        <div class="col-xs-4 col-md-4  col-sm-4 ">
                                            <div id="chart_03" style="height:270px; margin:0 auto;"></div>
                                        </div>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="JHPT.RJH.ZBZRSP">
                                        <div class="col-xs-4 col-md-4  col-sm-4 ">
                                            <div id="chart_04" style="height:270px; margin:0 auto;"></div>
                                        </div>
                                    </shiro:hasPermission>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 col-md-12 col-lg-12 paddingleft0 paddingtop5 paddingright0">
                            <div class="table-responsive">
                                <table id="plan_table" class="table table-bordered table-striped table-hover tableradius" data-bind="with: tableModel">
                                    <thead>
                                    <tr>
                                        <shiro:hasPermission name="JHPT.RJH.KDSP"><!-- 客运调度审批 -->
                                            <th rowspan="2" class="text-center"><input class="checkbox-inline" type="checkbox" data-bind="checked: $root.allBtn, click: $root.selectAllLev1"/></th>
                                        </shiro:hasPermission>
                                       <shiro:hasPermission name="JHPT.RJH.ZBZRSP"><!-- 值班主任审批权限 -->
                                            <th rowspan="2" class="text-center"><input class="checkbox-inline" type="checkbox" data-bind="checked: $root.allBtn, click: $root.selectAllLev2"/></th>
                                        </shiro:hasPermission>
                                        <th rowspan="2" class="text-center">序号</th>
                                        <th rowspan="2" class="text-center">车次</th>
                                        <th rowspan="2" class="text-center">来源</th>
                                        <th rowspan="2" class="text-center">是否高线</th>
                                        <th rowspan="2" class="text-center">始发站</th>
                                        <th rowspan="2" class="text-center">始发时间</th>
                                        <th rowspan="2" class="text-center">终到站</th>
                                        <th rowspan="2" class="text-center">终到时间</th>
                                        <th rowspan="2" class="text-center">是否上图</th>
                                        <th colspan="2" class="text-center">校验项</th>

                                    </tr>
                                    <tr>
                                        <th class="text-center">列车</th>
                                        <th class="text-center">时刻</th>
                                        <%--<th class="text-center">经由</th>--%>
                                    </tr>
                                    </thead>
                                    <tbody data-bind="foreach: planList">
                                    <tr>
                                        <shiro:hasPermission name="JHPT.RJH.KDSP"><!-- 客运调度审批 -->
                                            <td class="text-center"><input class="checkbox-inline" name="plan" type="checkbox" data-bind="enable: needLev1, checked: isSelected"/></td>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="JHPT.RJH.ZBZRSP"><!-- 值班主任审批权限 -->
                                            <td class="text-center"><input class="checkbox-inline" name="plan" type="checkbox" data-bind="enable: needLev2, checked: isSelected"/></td>
                                        </shiro:hasPermission>
                                        <td class="text-center" data-bind="text: $index() + 1"></td>
                                        <td class="text-center"><a href="#" data-bind="text: name"></a></td>
                                        <td class="text-center" data-bind="text: sourceType"></td>
                                        <td class="text-center" data-bind="text: highLineFlag"></td>
                                        <td class="text-center" data-bind="text: startStn"></td>
                                        <td class="text-center" data-bind="text: startTime"></td>
                                        <td class="text-center" data-bind="text: endStn"></td>
                                        <td class="text-center" data-bind="text: endTime"></td>
                                        <td class="text-center" data-bind="text: dailyLineFlag"></td>
                                        <td class="text-center">
                                            <button type="button" class="btn" style="padding: 0px 5px 0px 5px" data-bind="css: isTrainInfoMatchClass, text: isTrainInfoMatchText, click: showInfoComparePanel"></button>
                                        </td>
                                        <td class="text-center">
                                            <button type="button" class="btn" style="padding: 0px 5px 0px 5px" data-bind="css: isTimeTableMatchClass, text: isTimeTableMatchText, click: showTimeTableComparePanel"></button>
                                        </td>
                                        <%--<td class="text-center">
                                            <button type="button" class="btn" style="padding: 0px 5px 0px 5px" data-bind="css: isRoutingMatchClass, text: isRoutingMatchText"></button>
                                        </td>--%>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>