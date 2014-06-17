//界面测试专用
var dateArray = [];//[{runDate:"20140510",runDateText:"2014-06-10"},{runDate:"20140511",runDateText:"2014-06-11"}]
for(var i=0;i<7;i++) {
	dateArray.push({
//			dayIndex: i,
		runDate:"2014-06-1"+i
	});
};






/*==================================================
 * x\y坐标请求接口返回数据格式
 */
var gridData = {
			days:dateArray,//[{runDate:"20140510",runDateText:"2014-06-10"},{runDate:"20140511",runDateText:"2014-06-11"}]
			crossStns:[{stnName:"成都"},
			           {stnName:"遂宁"},
			           {stnName:"南充"},
			           {stnName:"蓬安"},
			           {stnName:"营山"},
			           {stnName:"土溪"},
			           {stnName:"达州"},
			           {stnName:"安康"},
			           {stnName:"华山"},
			           {stnName:"三门峡"},
			           {stnName:"洛阳"},
			           {stnName:"郑州"},
			           {stnName:"新乡"},
			           {stnName:"安阳"},
			           {stnName:"卫辉",isCurrentBureau:1},
			           {stnName:"邯郸",isCurrentBureau:1},
			           {stnName:"邢台",isCurrentBureau:1},
			           {stnName:"石家庄",isCurrentBureau:1},
			           {stnName:"定州",isCurrentBureau:1},
			           {stnName:"保定",isCurrentBureau:1},
			           {stnName:"高碑店",isCurrentBureau:1},
			           {stnName:"北京西",isCurrentBureau:1}
			]
};


/*==================================================
 *按交路绘图请求接口返回数据格式
 */
var myJlData = 
			[
		      {
		    	  crossName:"K818-K817-1",
		    	  jxgx:[//接续关系数组
		    	        {fromStnName:"北京西",fromTime:"2014-06-11 21:07",toStnName:"北京西",toTime:"2014-06-12 08:35"},
  		    	        {fromStnName:"成都",fromTime:"2014-06-13 12:30",toStnName:"成都",toTime:"2014-06-13 19:18"},
		    	        {fromStnName:"北京西",fromTime:"2014-06-14 21:07",toStnName:"北京西",toTime:"2014-06-15 08:35"}

		    	        ],
		    	  trains:[//该交路下所有车次数组
		    	          {
		    	        	  trainName:"K818",
		    				  startStn:"成都",//始发站名 为null时填入""（空串）
		    				  endStn:"北京西",//终到站名为null时填入""（空串）
		    				  trainStns:[
		    						{runDays:0,stnName:"成都",arrTime:"2014-06-10 19:18",dptTime:"2014-06-10 19:18",stayTime:0},
		    						{runDays:0,stnName:"遂宁",arrTime:"2014-06-10 21:19",dptTime:"2014-06-10 21:33",stayTime:14},
		    						{runDays:0,stnName:"南充",arrTime:"2014-06-10 22:15",dptTime:"2014-06-10 22:22",stayTime:7},
		    						{runDays:0,stnName:"蓬安",arrTime:"2014-06-10 22:49",dptTime:"2014-06-10 22:54",stayTime:5},
		    						{runDays:0,stnName:"营山",arrTime:"2014-06-10 23:06",dptTime:"2014-06-10 23:11",stayTime:5},
		    						{runDays:1,stnName:"达州",arrTime:"2014-06-11 00:13",dptTime:"2014-06-11 00:23",stayTime:10},
		    						{runDays:1,stnName:"安康",arrTime:"2014-06-11 03:12",dptTime:"2014-06-11 03:32",stayTime:20},
		    						{runDays:1,stnName:"华山",arrTime:"2014-06-11 07:45",dptTime:"2014-06-11 07:51",stayTime:6},
		    						{runDays:1,stnName:"三门峡",arrTime:"2014-06-11 09:39",dptTime:"2014-06-11 09:41",stayTime:2},
		    						{runDays:1,stnName:"洛阳",arrTime:"2014-06-11 11:20",dptTime:"2014-06-11 11:23",stayTime:3},
		    						{runDays:1,stnName:"郑州",arrTime:"2014-06-11 12:54",dptTime:"2014-06-11 13:08",stayTime:14},
		    						{runDays:1,stnName:"新乡",arrTime:"2014-06-11 14:02",dptTime:"2014-06-11 14:12",stayTime:10},
		    						{runDays:1,stnName:"安阳",arrTime:"2014-06-11 15:21",dptTime:"2014-06-11 15:23",stayTime:2},
		    						{runDays:1,stnName:"邯郸",arrTime:"2014-06-11 15:58",dptTime:"2014-06-11 16:02",stayTime:4},
		    						{runDays:1,stnName:"邢台",arrTime:"2014-06-11 16:33",dptTime:"2014-06-11 16:37",stayTime:4},
		    						{runDays:1,stnName:"石家庄",arrTime:"2014-06-11 17:48",dptTime:"2014-06-11 17:56",stayTime:8},
		    						{runDays:1,stnName:"定州",arrTime:"2014-06-11 18:43",dptTime:"2014-06-11 18:45",stayTime:2},
		    						{runDays:1,stnName:"保定",arrTime:"2014-06-11 19:22",dptTime:"2014-06-11 19:26",stayTime:4},
		    						{runDays:1,stnName:"北京西",arrTime:"2014-06-11 21:07",dptTime:"2014-06-11 21:07",stayTime:0}
		    				  ]
		    	          },
		    	          {
		    	        	  trainName:"K817",
		    	          	  startStn:"北京西",//始发站名 为null时填入""（空串）
		    	          	  endStn:"成都",//终到站名为null时填入""（空串）
		    	        	  trainStns:[
									{runDays:0,stnName:"北京西",arrTime:"2014-06-12 08:35",dptTime:"2014-06-12 08:35",stayTime:0},
		    						{runDays:0,stnName:"高碑店",arrTime:"2014-06-12 09:29",dptTime:"2014-06-12 09:32",stayTime:3},
		    						{runDays:0,stnName:"保定",arrTime:"2014-06-12 10:10",dptTime:"2014-06-12 10:13",stayTime:3},
		    						{runDays:0,stnName:"石家庄",arrTime:"2014-06-12 11:45",dptTime:"2014-06-12 11:51",stayTime:6},
		    						{runDays:0,stnName:"邢台",arrTime:"2014-06-12 12:54",dptTime:"2014-06-12 13:11",stayTime:17},
		    						{runDays:0,stnName:"邯郸",arrTime:"2014-06-12 13:42",dptTime:"2014-06-12 13:46",stayTime:4},
		    						{runDays:0,stnName:"卫辉",arrTime:"2014-06-12 15:16",dptTime:"2014-06-12 15:29",stayTime:13},
		    						{runDays:0,stnName:"新乡",arrTime:"2014-06-12 15:48",dptTime:"2014-06-12 15:58",stayTime:10},
		    						{runDays:0,stnName:"郑州",arrTime:"2014-06-12 17:03",dptTime:"2014-06-12 17:23",stayTime:20},
		    						{runDays:0,stnName:"洛阳",arrTime:"2014-06-12 18:58",dptTime:"2014-06-12 19:00",stayTime:2},
		    						{runDays:0,stnName:"三门峡",arrTime:"2014-06-12 21:11",dptTime:"2014-06-12 21:16",stayTime:5},
									{runDays:0,stnName:"华山",arrTime:"2014-06-12 23:07",dptTime:"2014-06-12 23:13",stayTime:6},
									{runDays:1,stnName:"安康",arrTime:"2014-06-13 03:59",dptTime:"2014-06-13 04:13",stayTime:14},
									{runDays:1,stnName:"达州",arrTime:"2014-06-13 07:29",dptTime:"2014-06-13 07:35",stayTime:6},
									{runDays:1,stnName:"土溪",arrTime:"2014-06-13 08:09",dptTime:"2014-06-13 08:13",stayTime:4},
									{runDays:1,stnName:"营山",arrTime:"2014-06-13 08:45",dptTime:"2014-06-13 08:49",stayTime:4},
									{runDays:1,stnName:"蓬安",arrTime:"2014-06-13 09:02",dptTime:"2014-06-13 09:05",stayTime:3},
									{runDays:1,stnName:"南充",arrTime:"2014-06-13 09:32",dptTime:"2014-06-13 09:36",stayTime:4},
									{runDays:1,stnName:"遂宁",arrTime:"2014-06-13 10:30",dptTime:"2014-06-13 10:33",stayTime:3},
									{runDays:1,stnName:"成都",arrTime:"2014-06-13 12:30",dptTime:"2014-06-13 12:30",stayTime:0}
		    	        	  ]
		    	          },
		    	          {
		    	        	  trainName:"K818",
		    				  startStn:"成都",//始发站名 为null时填入""（空串）
		    				  endStn:"北京西",//终到站名为null时填入""（空串）
		    				  trainStns:[
		    						{runDays:0,stnName:"成都",arrTime:"2014-06-13 19:18",dptTime:"2014-06-13 19:18",stayTime:0},
		    						{runDays:0,stnName:"遂宁",arrTime:"2014-06-13 21:19",dptTime:"2014-06-13 21:33",stayTime:14},
		    						{runDays:0,stnName:"南充",arrTime:"2014-06-13 22:15",dptTime:"2014-06-13 22:22",stayTime:7},
		    						{runDays:0,stnName:"蓬安",arrTime:"2014-06-13 22:49",dptTime:"2014-06-13 22:54",stayTime:5},
		    						{runDays:0,stnName:"营山",arrTime:"2014-06-13 23:06",dptTime:"2014-06-13 23:11",stayTime:5},
		    						{runDays:1,stnName:"达州",arrTime:"2014-06-14 00:13",dptTime:"2014-06-14 00:23",stayTime:10},
		    						{runDays:1,stnName:"安康",arrTime:"2014-06-14 03:12",dptTime:"2014-06-14 03:32",stayTime:20},
		    						{runDays:1,stnName:"华山",arrTime:"2014-06-14 07:45",dptTime:"2014-06-14 07:51",stayTime:6},
		    						{runDays:1,stnName:"三门峡",arrTime:"2014-06-14 09:39",dptTime:"2014-06-14 09:41",stayTime:2},
		    						{runDays:1,stnName:"洛阳",arrTime:"2014-06-14 11:20",dptTime:"2014-06-14 11:23",stayTime:3},
		    						{runDays:1,stnName:"郑州",arrTime:"2014-06-14 12:54",dptTime:"2014-06-14 13:08",stayTime:14},
		    						{runDays:1,stnName:"新乡",arrTime:"2014-06-14 14:02",dptTime:"2014-06-14 14:12",stayTime:10},
		    						{runDays:1,stnName:"安阳",arrTime:"2014-06-14 15:21",dptTime:"2014-06-14 15:23",stayTime:2},
		    						{runDays:1,stnName:"邯郸",arrTime:"2014-06-14 15:58",dptTime:"2014-06-14 16:02",stayTime:4},
		    						{runDays:1,stnName:"邢台",arrTime:"2014-06-14 16:33",dptTime:"2014-06-14 16:37",stayTime:4},
		    						{runDays:1,stnName:"石家庄",arrTime:"2014-06-14 17:48",dptTime:"2014-06-14 17:56",stayTime:8},
		    						{runDays:1,stnName:"定州",arrTime:"2014-06-14 18:43",dptTime:"2014-06-14 18:45",stayTime:2},
		    						{runDays:1,stnName:"保定",arrTime:"2014-06-14 19:22",dptTime:"2014-06-14 19:26",stayTime:4},
		    						{runDays:1,stnName:"北京西",arrTime:"2014-06-14 21:07",dptTime:"2014-06-14 21:07",stayTime:0}
		    				  ]
		    	          },
		    	          {
		    	        	  trainName:"K817",
		    	          	  startStn:"北京西",//始发站名 为null时填入""（空串）
		    	          	  endStn:"成都",//终到站名为null时填入""（空串）
		    	        	  trainStns:[
									{runDays:0,stnName:"北京西",arrTime:"2014-06-15 08:35",dptTime:"2014-06-15 08:35",stayTime:0},
		    						{runDays:0,stnName:"高碑店",arrTime:"2014-06-15 09:29",dptTime:"2014-06-15 09:32",stayTime:3},
		    						{runDays:0,stnName:"保定",arrTime:"2014-06-15 10:10",dptTime:"2014-06-15 10:13",stayTime:3},
		    						{runDays:0,stnName:"石家庄",arrTime:"2014-06-15 11:45",dptTime:"2014-06-15 11:51",stayTime:6},
		    						{runDays:0,stnName:"邢台",arrTime:"2014-06-15 12:54",dptTime:"2014-06-15 13:11",stayTime:17},
		    						{runDays:0,stnName:"邯郸",arrTime:"2014-06-15 13:42",dptTime:"2014-06-15 13:46",stayTime:4},
		    						{runDays:0,stnName:"卫辉",arrTime:"2014-06-15 15:16",dptTime:"2014-06-15 15:29",stayTime:13},
		    						{runDays:0,stnName:"新乡",arrTime:"2014-06-15 15:48",dptTime:"2014-06-15 15:58",stayTime:10},
		    						{runDays:0,stnName:"郑州",arrTime:"2014-06-15 17:03",dptTime:"2014-06-15 17:23",stayTime:20},
		    						{runDays:0,stnName:"洛阳",arrTime:"2014-06-15 18:58",dptTime:"2014-06-15 19:00",stayTime:2},
		    						{runDays:0,stnName:"三门峡",arrTime:"2014-06-15 21:11",dptTime:"2014-06-15 21:16",stayTime:5},
									{runDays:0,stnName:"华山",arrTime:"2014-06-15 23:07",dptTime:"2014-06-15 23:13",stayTime:6},
									{runDays:1,stnName:"安康",arrTime:"2014-06-16 03:59",dptTime:"2014-06-16 04:13",stayTime:14},
									{runDays:1,stnName:"达州",arrTime:"2014-06-16 07:29",dptTime:"2014-06-16 07:35",stayTime:6},
									{runDays:1,stnName:"土溪",arrTime:"2014-06-16 08:09",dptTime:"2014-06-16 08:13",stayTime:4},
									{runDays:1,stnName:"营山",arrTime:"2014-06-16 08:45",dptTime:"2014-06-16 08:49",stayTime:4},
									{runDays:1,stnName:"蓬安",arrTime:"2014-06-16 09:02",dptTime:"2014-06-16 09:05",stayTime:3},
									{runDays:1,stnName:"南充",arrTime:"2014-06-16 09:32",dptTime:"2014-06-16 09:36",stayTime:4},
									{runDays:1,stnName:"遂宁",arrTime:"2014-06-16 10:30",dptTime:"2014-06-16 10:33",stayTime:3},
									{runDays:1,stnName:"成都",arrTime:"2014-06-16 12:30",dptTime:"2014-06-16 12:30",stayTime:0}
		    	        	  ]
		    	          }
		    	  ]
		      },
		      //end 一条交路
		      {
  		    	  crossName:"K818-K817-2",
  		    	  jxgx:[//接续关系数组
  		    	        {fromStnName:"成都",fromTime:"2014-06-11 12:30",toStnName:"成都",toTime:"2014-06-11 19:18"},
  		    	        {fromStnName:"北京西",fromTime:"2014-06-12 21:07",toStnName:"北京西",toTime:"2014-06-13 08:35"},
  		    	        {fromStnName:"成都",fromTime:"2014-06-14 12:30",toStnName:"成都",toTime:"2014-06-14 19:18"},
  		    	        ],
  		    	  trains:[//该交路下所有车次数组
  		    	          {
  		    	        	  trainName:"K817",
  		    	          	  startStn:"北京西",//始发站名 为null时填入""（空串）
  		    	          	  endStn:"成都",//终到站名为null时填入""（空串）
  		    	        	  trainStns:[
  									{runDays:0,stnName:"北京西",arrTime:"2014-06-10 08:35",dptTime:"2014-06-10 08:35",stayTime:0},
  		    						{runDays:0,stnName:"高碑店",arrTime:"2014-06-10 09:29",dptTime:"2014-06-10 09:32",stayTime:3},
  		    						{runDays:0,stnName:"保定",arrTime:"2014-06-10 10:10",dptTime:"2014-06-10 10:13",stayTime:3},
  		    						{runDays:0,stnName:"石家庄",arrTime:"2014-06-10 11:45",dptTime:"2014-06-10 11:51",stayTime:6},
  		    						{runDays:0,stnName:"邢台",arrTime:"2014-06-10 12:54",dptTime:"2014-06-10 13:11",stayTime:17},
  		    						{runDays:0,stnName:"邯郸",arrTime:"2014-06-10 13:42",dptTime:"2014-06-10 13:46",stayTime:4},
  		    						{runDays:0,stnName:"卫辉",arrTime:"2014-06-10 15:16",dptTime:"2014-06-10 15:29",stayTime:13},
  		    						{runDays:0,stnName:"新乡",arrTime:"2014-06-10 15:48",dptTime:"2014-06-10 15:58",stayTime:10},
  		    						{runDays:0,stnName:"郑州",arrTime:"2014-06-10 17:03",dptTime:"2014-06-10 17:23",stayTime:20},
  		    						{runDays:0,stnName:"洛阳",arrTime:"2014-06-10 18:58",dptTime:"2014-06-10 19:00",stayTime:2},
  		    						{runDays:0,stnName:"三门峡",arrTime:"2014-06-10 21:11",dptTime:"2014-06-10 21:16",stayTime:5},
  									{runDays:0,stnName:"华山",arrTime:"2014-06-10 23:07",dptTime:"2014-06-10 23:13",stayTime:6},
  									{runDays:1,stnName:"安康",arrTime:"2014-06-11 03:59",dptTime:"2014-06-11 04:13",stayTime:14},
  									{runDays:1,stnName:"达州",arrTime:"2014-06-11 07:29",dptTime:"2014-06-11 07:35",stayTime:6},
  									{runDays:1,stnName:"土溪",arrTime:"2014-06-11 08:09",dptTime:"2014-06-11 08:13",stayTime:4},
  									{runDays:1,stnName:"营山",arrTime:"2014-06-11 08:45",dptTime:"2014-06-11 08:49",stayTime:4},
  									{runDays:1,stnName:"蓬安",arrTime:"2014-06-11 09:02",dptTime:"2014-06-11 09:05",stayTime:3},
  									{runDays:1,stnName:"南充",arrTime:"2014-06-11 09:32",dptTime:"2014-06-11 09:36",stayTime:4},
  									{runDays:1,stnName:"遂宁",arrTime:"2014-06-11 10:30",dptTime:"2014-06-11 10:33",stayTime:3},
  									{runDays:1,stnName:"成都",arrTime:"2014-06-11 12:30",dptTime:"2014-06-11 12:30",stayTime:0}
  		    	        	  ]
  		    	          },
  		    	          {
		    	        	  trainName:"K818",
		    				  startStn:"成都",//始发站名 为null时填入""（空串）
		    				  endStn:"北京西",//终到站名为null时填入""（空串）
		    				  trainStns:[
		    						{runDays:0,stnName:"成都",arrTime:"2014-06-11 19:18",dptTime:"2014-06-11 19:18",stayTime:0},
		    						{runDays:0,stnName:"遂宁",arrTime:"2014-06-11 21:19",dptTime:"2014-06-11 21:33",stayTime:14},
		    						{runDays:0,stnName:"南充",arrTime:"2014-06-11 22:15",dptTime:"2014-06-11 22:22",stayTime:7},
		    						{runDays:0,stnName:"蓬安",arrTime:"2014-06-11 22:49",dptTime:"2014-06-11 22:54",stayTime:5},
		    						{runDays:0,stnName:"营山",arrTime:"2014-06-11 23:06",dptTime:"2014-06-11 23:11",stayTime:5},
		    						{runDays:1,stnName:"达州",arrTime:"2014-06-12 00:13",dptTime:"2014-06-12 00:23",stayTime:10},
		    						{runDays:1,stnName:"安康",arrTime:"2014-06-12 03:12",dptTime:"2014-06-12 03:32",stayTime:20},
		    						{runDays:1,stnName:"华山",arrTime:"2014-06-12 07:45",dptTime:"2014-06-12 07:51",stayTime:6},
		    						{runDays:1,stnName:"三门峡",arrTime:"2014-06-12 09:39",dptTime:"2014-06-12 09:41",stayTime:2},
		    						{runDays:1,stnName:"洛阳",arrTime:"2014-06-12 11:20",dptTime:"2014-06-12 11:23",stayTime:3},
		    						{runDays:1,stnName:"郑州",arrTime:"2014-06-12 12:54",dptTime:"2014-06-12 13:08",stayTime:14},
		    						{runDays:1,stnName:"新乡",arrTime:"2014-06-12 14:02",dptTime:"2014-06-12 14:12",stayTime:10},
		    						{runDays:1,stnName:"安阳",arrTime:"2014-06-12 15:21",dptTime:"2014-06-12 15:23",stayTime:2},
		    						{runDays:1,stnName:"邯郸",arrTime:"2014-06-12 15:58",dptTime:"2014-06-12 16:02",stayTime:4},
		    						{runDays:1,stnName:"邢台",arrTime:"2014-06-12 16:33",dptTime:"2014-06-12 16:37",stayTime:4},
		    						{runDays:1,stnName:"石家庄",arrTime:"2014-06-12 17:48",dptTime:"2014-06-12 17:56",stayTime:8},
		    						{runDays:1,stnName:"定州",arrTime:"2014-06-12 18:43",dptTime:"2014-06-12 18:45",stayTime:2},
		    						{runDays:1,stnName:"保定",arrTime:"2014-06-12 19:22",dptTime:"2014-06-12 19:26",stayTime:4},
		    						{runDays:1,stnName:"北京西",arrTime:"2014-06-12 21:07",dptTime:"2014-06-12 21:07",stayTime:0}
		    				  ]
		    	          },
		    	          {
		    	        	  trainName:"K817",
		    	          	  startStn:"北京西",//始发站名 为null时填入""（空串）
		    	          	  endStn:"成都",//终到站名为null时填入""（空串）
		    	        	  trainStns:[
									{runDays:0,stnName:"北京西",arrTime:"2014-06-13 08:35",dptTime:"2014-06-13 08:35",stayTime:0},
		    						{runDays:0,stnName:"高碑店",arrTime:"2014-06-13 09:29",dptTime:"2014-06-13 09:32",stayTime:3},
		    						{runDays:0,stnName:"保定",arrTime:"2014-06-13 10:10",dptTime:"2014-06-13 10:13",stayTime:3},
		    						{runDays:0,stnName:"石家庄",arrTime:"2014-06-13 11:45",dptTime:"2014-06-13 11:51",stayTime:6},
		    						{runDays:0,stnName:"邢台",arrTime:"2014-06-13 12:54",dptTime:"2014-06-13 13:11",stayTime:17},
		    						{runDays:0,stnName:"邯郸",arrTime:"2014-06-13 13:42",dptTime:"2014-06-13 13:46",stayTime:4},
		    						{runDays:0,stnName:"卫辉",arrTime:"2014-06-13 15:16",dptTime:"2014-06-13 15:29",stayTime:13},
		    						{runDays:0,stnName:"新乡",arrTime:"2014-06-13 15:48",dptTime:"2014-06-13 15:58",stayTime:10},
		    						{runDays:0,stnName:"郑州",arrTime:"2014-06-13 17:03",dptTime:"2014-06-13 17:23",stayTime:20},
		    						{runDays:0,stnName:"洛阳",arrTime:"2014-06-13 18:58",dptTime:"2014-06-13 19:00",stayTime:2},
		    						{runDays:0,stnName:"三门峡",arrTime:"2014-06-13 21:11",dptTime:"2014-06-13 21:16",stayTime:5},
									{runDays:0,stnName:"华山",arrTime:"2014-06-13 23:07",dptTime:"2014-06-13 23:13",stayTime:6},
									{runDays:1,stnName:"安康",arrTime:"2014-06-14 03:59",dptTime:"2014-06-14 04:13",stayTime:14},
									{runDays:1,stnName:"达州",arrTime:"2014-06-14 07:29",dptTime:"2014-06-14 07:35",stayTime:6},
									{runDays:1,stnName:"土溪",arrTime:"2014-06-14 08:09",dptTime:"2014-06-14 08:13",stayTime:4},
									{runDays:1,stnName:"营山",arrTime:"2014-06-14 08:45",dptTime:"2014-06-14 08:49",stayTime:4},
									{runDays:1,stnName:"蓬安",arrTime:"2014-06-14 09:02",dptTime:"2014-06-14 09:05",stayTime:3},
									{runDays:1,stnName:"南充",arrTime:"2014-06-14 09:32",dptTime:"2014-06-14 09:36",stayTime:4},
									{runDays:1,stnName:"遂宁",arrTime:"2014-06-14 10:30",dptTime:"2014-06-14 10:33",stayTime:3},
									{runDays:1,stnName:"成都",arrTime:"2014-06-14 12:30",dptTime:"2014-06-14 12:30",stayTime:0}
		    	        	  ]
		    	          },
		    	          {
		    	        	  trainName:"K818",
		    				  startStn:"成都",//始发站名 为null时填入""（空串）
		    				  endStn:"北京西",//终到站名为null时填入""（空串）
		    				  trainStns:[
		    						{runDays:0,stnName:"成都",arrTime:"2014-06-14 19:18",dptTime:"2014-06-14 19:18",stayTime:0},
		    						{runDays:0,stnName:"遂宁",arrTime:"2014-06-14 21:19",dptTime:"2014-06-14 21:33",stayTime:14},
		    						{runDays:0,stnName:"南充",arrTime:"2014-06-14 22:15",dptTime:"2014-06-14 22:22",stayTime:7},
		    						{runDays:0,stnName:"蓬安",arrTime:"2014-06-14 22:49",dptTime:"2014-06-14 22:54",stayTime:5},
		    						{runDays:0,stnName:"营山",arrTime:"2014-06-14 23:06",dptTime:"2014-06-14 23:11",stayTime:5},
		    						{runDays:1,stnName:"达州",arrTime:"2014-06-15 00:13",dptTime:"2014-06-15 00:23",stayTime:10},
		    						{runDays:1,stnName:"安康",arrTime:"2014-06-15 03:12",dptTime:"2014-06-15 03:32",stayTime:20},
		    						{runDays:1,stnName:"华山",arrTime:"2014-06-15 07:45",dptTime:"2014-06-15 07:51",stayTime:6},
		    						{runDays:1,stnName:"三门峡",arrTime:"2014-06-15 09:39",dptTime:"2014-06-15 09:41",stayTime:2},
		    						{runDays:1,stnName:"洛阳",arrTime:"2014-06-15 11:20",dptTime:"2014-06-15 11:23",stayTime:3},
		    						{runDays:1,stnName:"郑州",arrTime:"2014-06-15 12:54",dptTime:"2014-06-15 13:08",stayTime:14},
		    						{runDays:1,stnName:"新乡",arrTime:"2014-06-15 14:02",dptTime:"2014-06-15 14:12",stayTime:10},
		    						{runDays:1,stnName:"安阳",arrTime:"2014-06-15 15:21",dptTime:"2014-06-15 15:23",stayTime:2},
		    						{runDays:1,stnName:"邯郸",arrTime:"2014-06-15 15:58",dptTime:"2014-06-15 16:02",stayTime:4},
		    						{runDays:1,stnName:"邢台",arrTime:"2014-06-15 16:33",dptTime:"2014-06-15 16:37",stayTime:4},
		    						{runDays:1,stnName:"石家庄",arrTime:"2014-06-15 17:48",dptTime:"2014-06-15 17:56",stayTime:8},
		    						{runDays:1,stnName:"定州",arrTime:"2014-06-15 18:43",dptTime:"2014-06-15 18:45",stayTime:2},
		    						{runDays:1,stnName:"保定",arrTime:"2014-06-15 19:22",dptTime:"2014-06-15 19:26",stayTime:4},
		    						{runDays:1,stnName:"北京西",arrTime:"2014-06-15 21:07",dptTime:"2014-06-15 21:07",stayTime:0}
		    				  ]
		    	          }
  		    	  ]
  		      },
  			  //end 一条交路
  		    {
  		    	  crossName:"K818-K817-3",
  		    	  jxgx:[//接续关系数组
  		    	        {fromStnName:"成都",fromTime:"2014-06-12 12:30",toStnName:"成都",toTime:"2014-06-12 19:18"},
  		    	        {fromStnName:"北京西",fromTime:"2014-06-13 21:07",toStnName:"北京西",toTime:"2014-06-14 08:35"},
  		    	        {fromStnName:"成都",fromTime:"2014-06-15 12:30",toStnName:"成都",toTime:"2014-06-15 19:18"}

  		    	        ],
  		    	  trains:[//该交路下所有车次数组
  		    	          {
  		    	        	  trainName:"K817",
  		    	          	  startStn:"北京西",//始发站名 为null时填入""（空串）
  		    	          	  endStn:"成都",//终到站名为null时填入""（空串）
  		    	        	  trainStns:[
  									{runDays:0,stnName:"北京西",arrTime:"2014-06-11 08:35",dptTime:"2014-06-11 08:35",stayTime:0},
  		    						{runDays:0,stnName:"高碑店",arrTime:"2014-06-11 09:29",dptTime:"2014-06-11 09:32",stayTime:3},
  		    						{runDays:0,stnName:"保定",arrTime:"2014-06-11 10:10",dptTime:"2014-06-11 10:13",stayTime:3},
  		    						{runDays:0,stnName:"石家庄",arrTime:"2014-06-11 11:45",dptTime:"2014-06-11 11:51",stayTime:6},
  		    						{runDays:0,stnName:"邢台",arrTime:"2014-06-11 12:54",dptTime:"2014-06-11 13:11",stayTime:17},
  		    						{runDays:0,stnName:"邯郸",arrTime:"2014-06-11 13:42",dptTime:"2014-06-11 13:46",stayTime:4},
  		    						{runDays:0,stnName:"卫辉",arrTime:"2014-06-11 15:16",dptTime:"2014-06-11 15:29",stayTime:13},
  		    						{runDays:0,stnName:"新乡",arrTime:"2014-06-11 15:48",dptTime:"2014-06-11 15:58",stayTime:10},
  		    						{runDays:0,stnName:"郑州",arrTime:"2014-06-11 17:03",dptTime:"2014-06-11 17:23",stayTime:20},
  		    						{runDays:0,stnName:"洛阳",arrTime:"2014-06-11 18:58",dptTime:"2014-06-11 19:00",stayTime:2},
  		    						{runDays:0,stnName:"三门峡",arrTime:"2014-06-11 21:11",dptTime:"2014-06-11 21:16",stayTime:5},
  									{runDays:0,stnName:"华山",arrTime:"2014-06-11 23:07",dptTime:"2014-06-11 23:13",stayTime:6},
  									{runDays:1,stnName:"安康",arrTime:"2014-06-12 03:59",dptTime:"2014-06-12 04:13",stayTime:14},
  									{runDays:1,stnName:"达州",arrTime:"2014-06-12 07:29",dptTime:"2014-06-12 07:35",stayTime:6},
  									{runDays:1,stnName:"土溪",arrTime:"2014-06-12 08:09",dptTime:"2014-06-12 08:13",stayTime:4},
  									{runDays:1,stnName:"营山",arrTime:"2014-06-12 08:45",dptTime:"2014-06-12 08:49",stayTime:4},
  									{runDays:1,stnName:"蓬安",arrTime:"2014-06-12 09:02",dptTime:"2014-06-12 09:05",stayTime:3},
  									{runDays:1,stnName:"南充",arrTime:"2014-06-12 09:32",dptTime:"2014-06-12 09:36",stayTime:4},
  									{runDays:1,stnName:"遂宁",arrTime:"2014-06-12 10:30",dptTime:"2014-06-12 10:33",stayTime:3},
  									{runDays:1,stnName:"成都",arrTime:"2014-06-12 12:30",dptTime:"2014-06-12 12:30",stayTime:0}
  		    	        	  ]
  		    	          },
  		    	        {
		    	        	  trainName:"K818",
		    				  startStn:"成都",//始发站名 为null时填入""（空串）
		    				  endStn:"北京西",//终到站名为null时填入""（空串）
		    				  trainStns:[
		    						{runDays:0,stnName:"成都",arrTime:"2014-06-12 19:18",dptTime:"2014-06-12 19:18",stayTime:0},
		    						{runDays:0,stnName:"遂宁",arrTime:"2014-06-12 21:19",dptTime:"2014-06-12 21:33",stayTime:14},
		    						{runDays:0,stnName:"南充",arrTime:"2014-06-12 22:15",dptTime:"2014-06-12 22:22",stayTime:7},
		    						{runDays:0,stnName:"蓬安",arrTime:"2014-06-12 22:49",dptTime:"2014-06-12 22:54",stayTime:5},
		    						{runDays:0,stnName:"营山",arrTime:"2014-06-12 23:06",dptTime:"2014-06-12 23:11",stayTime:5},
		    						{runDays:1,stnName:"达州",arrTime:"2014-06-13 00:13",dptTime:"2014-06-13 00:23",stayTime:10},
		    						{runDays:1,stnName:"安康",arrTime:"2014-06-13 03:12",dptTime:"2014-06-13 03:32",stayTime:20},
		    						{runDays:1,stnName:"华山",arrTime:"2014-06-13 07:45",dptTime:"2014-06-13 07:51",stayTime:6},
		    						{runDays:1,stnName:"三门峡",arrTime:"2014-06-13 09:39",dptTime:"2014-06-13 09:41",stayTime:2},
		    						{runDays:1,stnName:"洛阳",arrTime:"2014-06-13 11:20",dptTime:"2014-06-13 11:23",stayTime:3},
		    						{runDays:1,stnName:"郑州",arrTime:"2014-06-13 12:54",dptTime:"2014-06-13 13:08",stayTime:14},
		    						{runDays:1,stnName:"新乡",arrTime:"2014-06-13 14:02",dptTime:"2014-06-13 14:12",stayTime:10},
		    						{runDays:1,stnName:"安阳",arrTime:"2014-06-13 15:21",dptTime:"2014-06-13 15:23",stayTime:2},
		    						{runDays:1,stnName:"邯郸",arrTime:"2014-06-13 15:58",dptTime:"2014-06-13 16:02",stayTime:4},
		    						{runDays:1,stnName:"邢台",arrTime:"2014-06-13 16:33",dptTime:"2014-06-13 16:37",stayTime:4},
		    						{runDays:1,stnName:"石家庄",arrTime:"2014-06-13 17:48",dptTime:"2014-06-13 17:56",stayTime:8},
		    						{runDays:1,stnName:"定州",arrTime:"2014-06-13 18:43",dptTime:"2014-06-13 18:45",stayTime:2},
		    						{runDays:1,stnName:"保定",arrTime:"2014-06-13 19:22",dptTime:"2014-06-13 19:26",stayTime:4},
		    						{runDays:1,stnName:"北京西",arrTime:"2014-06-13 21:07",dptTime:"2014-06-13 21:07",stayTime:0}
		    				  ]
		    	          },
		    	          {
		    	        	  trainName:"K817",
		    	          	  startStn:"北京西",//始发站名 为null时填入""（空串）
		    	          	  endStn:"成都",//终到站名为null时填入""（空串）
		    	        	  trainStns:[
									{runDays:0,stnName:"北京西",arrTime:"2014-06-14 08:35",dptTime:"2014-06-14 08:35",stayTime:0},
		    						{runDays:0,stnName:"高碑店",arrTime:"2014-06-14 09:29",dptTime:"2014-06-14 09:32",stayTime:3},
		    						{runDays:0,stnName:"保定",arrTime:"2014-06-14 10:10",dptTime:"2014-06-14 10:13",stayTime:3},
		    						{runDays:0,stnName:"石家庄",arrTime:"2014-06-14 11:45",dptTime:"2014-06-14 11:51",stayTime:6},
		    						{runDays:0,stnName:"邢台",arrTime:"2014-06-14 12:54",dptTime:"2014-06-14 13:11",stayTime:17},
		    						{runDays:0,stnName:"邯郸",arrTime:"2014-06-14 13:42",dptTime:"2014-06-14 13:46",stayTime:4},
		    						{runDays:0,stnName:"卫辉",arrTime:"2014-06-14 15:16",dptTime:"2014-06-14 15:29",stayTime:13},
		    						{runDays:0,stnName:"新乡",arrTime:"2014-06-14 15:48",dptTime:"2014-06-14 15:58",stayTime:10},
		    						{runDays:0,stnName:"郑州",arrTime:"2014-06-14 17:03",dptTime:"2014-06-14 17:23",stayTime:20},
		    						{runDays:0,stnName:"洛阳",arrTime:"2014-06-14 18:58",dptTime:"2014-06-14 19:00",stayTime:2},
		    						{runDays:0,stnName:"三门峡",arrTime:"2014-06-14 21:11",dptTime:"2014-06-14 21:16",stayTime:5},
									{runDays:0,stnName:"华山",arrTime:"2014-06-14 23:07",dptTime:"2014-06-14 23:13",stayTime:6},
									{runDays:1,stnName:"安康",arrTime:"2014-06-15 03:59",dptTime:"2014-06-15 04:13",stayTime:14},
									{runDays:1,stnName:"达州",arrTime:"2014-06-15 07:29",dptTime:"2014-06-15 07:35",stayTime:6},
									{runDays:1,stnName:"土溪",arrTime:"2014-06-15 08:09",dptTime:"2014-06-15 08:13",stayTime:4},
									{runDays:1,stnName:"营山",arrTime:"2014-06-15 08:45",dptTime:"2014-06-15 08:49",stayTime:4},
									{runDays:1,stnName:"蓬安",arrTime:"2014-06-15 09:02",dptTime:"2014-06-15 09:05",stayTime:3},
									{runDays:1,stnName:"南充",arrTime:"2014-06-15 09:32",dptTime:"2014-06-15 09:36",stayTime:4},
									{runDays:1,stnName:"遂宁",arrTime:"2014-06-15 10:30",dptTime:"2014-06-15 10:33",stayTime:3},
									{runDays:1,stnName:"成都",arrTime:"2014-06-15 12:30",dptTime:"2014-06-15 12:30",stayTime:0}
		    	        	  ]
		    	          },
		    	          {
		    	        	  trainName:"K818",
		    				  startStn:"成都",//始发站名 为null时填入""（空串）
		    				  endStn:"北京西",//终到站名为null时填入""（空串）
		    				  trainStns:[
		    						{runDays:0,stnName:"成都",arrTime:"2014-06-15 19:18",dptTime:"2014-06-15 19:18",stayTime:0},
		    						{runDays:0,stnName:"遂宁",arrTime:"2014-06-15 21:19",dptTime:"2014-06-15 21:33",stayTime:14},
		    						{runDays:0,stnName:"南充",arrTime:"2014-06-15 22:15",dptTime:"2014-06-15 22:22",stayTime:7},
		    						{runDays:0,stnName:"蓬安",arrTime:"2014-06-15 22:49",dptTime:"2014-06-15 22:54",stayTime:5},
		    						{runDays:0,stnName:"营山",arrTime:"2014-06-15 23:06",dptTime:"2014-06-15 23:11",stayTime:5},
		    						{runDays:1,stnName:"达州",arrTime:"2014-06-16 00:13",dptTime:"2014-06-16 00:23",stayTime:10},
		    						{runDays:1,stnName:"安康",arrTime:"2014-06-16 03:12",dptTime:"2014-06-16 03:32",stayTime:20},
		    						{runDays:1,stnName:"华山",arrTime:"2014-06-16 07:45",dptTime:"2014-06-16 07:51",stayTime:6},
		    						{runDays:1,stnName:"三门峡",arrTime:"2014-06-16 09:39",dptTime:"2014-06-16 09:41",stayTime:2},
		    						{runDays:1,stnName:"洛阳",arrTime:"2014-06-16 11:20",dptTime:"2014-06-16 11:23",stayTime:3},
		    						{runDays:1,stnName:"郑州",arrTime:"2014-06-16 12:54",dptTime:"2014-06-16 13:08",stayTime:14},
		    						{runDays:1,stnName:"新乡",arrTime:"2014-06-16 14:02",dptTime:"2014-06-16 14:12",stayTime:10},
		    						{runDays:1,stnName:"安阳",arrTime:"2014-06-16 15:21",dptTime:"2014-06-16 15:23",stayTime:2},
		    						{runDays:1,stnName:"邯郸",arrTime:"2014-06-16 15:58",dptTime:"2014-06-16 16:02",stayTime:4},
		    						{runDays:1,stnName:"邢台",arrTime:"2014-06-16 16:33",dptTime:"2014-06-16 16:37",stayTime:4},
		    						{runDays:1,stnName:"石家庄",arrTime:"2014-06-16 17:48",dptTime:"2014-06-16 17:56",stayTime:8},
		    						{runDays:1,stnName:"定州",arrTime:"2014-06-16 18:43",dptTime:"2014-06-16 18:45",stayTime:2},
		    						{runDays:1,stnName:"保定",arrTime:"2014-06-16 19:22",dptTime:"2014-06-16 19:26",stayTime:4},
		    						{runDays:1,stnName:"北京西",arrTime:"2014-06-16 21:07",dptTime:"2014-06-16 21:07",stayTime:0}
		    				  ]
		    	          }
  		    	  ]
  		      }
  			  //end 一条交路 
		];



var canvasData = {
		grid:gridData,
		jlData:myJlData
};

