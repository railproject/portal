
function GetDateDiff(startTime, endTime)
{ 
	var result = "";
	
	var date3=endTime.getTime()-startTime.getTime(); //时间差的毫秒数 
	
	//计算出相差天数
	var days=Math.floor(date3/(24*3600*1000));
	
	result += days > 0 ? days + "天" : "";  
	//计算出小时数
	var leave1=date3%(24*3600*1000);     //计算天数后剩余的毫秒数
	var hours=Math.floor(leave1/(3600*1000));
	
	result += hours > 0 ? hours + "小时" : ""; 
	
	//计算相差分钟数
	var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
	var minutes=Math.floor(leave2/(60*1000));
	
	result += minutes > 0 ? minutes + "分" : "";
	//计算相差秒数
	var leave3=leave2%(60*1000);          //计算分钟数后剩余的毫秒数
	var seconds=Math.round(leave3/1000);
	
	result += seconds > 0 ? seconds + "秒" : "";  
	 
	return result == "" ? "不停靠" : result; 
} 
 
function filterValue(value){
	return value == null || value == "null" ? "--" : value;
}

function TrainInfo(checkbox){
	var _self = this;
	_self.checkbox = checkbox;
	_self.requestId = null;
	_self.routLines = null;
	_self.setLines = function(arr){
		_self.routLines = arr;
	}; 
	_self.setRequestId = function(requestId){
		_self.requestId = requestId;
	};
	
}

function Train(tr, lines){
	var _self = this; 
	_self.lines = lines;
	_self.tr = tr; 
	
}
 
var PlanConstructionPage = function(){
	

	var _self = this; 
	
	_self.selectTrains = [];
	
	_self.trainLineMap = [];
	
	_self.results = {};
	
	
	_self.pageselectCallback = function(page_index, jq){
		_self.queryConstructionDetail(page_index+1);
	    return false;
	};

	/** 
	 * Callback function for the AJAX content loader.
	 */
	_self.initPagination = function(num_entries, page_index) { 
	    // Create pagination element
	    $("#Pagination").pagination(num_entries, {
	        num_edge_entries: 5,
	        num_display_entries: 6,
	        prev_text:"<上一页",
	        next_text:"下一页>",
	        current_page: page_index-1, 
	        callback: _self.pageselectCallback,
	        items_per_page:15
	    });
	 };
	
	_self.showTable = function(skarr){
		
		_plan_review_table_trainLine.find("tr:gt(0)").remove();  
		
		$.each(skarr, function(i, trainObj){
			var tr = $("<tr/>");
			tr.append("<td>"+(trainObj.index+1)+"</td>");
			tr.append("<td>"+filterValue(trainObj.name)+"</td>");
			tr.append("<td>"+filterValue(trainObj.bureauShortName)+"</td>"); 
			
			if(trainObj.sourceTimeDto2 != null){
				var a = trainObj.sourceTimeDto2.substring(5).replace(/-/, "");
				tr.append("<td>"+a+"</td>");
			}else{
				tr.append("<td>--</td>");
			}
			
			if(trainObj.targetTimeDto2 != null){
				var a = trainObj.targetTimeDto2.substring(5).replace(/-/, "");
				tr.append("<td>"+a+"</td>");
			}else{
				tr.append("<td>--</td>");
			} 

			if(trainObj.sourceTimeDto2 && trainObj.targetTimeDto2){
			
				var sourceTimeDto1 =  new Date(trainObj.sourceTimeDto2); 
				
				var targetTimeDto1 = new Date(trainObj.targetTimeDto2);  
				 
				tr.append("<td>"+GetDateDiff(sourceTimeDto1, targetTimeDto1)+"</td>");  
			}else{
				tr.append("<td>出发</td>"); 
			}
			
			
			tr.append("<td>"+filterValue(trainObj.trackName)+"</td>");
			
			 
			_plan_review_table_trainLine.append(tr);   
		});
	}; 
	_self.initLjSelectValue = function() {
		_plan_runline_batch_select_lj.empty();//清除路局下拉控件值
		$.ajax({
			url : basePath+"/plan/getFullStationInfo",
			cache : false,
			type : "GET",
			dataType : "json",
			contentType : "application/json",
			success : function(result) {
				if (result != null && result != "undefind" && result.code == "0") {
					if (result.data !=null) {
						$.each(result.data,function(n,ljObj){
							if (n==0) {
								_plan_runline_batch_select_lj.append('<option selected="selected" value="'+ljObj.ljjc+'">'+ljObj.ljjc+'</option>');
							} else {
								_plan_runline_batch_select_lj.append('<option value="'+ljObj.ljjc+'">'+ljObj.ljjc+'</option>');
							}
						}); 
					}
				} else {
					showErrorDialog("接口调用返回错误，code="+result.code+"   message:"+result.message);
				}

			},
			error : function() {
				showErrorDialog("接口调用失败");
//				showErrorDialog("请求发送失败");
//				showErrorDialog(portal.common.dialogmsg.REQUESTFAIL);
			}
		});
	};
	 
	_self.logout = function() {
		stompClient.disconnect();
		window.location.href = "../logout.html";
	};

	//获取当期系统日期
	this.currdate =function(){
		var d = new Date();
		var year = d.getFullYear();    //获取完整的年份(4位,1970-????)
		var month = d.getMonth()+1;       //获取当前月份(0-11,0代表1月)
		var days = d.getDate(); 
		return year+"-"+month+"-"+days;
	};
	
	
	this.initPage = function() {
		//1.初始化日期控件值
		_plan_construction_selectdate.datepicker();
		_plan_construction_selectdate.val(this.currdate());//获取当期系统日期 
		_self.initLjSelectValue();
		_self.initPagination(0);
	  
	};
	
	_self.createConstructionPlain = function(){ 
		
		if(_plan_construction_selectdate.val() == null || _plan_construction_selectdate.val() == ""){
			showErrorDialog("日期不能为空");
			_plan_construction_selectdate.focus();
			return;
		}
		if(_self.selectTrains.length <= 0){
			showErrorDialog("请选中一行记录");
			return
		} 
		for(var i = 0; i < _self.selectTrains.length; i++){
			var train = _self.selectTrains[i];
			 $(train).parent().parent().find("td:eq(7)").html("<span color=\'red\'>正在生成运行线</span>"); 
			 var trainInfo = null;
			 for(var z = 0; z < _self.trainLineMap.length; z++){
				 if(_self.trainLineMap[z].checkbox == train){
					 trainInfo = _self.trainLineMap[z];
					 break;
				 }
			 } 
			 if(trainInfo == null){
				 trainInfo = new TrainInfo(train);
			 }
			 _self.trainLineMap.push(trainInfo);  
			$.ajax({
				url : basePath+"/construction/createConstructionPlain",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({
					runDate : _plan_construction_selectdate.val(),//开行日期
					trainNbr : train.value //车次号 
				}),
				success : function(result) {    
					if (result != null && result != "undefind" && result.code == "0") {  
						var trainNbr = $.parseJSON(this.data).trainNbr;
						var result1 = $.parseJSON(result.data.result); 
						 
						for(var j = 0; j < _self.trainLineMap.length; j++){
							 
							if(_self.trainLineMap[j].checkbox.value == trainNbr){
								
								_self.trainLineMap[j].setRequestId(result1.requestId);
							}
						}  
					} else {
						showErrorDialog("接口调用返回错误，code="+result.code+"   message:"+result.message);
					} 
				},
				error : function() {
					showErrorDialog("接口调用失败");
				}
			});
		}
	
		
	};
	 
	_self.queryConstructionDetail = function(currentPage) {
		_plan_construction_btnQuery.attr("disabled", "disabled");
		_plan_review_table_trainInfo.find("tr:gt(0)").remove();
		_plan_review_table_trainLine.find("tr:gt(0)").remove();  
		commonJsScreenLock();
		
		//_plan_construction_table.find("tr:gt(0)").remove();//清除车次详细信息列表所有数据
		if(_plan_construction_selectdate.val() == null || _plan_construction_selectdate.val() == ""){
			showErrorDialog("日期不能为空");
			_plan_construction_selectdate.focus();
			_myProgressModal.hide();
			_plan_construction_btnQuery.removeAttr("disabled");
			return;
		}
		
		if(_plan_runline_batch_select_lj.val() == null || _plan_runline_batch_select_lj.val() == ""){
			showErrorDialog("请选择路局");
			_plan_runline_batch_select_lj.focus();
			_myProgressModal.hide();
			_plan_construction_btnQuery.removeAttr("disabled");
			return;
		}
		
		$.ajax({
			url : basePath+"/construction/queryTrainForBureau",
			cache : false,
			type : "POST",
			dataType : "json",
			contentType : "application/json",
			data :JSON.stringify({ 
				runDate : _plan_construction_selectdate.val(),//开行日期
				bureauNrm : _plan_runline_batch_select_lj.val(),//车次号  
				currentPage: currentPage,
				pageSize: 15
			}),
			success : function(result) {   
//	            <td><div style="text-align:center;margin:2px 0 10px 0;"><input type="checkbox"/></div></td> 
				if (result != null && result != "undefind" && result.code == "0") {  
					if (result.data !=null) {   
						_self.trainLineMap=[];
						$.each(result.data,function(n,constructionObj){   
							var result = $.parseJSON(constructionObj); 
							
							if(result.resultCount == null || result.resultCount == 0){
								var tr = $("<tr/>");
								tr.append("<td colspan='8'>没有可现显示的数据</td>");
								_plan_review_table_trainInfo.append(tr);     
								return;
							}
							_self.initPagination(result.resultCount, currentPage);
							
							if(result.code == "-1"){
								showErrorDialog("接口调用返回错误，code="+result.code+"   message:"+result.message);
								commonJsScreenUnLock();
								return;
							} 
							
							$.each(result.data, function(n,temp){ 
								var tr = $("<tr/>"); 
								tr.append("<td>"+filterValue(temp.name)+"</td>");
								
								if(temp.scheduleDto !=null){								 
									var sourceItemDto = temp.scheduleDto.sourceItemDto;
									var targetItemDto = temp.scheduleDto.targetItemDto; 
									
									if(sourceItemDto != null){
										tr.append("<td>"+filterValue(sourceItemDto.bureauShortName)+"</td>");
										tr.append("<td>"+sourceItemDto.name+"</td>"); 
										if(sourceItemDto.sourceTimeDto2 != null){
											var a = sourceItemDto.sourceTimeDto2.substring(5).replace(/-/, "");
											tr.append("<td>"+a+"</td>"); 
										}else{
											tr.append("<td>--</td>");
										}
									}else{
										tr.append("<td>--</td>");
										tr.append("<td>--</td>");
									}
									if(targetItemDto != null){
										tr.append("<td>"+targetItemDto.name+"</td>");
										if(targetItemDto.sourceTimeDto2 != null){
											var a = targetItemDto.sourceTimeDto2.substring(5).replace(/-/, "");
											tr.append("<td>"+a+"</td>"); 
										}else{
											tr.append("<td>--</td>");
										} 
									}else{
										tr.append("<td>--</td>");
										tr.append("<td>--</td>");
									}  
									
									var skarr = []; 
									if(temp.scheduleDto.sourceItemDto != null){
										skarr.push(temp.scheduleDto.sourceItemDto); 
									}   
									
									if(temp.scheduleDto.routeItemDtos != null && temp.scheduleDto.routeItemDtos.length > 0){
										$.each(temp.scheduleDto.routeItemDtos,function(i, a){ 
											skarr.push(a);
										});
									} 
									if(temp.scheduleDto.targetItemDto != null){
										skarr.push(temp.scheduleDto.targetItemDto);
									} 
									
									skarr.sort(function(a, b){  
										return a.index - b.index;
									});  
									var train = new Train(tr[0], skarr);
									
									_self.trainLineMap.push(train); 
									
									tr.click(function(){
										_plan_review_table_trainLine.find("tr:gt(0)").remove();  
										for(var i = 0; i < _self.trainLineMap.length; i++){  
											if(_self.trainLineMap[i].tr == this){   
												if(_self.trainLineMap[i].lines != null){ 
													_self.showTable(_self.trainLineMap[i].lines);
												}
												break;
											}
										} 
									}); 
									_plan_review_table_trainInfo.append(tr);
								} 
							}); 
						}); 
					} 
				} else {
					
					showErrorDialog("接口调用返回错误，code="+result.code+"   message:"+result.message);
				} 

			},
			error : function() {
				showErrorDialog("接口调用失败");
			},
			complete: function(){ 
				_plan_construction_btnQuery.removeAttr("disabled");
				commonJsScreenUnLock();
			}
		});
	};  
};


 

var _PlanViewPage = null;
var _plan_construction_table = $("#plan_construction_table");
var _plan_construction_btnQuery = $("#plan_construction_btnQuery");// 错误数总计 
var _plan_construction_input_trainNbr = $("#plan_construction_input_trainNbr");// 车次详情div 车次号input
var _plan_construction_selectdate = $("#plan_construction_selectdate");// 日期

var _plan_construction_createRunLine = $("#plan_construction_createRunLine");// 
 
 
var _plan_review_table_trainInLine = $("#plan_review_table_trainInLine");
 
var _plan_review_table_trainLine = $("#plan_review_table_trainLine");
 
var _plan_review_table_trainInfo = $("#plan_review_table_trainInfo");

var _plan_runline_batch_select_lj = $("#plan_runline_batch_select_lj");

var _myProgressModal =  $("#myProgressModal");
var socket = null;
var stompClient = null;
$(function(){
	_PlanConstructionPage = new PlanConstructionPage();  
	
	//车次详情div 车次查询按钮增加事件
	_plan_construction_btnQuery.click(function(){
		_PlanConstructionPage.queryConstructionDetail(1);
	});
	
	_plan_construction_createRunLine.click(function(){
		_PlanConstructionPage.createConstructionPlain();
	});
	 
	
//	 $("#checkAll").change(function() {
//		 $('input[name="subBox"]').attr("checked", this.checked);
//     });
     
	//创建websocket连接
//	socket = new SockJS(basePath+'/portfolio');
//    stompClient = Stomp.over(socket);
    _PlanConstructionPage.initPage(); 
	
});



