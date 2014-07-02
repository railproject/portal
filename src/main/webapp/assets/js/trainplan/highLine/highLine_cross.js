var canvasData = {};  
var cross = null;
$(function() { 
	
	cross = new CrossModel();
	ko.applyBindings(cross); 
	
	cross.init();   
});

var highlingFlags = [{"value": "0", "text": "普线"},{"value": "1", "text": "高线"},{"value": "2", "text": "混合"}];
var checkFlags = [{"value": "1", "text": "已"},{"value": "0", "text": "未"}];
var unitCreateFlags = [{"value": "0", "text": "未"}, {"value": "1", "text": "已"},{"value": "2", "text": "全"}];
var highlingrules = [{"value": "1", "text": "平日"},{"value": "2", "text": "周末"},{"value": "3", "text": "高峰"}];
var commonlinerules = [{"value": "1", "text": "每日"},{"value": "2", "text": "隔日"}];
 
var _cross_role_key_pre = "JHPT.KYJH.JHBZ.";
function hasActiveRole(bureau){ 
	var roleKey = _cross_role_key_pre + bureau;
	return all_role.indexOf(roleKey) > -1; 
}

var gloabBureaus = [];   

function HighLineCrossModle(){
	var self = this;
	self.trains = ko.observableArray();  
	
	self.loadTrains = function(trains){
		$.each(trains, function(i, n){
			var train = new TrainRow(n);
			train.loadTimes(n.highlineTrainTimeList);
			self.trains.push(train);
			
		});
	};
	
	self.addTrain = function(train){
		train.trainSort(self.trains().length);
		self.trains.push(train);
	};
	 
	self.crossStartDate = ko.computed(function(){  
		return self.trains().length > 0 ? self.trains()[0].times()[0].targetTime : ""; 
	});;
	//结束日期（该日历交路最后一个车次的终到日期）
	self.crossEndDate = ko.computed(function(){  
		return self.trains().length > 0 ? self.trains()[self.trains().length - 1].times()[self.trains()[self.trains().length - 1].times().length - 1].sourceTime : ""; 
	});
	
	
	self.crossStartStn = ko.computed(function(){  
		return self.trains().length > 0 ? self.trains()[0].times()[0].stnName : ""; 
	});;
	//结束日期（该日历交路最后一个车次的终到日期）
	self.crossEndStn = ko.computed(function(){  
		return self.trains().length > 0 ? self.trains()[self.trains().length - 1].times()[self.trains()[self.trains().length - 1].times().length - 1].stnName : ""; 
	});
	 
	 
	//备用及停运标记（1:开行;2:备用;0:停运）
	self.spareFlag = 1;
	//相关局（局码）
	self.relevantBureau = ko.computed(function(){ 
		var result = ""; 
		var trains = self.trains(); 
		for(var j = 0; j < trains.length; j++){ 
			if(trains[j].passBureau != null){
				for(var i = 0; i < trains[j].passBureau.length; i++){
					if(result.indexOf(trains[j].passBureau.substring(i, i + 1)) > -1){
						continue;
					}else{
						result += trains[j].passBureau.substring(i, i + 1);
					}
				} 
			}
			
		};
		return result; 
	});
	
	self.tokenVehBureau = "";
	 
	//担当车辆段/动车段
	self.tokenVehDept = "";
	//担当动车所（用于高铁）
	self.tokenVehDepot = "";
	//客运担当局（局码）
	self.tokenPsgBureau = "";
	//担当客运段
	self.tokenPsgDept = "";
	//动车组车型（用于高铁）
	self.crhType = "";
	//动车组车组号1（用于高铁）
	self.vehicle1 = "";
	//动车组车组号2（用于高铁）
	self.vehicle2 = "";
	
	self.crossName = ko.computed(function(){ 
		var result = ""; 
		var trains = self.trains();
		for(var j = 0; j < trains.length; j++){
			result += result == "" ? trains[j].trainNbr : "-" + trains[j].trainNbr;
		};
		return result; 
	});
}

function CrossModel() {
	var self = this;
		//列车列表
	self.trains = ko.observableArray();
	
	self.stns = ko.observableArray();
	//交路列表 
	self.crossAllcheckBox = ko.observable(0);
	
	self.trainPlans = ko.observableArray();
	
	self.planDays = ko.observableArray(); 
	
	self.gloabBureaus = [];  
	
	self.times = ko.observableArray();
	
	self.simpleTimes = ko.observableArray();
	
	self.runPlanCanvasPage = new RunPlanCanvasPage(self);
	
	self.currentTrainInfoMessage = ko.observable("");
	
	self.currentTrain = ko.observable(); 
	
	//中间多选列表的对象
	self.acvtiveHighLineCrosses = ko.observableArray(); 
	
	//组合拆解功能 从左边交路中拉过来未处理的
	self.oldHighLineCrosses = ko.observableArray();  
	//车辆担当局
	self.searchModle = ko.observable(new searchModle());
	//当前日期可调整的交路
	self.highLineCrossRows =  ko.observableArray(); 
	//左边交路选中的记录列表
	self.selectedHighLineCrossRows = ko.observableArray(); 
	//中间的列表选中的记录列表
	self.selectedActiveHighLineCrossRows = ko.observableArray(); 
	//车次组合后的时刻点单，用于显示
	self.activeTimes = ko.observableArray(); 
	
	
	
	self.activeCrossUp = function(){
		var currentCorss = self.selectedActiveHighLineCrossRows()[0]; 
		var acvtiveHighLineCrosses = self.acvtiveHighLineCrosses();
		for(var i = 0 ; i < acvtiveHighLineCrosses.length; i++){
			console.log(currentCorss);
			if(acvtiveHighLineCrosses[i] == currentCorss){ 
				if(i > 0){ 
					var temp = acvtiveHighLineCrosses[i - 1];
					self.acvtiveHighLineCrosses.splice(i - 1, 2, currentCorss, temp);  
				}
				break;
			}
		}
	};
	
	self.activeCrossDown = function(){
		var currentCorss = self.selectedActiveHighLineCrossRows()[0]; 
		var acvtiveHighLineCrosses = self.acvtiveHighLineCrosses();
		for(var i = 0 ; i < acvtiveHighLineCrosses.length; i++){
			console.log(currentCorss);
			if(acvtiveHighLineCrosses[i] == currentCorss){ 
				if(i < acvtiveHighLineCrosses.length - 1){  
					var temp = acvtiveHighLineCrosses[i + 1];
					self.acvtiveHighLineCrosses.splice(i , 2, temp, currentCorss);   
				}
				break;
			}
		}
	};
	
	self.selectedActiveHighLineCrossChange = function(){  
		self.activeTimes.remove(function(item){
			return true;
		}); 
		$.each(self.selectedActiveHighLineCrossRows(), function(i, n){
			$.each(n.trains(), function(a, t){
				$.each(t.times(), function(z, time){
					self.activeTimes.push(time);
				});
			});
		});
	};
	
	self.selectedCrosse = function(){ 
		 var currentCorss = self.selectedHighLineCrossRows()[0]; 
		 //做恢复使用
		 self.oldHighLineCrosses.push(currentCorss);
		 commonJsScreenLock();
		 $.ajax({
				url : "highLine/getHighlineTrainTimeForHighlineCrossId",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({highlineCrossId: currentCorss.highLineCrossId()}),
				success : function(result) {    
					if (result != null && result != "undefind" && result.code == "0") { 
						 var cross = new HighLineCrossModle(); 
						 cross.loadTrains(result.data);
						 self.highLineCrossRows.remove(currentCorss);
						 self.selectedHighLineCrossRows.remove(currentCorss);
						 self.acvtiveHighLineCrosses.push(cross); 
					} else {
						showErrorDialog("没有加载的交路数据");
					} 
				},
				error : function() {
					showErrorDialog("加载的交路数据失败");
				},
				complete : function(){
					commonJsScreenUnLock();
				}
		    });   
	};
	
	self.cjHighLineCross = function(){
		$.each(self.selectedActiveHighLineCrossRows(), function(i, n){
			$.each(n.trains(), function(a, t){ 
				var cross = new HighLineCrossModle(); 
				 cross.addTrain(t);
				self.acvtiveHighLineCrosses.push(cross);  
			}); 
			self.selectedActiveHighLineCrossRows.remove(n);
			self.acvtiveHighLineCrosses.remove(n);
		});
	};
	
	//合并交路
	self.hbHighLineCross = function(){ 
		var selectedActiveHighLineCrossRows = self.selectedActiveHighLineCrossRows();  
		var cross = new HighLineCrossModle(); 
		
		for(var i = 0; i < selectedActiveHighLineCrossRows.length; i++){
			var cr = selectedActiveHighLineCrossRows[i];
			if(i > 0){
				var pre = selectedActiveHighLineCrossRows[i - 1]; 
				if(!timeCompare(pre.crossEndDate(), cr.crossStartDate()) || pre.crossEndStn() != cr.crossStartStn()){
					showConfirmDiv("提示", "车次" + cr.crossName() + "无法与" + pre.crossName() + "前车接续", function(r){
						return;
					});
					return;
				}  
			} 
			var trains = cr.trains();  
			for(var j = 0; j < trains.length; j++ ){  
				cross.addTrain(trains[j]);
			};   
			
		} 
		while(selectedActiveHighLineCrossRows.length > 0){
			self.acvtiveHighLineCrosses.remove(selectedActiveHighLineCrossRows[0]); 
			self.selectedActiveHighLineCrossRows.remove(selectedActiveHighLineCrossRows[0]); 
		}
		 
		self.acvtiveHighLineCrosses.push(cross); 
	};
	
	//保存交路合并结果
	self.submitHighLineCross = function(){ 
		var activeCrosses = self.acvtiveHighLineCrosses();
		var crosses = [];
		for(var i = 0; i < activeCrosses.length; i++){  
			var postParam = $.parseJSON(ko.toJSON(activeCrosses[i])); 
			$.each(postParam.trains , function(i, n){
				delete(n.times);
				delete(n.simpleTimes);
			});
			crosses.push(postParam);
		}
		var oldCrosses = self.oldHighLineCrosses();
		var oldCrossIds = "";
		for(var i = 0; i < oldCrosses.length; i++){ 
			oldCrossIds += (oldCrossIds == "" ? "'"  : ",'")  + oldCrosses[i].highLineCrossId() + "'";
		}
		
		$.ajax({
				url : "highLine/saveHighlineCrossAndTrainInfo",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({"highLineCrossIds": oldCrossIds, "newCrosses" : crosses}),
				success : function(result) {    
					if (result != null && result != "undefind" && result.code == "0") {
						self.loadCrosses();
						self.acvtiveHighLineCrosses.remove(function(item){
							return true;
						});
						showSuccessDialog("交路调整成功"); 
					} else {
						showErrorDialog("交路调整失败");
					} 
				},
				error : function() {
					showErrorDialog("交路调整失败");
				},
				complete : function(){
					commonJsScreenUnLock();
				}
		    });  
		
	};
	
	self.vehicle1Change = function(row){
		row.updateFlag(true);
		var highLineCrossRows = self.highLineCrossRows();
		if(row.vehicle1() != null && row.vehicle1() != ""){
			for(var i = 0; i < highLineCrossRows.length; i++){
				if(highLineCrossRows[i] != row && highLineCrossRows[i].vehicle1() == row.vehicle1()){
					showConfirmDiv("提示", "当前车底被交路：" + highLineCrossRows[i].crossName() + "使用是否要重新绑定到当前交路?", function (r) { 
				        if (r) { 
				        	highLineCrossRows[i].vehicle1("");
				        }else{
				        	row.vehicle1("");
				        }
					});
				}else if(highLineCrossRows[i].vehicle2() == row.vehicle1()){
					showConfirmDiv("提示", "当前车底被交路：" + highLineCrossRows[i].crossName() + "使用是否要重新绑定到当前交路?", function (r) { 
				        if (r) { 
				        	highLineCrossRows[i].vehicle2("");
				        }else{
				        	row.vehicle1("");
				        }
					});
				}
			} 
		}
	};
	self.vehicle2Change = function(row){ 
		row.updateFlag(true);
		var highLineCrossRows = self.highLineCrossRows();
		if(row.vehicle2() != null && row.vehicle2() != ""){
			for(var i = 0; i < highLineCrossRows.length; i++){
				if(highLineCrossRows[i].vehicle1() == row.vehicle2()){
					showConfirmDiv("提示", "当前车底被交路：" + highLineCrossRows[i].crossName() + "使用是否要重新绑定到当前交路?", function (r) { 
				        if (r) { 
				        	highLineCrossRows[i].vehicle1(""); 
				        	highLineCrossRows[i].updateFlag(true);
				        }else{
				        	row.vehicle2("");
				        	row.updateFlag(false);
				        }
					});
					break;
				}else if(highLineCrossRows[i] != row && highLineCrossRows[i].vehicle2() == row.vehicle2()){
					showConfirmDiv("提示", "当前车底被交路：" + highLineCrossRows[i].crossName() + "使用是否要重新绑定到当前交路?", function (r) { 
				        if (r) { 
				        	highLineCrossRows[i].vehicle2("");
				        	highLineCrossRows[i].updateFlag(true);
				        }else{
				        	row.vehicle2("");
				        	row.updateFlag(false);
				        }
					});
					break;
				}
			}
	   }
		
	};
	 
	self.updateHighLineCrosses = function(){ 
		var highLineCrossRows = self.highLineCrossRows();
		updateCrossRows = [];
		for(var i = 0; i < highLineCrossRows.length; i++){
			if(highLineCrossRows[i].updateFlag()){
				updateCrossRows.push({"highLineCrossId": highLineCrossRows[i].highLineCrossId(), "vehicle1":  highLineCrossRows[i].vehicle1(), "vehicle2":  highLineCrossRows[i].vehicle2()});
			}
		} 
		 $.ajax({
				url : "highLine/updateHighLineVehicle",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({"highLineCrosses": updateCrossRows}),
				success : function(result) {    
					if (result != null && result != "undefind" && result.code == "0") {
						showSuccessDialog("提交交动车交路计划成功"); 
					} else {
						showErrorDialog("获取运行规律失败");
					} 
				},
				error : function() {
					showErrorDialog("接口调用失败");
				},
				complete : function(){
					commonJsScreenUnLock();
				}
		    });  
	};
	
	self.trainRunPlanChange = function(row, event){ 
		console.log(row);
		console.log(event.target.name);
		console.log("trainRunPlanChange test");
	};
	
	self.dragRunPlan = function(n,event){
		$(event.target).dialog("open"); 
	};
	

	
	self.loadStns = function(currentTrain){  
		self.times.remove(function(item){
			return true;
		});
		self.simpleTimes.remove(function(item){
			return true;
		});
		commonJsScreenLock();
		 $.ajax({ 
				url : "jbtcx/queryPlanLineTrainTimes",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({
					trainId: currentTrain.obj.planTrainId
				}),
				success : function(result) {    
					if (result != null && result != "undefind" && result.code == "0") { 
							var message = "车次：" + currentTrain.obj.trainName + "&nbsp;&nbsp;&nbsp;";
							
							$.each(result.data, function(i, n){
								var timeRow = new TrainTimeRow(n); 
								self.times.push(timeRow); 
								if(n.stationFlag != 'BTZ'){
									self.simpleTimes.push(timeRow); 
								}
								if(i == 0){
									message += n.stnName;
								}else if(i == result.data.length - 1){
									self.currentTrainInfoMessage(message + "——" + n.stnName);
									if($("#run_plan_train_times").is(":hidden")){
										$("#run_plan_train_times").dialog({top:10, draggable: true, resizable:true, onResize:function() {
											var simpleTimes_table = $("#simpleTimes_table");
											var allTimes_table = $("#allTimes_table");
											var isChrome = navigator.userAgent.toLowerCase().match(/chrome/) != null;
											var WH = $('#run_plan_train_times').height() - 100;
											var WW = $('#run_plan_train_times').width();
								            if (isChrome) {
								            	simpleTimes_table.css({ "height": (WH) + "px"});
								            	simpleTimes_table.css({ "min-height": (WH) + "px"});
								            	simpleTimes_table.attr("width", (WW));
								            	
								            	allTimes_table.css({ "height": (WH) + "px"});
								            	allTimes_table.css({ "min-height": (WH) + "px"});
								            	allTimes_table.attr("width", (WW));

								            }else{
								            	simpleTimes_table.css({ "height": (WH)  + "px"});
								            	simpleTimes_table.css({ "min-height": (WH) + "px"});
								            	simpleTimes_table.attr("width", (WW));
								            	
								            	allTimes_table.css({ "height": (WH) + "px"});
								            	allTimes_table.css({ "min-height": (WH) + "px"});
								            	allTimes_table.attr("width", (WW));
								            }
										}});
									} 
								}  
							}); 
					} else {
						showErrorDialog("接口调用返回错误，code="+result.code+"   message:"+result.message);
					} 
				},
				error : function() {
					showErrorDialog("接口调用失败");
				},
				complete : function(){ 
						commonJsScreenUnLock();  
				}
		    }); 
		   
		// $("#run_plan_train_times").dialog("open");
	};
	self.setCurrentTrain = function(train){ 
		self.currentTrain(train); 
	};
	
	self.setCurrentCross = function(cross){
//		if(hasActiveRole(cross.tokenVehBureau()) && self.searchModle().activeFlag() == 0){
//			self.searchModle().activeFlag(1);  
//		}else if(!hasActiveRole(cross.tokenVehBureau()) && self.searchModle().activeFlag() == 1){
//			self.searchModle().activeFlag(0); 
//		} 
		self.currentCross(cross); 
	};
	
	
	
	self.selectCrosses = function(){
//		self.crossAllcheckBox(); 
		$.each(self.highLineCrossRows(), function(i, crossRow){ 
			if(self.crossAllcheckBox() == 1){
				crossRow.selected(0);
				self.searchModle().activeFlag(0);
				self.searchModle().checkActiveFlag(0); 
			}else{
				if(hasActiveRole(crossRow.tokenVehBureau())){ 
					crossRow.selected(1); 
					self.searchModle().activeFlag(1);  
				}
				//可以审核的
				if(crossRow.checkActiveFlag() == 1){
					crossRow.selected(1); 
					self.searchModle().checkActiveFlag(1); 
				}
			}  
		});  
	};
	
	self.selectCross = function(row){ 
//		self.crossAllcheckBox();  
		if(row.selected() == 0){  
			self.crossAllcheckBox(1);   
			if(row.activeFlag() == 1){ 
				self.searchModle().activeFlag(1);
				$.each(self.highLineCrossRows(), function(i, crossRow){   
					if(crossRow.selected() != 1 && crossRow != row && crossRow.activeFlag() == 1){
						self.crossAllcheckBox(0);
						return false;
					}  
				}); 
			} 
			if(row.checkActiveFlag() == 1){
				self.searchModle().checkActiveFlag(1);
				$.each(self.highLineCrossRows(), function(i, crossRow){   
					if(crossRow.selected() != 1 && crossRow != row && crossRow.checkActiveFlag() == 1){
						self.crossAllcheckBox(0);
						return false;
					}  
				}); 
				 
			};  
		}else{
			self.crossAllcheckBox(0);
			if(row.activeFlag() == 1){
				self.searchModle().activeFlag(0); 
				$.each(self.highLineCrossRows(), function(i, crossRow){   
					if(crossRow.selected() == 1 && crossRow != row && crossRow.activeFlag() == 1){
						self.searchModle().activeFlag(1);  
						 return false;
						//可以审核的  
					}  
				}); 
			}
			if(row.checkActiveFlag() == 1){
				self.searchModle().checkActiveFlag(0);
				$.each(self.highLineCrossRows(), function(i, crossRow){   
					if(crossRow.selected() == 1 && crossRow != row && crossRow.checkActiveFlag() == 1){
						self.searchModle().checkActiveFlag(1);   
						return false;
					};  
				}); 
			}; 
		}; 
	};
	
	
	// cross基础信息中的下拉列表  
	self.loadBureau = function(bureaus){   
		for ( var i = 0; i < bureaus.length; i++) {  
			self.tokenVehBureaus.push(new BureausRow(bureaus[i])); 
			if(bureaus[i].code == self.tokenVehBureau()){
				self.tokenVehBureau(bureaus[i]);
				break;
			}
		} 
	};
	self.filterCrosses = function(){  
		 var filterTrainNbr = self.searchModle().filterTrainNbr();
		 filterTrainNbr = filterTrainNbr || filterTrainNbr != "" ? filterTrainNbr.toUpperCase() : "all";
		 if(filterTrainNbr == "all"){
			 $.each(self.crossRows.rows(),function(n, crossRow){
				  crossRow.visiableRow(true);
			  });
		 }else{
			 $.each(self.crossRows.rows(),function(n, crossRow){
				 if(crossRow.crossName().indexOf(filterTrainNbr) > -1){
					 crossRow.visiableRow(true);
				 }else{
					 crossRow.visiableRow(false);
				 } 
			  }); 
		 };
	};  
	 
	self.defualtCross = {"crossId":"",
		"crossName":"", 
		"chartId":"",
		"chartName":"",
		"crossStartDate":"",
		"crossEndDate":"",
		"crossSpareName":"",
		"alterNateDate":"",
		"alterNateTranNbr":"",
		"spareFlag":"",
		"cutOld":"",
		"groupTotalNbr":"",
		"pairNbr":"",
		"highlineFlag":"",
		"highlineRule":"",
		"commonlineRule":"",
		"appointWeek":"",
		"appointDay":"",
		"crossSection":"",
		"throughline":"",
		"startBureau":"",
		"tokenVehBureau":"",
		"tokenVehDept":"",
		"tokenVehDepot":"",
		"appointPeriod":"",
		"tokenPsgBureau":"",
		"tokenPsgDept":"",
		"tokenPsgDepot":"",
		"locoType":"",
		"crhType":"",
		"elecSupply":"",
		"dejCollect":"",
		"airCondition":"",
		"note":"", 
		"createPeople":"", 
		"createPeopleOrg":"",  
		"createTime":""};
	//当前选中的交路对象
	self.currentCross = ko.observable(new CrossRow(self.defualtCross)); 
	 
	//currentIndex 
	self.currdate =function(){
		var d = new Date();
		var year = d.getFullYear();    //获取完整的年份(4位,1970-????)
		var month = d.getMonth()+1;       //获取当前月份(0-11,0代表1月)
		var days = d.getDate(); 
		month = ("" + month).length == 1 ? "0" + month : month;
		days = ("" + days).length == 1 ? "0" + days : days;
		return year+"-"+month+"-"+days;
	};
	
	self.dayHeader =function(d){ 
	 
		var month = d.getMonth()+1;       //获取当前月份(0-11,0代表1月)
		var days = d.getDate(); 
		month = ("" + month).length == 1 ? "0" + month : month;
		days = ("" + days).length == 1 ? "0" + days : days;
		return month + "-"+ days;
	};
	
	self.getDateByInt = function(n){
		var d = new Date();
		d.setDate(d.getDate() + n);
		
		var year = d.getFullYear();    //获取完整的年份(4位,1970-????)
		var month = d.getMonth()+1;       //获取当前月份(0-11,0代表1月)
		var days = d.getDate(); 
		month = ("" + month).length == 1 ? "0" + month : month;
		days = ("" + days).length == 1 ? "0" + days : days;
		return year + "-" + month + "-" + days;
	};
	
	self.get40Date = function(){
		var d = new Date();
		d.setDate(d.getDate() + 40);
		
		var year = d.getFullYear();    //获取完整的年份(4位,1970-????)
		var month = d.getMonth()+1;       //获取当前月份(0-11,0代表1月)
		var days = d.getDate(); 
		month = ("" + month).length == 1 ? "0" + month : month;
		days = ("" + days).length == 1 ? "0" + days : days;
		return year + "-" + month + "-" + days;
	};
	
	
	
	self.init = function(){  
 
		$("#run_plan_train_times").dialog("close"); 
 
		$("#runplan_input_startDate").datepicker();
		$("#runplan_input_endDate").datepicker();
		
		$("#current_highLineCrosses").on("dblclick", self.selectedCrosse);
		
		
		
		$("#active_highLine_cross_dialog").dialog("close"); 
		
//		$("#current_active_highLineCrosses").on("dblclick", self.selectedCrosse);
		//x放大2倍
		$("#canvas_event_btn_x_magnification").click(function(){
			if (currentXScaleCount == 32) {
				showErrorDialog("当前已经不支持继续放大啦！");
				return;
			}
			
			//必须清除
			lineList = [];	//列车线对象封装类  用于保存列车线元素，以便重新绘图
			jlList = [];	//用于保存交路数据元素，以便重新绘图
			
			//计算画布比例及倍数
			currentXScale = currentXScale/2;
			currentXScaleCount = currentXScaleCount*2;

			$("#canvas_event_label_xscale").text(currentXScaleCount);
			self.runPlanCanvasPage.clearChart();	//清除画布
			self.runPlanCanvasPage.drawChart({
					 xScale : currentXScale,			//x轴缩放比例
					 xScaleCount : currentXScaleCount,	//x轴放大总倍数
					 yScale : currentYScale,		//y轴缩放比例
					 stationTypeArray:self.searchModle().drawFlags()
				 });
			
		});
		
		//x缩小2倍
		$("#canvas_event_btn_x_shrink").click(function(){
			if (currentXScaleCount == 0.25) {
				showErrorDialog("当前已经不支持继续缩小啦！");
				return;
			}
			
			//必须清除
			lineList = [];	//列车线对象封装类  用于保存列车线元素，以便重新绘图
			jlList = [];	//用于保存交路数据元素，以便重新绘图

			//计算画布比例及倍数
			currentXScale = currentXScale*2;
			currentXScaleCount = currentXScaleCount/2;

			$("#canvas_event_label_xscale").text(currentXScaleCount);
			self.runPlanCanvasPage.clearChart();	//清除画布
			self.runPlanCanvasPage.drawChart({
				 xScale : currentXScale,			//x轴缩放比例
				 xScaleCount : currentXScaleCount,	//x轴放大总倍数
				 yScale : currentYScale,			//y轴缩放比例
				 stationTypeArray:self.searchModle().drawFlags()
			 });
		});
		//y放大2倍
		$("#canvas_event_btn_y_magnification").click(function(){
			if (currentYScaleCount == 8) {
				showErrorDialog("当前已经不支持继续放大啦！");
				return;
			}
			
			//必须清除
			lineList = [];	//列车线对象封装类  用于保存列车线元素，以便重新绘图
			jlList = [];	//用于保存交路数据元素，以便重新绘图
			
			//计算画布比例及倍数
			currentYScale = currentYScale/2;
			currentYScaleCount = currentYScaleCount*2;

			$("#canvas_event_label_yscale").text(currentYScaleCount);
			self.runPlanCanvasPage.clearChart();	//清除画布
			self.runPlanCanvasPage.drawChart({
					 xScale : currentXScale,			//x轴缩放比例
					 xScaleCount : currentXScaleCount,	//x轴放大总倍数
					 yScale : currentYScale,			//y轴缩放比例
					 stationTypeArray:self.searchModle().drawFlags()
				 }); 
		});
		
		//y缩小2倍
		$("#canvas_event_btn_y_shrink").click(function(){
			if (currentYScaleCount == 0.25) {
				showErrorDialog("当前已经不支持继续缩小啦！");
				return;
			}
			
			//必须清除
			lineList = [];	//列车线对象封装类  用于保存列车线元素，以便重新绘图
			jlList = [];	//用于保存交路数据元素，以便重新绘图

			//计算画布比例及倍数
			currentYScale = currentYScale*2;
			currentYScaleCount = currentYScaleCount/2;

			$("#canvas_event_label_yscale").text(currentYScaleCount);
			self.runPlanCanvasPage.clearChart();	//清除画布
			self.runPlanCanvasPage.drawChart({
				 xScale : currentXScale,			//x轴缩放比例
				 xScaleCount : currentXScaleCount,	//x轴放大总倍数
				 yScale : currentYScale,			//y轴缩放比例
				 stationTypeArray:self.searchModle().drawFlags()
			 });
		}); 
		self.runPlanCanvasPage.initPage();   
		self.searchModle().planStartDate(self.getDateByInt(1)); 
		
		commonJsScreenLock(2); 
		//获取当期系统日期 
		 $.ajax({
				url : "jbtcx/querySchemes",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({}),
				success : function(result) {    
					if (result != null && result != "undefind" && result.code == "0") {
						if (result.data !=null) { 
							self.searchModle().loadChats(result.data); 
						} 
					} else {
						showErrorDialog("接口调用返回错误，code="+result.code+"   message:"+result.message);
					} 
				},
				error : function() {
					showErrorDialog("接口调用失败");
				},
				complete : function(){ 
					commonJsScreenUnLock();  
				}
		    }); 
		 
	    $.ajax({
			url : "plan/getFullStationInfo",
			cache : false,
			type : "GET",
			dataType : "json",
			contentType : "application/json", 
			success : function(result) {    
				if (result != null && result != "undefind" && result.code == "0") { 
					self.searchModle().loadBureau(result.data); 
					if (result.data !=null) { 
						$.each(result.data,function(n, bureau){  
							self.gloabBureaus.push({"shortName": bureau.ljjc, "code": bureau.ljpym});
							gloabBureaus.push({"shortName": bureau.ljjc, "code": bureau.ljpym});
						});
					} 
				} else {
					showErrorDialog("获取路局列表失败");
				} 
			},
			error : function() {
				showErrorDialog("获取路局列表失败");
			},
			complete : function(){ 
				commonJsScreenUnLock(); 
			}
	    });
		
		
	};  
	self.createHighLineCrosses = function(){
		 var planStartDate = $("#runplan_input_startDate").val();
		 $.ajax({
				url : "highLine/createHighLineCross",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({  
					startDate : (planStartDate != null ? planStartDate : self.currdate()).replace(/-/g, '') 
				}),
				success : function(result) {   
					if (result != null && result != "undefind" && result.code == "0") { 
						if(result.data != null && result.data.length > 0){
							$.each(result.data, function(i, n){
								self.highLineCrossRows.push(new CrossRow(n));
							});
						}
						showSuccessDialog("加载图定成功");  
					} else {
						showErrorDialog("获取车底交路列表失败");
					};
				},
				error : function() {
					showErrorDialog("获取车底交路列表失败");
				},
				complete : function(){
					commonJsScreenUnLock();
				}
			}); 
	};
	
	self.loadCrosses = function(){
		self.loadCrosseForPage();
	};
	self.loadCrosseForPage = function(startIndex, endIndex) {  
	 
//		commonJsScreenLock();
		 
//		var bureauCode = self.searchModle().bureau(); 
//		var highlingFlag = self.searchModle().highlingFlag();
//		var trainNbr = self.searchModle().filterTrainNbr(); 
//		var checkFlag = self.searchModle().checkFlag();
//		var unitCreateFlag = self.searchModle().unitCreateFlag();
//		var chart = self.searchModle().chart();
//		var startBureauCode = self.searchModle().startBureau();  
//		var planStartDate = self.searchModle().planStartDate(); 
//		var planEndDate = self.searchModle().planEndDate(); 
		 var planStartDate = $("#runplan_input_startDate").val();
			
//		 var planEndDate =  $("#runplan_input_endDate").val();
//		 var currentBureanFlag = self.searchModle().currentBureanFlag() ? '1' : '0';   
//		 self.searchModle().checkActiveFlag(0); 
//		 if(hasActiveRole(bureauCode) && self.searchModle().activeFlag() == 0){
//			self.searchModle().activeFlag(1);  
//		}else if(!hasActiveRole(bureauCode) && self.searchModle().activeFlag() == 1){
//			self.searchModle().activeFlag(0); 
//		} 
		 
		
//		if(chart == null){
//			showErrorDialog("请选择方案!");
//			commonJsScreenUnLock();
//			return;
//		}
		self.highLineCrossRows.remove(function(item) {
			return true;
		});  
		$.ajax({
				url : "highLine/getHighlineCrossList",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({  
		
//					tokenVehBureau : bureauCode, 
//					highlineFlag : highlingFlag == null ? null : highlingFlag.value,  
//					checkFlag : checkFlag == null ? null : checkFlag.value,
//					startBureau : startBureauCode,
//					unitCreateFlag :  unitCreateFlag == null ? null : unitCreateFlag.value,
//							chartId : chart == null ? null: chart.chartId,
//					trainNbr : trainNbr,
		
					crossStartDate : (planStartDate != null ? planStartDate : self.currdate()).replace(/-/g, ''),

//					endTime : (planEndDate != null ? planEndDate : self.get40Date()).replace(/-/g, ''),
//					currentBureanFlag : currentBureanFlag
					
					  
				}),
				success : function(result) {    
 
					if (result != null && result != "undefind" && result.code == "0") {
						//var rows = [];
						if(result.data != null){  
							$.each(result.data,function(n, crossInfo){ 
								self.highLineCrossRows.push(new CrossRow(crossInfo));  
							}); 
							//self.crossRows.loadPageRows(result.data.totalRecord, rows);
						}   
						 
					} else {
						showErrorDialog("获取车底交路列表失败");
					};
				},
				error : function() {
					showErrorDialog("获取车底交路列表失败");
				},
				complete : function(){
					commonJsScreenUnLock();
				}
			}); 
	};
	//必须定义在load函数之后
	self.crossRows = new PageModle(50, self.loadCrosseForPage);
	
	self.saveCrossInfo = function() { 
		alert(self.currentCross().tokenVehBureau());
	};
	 
	self.showUploadDlg = function(){
		
		$("#file_upload_dlg").dialog("open"); 
	};
	
	self.showActiveHighLineCrossDlg = function(){
		$("#active_highLine_cross_dialog").dialog("open"); 
	};  
	
	self.showRunPlans = function(){  
		if($('#learn-more-content').is(":visible")){
			$('#learn-more-content').hide();
			$('#plan_cross_default_panel').css({height: '620px'});
			$('#plan_cross_panel_body').css({height: '490px'});
			$('#plan_train_panel_body').css({height: '490px'});
			$('#canvas_parent_div').css({height: '630px'});
		}else{
			 $('#learn-more-content').show(); 
			 $('#plan_cross_default_panel').css({height: '520px'});
			 $('#plan_cross_panel_body').css({height: '390px'});
			 $('#plan_train_panel_body').css({height: '390px'});
			 $('#canvas_parent_div').css({height:'530px'});
		}
	    
	};
	
	self.showDialog = function(id, title){
		$('#' + id).dialog({ title:  title, autoOpen: true, height:600,width: 800, modal: false, draggable: true, resizable:true })
	};
	
	self.showCrossTrainDlg = function(){
		$("#cross_train_dlg").dialog("open");
	};
	
	self.showCrossTrainTimeDlg = function(){
		
		$("#run_plan_train_times").dialog({inline: false, top:10});
	};
	
	self.trainNbrChange = function(n,  event){
		self.searchModle().filterTrainNbr(event.target.value.toUpperCase());
	};
	
	self.showTrainTimes = function(row) {
		self.currentTrain(row);
		self.runPlanCanvasPage.reDrawByTrainNbr(row.trainNbr);
//		self.stns.remove(function(item){
//			return true;
//		});
//		if(row.times().length > 0){ 
//			$.each(row.times(), function(i, n){
//				self.stns.push(n); 
//			}) ;
//			 
//		}else{
//			$.ajax({
//				url : "jbtcx/queryTrainTimes",
//				cache : false,
//				type : "POST",
//				dataType : "json",
//				contentType : "application/json",
//				data :JSON.stringify({   
//					trainId : row.baseTrainId
//				}),
//				success : function(result) {  
//					if (result != null && result != "undefind" && result.code == "0") {  
//						row.loadTimes(result.data);  
//						$.each(row.times(), function(i, n){
//							self.stns.push(n); 
//						});
//					} else {
//						showErrorDialog("获取列车时刻表失败");
//					};
//				},
//				error : function() {
//					showErrorDialog("获取列车时刻表失败");
//				},
//				complete : function(){
//					commonJsScreenUnLock();
//				}
//			}); 
//		}
		
	};  
	
	self.deleteCrosses = function(){ 
		var crossIds = "";
		var crosses = self.highLineCrossRows(); 
		var delCrosses = [];
		for(var i = 0; i < crosses.length; i++){ 
			if(crosses[i].selected() == 1 && hasActiveRole(crosses[i].tokenVehBureau())){ 
				crossIds += (crossIds == "" ? "" : ",");
				crossIds += crosses[i].planCrossId();  
				delCrosses.push(crosses[i]); 
			}else if(crosses[i].selected() == 1 && !hasActiveRole(crosses[i].tokenVehBureau())){
				showWarningDialog("你没有权限删除:" + crosses[i].crossName());
				return;
			}  
		}
		if(crossIds == ""){
			showErrorDialog("没有可删除的记录");
			return;
		}
		
		showConfirmDiv("提示", "你确定要执行删除操作?", function (r) { 
	        if (r) { 
				$.ajax({
					url : "runPlan/deletePlanCrosses",
					cache : false,
					type : "POST",
					dataType : "json",
					contentType : "application/json",
					data :JSON.stringify({  
						planCrossIds : crossIds
					}),
					success : function(result) {     
						if(result.code == 0){
							$.each(delCrosses, function(i, n){ 
								self.highLineCrossRows.remove(n); 
							});
							showSuccessDialog("删除车底交路成功"); 
						}else{
							showErrorDialog("删除车底交路失败");
						};
					}
				}); 
	        };
		});
	};
	
	self.createTrainLines = function(){  
		var crossIds = "";
		var delCrosses = [];
		var crosses = self.highLineCrossRows();
		for(var i = 0; i < crosses.length; i++){  
			if(crosses[i].selected() == 1 && crosses[i].checkFlag() == 2 && hasActiveRole(crosses[i].tokenVehBureau())){ 
				crossIds += (crossIds == "" ? "" : ",");
				crossIds += crosses[i].planCrossId();  
				delCrosses.push(crosses[i]); 
			}else if(crosses[i].selected() == 1){
				if(!hasActiveRole(crosses[i].tokenVehBureau())){
					showWarningDialog("你没有权限生成:" + crosses[i].crossName() + " 的运行线");
					return;
				}else if(crosses[i].checkFlag() != 2){
					showWarningDialog(crosses[i].crossName() + "经由局未全部审核");
					return;
				} 
			}  
			
		}  
		 var planStartDate = $("#runplan_input_startDate").val();
			
		 var planEndDate =  $("#runplan_input_endDate").val();
		 if(crossIds == ""){
			 showWarningDialog("未选中数据");
		 }
		 commonJsScreenLock();
		 $.ajax({
				url : "runPlan/handleTrainLinesWithCross",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({  
					planCrossIds : crossIds,
					startDate : (planStartDate != null ? planStartDate : self.currdate()).replace(/-/g, ''),
					endDate : (planEndDate != null ? planEndDate : self.get40Date()).replace(/-/g, '')
				}),
				success : function(result) {     
					if(result.code == 0){ 
						showSuccessDialog("正在生成运行线");
					}else{
						showErrorDialog("生成运行线失败");
					}
				},
				error : function() {
					showErrorDialog("生成运行线失败");
				},
				complete : function(){
					commonJsScreenUnLock();
				}
			}); 
	};
	self.clearData = function(){ 
		 self.currentCross(new CrossRow({"crossId":"",
				"crossName":"", 
				"chartId":"",
				"chartName":"",
				"crossStartDate":"",
				"crossEndDate":"",
				"crossSpareName":"",
				"alterNateDate":"",
				"alterNateTranNbr":"",
				"spareFlag":"",
				"cutOld":"",
				"groupTotalNbr":"",
				"pairNbr":"",
				"highlineFlag":"",
				"highlineRule":"",
				"commonlineRule":"",
				"appointWeek":"",
				"appointDay":"",
				"crossSection":"",
				"throughline":"",
				"startBureau":"",
				"tokenVehBureau":"",
				"tokenVehDept":"",
				"tokenVehDepot":"",
				"appointPeriod":"",
				"tokenPsgBureau":"",
				"tokenPsgDept":"",
				"tokenPsgDepot":"",
				"locoType":"",
				"crhType":"",
				"elecSupply":"",
				"dejCollect":"",
				"airCondition":"",
				"note":"", 
				"createPeople":"", 
				"createPeopleOrg":"",  
				"createTime":""})); 
		 self.stns.remove(function(item){
			return true;
		 });
		 self.highLineCrossRows.remove(function(item){
			return true;
		 });
		 
		 self.trainPlans.remove(function(item){
			return true;
		 }); 
		self.planDays.remove(function(item){
			return true;
		 }); 
		 self.trains.remove(function(item){
			return true;
		 });  
		 self.currentTrain = ko.observable();
	};
	self.bureauChange = function(){ 
		if(hasActiveRole(self.searchModle().bureau())){
			self.searchModle().activeFlag(1); 
			self.clearData();
		}else if(self.searchModle().activeFlag() == 1){
			self.searchModle().activeFlag(0);
			self.clearData();
		}
	};
	
	//审核
	self.checkCrossInfo = function(){ 
		var crossIds = "";
		var checkedCrosses = [];
		var crosses = self.highLineCrossRows();
		for(var i = 0; i < crosses.length; i++){  
			if(crosses[i].checkFlag() == 2 && crosses[i].selected() == 1){ 
				showWarningDialog(crosses[i].crossName() + "已审核"); 
				return;
			}else if(crosses[i].checkedBureau() != null && crosses[i].checkedBureau().indexOf(currentUserBureau) > -1 && crosses[i].selected() == 1){  
				showWarningDialog(crosses[i].crossName() + "本局已审核"); 
				return;
			}else if(crosses[i].selected() == 1){
				crossIds += (crossIds == "" ? "" : ";");
				crossIds += crosses[i].planCrossId() + "#" + crosses[i].relevantBureau();
				checkedCrosses.push(crosses[i]); 
			}; 
		}  
		var planStartDate = $("#runplan_input_startDate").val(); 
		var planEndDate =  $("#runplan_input_endDate").val();
		
		if(crossIds == ""){
			showWarningDialog("没有可审核的");
			return;
		}
		commonJsScreenLock();
		 $.ajax({
				url : "runPlan/checkCrossRunLine",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({   
					startTime : (planStartDate != null ? planStartDate : self.currdate()).replace(/-/g, ''),
					endTime : (planEndDate != null ? planEndDate : self.get40Date()).replace(/-/g, ''),
					planCrossIds : crossIds
				}),
				success : function(result) {     
					if(result.code == 0){
						$.each(checkedCrosses, function(i, n){
							n.checkedBureau(n.checkedBureau() + "," + currentUserBureau); 
						});
						showSuccessDialog("审核成功");
					}else{
						showErrorDialog("审核失败");
					}
				},
				error : function() {
					showErrorDialog("审核失败");
				},
				complete : function(){
					commonJsScreenUnLock();
				}
			}); 
		
	};
	
	self.createCrossMap = function(row){ 
		
//		 var planStartDate = self.searchModle().planStartDate();
//		
//		 var planEndDate = self.searchModle().planEndDate();
		self.runPlanCanvasPage.clearChart();	//清除画布
		 var planStartDate = $("#runplan_input_startDate").val();
			
		 var planEndDate =  $("#runplan_input_endDate").val();
		 
		 $.ajax({
				url : "cross/createCrossMap",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({  
					planCrossId : row.planCrossId(),  
					startTime : planStartDate != null ? planStartDate : self.currdate(),
					endTime : planEndDate != null ? planEndDate : self.get40Date()
				}),
				success : function(result) {    
					if (result != null && result != "undefind" && result.code == "0") {
						if (result.data !=null) {   
							canvasData = {
									grid: $.parseJSON(result.data.gridData),
									jlData: $.parseJSON(result.data.myJlData)
							};   
							self.runPlanCanvasPage.drawChart({stationTypeArray:self.searchModle().drawFlags()}); 
						}
						 
					} else {
						showErrorDialog("获取车底交路绘图数据失败");
					} 
				},
				error : function() {
					showErrorDialog("获取车底交路绘图数据失败");
				},
				complete : function(){
					commonJsScreenUnLock();
				}
			}); 
	};
	self.showTrains = function(row) {   
		self.setCurrentCross(row);  
		self.trains.remove(function(item) {
			return true;
		});   
		$.ajax({
				url : "highLine/getHighlineCrossTrainBaseInfoList",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({  
					highlineCrossId : row.highLineCrossId() 
				}),
				success : function(result) {    
					if (result != null && result != "undefind" && result.code == "0") {
						if (result.data !=null && result.data.length > 0) {   
							$.each(result.data,function(n, trainInfo){
								var row = new HighLineTrain(trainInfo);
								self.trains.push(row); 
							}); 
						}
						 
					} else {
						showErrorDialog("获取交路列车列表失败");
					} 
				},
				error : function() {
					showErrorDialog("获取交路列车列表失败");
				} 
			}); 
		
	};  
	self.removeArrayValue = function(arr, value){
		var index = -1;
		for(var i = 0 ; i < arr.length; i++){
			if(value == arr[i]){
				index = i;
				break;
			}
		}
		if(index > -1){
			arr.splice(index, 1);
		}
	};
	self.drawFlagChange = function(a, n){  
		if(n.target.checked){
			self.searchModle().drawFlags.push(n.target.value);
		}else{ 
			self.removeArrayValue(self.searchModle().drawFlags(), n.target.value);
		} 
		self.runPlanCanvasPage.drawChart({stationTypeArray:self.searchModle().drawFlags()}); 
	};
	
	self.filterCrosses = function(){
		var filterCheckFlag = self.searchModle().filterCheckFlag();  
		var filterUnitCreateFlag = self.searchModle().filterUnitCreateFlag(); 
		$.each(self.crossRows.rows(),function(n, crossRow){
			  if(crossRow.checkFlag() == filterCheckFlag || crossRow.unitCreateFlag() == filterUnitCreateFlag){
				  crossRow.visiableRow(true);
			  }else{
				  crossRow.visiableRow(false);
			  }
				 
		  }); 
	}; 
}

function searchModle(){
	self = this;  
	 
	self.activeFlag = ko.observable(0);  
	
	self.checkActiveFlag = ko.observable(0);  
	
	self.activeCurrentCrossFlag = ko.observable(0);  
	
	self.drawFlags =ko.observableArray(['0']); 
	
	self.planStartDate = ko.observable();
	
	self.currentBureanFlag = ko.observable(0);
	
	self.planEndDate = ko.observable();
	
	self.bureaus = ko.observableArray();
	
	self.startBureaus = ko.observableArray();
	
	self.charts = ko.observableArray();
	 
	self.highlingFlags = highlingFlags;
	
	self.unitCreateFlags = unitCreateFlags; 
	
	self.filterCheckFlag = ko.observable(0);
	
	self.filterUnitCreateFlag = ko.observable(0);
		
	self.checkFlags = checkFlags;
	
	self.highlingFlag = ko.observable();
	
	self.checkFlag = ko.observable(); 
	 
	self.unitCreateFlag = ko.observable(); 
	
	self.bureau = ko.observable();
	 
	self.chart =  ko.observable();
	
	self.startDay = ko.observable();
	
	self.startBureau = ko.observable();
	
	self.filterTrainNbr = ko.observable();
	
	self.showCrossMap = ko.observable(0);
	
	self.shortNameFlag = ko.observable(2);
	
	self.loadBureau = function(bureaus){   
		for ( var i = 0; i < bureaus.length; i++) {  
			self.bureaus.push(new BureausRow(bureaus[i])); 
			self.startBureaus.push(new BureausRow(bureaus[i]));  
		} 
	}; 
	
	self.loadChats = function(charts){   
		for ( var i = 0; i < charts.length; i++) {   
			self.charts.push({"chartId": charts[i].schemeId, "name": charts[i].schemeName});  
		} 
	}; 
	
}

function BureausRow(data) {
	var self = this;  
	self.shortName = data.ljjc;   
	self.code = data.ljpym;   
	//方案ID 
} 

 

function CrossRow(data) {
	var self = this; 
	
	if(data == null){
		return ;
	}
	
	self.visiableRow =  ko.observable(true); 
	
	self.updateFlag = ko.observable(false); 
	
	self.selected =  ko.observable(0); 
	
	self.baseCrossId = data.baseCrossId; 
	
	self.crossId = data.crossId; 
	
	self.shortNameFlag =  ko.observable(true);
	
	self.planCrossId = ko.observable(data.planCrossId);  
	
	self.highLineCrossId = ko.observable(data.highLineCrossId);  
	
	self.vehicle1 = ko.observable(data.vehicle1);   
	
	self.vehicle2 = ko.observable(data.vehicle2);   
	 
	self.crossName = ko.observable(data.planCrossName == null ? data.crossName : data.planCrossName);   
	
	self.shortName = ko.computed(function(){
		if(data.planCrossName != null){
			trainNbrs = data.planCrossName.split('-');
			if(trainNbrs.length > 2){
				return trainNbrs[0] + '-......-' + trainNbrs[trainNbrs.length-1];
			}else{
				return data.planCrossName;
			}
		}else{
			return "";
		}
		
	});  
	
	self.checkFlag = ko.observable(data.checkType);
	
	self.unitCreateFlag = ko.observable(data.unitCreateFlag);
	
	self.startStn = ko.observable(data.crossStartStn);
	self.endStn = ko.observable(data.crossEndStn);
	//方案ID
	self.chartId = ko.observable(data.chartId);
	self.chartName = ko.observable(data.chartName);
	self.crossStartDate = ko.observable(data.crossStartDate);
	self.crossEndDate = ko.observable(data.crossEndDate);
	self.crossSpareName = ko.observable(data.crossSpareName);
	self.alterNateDate = ko.observable(data.alterNateDate);
	self.alterNateTranNbr = ko.observable(data.alterNateTranNbr);
	self.spareFlag = ko.observable(data.spareFlag);
	self.cutOld = ko.observable(data.cutOld);
	self.groupTotalNbr = ko.observable(data.groupTotalNbr);
	self.pairNbr = ko.observable(data.pairNbr);
	self.highlineFlag = ko.observable(data.highlineFlag);
	self.highlineRule = ko.observable(data.highlineRule);
	self.commonlineRule = ko.observable(data.commonlineRule);
	self.appointWeek = ko.observable(data.appointWeek);
	self.appointDay = ko.observable(data.appointDay);
	self.appointPeriod = ko.observable(data.appointPeriod); 
	self.crossSection = ko.observable(data.crossSection);
	self.throughline = ko.observable(data.throughline);
	self.startBureau = ko.observable(data.startBureau); 
	//车辆担当局 
	self.tokenVehBureau = ko.observable(data.tokenVehBureau); 
	//车辆担当局 
	self.tokenVehBureauShowValue = ko.computed(function(){ 
			var result = "";
			 if(data.tokenVehBureau != null && data.tokenVehBureau != "null"){
				 var bs = data.tokenVehBureau.split("、"); 
				 result = data.tokenVehBureau;
				 for(var j = 0; j < bs.length; j++){
					 for(var i = 0; i < gloabBureaus.length; i++){
						 if(bs[j] == gloabBureaus[i].code){
							 result = result.replace(bs[j], gloabBureaus[i].shortName);
							 break;
						 };
					 };
				 }; 
			 }
			 return result; 
	});
	
	self.relevantBureauShowValue =  ko.computed(function(){ 
		var result = "";
		 if(data.relevantBureau != null && data.relevantBureau != "null"){  
			 for(var j = 0; j < data.relevantBureau.length; j++){
				 for(var i = 0; i < gloabBureaus.length; i++){
					 if(data.relevantBureau.substring(j, j + 1) == gloabBureaus[i].code){
						 result += result == "" ? gloabBureaus[i].shortName : "、" + gloabBureaus[i].shortName;
						 break;
					 };
				 };
			 }; 
		 } 
		 return  result; 
	});
	
	self.relevantBureau =  ko.observable(data.relevantBureau);
	
	self.checkedBureau = ko.observable(data.checkedBureau);
	
	self.checkedBureauShowValue =  ko.computed(function(){ 
		var result = "";
		 if(self.checkedBureau() != null && self.checkedBureau() != "null"){  
			 var bs = self.checkedBureau().split(","); 
			 for(var j = 0; j < bs.length; j++){
				 for(var i = 0; i < gloabBureaus.length; i++){
					 if(bs[j] == gloabBureaus[i].code){
						 result += result == "" ? gloabBureaus[i].shortName : "、" + gloabBureaus[i].shortName;
						 break;
					 };
				 };
			 };
		 } 
		 return result; 
	}); 
	
	self.activeFlag = ko.computed(function(){
		return hasActiveRole(data.tokenVehBureau);
	});   
	
	self.checkActiveFlag = ko.computed(function(){
		return data.relevantBureau != null ? (data.relevantBureau.indexOf(currentUserBureau) > -1 ? 1 : 0) : 0;
	});  
	 
	self.tokenVehDept = ko.observable(data.tokenVehDept);
	self.tokenVehDepot = ko.observable(data.tokenVehDepot);
	self.tokenPsgBureau = ko.observable(data.tokenPsgBureau);
	self.tokenPsgBureauShowValue = ko.computed(function(){ 
		var result = "";
		 if(data.tokenPsgBureau != null && data.tokenPsgBureau != "null"){
			 var bs = data.tokenPsgBureau.split("、"); 
			 result = data.tokenPsgBureau;
			 for(var j = 0; j < bs.length; j++){
				 for(var i = 0; i < gloabBureaus.length; i++){
					 if(bs[j] == gloabBureaus[i].code){
						 result = result.replace(bs[j], gloabBureaus[i].shortName);
						 break;
					 };
				 };
			 }; 
		 }
		 return result; 
	});
	self.tokenPsgDept = ko.observable(data.tokenPsgDept);
	self.tokenPsgDepot = ko.observable(data.tokenPsgDepot);
	self.locoType = ko.observable(data.locoType);
	self.crhType = ko.observable(data.crhType);
	self.elecSupply = ko.observable(data.elecSupply);
	self.dejCollect = ko.observable(data.dejCollect);
	self.airCondition = ko.observable(data.airCondition);
	self.note = ko.observable(data.note);  
};

function TrainModel() {
	var self = this;
	self.rows = ko.observableArray();
	self.rowLookup = {};
}


function HighLineTrain(data){
	var self = this; 

	self.trainNbr = data.trainNbr;
	self.startStn = data.startStn;
	self.startTime = data.startTime;
	self.endTime = data.endTime;
	self.endStn = data.endStn; 
}

function TrainRow(data) {
	var self = this; 
	self.planTainId  = data.planTainId;//BASE_CROSS_TRAIN_ID
	self.crossId = data.crossId;//BASE_CROSS_ID
	self.trainSort = ko.observable(data.trainSort);//TRAIN_SORT
	self.baseTrainId = data.baseTrainId;
	self.trainNbr = data.trainNbr;//TRAIN_NBR
	self.startStn = data.startStn;//START_STN
	self.times = ko.observableArray(); 
	self.simpleTimes = ko.observableArray(); 
	
	 
	self.sourceTime = 
	self.passBureau = data.passBureau;
	
	self.startTime = ko.computed(function(){  
		return self.times().length > 0 ? self.times()[0].sourceTime : ""; 
	});;
	//结束日期（该日历交路最后一个车次的终到日期）
	self.endTime = ko.computed(function(){  
		return self.times().length > 0 ? self.times()[self.times().length - 1].targetTime : ""; 
	});
	
	self.trainNbr = data.trainNbr;
	self.startStn = ko.computed(function(){  
		return self.times().length > 0 ? self.times()[0].stnName : ""; 
	});;
	self.endStn = ko.computed(function(){  
		return self.times().length > 0 ? self.times()[self.times().length - 1].stnName : ""; 
	});
	 
 
	
	
	self.loadTimes = function(times){
		$.each(times, function(i, n){ 
			var timeRow = new TrainTimeRow(n);
			self.times.push(timeRow);
			if(n.stationFlag != 'BTZ'){
				self.simpleTimes.push(timeRow);
			}
		});
	}; 
} ;
function filterValue(value){
	return value == null || value == "null" ? "--" : value;
};

function TrainRunPlanRow(data){
	var self = this; 
	self.trainNbr = data.trainNbr;
	self.runPlans =  ko.observableArray();
	
	if(data.runPlans !=null){
		$.each(data.runPlans, function(i, n){
			self.runPlans.push(new RunPlanRow(n));
		});
	}
}

function RunPlanRow(data){
	var self = this; 
	self.color = ko.observable("gray");
	self.runFlag = ko.computed(function(){ 
		switch (data.runFlag) {
		case '0':
			self.color("gray");
			return "停";
			break;
		case '1':
			self.color("green");
			return "开";
			break;
		case '2':
			self.color("blue");
			return "备";
			break;
		default: 
			return '';
			break;
		}
	}); 
}

function TrainTimeRow(data) { 
	var self = this;   
	self.stnSort = parseInt(data.stnSort) + 1; 
	self.stnName = filterValue(data.stnName);
	self.bureauShortName = filterValue(data.bureauShortName);
	self.sourceTime = filterValue(data.arrTime);
	self.targetTime = filterValue(data.dptTime);
	self.stepStr = GetDateDiff(data); 
	self.trackName = filterValue(data.trackName);  
	self.runDays = data.runDays;
	self.stationFlag = data.stationFlag;
	 
}; 

function timeCompare(preTargetTime, curSourceTime){
	 
	var preEndTime = Date.parse(preTargetTime);
	var curStartTime = Date.parse(curSourceTime);
	if(preEndTime > curStartTime){
		return false;
	}
	return true;
};

function GetDateDiff(data)
{ 
	 if(data.childIndex == 0 
			 || data.dptTime == '-' 
			 || data.dptTime == null 
			 || data.arrTime == null
			 || data.arrTime == '-'){
		return "";
	} 
	var startTime = new Date(data.arrTime);
	var endTime = new Date(data.dptTime);  
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
	 
	return result == "" ? "" : result; 
};
function openLogin() {
	$("#file_upload_dlg").dialog("open");
}
 