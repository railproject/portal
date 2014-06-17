<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/css/custom-bootstrap.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/css/login.css"/>
    <script type="application/javascript" src="${ctx}/assets/js/jquery.js"></script>
    <script type="application/javascript" src="${ctx}/assets/js/html5.js"></script>
    <script type="application/javascript" src="${ctx}/assets/js/bootstrap.min.js"></script>
    <script type="application/javascript" src="${ctx}/assets/js/respond.min.js"></script>
    <script type="application/javascript" type="application/javascript" src="${ctx}/assets/js/trainplan/login.js"></script>
    <title>铁路运输调度计划平台</title>
</head>
<body>
<div class="login">
    <div class="login_backgroud">
        <div class="login_overlay">
            <div class="login-logo"></div>
            <div class="login_input">
                <div class="form-group paddingtop30">
                    <label for="inputUsername" class="col-sm-2 control-label">账号：</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputUsername" placeholder="用户名" style="width: 240px">
                    </div>
                </div>
                <!--form-group-->
                <div class="form-group paddingtop30">
                    <label for="inputAccount" class="col-sm-2 control-label">岗位：</label>
                    <div class="col-sm-10">
                        <select id="inputAccount" class="form-control" style="width: 240px"></select>
                    </div>
                </div>
                <!--form-group-->
                <div class="form-group paddingtop30">
                    <label for="inputPassword" class="col-sm-2 control-label">密码：</label>
                    <div class="col-sm-10">
                        <div class="input-group">
                            <input type="password" class="form-control" id="inputPassword" placeholder="密码" style="border-radius: 4px; width: 240px">
                        </div>
                    </div>
                </div>

                <div class="form-group paddingtop30">
                    <div class="col-sm-6">
                        <form class="form-horizontal" role="form" method="post" action="${ctx}/login" id="loginForm" name="loginForm">
                            <div class="btn-group">
                                <input type="hidden" name="username"/>
                                <input type="hidden" name="password"/>
                                <button class="btn btn-warning" type="submit" id="login" style="padding:6px 13px; border-radius: 4px; width: 100px;margin-left: 170px;">登&nbsp;录</button>
                            </div>
                        </form>
                    </div>
                </div>

                <!--输入框结束-->
                <!--错误提示开始-->
                <!--      <div class="alert alert-danger" style="margin-top:15px;width:200px;height:26px;line-height:26px;font-size:10px;padding-top:0px; ">您输入的用户名或密码有误！ </div>-->
                <!--错误提示结束-->
            </div>
            <div class="aquila" style="margin-right: -500px;"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $(".login_backgroud").css("min-height", $(window).height() + "px");
        $(window).resize(function () {
            $(".login_backgroud").css("min-height", $(window).height() + "px");
        });
    });
</script>
</body>
</html>
