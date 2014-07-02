var HightLineCrewSjPage = function () {
	/**
	 * 页面加载时执行
	 * public
	 */
	this.initPage = function () {
		$("#crew_input_rundate").datepicker();
		
		//页面元素绑定
		var pageModel = new PageModel();
		ko.applyBindings(pageModel);
		
		pageModel.initPageData();
	};
	
	
	
	
	
	/**
	 * 查询model
	 * 
	 * private
	 */
	function SearchModle(){
		
		var _self = this;
		
		/**
		 * 查询条件
		 */
		_self.runDate =  ko.observable();		//日期
		_self.trainNbr = ko.observable();		//车次号
		

		
		_self.initData = function() {
			_self.runDate(currdate());
		};
		
		//currentIndex 
		function currdate(){
			var d = new Date();
			var year = d.getFullYear();    //获取完整的年份(4位,1970-????)
			var month = d.getMonth()+1;       //获取当前月份(0-11,0代表1月)
			var days = d.getDate(); 
			month = ("" + month).length == 1 ? "0" + month : month;
			days = ("" + days).length == 1 ? "0" + days : days;
			return year+"-"+month+"-"+days;
		};
		
	};
	
	
	
	/**
	 * 乘务计划model用于新增、修改
	 */
	function HighlineCrewModel() {
		var _self = this;
		_self.crewHighlineId = ko.observable();
		_self.crewDate = ko.observable();
		_self.crewBureau = ko.observable();
		_self.crewType = ko.observable();
		_self.crewCross = ko.observable();
		_self.crewGroup = ko.observable();
		_self.throughLine = ko.observable();
		_self.name1 = ko.observable();
		_self.tel1 = ko.observable();
		_self.identity1 = ko.observable();
		_self.name2 = ko.observable();
		_self.tel2 = ko.observable();
		_self.identity2 = ko.observable();
		_self.note = ko.observable();
		_self.recordPeople = ko.observable();
		_self.recordPeopleOrg = ko.observable();
		_self.recordTime = ko.observable();
		_self.submitType = ko.observable();
		
		_self.update = function (obj) {
			if (obj == null) {
				_self.crewHighlineId("");
				_self.crewDate("");
				_self.crewBureau("");
				_self.crewType("");
				_self.crewCross("");
				_self.crewGroup("");
				_self.throughLine("");
				_self.name1("");
				_self.tel1("");
				_self.identity1("");
				_self.name2("");
				_self.tel2("");
				_self.identity2("");
				_self.note("");
				_self.recordPeople("");
				_self.recordPeopleOrg("");
				_self.recordTime("");
				_self.submitType("");
			} else {
				_self.crewHighlineId(obj.crewHighlineId);
				_self.crewDate(obj.crewDate);
				_self.crewBureau(obj.crewBureau);
				_self.crewType(obj.crewType);
				_self.crewCross(obj.crewCross);
				_self.crewGroup(obj.crewGroup);
				_self.throughLine(obj.throughLine);
				_self.name1(obj.name1);
				_self.tel1(obj.tel1);
				_self.identity1(obj.identity1);
				_self.name2(obj.name2);
				_self.tel2(obj.tel2);
				_self.identity2(obj.identity2);
				_self.note(obj.note);
				_self.recordPeople(obj.recordPeople);
				_self.recordPeopleOrg(obj.recordPeopleOrg);
				_self.recordTime(obj.recordTime);
				_self.submitType(obj.submitType);
			}
		};
	};
	
	
	
	/**
	 * 开行计划列表行数据model
	 * @param palnTrainObj
	 */
	function PlanTrainRowModel(palnTrainObj) {
		this.trainNbr = ko.observable(palnTrainObj.trainNbr);
		this.startStn = ko.observable(palnTrainObj.startStn);
		this.startTimeStr = ko.observable(moment(palnTrainObj.startTimeStr).format("YYMMDD HH:mm"));
		this.endStn = ko.observable(palnTrainObj.endStn);
		this.endTimeStr = ko.observable(moment(palnTrainObj.endTimeStr).format("YYMMDD HH:mm"));
		this.isMatch = ko.observable(palnTrainObj.isMatch);	//是否已上报车长乘务计划	1：真 0：假
	};
	
	
	
	/**
	 * 页面元素绑定对象
	 * 
	 * private
	 */
	function PageModel() {
		var _self = this;
		_self.queryBtnCount = ko.observable(0);	//查询按钮点击次数 
		_self.searchModle = ko.observable(new SearchModle());		//页面查询对象
		_self.planTrainRows = new PageModle(5000, loadPlanDataForPage);		//页面开行计划列表对象
		_self.hightLineCrewRows = new PageModle(10000, loadHightLineCrewSjDataForPage);		//页面车长乘务计划列表对象
		_self.hightLineCrewModel = ko.observable(new HighlineCrewModel());	//用于乘务计划新增、修改
		_self.hightLineCrewModelTitle = ko.observable();	//用于乘务计划新增、修改窗口标题
		_self.hightLineCrewSaveFlag = ko.observable();		//用于乘务计划新增、修改标识
		
		
		/**
		 * 初始化查询条件
		 */
		_self.initPageData = function() {
			_self.searchModle().initData();
		};
		

		/**
		 * 查询按钮事件
		 */
		_self.queryList = function(){
			commonJsScreenLock(2);
			//1.查询开行计划
			_self.planTrainRows.loadRows();	//loadRows为分页组件中方法

			//2.查询车长乘务计划信息
			_self.hightLineCrewRows.loadRows();	//loadRows为分页组件中方法
			
			_self.queryBtnCount += 1;	//查询按钮点击次数+1
		};
		
		
		/**
		 * 检验按钮事件
		 * 检测车次是否上报车长乘务计划
		 * 
		 * 当planTrainRows中车次存在于hightLineCrewRows中乘务交路 则视为该车次已上报
		 */
		_self.checkCrew = function() {
			
			for(var i=0; i<_self.planTrainRows.rows().length;i++) {
				var _trainNbr = _self.planTrainRows.rows()[i].trainNbr();
				for(var j=0; j<_self.hightLineCrewRows.rows().length;j++) {
					var crewCrossArray = _self.hightLineCrewRows.rows()[j].crewCross.split("-");
					
					if($.inArray(_trainNbr, crewCrossArray) > -1) {
						_self.planTrainRows.rows()[i].isMatch("1");//车次匹配  已上报车长乘务计划 
						break;
					}
				}
			}
		};
		
		
		/**
		 * 检验按钮是否允许点击
		 * 
		 * 当查询按钮点击次数 ==0 或开行计划list==0时  按钮不可用
		 */
		_self.checkAndSendBtnEnable = ko.computed(function() {
			//查询按钮点击次数 
	        if(_self.queryBtnCount == 0 || _self.planTrainRows.rows().length==0) {
	            return false;
	        }
	        
	        return true;
	    });
		
		
		/**
		 * 新增页面打开时
		 */
		_self.onAddOpen = function() {
			_self.hightLineCrewSaveFlag("add");
			_self.hightLineCrewModelTitle("新增车长乘务计划");
			_self.hightLineCrewModel().update(null);
		};
		
		
		

		/**
		 * 修改页面打开时
		 */
		_self.onEditOpen = function() {
			_self.hightLineCrewSaveFlag("update");
			_self.hightLineCrewModelTitle("修改车长乘务计划");
			var currentCrewHighlineId = "";
			$("[name='crew_checkbox']").each(function(){
				if($(this).is(":checked")) {
					currentCrewHighlineId = $(this).val();
					return false; //跳出循环
				}
		    });
			
			if (currentCrewHighlineId == "") {
				showWarningDialog("请选择一条乘务计划记录");
				return;
			}
			
			
			commonJsScreenLock();
			$.ajax({
				url : basePath+"/crew/highline/getHighLineCrew",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({
					crewHighLineId : currentCrewHighlineId
				}),
				success : function(result) {
					if (result != null && typeof result == "object" && result.code == "0") {
						_self.hightLineCrewModel().update(result.data);
					} else {
						showErrorDialog("获取车长乘务计划信息失败");
					};
				},
				error : function() {
					showErrorDialog("获取车长乘务计划信息失败");
				},
				complete : function(){
					commonJsScreenUnLock();
				}
			});
			
			
		};
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * 删除
		 */
		_self.deleteHightLineCrew = function() {
			var currentCrewHighlineId = "";
			$("[name='crew_checkbox']").each(function(){
				if($(this).is(":checked")) {
					currentCrewHighlineId += "'"+$(this).val()+"',";
					//return false; //跳出循环
				}
		    });
			
			if (currentCrewHighlineId == "") {
				showWarningDialog("请选择一条乘务计划记录");
				return;
			} else {
				currentCrewHighlineId = currentCrewHighlineId.substring(0, currentCrewHighlineId.lastIndexOf(","));
			}

			
			commonJsScreenLock(2);
			$.ajax({
				url : basePath+"/crew/highline/deleteHighLineCrewInfo",
				cache : false,
				type : "DELETE",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({
					crewHighLineId : currentCrewHighlineId
				}),
				success : function(result) {
					if (result != null && typeof result == "object" && result.code == "0") {
						//2.查询车长乘务计划信息
						_self.hightLineCrewRows.loadRows();	//loadRows为分页组件中方法
						showSuccessDialog("成功删除车长乘务计划信息");
					} else {
						commonJsScreenUnLock(1);
						showErrorDialog("删除车长乘务计划信息失败");
					};
				},
				error : function() {
					commonJsScreenUnLock(1);
					showErrorDialog("删除车长乘务计划信息失败");
				},
				complete : function(){
					commonJsScreenUnLock(1);
				}
			});
			
			
		};
		
		
		

		/**
		 * 发布按钮事件
		 */
		_self.sendCrew = function(){
			commonJsScreenLock(2);
			$.ajax({
				url : basePath+"/crew/highline/updateSubmitType",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({
					crewType : "1",//乘务类型（1车长、2车长、3机械师）
					crewDate : $("#crew_input_rundate").val()//_self.searchModle().runDate()
				}),
				success : function(result) {
					if (result != null && typeof result == "object" && result.code == "0") {
						//2.查询车长乘务计划信息
						_self.hightLineCrewRows.loadRows();	//loadRows为分页组件中方法
						showSuccessDialog("提交成功");
					} else {
						commonJsScreenUnLock(1);
						showErrorDialog("提交车长乘务计划信息失败");
					};
				},
				error : function() {
					commonJsScreenUnLock(1);
					showErrorDialog("提交车长乘务计划信息失败");
				},
				complete : function(){
					commonJsScreenUnLock(1);
				}
			});
			
		};
		
		

		/**
		 * 新增/修改乘务计划
		 */
		_self.saveHightLineCrew = function(){
			commonJsScreenLock(2);
			
			var _url = "";
			var _type = "";
			if (_self.hightLineCrewSaveFlag() == "add") {
				_url = basePath+"/crew/highline/add";
				_type = "POST";
			} else if (_self.hightLineCrewSaveFlag() == "update") {
				_url = basePath+"/crew/highline/update";
				_type = "PUT";
			}
			
			$.ajax({
				url : _url,
				cache : false,
				type : _type,
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({
					crewHighlineId : _self.hightLineCrewModel().crewHighlineId(),
					crewType : "1",//乘务类型（1车长、2车长、3机械师）
					crewDate : $("#crew_input_rundate").val(),//_self.searchModle().runDate(),
					crewCross : _self.hightLineCrewModel().crewCross(),
					crewGroup : _self.hightLineCrewModel().crewGroup(),
					throughLine : _self.hightLineCrewModel().throughLine(),
					name1 : _self.hightLineCrewModel().name1(),
					tel1 : _self.hightLineCrewModel().tel1(),
					identity1 : _self.hightLineCrewModel().identity1(),
					name2 : _self.hightLineCrewModel().name2(),
					tel2 : _self.hightLineCrewModel().tel2(),
					identity2 : _self.hightLineCrewModel().identity2(),
					note : _self.hightLineCrewModel().note()
				}),
				success : function(result) {
					if (result != null && typeof result == "object" && result.code == "0") {
						//2.查询车长乘务计划信息
						_self.hightLineCrewRows.loadRows();	//loadRows为分页组件中方法
						showSuccessDialog("保存成功");
					} else {
						commonJsScreenUnLock(1);
						showErrorDialog("保存车长乘务计划信息失败");
					};
				},
				error : function() {
					commonJsScreenUnLock(1);
					showErrorDialog("保存车长乘务计划信息失败");
				},
				complete : function(){
					commonJsScreenUnLock(1);
				}
			});
		};
		
		
		
		
		
		
		/**
		 * 分页查询开行计划列表
		 */
		function loadPlanDataForPage(startIndex, endIndex) {
			var _runDate = $("#crew_input_rundate").val();//_self.searchModle().runDate();	//运行日期
			var _trainNbr =_self.searchModle().trainNbr()!="undefined"?_self.searchModle().trainNbr():"";//车次
			
			
			if(_runDate == null || typeof _runDate!="string"){
				showErrorDialog("请选择日期!");
				commonJsScreenUnLock();
				return;
			}
			
			$.ajax({
				url : basePath+"/crew/highline/getRunLineListForRunDate",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({
					runDate : _runDate,
					trainNbr : _trainNbr,
					rownumstart : startIndex, 
					rownumend : endIndex
				}),
				success : function(result) {
 
					if (result != null && typeof result == "object" && result.code == "0") {
						var rows = [];
						if(result.data.data != null){
							$.each(result.data.data,function(n, obj){
//								var row = new PlanTrainRowModel(obj);
//								row.isMatch("0");
								obj.isMatch = "0";
								rows.push(new PlanTrainRowModel(obj));
							});
							_self.planTrainRows.loadPageRows(result.data.totalRecord, rows);
						}
						
					} else {
						showErrorDialog("获取开行计划信息失败");
					};
				},
				error : function() {
					showErrorDialog("获取开行计划信息失败");
				},
				complete : function(){
					commonJsScreenUnLock(1);
				}
			});
		};
		
		
		
		/**
		 * 分页查询车长乘务计划列表
		 */
		function loadHightLineCrewSjDataForPage(startIndex, endIndex) {
			
			var _runDate = $("#crew_input_rundate").val();//_self.searchModle().runDate();	//运行日期
			var _trainNbr = _self.searchModle().trainNbr();	//车次
			
			
			if(_runDate == null || typeof _runDate!="string"){
				showErrorDialog("请选择日期!");
				commonJsScreenUnLock();
				return;
			}
			
			$.ajax({
				url : basePath+"/crew/highline/getHighlineCrewListForRunDate",
				cache : false,
				type : "POST",
				dataType : "json",
				contentType : "application/json",
				data :JSON.stringify({
					crewType : "1",	//乘务类型（1车长、2车长、3机械师）
					crewDate : _runDate,
					trainNbr : _trainNbr,
					rownumstart : startIndex, 
					rownumend : endIndex
				}),
				success : function(result) {
 
					if (result != null && typeof result == "object" && result.code == "0") {
						var rows = [];
						if(result.data.data != null){
							$.each(result.data.data,function(n, obj){
								if (obj.submitType != null && obj.submitType==0) {
									obj.submitTypeStr = "<span class='label label-danger'>未</span>";
								} else if (obj.submitType != null && obj.submitType==1) {
									obj.submitTypeStr = "<span class='label label-success'>已</span>";
								} else {
									obj.submitTypeStr = "";
								}
								rows.push(obj);
							});
							_self.hightLineCrewRows.loadPageRows(result.data.totalRecord, rows);
						}
						 
					} else {
						showErrorDialog("获取车长乘务计划信息失败");
					};
				},
				error : function() {
					showErrorDialog("获取车长乘务计划信息失败");
				},
				complete : function(){
					commonJsScreenUnLock(1);
				}
			});
		};
		
		
		
		
		/**
		 * 导出excel
		 */
		_self.exportExcel = function() {
			var _runDate = $("#crew_input_rundate").val();//_self.searchModle().runDate();	//运行日期
			var _trainNbr = _self.searchModle().trainNbr();	//车次
			if(_trainNbr=="undefined" || _trainNbr=="" || typeof _trainNbr=="undefined") {
				_trainNbr = "-1";
			}
			
			if(_runDate == null || typeof _runDate!="string" || _runDate==""){
				showErrorDialog("请选择日期!");
				return;
			}
			window.open(basePath+"/crew/highline/exportExcel/1/"+_runDate+"/"+_trainNbr);
		};
		
		
		
		/**
		 * 导入excel点击事件
		 * 
		 * 检查导入条件
		 */
		_self.checkBeforImportExcel = function() {
			var _runDate = $("#crew_input_rundate").val();//_self.searchModle().runDate();	//运行日期
			if(_runDate == null || typeof _runDate!="string" || _runDate==""){
				showErrorDialog("请选择日期!");
				return;
			}
		};
		
		
		/**
		 * 导入excel
		 */
		_self.uploadExcel = function() {
			var _runDate = $("#crew_input_rundate").val();//_self.searchModle().runDate();	//运行日期
			if(_runDate == null || typeof _runDate!="string" || _runDate==""){
				showErrorDialog("请选择日期!");
				return;
			}
		    if($("#crewExcelFile").val() == null || $("#crewExcelFile").val() == ""){
		    	showErrorDialog("没有可导入的文件"); 
		    	return;
		    }
		    $("#loading").show();
		    $("#btn_fileToUpload").attr("disabled", "disabled");
	        $.ajaxFileUpload ({
                url : basePath+"/crew/highline/importExcel",
                secureuri:false,
                fileElementId:'crewExcelFile',
                type : "POST",
                dataType: 'json',  
                data:{
                	crewDate:_runDate,
                	crewType:"1"//乘务类型（1车长、2车长、3机械师）
                },
                success: function (data, status) {
					_self.hightLineCrewRows.loadRows();	//loadRows为分页组件中方法
                	
                	showSuccessDialog("导入成功");
                	$("#loading").hide();
                	$("#btn_fileToUpload").removeAttr("disabled");
                	$("#btn_fileToUpload_cancel").click();
                	
                },
                error: function(result){
                	showErrorDialog("导入失败");
                	$("#loading").hide();
                	$("#btn_fileToUpload").removeAttr("disabled");
                }
            });  
	        return true;
		};
		
		
	};
	
	
	
	
	
	
	
};




$(function() {
	new HightLineCrewSjPage().initPage();
});