<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <jsp:include page="/assets/commonpage/global.jsp" flush="true" />
    <script type="text/javascript" src="${ctx}/assets/js/jquery.freezeheader.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/purl.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/trainplan/timetable.js"></script>
</head>
<body>
<div class="container padding0 margin0" style="width: 100%;">
<div class="row padding0 margin0">
    <div class="col-md-12 col-xs-12 col-lg-12 padding0 margin0">
        <div class="panel-body padding0 margin0">
            <div class="table-responsive padding0 margin0">
                <table id="planTable" class="table table-bordered table-striped table-hover">
                    <thead>
                    <tr>
                        <th class="text-center">来源</th>
                        <th class="text-center">站名</th>
                        <th class="text-center">始发站</th>
                        <th class="text-center">始发局</th>
                        <th class="text-center">始发时间</th>
                        <th class="text-center">终到站</th>
                        <th class="text-center">终到局</th>
                        <th class="text-center">终到时间</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="text-center">客运计划</td>
                            <td class="text-center">${plan.trainName}</td>
                            <td class="text-center">${plan.startStn}</td>
                            <td class="text-center">${plan.startBureau}</td>
                            <td class="text-center">${plan.startTime}</td>
                            <td class="text-center">${plan.endStn}</td>
                            <td class="text-center">${plan.endBureau}</td>
                            <td class="text-center">${plan.endTime}</td>
                        </tr>
                        <tr>
                            <td class="text-center">日计划</td>
                            <td class="text-center">${line.trainName}</td>
                            <td class="text-center">${line.startStn}</td>
                            <td class="text-center">${line.startBureau}</td>
                            <td class="text-center">${line.startTime}</td>
                            <td class="text-center">${line.endStn}</td>
                            <td class="text-center">${line.endBureau}</td>
                            <td class="text-center">${line.endTime}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>