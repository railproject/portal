/**
 * Created by star on 5/15/14.
 */
$(function() {
    $("#inputUsername").focus();
    $("#inputUsername").blur(function() {
        if($("#inputUsername").val().trim().length == 0) {
            return false;
        }
        $.ajax({
            url: "user/" + $("#inputUsername").val().trim() + "/account",
            method: "GET",
            contentType: "application/json; charset=UTF-8"
        }).done(function(list) {
            $("#inputAccount option").remove();
            for( var i = 0; i < list.length; i++) {
                $("#inputAccount").append("<option value='" + list[i].ACC_ID + "'>" + list[i].ACC_NAME + "</option>");
            }
        }).fail(function() {

        }).always(function() {
        })
    });

    $("#loginForm").submit(function() {
        if($("#inputUsername").val().trim().length == 0) {
            return false;
        }
        if(!$("#inputAccount").val()) {
            return false;
        }
        $("input[name='username']").val($("#inputUsername").val() + "@" + $("#inputAccount").val());
        $("input[name='password']").val($("#inputPassword").val());
    });

    $("#inputPassword").bind("keydown", function(e) {

        if (e.keyCode == 13) {
            e.preventDefault();

            $("#loginForm").submit();
        }
    });
    
    /**
     * 获取项目根路径
     * @returns /projectPath
     */
    function getRootPath(){
    	var _self = this;
        //获取主机地址之后的目录，如： /uimcardprj/share/meun.jsp
        var pathName = _self.location.pathname;
        
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
        return projectName;
    };
    
    
    
    var _self = this;
	var currentLocation = _self.location;	//当前页面的location
	var topLocation = top.location;			//当前页面所属的父页面的location
	
    if (topLocation != currentLocation) {
    	top.location = getRootPath()+"/login";
    }
    
});