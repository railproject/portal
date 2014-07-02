package org.railway.com.portal.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.railway.com.portal.common.constants.Constants;
import org.railway.com.portal.common.constants.StaticCodeType;
import org.railway.com.portal.common.utils.StringUtil;
import org.railway.com.portal.entity.BaseCrossTrainInfo;
import org.railway.com.portal.entity.BaseCrossTrainInfoTime;
import org.railway.com.portal.entity.BaseCrossTrainSubInfo;
import org.railway.com.portal.entity.CrossInfo;
import org.railway.com.portal.entity.CrossTrainInfo;
import org.railway.com.portal.entity.SubCrossInfo;
import org.railway.com.portal.entity.TrainLineInfo;
import org.railway.com.portal.entity.TrainLineSubInfo;
import org.railway.com.portal.entity.TrainLineSubInfoTime;
import org.railway.com.portal.entity.UnitCrossTrainInfo;
import org.railway.com.portal.entity.UnitCrossTrainSubInfo;
import org.railway.com.portal.entity.UnitCrossTrainSubInfoTime;
import org.railway.com.portal.service.CrossService;
import org.railway.com.portal.service.RemoteService;
import org.railway.com.portal.service.ShiroRealm;
import org.railway.com.portal.service.TrainInfoService;
import org.railway.com.portal.service.dto.PagingResult;
import org.railway.com.portal.web.dto.CrossRelationDto;
import org.railway.com.portal.web.dto.PlanLineGrid;
import org.railway.com.portal.web.dto.PlanLineGridX;
import org.railway.com.portal.web.dto.PlanLineGridY;
import org.railway.com.portal.web.dto.PlanLineSTNDto;
import org.railway.com.portal.web.dto.Result;
import org.railway.com.portal.web.dto.TrainInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

 

@Controller
@RequestMapping(value = "/cross")
public class CrossController {
	private static Log logger = LogFactory.getLog(CrossController.class.getName());
	 @RequestMapping(method = RequestMethod.GET)
     public String content() {
		 return "cross/cross_manage";
     }
	 
	 @RequestMapping(value="/unit", method = RequestMethod.GET)
     public String unit() {
		 return "cross/cross_unit_manage";
     } 
	 
	 @RequestMapping(value="/crossCanvas", method = RequestMethod.GET)
     public String crossCanvas() {
		 return "cross/canvas_event_getvalue";
     }
	
	 @Autowired
	private CrossService crossService;
	
	@Autowired
	private RemoteService remoteService;
	
	@Autowired
	private TrainInfoService trainInfoService;
	
	@ResponseBody
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public Result getFullStationTrains(HttpServletRequest request, HttpServletResponse response){
		Result result = new Result(); 
		  try {  
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
				String chartId = request.getParameter("chartId");
				String chartName = request.getParameter("chartName");
				String startDay = request.getParameter("startDay"); 
			    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
			    for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {   
			    	// 上传文件 
			    	MultipartFile mf = entity.getValue();  
			    	crossService.actionExcel(mf.getInputStream(), chartId, startDay, chartName);
			  	}  
 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setCode("401");
				result.setMessage("上传失败");
			}  
		return result;
	}
	
	/**
	 * 删除base_cross表和表base_cross_train中数据
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteUnitCorssInfo", method = RequestMethod.POST)
	public Result deleteUnitCorssInfo(@RequestBody Map<String,Object> reqMap){
		 Result result = new Result();
		 try{
			 String crossIds = StringUtil.objToStr(reqMap.get("unitCrossIds"));
			 if(crossIds != null){
				String[] crossIdsArray = crossIds.split(",");
				//先删除cross_train表中数据
				int countTrain = crossService.deleteUnitCrossInfoTrainForCorssIds(crossIdsArray);
				//删除unit_cross表中数据
				int count = crossService.deleteUnitCrossInfoForCorssIds(crossIdsArray);
			 }  
		 }catch(Exception e){
			 logger.error("deleteUnitCorssInfo error==" + e.getMessage());
			 result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			 result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	 
		 }
		 return result;
	}
	
	/**
	 * 删除base_cross表和表base_cross_train中数据
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCorssInfo", method = RequestMethod.POST)
	public Result deleteCorssInfo(@RequestBody Map<String,Object> reqMap){
		 Result result = new Result();
		 try{
			 String crossIds = StringUtil.objToStr(reqMap.get("crossIds"));
			 if(crossIds != null){
				String[] crossIdsArray = crossIds.split(",");
				//先删除base_cross_train表中数据
				int countTrain = crossService.deleteCrossInfoTrainForCorssIds(crossIdsArray);
				//删除base_cross表中数据
				int count = crossService.deleteCrossInfoForCorssIds(crossIdsArray);
			 }  
		 }catch(Exception e){
			 logger.error("deleteCorssInfo error==" + e.getMessage());
			 result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			 result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	 
		 }
		 return result;
	}
	
	/**
	 * 审核交路信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCorssInfo", method = RequestMethod.POST)
	public Result checkCorssInfo(@RequestBody Map<String,Object> reqMap) {
		 Result result = new Result();
		 try{
			 String crossIds = StringUtil.objToStr(reqMap.get("crossIds"));
			 if(crossIds != null){
				String[] crossIdsArray = crossIds.split(",");
				int count = crossService.updateCorssCheckTime(crossIdsArray);
				logger.debug("update--count==" + count);
			 } 
		 }catch(Exception e){
			 logger.error("checkCorssInfo error==" + e.getMessage());
			 result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			 result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		 }
		
		 return result;
	}
	
	/**
	 * 审核交路信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkUnitCorssInfo", method = RequestMethod.POST)
	public Result checkUnitCorssInfo(@RequestBody Map<String,Object> reqMap) {
		 Result result = new Result();
		 try{
			 String unitCrossIds = StringUtil.objToStr(reqMap.get("unitCrossIds"));
			 if(unitCrossIds != null){
				String[] unitCrossIdsArray = unitCrossIds.split(",");
				int count = crossService.updateUnitCorssCheckTime(unitCrossIdsArray);
				logger.debug("update--count==" + count);
			 } 
		 }catch(Exception e){
			 logger.error("checkCorssInfo error==" + e.getMessage());
			 result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			 result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		 }
		
		 return result;
	}
	/**
	 * 提供画交路单元图形的数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/provideUnitCrossChartData", method = RequestMethod.GET)
	public ModelAndView  provideUnitCrossChartData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 ModelAndView result = new ModelAndView("cross/unit_cross_canvas"); 
		 ObjectMapper objectMapper = new ObjectMapper();
		 String unitCrossId = StringUtil.objToStr(request.getParameter("unitCrossId"));
		 PlanLineGrid grid = null;
		 String crossStartDate = "";
		 String crossEndDate = "";
		 
		 logger.debug("unitCrossId---unit=="+ unitCrossId);
	
		 //众坐标的站列表
		 List<String> stationList = new ArrayList<String>();
		//通过unitCrossId获取unitCross列表信息
		 List<UnitCrossTrainInfo> list = crossService.getUnitCrossTrainInfoForUnitCrossid(unitCrossId);
		 List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		 if(list != null && list.size() > 0){
			 int size = list.size();
			 //i是用来循环交路单元的组
			 for(int i = 0;i<size;i++){
				 Map<String,Object> crossMap = new HashMap<String,Object>();
				 UnitCrossTrainInfo unitCrossInfo = list.get(i);
				 //列车信息列表
				 List<UnitCrossTrainSubInfo> unitStationsList = unitCrossInfo.getTrainInfoList();
				//列车信息列表
				 List<TrainInfoDto> trains = new ArrayList<TrainInfoDto>();
				 
				 if(unitStationsList != null && unitStationsList.size() > 0){
					 int sizeStation = unitStationsList.size();
					 for(int j = 0;j<unitStationsList.size();j++){
						
						 UnitCrossTrainSubInfo subInfo = unitStationsList.get(j);
						 TrainInfoDto dto = new TrainInfoDto();
						 dto.setTrainName(subInfo.getTrainNbr());
						 dto.setStartStn(subInfo.getStartStn());
						 dto.setEndStn(subInfo.getEndStn());
						 dto.setStartDate(subInfo.getRunDate());
						 dto.setEndDate(subInfo.getEndDate());
						 //列车经由时刻等信息
						 List<PlanLineSTNDto> trainStns = new ArrayList<PlanLineSTNDto>();
						 List<UnitCrossTrainSubInfoTime> stationTimeList = subInfo.getStationTimeList();
						 if(stationTimeList != null && stationTimeList.size() > 0){
							 for(int k = 0;k<stationTimeList.size();k++){
								 UnitCrossTrainSubInfoTime subInfoTime = stationTimeList.get(k);	 
								 PlanLineSTNDto stnDto = new PlanLineSTNDto();
								 BeanUtils.copyProperties(stnDto, subInfoTime);
								 trainStns.add(stnDto);
							 }
							 dto.setTrainStns(trainStns);
						 }
						 
						 trains.add(dto);
						
						 
						 if(i == 0 && j == 0){
							 //取第一组交路单元的第一个列车的始发日期作为横坐标的开始
							 crossStartDate = subInfo.getRunDate();
							 if(crossStartDate != null){
								 crossStartDate = crossStartDate.substring(0,10);
							 }
						 }else if(i == size-1 && j == sizeStation - 1){
							 //取最后一组交路单元的最后一个列车的终到日期作为横坐标的结束
							 crossEndDate = subInfo.getEndDate();
							 if(crossEndDate != null ){
								 crossEndDate = crossEndDate.substring(0,10);
							 }
						 }	
						//合并始发站和终到站
						 if(!stationList.contains(subInfo.getStartStn())){
							 stationList.add(subInfo.getStartStn());
						 }
						 if(!stationList.contains(subInfo.getEndStn())){
							 stationList.add(subInfo.getEndStn());
						 }
						
					 } 
				 }
				
				
				// dataList.add(crossMap);
				 
				 //组装接续关系
				 List<CrossRelationDto> jxgx = getJxgx(trains);
				 crossMap.put("jxgx", jxgx);
				 crossMap.put("trains", trains);
				 crossMap.put("crossName","");
				 dataList.add(crossMap);
			 }
		 }
		 
		 //组装坐标
		 grid = getPlanLineGrid(stationList,crossStartDate,crossEndDate);
		 String myJlData = objectMapper.writeValueAsString(dataList);
			//图形数据
			result.addObject("myJlData",myJlData);
			logger.info("myJlData==" + myJlData);
			
			String gridStr = objectMapper.writeValueAsString(grid);
			logger.info("gridStr==" + gridStr);
			result.addObject("gridData",gridStr);
			
		
		 return result;
	}
	
	/**
	 * 提供画交路图形的数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/provideCrossChartData", method = RequestMethod.GET)
	public ModelAndView  provideCrossChartData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		    ModelAndView result = new ModelAndView("cross/unit_cross_canvas"); 
		
			String crossId = StringUtil.objToStr(request.getParameter("crossId"));
			
			//经由信息，由后面调用接口获取，用户提供画图的坐标
			PlanLineGrid grid = null;
			 Map<String,Object> crossMap = new HashMap<String,Object>();
			 ObjectMapper objectMapper = new ObjectMapper();
			 List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			 //众坐标的站列表
			 List<String> stationList = new ArrayList<String>();
			if(crossId !=null){
				 //根据crossId获取列车信息和始发站，终到站等信息
				 List<BaseCrossTrainInfo> list = crossService.getCrossTrainInfoForCrossId(crossId);
				 //只有一条记录
				 if(list != null && list.size() > 0){
					 BaseCrossTrainInfo trainInfo = list.get(0);
					 //列车信息
					 List<TrainInfoDto> trains = new ArrayList<TrainInfoDto>();
					
					 List<BaseCrossTrainSubInfo> trainList = trainInfo.getTrainList();
					 if(trainList != null && trainList.size() > 0){
						 for(int i = 0;i<trainList.size();i++){
							 //列车信息
							 BaseCrossTrainSubInfo subInfo = trainList.get(i);
							 TrainInfoDto dto = new TrainInfoDto();
							 dto.setTrainName(subInfo.getTrainNbr());
							 dto.setStartStn(subInfo.getStartStn());
							 dto.setEndStn(subInfo.getEndStn());
							 dto.setStartDate(subInfo.getStartTime());
							 dto.setEndDate(subInfo.getEndTime());
							 //列车经由时刻等信息
							 List<PlanLineSTNDto> trainStns = new ArrayList<PlanLineSTNDto>();
							 //列车经由时刻信息
							 List<BaseCrossTrainInfoTime> stationTimeList = subInfo.getStationList();
							 if(stationTimeList != null && stationTimeList.size() > 0 ){
								 for(int j = 0;j<stationTimeList.size();j++){
									 BaseCrossTrainInfoTime trainTime = stationTimeList.get(j);
									
									 PlanLineSTNDto stnDto = new PlanLineSTNDto();
									 BeanUtils.copyProperties(stnDto, trainTime);
								
									 trainStns.add(stnDto);
									//合并始发站和终到站
									 if(!stationList.contains(trainTime.getStnName())){
										 stationList.add(trainTime.getStnName());
									 }
								 }
								 dto.setTrainStns(trainStns) ;
							 }
							 trains.add(dto);
						 }
					 }
					 
					 //组装接续关系
					 List<CrossRelationDto> jxgx = getJxgx(trains);
					 crossMap.put("jxgx", jxgx);
					 crossMap.put("trains", trains);
					 crossMap.put("crossName", trainInfo.getCrossName());
					 crossMap.put("baseCrossId",trainInfo.getBaseCrossId() );
					 dataList.add(crossMap);
					 //组装坐标
					 grid = getPlanLineGrid(stationList,trainInfo.getCrossStartDate(),trainInfo.getCrossEndDate());
					 String myJlData = objectMapper.writeValueAsString(dataList);
					  //图形数据
					  result.addObject("myJlData",myJlData);
					  logger.debug("myJlData==" + myJlData);
					  //System.err.println("myJlData==" + myJlData);
					String gridStr = objectMapper.writeValueAsString(grid);
					logger.debug("gridStr==" + gridStr);
					//System.err.println("gridStr==" + gridStr);
					result.addObject("gridData",gridStr);

				 }

			}
				
		return result ;
	}
	
	
	
	/**
	 * 列车运行线图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createCrossMap", method = RequestMethod.POST)
	public Result  provideTrainLineChartDate(@RequestBody Map<String,Object> reqMap) throws Exception{
		 	Result result = new Result();
			String planCrossId = StringUtil.objToStr(reqMap.get("planCrossId"));
			String startTime = StringUtil.objToStr(reqMap.get("startTime"));
			String endTime = StringUtil.objToStr(reqMap.get("endTime"));
			ShiroRealm.ShiroUser user = (ShiroRealm.ShiroUser)SecurityUtils.getSubject().getPrincipal();
			String bureauShortName = user.getBureauShortName();
			//System.err.println("planCrossId==" + planCrossId);
			//System.err.println("bureauShortName==" + bureauShortName);
			//查询列车运行线信息
			List<TrainLineInfo>  list = crossService.getTrainPlanLineInfoForPlanCrossId(planCrossId,bureauShortName);
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			
			if(list != null && list.size() > 0 ){
				 //循环组
				 for(TrainLineInfo lineInfo :list ){
					 Map<String,Object> crossMap = new HashMap<String,Object>();
					 String groupSerialNbr = lineInfo.getGroupSerialNbr();
					 //列车信息列表
					 List<TrainLineSubInfo> subInfoList = lineInfo.getTrainSubInfoList();
					//列车信息
					 List<TrainInfoDto> trains = new ArrayList<TrainInfoDto>();
					 if(subInfoList != null && subInfoList.size() > 0 ){
						
						//循环列车
						 for(TrainLineSubInfo subInfo : subInfoList){
							 //设置列车信息
							 TrainInfoDto dto = new TrainInfoDto();
							 dto.setTrainName(subInfo.getTrainNbr());
							 dto.setStartStn(subInfo.getStartStn());
							 dto.setEndStn(subInfo.getEndStn());
							 dto.setGroupSerialNbr(groupSerialNbr);
							 dto.setStartDate(subInfo.getStartTime());
							 dto.setEndDate(subInfo.getEndTime());
							 //起始站，终到站，分解口列表              
							 List<TrainLineSubInfoTime> subInfoTimeList = subInfo.getTrainStaionList();
							 
							 if(subInfoTimeList != null && subInfoTimeList.size() > 0){
								 List<PlanLineSTNDto> trainStns = new ArrayList<PlanLineSTNDto>();
								 //循环经由站
								 for(TrainLineSubInfoTime subInfoTime : subInfoTimeList){
									 PlanLineSTNDto stnDtoStart = new PlanLineSTNDto();
									 stnDtoStart.setArrTime(subInfoTime.getArrTime());
									 stnDtoStart.setDptTime(subInfoTime.getDptTime());
									 stnDtoStart.setStnName(subInfoTime.getStnName());
									 stnDtoStart.setStationType(subInfoTime.getStationFlag());
									 trainStns.add(stnDtoStart);
								 }
								 dto.setTrainStns(trainStns);
							 }
							 trains.add(dto);
						 }
					 }
					 
					 //组装接续关系
					 List<CrossRelationDto> jxgx = getJxgx(trains);
					 crossMap.put("jxgx", jxgx);
					 crossMap.put("trains", trains);
					 dataList.add(crossMap);
				 }
				 
				 PlanLineGrid grid = null;
				 ObjectMapper objectMapper = new ObjectMapper();
				 //组装坐标
				 //获取stationList
				 List<Map<String,Object>> stationList = crossService.getStationListForPlanCrossId(planCrossId,bureauShortName);
				 
				 List<Map<String,Object>> stationTempList = new ArrayList<Map<String,Object>>();
				 
				
				 Map<String,Object> startMap = null;
				
				 if(stationList != null && stationList.size() > 0){
					 for(Map<String,Object> map : stationList){
						 String stnName = StringUtil.objToStr(map.get("STNNAME"));
						
						 String sort = StringUtil.objToStr(map.get("SORT"));
						 if("1".equals(sort) && startMap == null){
							 startMap = map;
							 stationTempList.add(startMap);
						 }else if("2".equals(sort)){
							 stationTempList.add(map);
						 }else if("3".equals(sort) && !stnName.equals(StringUtil.objToStr(startMap.get("STNNAME"))) ){
							 stationTempList.add(map);
						 }
					 }
				 }
				 grid = getPlanLineGridForPlanLine(stationTempList,startTime,endTime);
				 String myJlData = objectMapper.writeValueAsString(dataList);
			     //图形数据
				 Map<String,Object> dataMap = new HashMap<String,Object>();
				 String gridStr = objectMapper.writeValueAsString(grid);
				 dataMap.put("myJlData",myJlData);
				 dataMap.put("gridData", gridStr);
				 System.err.println("myJlData==" + myJlData);
				 System.err.println("gridStr==" + gridStr);
				result.setData(dataMap);
				
			}
		return result ;
	}
	
	/**
	 * 组装运行线坐标轴数据
	 * @param stationsInfo 经由站信息对象
	 * @param crossStartDate 交路开始日期，格式yyyy-MM-dd
	 * @param crossEndDate 交路终到日期，格式yyyy-MM-dd
	 * @return 坐标轴对象
	 */
	private PlanLineGrid getPlanLineGridForPlanLine(List<Map<String,Object>> stationsInfo,String crossStartDate,String crossEndDate){
		//纵坐标
		 List<PlanLineGridY> planLineGridYList = new ArrayList<PlanLineGridY>();
		 //横坐标
		 List<PlanLineGridX> gridXList = new ArrayList<PlanLineGridX>(); 
		 
		 /****组装纵坐标****/
		 if(stationsInfo != null){
			  
				for(Map<String,Object> map : stationsInfo){
				    if(map != null){
				    	planLineGridYList.add(new PlanLineGridY(
				    			StringUtil.objToStr(map.get("STNNAME")),
				    			((BigDecimal)map.get("ISCURRENTBUREAU")).intValue(),
				    			StringUtil.objToStr(map.get("STATIONTYPE"))
				    			));
				    }
					
				}
					
			}
		 
		/*****组装横坐标  *****/
		
		 LocalDate start = DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(crossStartDate);
	     LocalDate end = new LocalDate(DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(crossEndDate));
	     while(!start.isAfter(end)) {
	            gridXList.add(new PlanLineGridX(start.toString("yyyy-MM-dd")));
	            start = start.plusDays(1);
	        }
	     
		 return new PlanLineGrid(gridXList, planLineGridYList);
	}
	
	/**
	 * 组装坐标轴数据
	 * @param stationsInfo 经由站信息对象
	 * @param crossStartDate 交路开始日期，格式yyyy-MM-dd
	 * @param crossEndDate 交路终到日期，格式yyyy-MM-dd
	 * @return 坐标轴对象
	 */
	private PlanLineGrid getPlanLineGrid(List<String> stationsInfo,String crossStartDate,String crossEndDate){
		//纵坐标
		 List<PlanLineGridY> planLineGridYList = new ArrayList<PlanLineGridY>();
		 //横坐标
		 List<PlanLineGridX> gridXList = new ArrayList<PlanLineGridX>(); 
		 
		 /****组装纵坐标****/
		 if(stationsInfo != null){
			  
				for(String stationName : stationsInfo){
				    if(stationName != null){
				    	planLineGridYList.add(new PlanLineGridY(stationName));
				    }
					
				}
					
			}
		 
		/*****组装横坐标  *****/
		
		 LocalDate start = DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(crossStartDate);
	     LocalDate end = new LocalDate(DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(crossEndDate));
	     while(!start.isAfter(end)) {
	            gridXList.add(new PlanLineGridX(start.toString("yyyy-MM-dd")));
	            start = start.plusDays(1);
	        }
	     
		 return new PlanLineGrid(gridXList, planLineGridYList);
	}
	/**
	 * 组装接续关系
	 * @param trains
	 * @return
	 */
	private List<CrossRelationDto> getJxgx(List<TrainInfoDto> trains){
		List<CrossRelationDto> returnList = new ArrayList<CrossRelationDto>();
		if(trains != null && trains.size() > 0 ){
			int size = trains.size();
			for(int i = 0;i<size;i++){
				//接续关系的对象
				CrossRelationDto dto = new CrossRelationDto();
				int temp = i+1;
				if(temp<size){
					TrainInfoDto dtoCurrent = trains.get(i);
					TrainInfoDto dtoNext = trains.get(temp);
					
					//取i的终点站信息
					dto.setFromStnName(dtoCurrent.getEndStn());
					//取i的终点站日期和时刻进行组合
					dto.setFromTime(dtoCurrent.getEndDate());
					dto.setFromStartStnName(dtoCurrent.getStartStn());
					//设置i-1始发日期和时刻
					dto.setToTime(dtoNext.getStartDate());
					//取i-1的起点信息
					dto.setToStnName(dtoNext.getStartStn());
					dto.setToEndStnName(dtoNext.getEndStn());
					returnList.add(dto);
				}
				
			}
		}
		return returnList;
	}
	
	
	/**
	 * 5.2.4	更新给定列车的基本图运行线车底交路id
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUnitCrossId", method = RequestMethod.POST)
	public Result updateUnitCrossId(@RequestBody Map<String,Object> reqMap){
		Result result = new Result(); 
		String unitCrossIds = StringUtil.objToStr(reqMap.get("unitCrossIds"));
		logger.info("updateUnitCrossId----unitCrossIds=="+unitCrossIds);
		
		try{
			if(unitCrossIds != null){
				JSONArray resultArr = new JSONArray();
				String[] crossArray = unitCrossIds.split(",");
				//取一条unit_cross_id查询出方案id
				String unitCrossid = crossArray[0];
				CrossInfo unitCrossInfo = crossService.getUnitCrossInfoForUnitCrossid(unitCrossid);
				//方案id
				String baseChartId= unitCrossInfo.getChartId() ;
				
				for(String unitCrossId :crossArray){
					//List<CrossTrainInfo> listTrainInfo = crossService.getCrossTrainInfoForCrossid(unitCrossId);
					
					try{
						List<CrossTrainInfo> listTrainInfo = crossService.getUnitCrossTrainInfoForUnitCrossId(unitCrossId);
						if(listTrainInfo !=null && listTrainInfo.size() > 0){ 
							List<String> trainNbrs = new ArrayList<String>();
							for(CrossTrainInfo trainInfo :listTrainInfo ){ 
								String trainNbr = trainInfo.getTrainNbr();
								trainNbrs.add(trainNbr); 
							}
							//调用后台接口
							String response = remoteService.updateUnitCrossId(baseChartId, unitCrossId, trainNbrs);
							
							if(response.equals(Constants.REMOTE_SERVICE_SUCCESS)){
								//调用后台接口成功，更新本地数据表unit_cross中字段CREAT_CROSS_TIME
								crossService.updateUnitCrossUnitCreateTime(new String[]{unitCrossId});
								JSONObject subResult = new JSONObject();
								subResult.put("unitCrossId", unitCrossId); 
								subResult.put("flag", 1); 
								resultArr.add(subResult); 
							} 
						}
					}catch (Exception e) {
						logger.error("updateUnitCrossId:[" + unitCrossId + "]error==" + e.getMessage());
					}  
				}
				result.setData(resultArr);
			}
		}catch(Exception e){
			logger.error("updateUnitCrossId error==" + e.getMessage());
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		}
	
		return result;
	}
	/**
	 * 获取车底交路信息
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnitCrossInfo", method = RequestMethod.POST)
	public Result getUnitCrossInfo(@RequestBody Map<String,Object> reqMap){
		Result result = new Result(); 
		List<SubCrossInfo> list = null;
	    try{
	    	list = crossService.getUnitCrossInfo(reqMap);
	    	PagingResult page = new PagingResult(crossService.getUnitCrossInfoCount(reqMap),list);
	    	result.setData(page);
	    }catch(Exception e){
			logger.error("getUnitCrossInfo error==" + e.getMessage());
			e.printStackTrace();
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		}
	
		return result;
	}
	
	
	/**
	 * 获取车底交路信息
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrossInfo", method = RequestMethod.POST)
	public Result getCrossInfo(@RequestBody Map<String,Object> reqMap){
		Result result = new Result(); 
		List<CrossInfo> list = null;
	    try{
	    	list = crossService.getCrossInfo(reqMap);
	    	PagingResult page = new PagingResult(crossService.getCrossInfoCount(reqMap),list);
	    	result.setData(page);
	    }catch(Exception e){
			logger.error("getCrossInfo error==" + e.getMessage());
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		}
	
		return result;
	}
	
	/**
	 * 获取车底交路信息
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnitCrossTrainInfo", method = RequestMethod.POST)
	public Result getUnitCrossTrainInfo(@RequestBody Map<String,Object> reqMap){
		Result result = new Result();
		String unitCrossId = (String)reqMap.get("unitCrossId");
		logger.debug("unitCrossId==" + unitCrossId);
		 try{
		    	//先获取unitcross基本信息
			 CrossInfo crossinfo = crossService.getUnitCrossInfoForUnitCrossid(unitCrossId);
			 //再获取unitcrosstrainInfo信息
			 List<CrossTrainInfo> list = crossService.getUnitCrossTrainInfoForUnitCrossId(unitCrossId);
		     List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		     Map<String,Object> dataMap = new HashMap<String,Object>();
		     dataMap.put("crossinfo", crossinfo);
		     dataMap.put("unitCrossTrainInfo", list);
		     dataList.add(dataMap);
			 result.setData(dataList);
		    }catch(Exception e){
				logger.error("getUnitCrossTrainInfo error==" + e.getMessage());
				result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
				result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
			}
		
		return result;
	}
	
	
	/**
	 * 获取车底交路信息
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrossTrainInfo", method = RequestMethod.POST)
	public Result getCrossTrainInfo(@RequestBody Map<String,Object> reqMap){
		Result result = new Result();
		String crossId = (String)reqMap.get("crossId");
		logger.debug("crossId==" + crossId);
		 try{
		    	//先获取cross基本信息
			 CrossInfo crossinfo = crossService.getCrossInfoForCrossid(crossId);
			 //再获取crosstrainInfo信息
			 List<CrossTrainInfo> list = crossService.getCrossTrainInfoForCrossid(crossId);
		     List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		     Map<String,Object> dataMap = new HashMap<String,Object>();
		     dataMap.put("crossInfo", crossinfo);
		     dataMap.put("crossTrainInfo", list);
		     dataList.add(dataMap);
			 result.setData(dataList);
		    }catch(Exception e){
				logger.error("getCrossTrainInfo error==" + e.getMessage());
				result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
				result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
			}
		
		return result;
	}
	
	/**
	 * 生成基本交路单元
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/completeUnitCrossInfo", method = RequestMethod.POST)
	public Result completeUnitCrossInfo(@RequestBody Map<String,Object> reqMap){
		Result result = new Result();
		try{
			//crossid以逗号分隔
			String crossId = StringUtil.objToStr(reqMap.get("crossIds"));
			logger.debug("crossId==" + crossId);
			if(crossId != null){
				String[] crossIds = crossId.split(",");
				for(String crossid :crossIds){
					//根据crossid生成交路单元
					crossService.completeUnitCrossInfo(crossid);
				}
				//生成交路单元完成后，更改表base_cross中的creat_unit_time字段的值
				crossService.updateCrossUnitCreateTime(crossIds);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("completeUnitCrossInfo error==" + e.getMessage());
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		}
		return result;
	}
}
