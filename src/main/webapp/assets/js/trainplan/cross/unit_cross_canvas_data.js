//界面测试专用
var dateArray = [];//[{runDate:"2014-05-10"},{runDate:"2014-05-11"}]
for(var i=0;i<4;i++) {
	dateArray.push({
//			dayIndex: i,
		runDate:"2014-05-1"+i
	});
};






/*==================================================
 * x\y坐标请求接口返回数据格式
 */
var gridData = {
//		code : "0",	//非0即接口内部异常
//		message:"",
//		data:{
			days:dateArray,//[{runDate:"2014-05-10"},{runDate:"2014-05-11"}]
			crossStns:[{stnName:"成都"},{stnName:"遂宁"},{stnName:"南充"},{stnName:"蓬安"},{stnName:"营山"},{stnName:"土溪"},{stnName:"达州"},{stnName:"安康"},{stnName:"华山"},{stnName:"北京西"}]
//		}
};





/*==================================================
 *按交路绘图请求接口返回数据格式
 */
var myJlData = 
	[{
		    	  crossName:"K818-K817-1",
		    	  jxgx:[//接续关系数组
		    	        {fromStnName:"北京西",fromTime:"2014-05-11 21:07",toStnName:"北京西",toTime:"2014-05-12 08:35"}

		    	        ],
		    	  trains:[//该交路下所有车次数组
		    	          {
		    	        	  trainName:"K818",
		    				  startStn:"成都",//始发站名 为null时填入""（空串）
		    				  endStn:"北京西",//终到站名为null时填入""（空串）
		    				  trainStns:[
		    						{runDays:0,stnName:"成都",arrTime:"2014-05-10 19:18",dptTime:"2014-05-10 19:18",stayTime:0},
		    						{runDays:0,stnName:"遂宁",arrTime:"2014-05-10 21:19",dptTime:"2014-05-10 21:33",stayTime:14},
		    						{runDays:0,stnName:"南充",arrTime:"2014-05-10 22:15",dptTime:"2014-05-10 22:22",stayTime:7},
		    						{runDays:0,stnName:"蓬安",arrTime:"2014-05-10 22:49",dptTime:"2014-05-10 22:54",stayTime:5},
		    						{runDays:0,stnName:"营山",arrTime:"2014-05-10 23:06",dptTime:"2014-05-10 23:11",stayTime:5},
		    						{runDays:1,stnName:"达州",arrTime:"2014-05-11 00:13",dptTime:"2014-05-11 00:23",stayTime:10},
		    						{runDays:1,stnName:"安康",arrTime:"2014-05-11 03:12",dptTime:"2014-05-11 03:32",stayTime:20},
		    						{runDays:1,stnName:"华山",arrTime:"2014-05-11 07:45",dptTime:"2014-05-11 07:51",stayTime:6},
		    						{runDays:1,stnName:"北京西",arrTime:"2014-05-11 21:07",dptTime:"2014-05-11 21:07",stayTime:0}
		    				  ]
		    	          },
		    	          {
		    	        	  trainName:"K817",
		    	          	  startStn:"北京西",//始发站名 为null时填入""（空串）
		    	          	  endStn:"成都",//终到站名为null时填入""（空串）
		    	        	  trainStns:[
									{runDays:0,stnName:"北京西",arrTime:"2014-05-12 08:35",dptTime:"2014-05-12 08:35",stayTime:0},
									{runDays:0,stnName:"华山",arrTime:"2014-05-12 23:07",dptTime:"2014-05-12 23:13",stayTime:6},
									{runDays:1,stnName:"安康",arrTime:"2014-05-13 03:59",dptTime:"2014-05-13 04:13",stayTime:14},
									{runDays:1,stnName:"达州",arrTime:"2014-05-13 07:29",dptTime:"2014-05-13 07:35",stayTime:6},
									{runDays:1,stnName:"土溪",arrTime:"2014-05-13 08:09",dptTime:"2014-05-13 08:13",stayTime:4},
									{runDays:1,stnName:"营山",arrTime:"2014-05-13 08:45",dptTime:"2014-05-13 08:49",stayTime:4},
									{runDays:1,stnName:"蓬安",arrTime:"2014-05-13 09:02",dptTime:"2014-05-13 09:05",stayTime:3},
									{runDays:1,stnName:"南充",arrTime:"2014-05-13 09:32",dptTime:"2014-05-13 09:36",stayTime:4},
									{runDays:1,stnName:"遂宁",arrTime:"2014-05-13 10:30",dptTime:"2014-05-13 10:33",stayTime:3},
									{runDays:1,stnName:"成都",arrTime:"2014-05-13 12:30",dptTime:"2014-05-13 12:30",stayTime:0}
		    	        	  ]
		    	          }
		    	  ]
		      }
		      //end 一条交路
		      
		      
		
		];



var canvasData = {};
canvasData.grid = gridData;
canvasData.jlData = myJlData;//交路数据