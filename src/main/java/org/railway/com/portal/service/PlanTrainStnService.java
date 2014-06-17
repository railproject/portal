package org.railway.com.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.javasimon.aop.Monitored;
import org.railway.com.portal.common.constants.Constants;
import org.railway.com.portal.common.utils.DateUtil;
import org.railway.com.portal.common.utils.StringUtil;
import org.railway.com.portal.entity.CrossInfo;
import org.railway.com.portal.entity.PlanCrossInfo;
import org.railway.com.portal.entity.PlanTrain;
import org.railway.com.portal.entity.TrainTimeInfo;
import org.railway.com.portal.repository.mybatis.BaseDao;
import org.railway.com.portal.service.dto.ParamDto;
import org.railway.com.portal.service.dto.PlanTrainDto;
import org.railway.com.portal.service.dto.TrainlineTemplateDto;
import org.railway.com.portal.service.dto.TrainlineTemplateSubDto;
import org.railway.com.portal.web.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Monitored
public class PlanTrainStnService {
	private static final Logger logger = Logger.getLogger(PlanTrainStnService.class);
	
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private RemoteService remoteService;
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private BaseDao baseDao;
    
    @Autowired
    private TrainInfoService trainInfoService;
    
    @Autowired
    private TrainTimeService trainTimeService;
    
    @Autowired
    private TreadService treadService;
    
    @Autowired
    private CrossService crossService;
    
    @Autowired
    private RunPlanService runPlanService;
    
    /**
	 * 更新数据表plan_train中字段daylyplan_flag,的值
	 * @param base_train_id
	 * @return
	 * @throws Exception
	 */
   
    public Result updatePlanTrainDaylyPlanFlag(Map<String,Object> reqMap) throws Exception {
    	Result result = new Result();
    	 baseDao.updateBySql(Constants.TRAINPLANDAO_UPDATE_PLANFLAG, reqMap);
    	return result;
    }
    /**
     * 更新表plan_train中字段check_state的值
     */
 
    public Result updatePlanTrainCheckStats(String base_train_id) throws Exception{
    	   Result result = new Result();
    	   //更新数据库
    	   baseDao.updateBySql(Constants.TRAINPLANDAO_UPDATE_CHECKSTATE, base_train_id);
    	   return result;
    }
  
    /**
     * runDate :格式：yyyyMMdd
     */
  
    public  List<ParamDto>  getTotalTrains(String runDate,String startBureauFull,String trainNbr){
    	    List<ParamDto>  list = new ArrayList<ParamDto>();
    	    Map<String,String> paramMap = new HashMap<String,String>();
    	    paramMap.put("runDate", runDate);
    	    paramMap.put("startBureauFull",startBureauFull );
    	    if(trainNbr != null && !"".equals(trainNbr.trim())){
    	    	paramMap.put("trainNbr", trainNbr);
    	    }
    	    
    	    List<Map<String,Object>> mapList = baseDao.selectListBySql(Constants.TRAINPLANDAO_GET_TOTALTRAINS, paramMap);
    	    if(mapList != null && mapList.size() > 0){
    	    	 //System.err.println("mapList.size===" + mapList.size());
    	    	for(Map<String,Object> map :mapList ){
    	    		ParamDto dto = new ParamDto();
    	    		dto.setSourceEntityId(StringUtil.objToStr(map.get("BASE_TRAIN_ID")));
    	    		dto.setPlanTrainId(StringUtil.objToStr(map.get("PLAN_TRAIN_ID")));
    	    		String time = StringUtil.objToStr(map.get("RUN_DATE"));
    	    		dto.setTime(DateUtil.formateDate(time));
    	    		list.add(dto);
    	    	}
    	    }
    	    return list;
    }
    


	/**
	 * 查询车次的始发站和终点站等信息
	 * 
	 * @param runDate
	 *            开行日期
	 * @param trainNbr
	 *            车次
	 * @param count
	 *            返回的条数
	 */
	
	public List<PlanTrainDto> getTrainShortInfo(String runDate,
			String trainNbr, int count) {
		List<PlanTrainDto> returnList = new ArrayList<PlanTrainDto>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("runDate",runDate);
		paramMap.put("rownum",count);
		if (trainNbr != null && !"".equals(trainNbr)) {
			paramMap.put("trainNbr",trainNbr);
		}
		// 调用dao获取数据  TRAINPLANDAO_GET_TRAIN_SHORTINFO
		List<Map<String, Object>> listMap = baseDao.selectListBySql(Constants.TRAINPLANDAO_GET_TRAIN_SHORTINFO, paramMap);
		if (listMap != null && listMap.size() > 0) {
			for (Map<String, Object> map : listMap) {
				PlanTrainDto dto = new PlanTrainDto();
				dto.setPlanTrainId(StringUtil.objToStr(map.get("PLAN_TRAIN_ID")));
				dto.setEndStn(StringUtil.objToStr(map.get("END_STN")));
				dto.setRunDate(StringUtil.objToStr(map.get("RUN_DATE")));
				dto.setStartStn(StringUtil.objToStr(map.get("START_STN")));
				dto.setTrainNbr(StringUtil.objToStr(map.get("TRAIN_NBR")));
				returnList.add(dto);
			}
		}
		return returnList;
	}

	/**
	 * 访问接口，将返回数据存到本地数据库
	 * startDate 格式:yyyy-mm-dd
	 * @throws Exception
	 */

	public int importTainPlan( String schemeId,
			 String startDate,  String dayCount) throws Exception {
		//调用后台接口之前推送一条消息到页面
		quoteService.sendQuotes("", 0, 0, "plan.getInfo.begin");
		List<PlanCrossInfo> listPlanCross = new ArrayList<PlanCrossInfo>();
		List<CrossInfo> listCross = crossService.getUnitCrossInfoForChartId(schemeId);
		if(listCross != null && listCross.size() > 0){
			for(CrossInfo cross : listCross){
				PlanCrossInfo planCross = new PlanCrossInfo();
				BeanUtils.copyProperties(planCross, cross);
				//单独设置属性名不一样的字段
				planCross.setPlanCrossId(UUID.randomUUID().toString());
				planCross.setBaseChartId(cross.getChartId());
				planCross.setBaseChartName(cross.getChartName());
				planCross.setBaseCrossId(cross.getBaseCrossId());
				planCross.setCrossStartDate(startDate.replaceAll("-",""));
				//TODO 暂时指定时间，以后要通过计算
				planCross.setCrossEndDate("20140830");
				listPlanCross.add(planCross);
			}
		}
		//添加数据到表plan_cross
		int count = crossService.addPlanCrossInfo(listPlanCross);
		//做交路计划，第一个参数传null,表示全部交路都做
		int crossCount = runPlanService.generateRunPlan(null, startDate, Integer.valueOf(dayCount));
		//全部处理完了，推送一条消息到页面
		quoteService.sendQuotes("", 0, 0, "plan.end");
		
		return crossCount;
	}

	
	/**
	 * 根据方案id查询列车并根据列车id查询列车时刻表
	 * @param schemeId 方案id
	 * @param operation 货运 or 客运
	 * @param runDate格式 yyyy-mm-dd
	 * @return
	 */
	public List<TrainlineTemplateDto>  getTrainsWithSchemeId(String schemeId,String operation,String runDate){
		   List<TrainlineTemplateDto> trainsList = new ArrayList<TrainlineTemplateDto>();
		   //通过方案查询列车
		   Map<String,Object> reqMap = new HashMap<String,Object>();
		   reqMap.put("chartId",schemeId );
		   reqMap.put("operation",operation );
		   long time1 = System.currentTimeMillis();
		   List<PlanTrain> list = trainInfoService.getTrains(reqMap);
		   if(list != null && list.size() > 0){
			  
			   for(PlanTrain dto : list){
				   TrainlineTemplateDto trainLineDto = new TrainlineTemplateDto();
				   //车次
				   trainLineDto.setTrainNbr(dto.getTrainNbr());
				   //始发局全称
				   trainLineDto.setStartBureauFull(dto.getStartBureauFull());
				   //终到局全称
				   trainLineDto.setEndBureauFull(dto.getEndBureauFull());
				   //始发站名
				   trainLineDto.setStartStn(dto.getStartStn());
				   //终到站名
				   trainLineDto.setEndStn(dto.getEndStn());
				   //始发时间
				   trainLineDto.setStartTime(runDate + " " + dto.getStartTimeStr());
				   //开行日期
				   trainLineDto.setRunDate(runDate);
				   //运行天数
				   int runDay = dto.getRelativeTargetTimeDay();
				   String endDate = DateUtil.getDateByDay(runDate, -runDay);
				   //终到时间
				   trainLineDto.setEndTime(endDate + " " + dto.getEndTimeStr());
				   String trainId = dto.getPlanTrainId();
				   //System.err.println("trainId==" + trainId);
				   trainLineDto.setBaseTrainId(trainId);
				   if(trainId != null && !"".equals(trainId)){
					   
				   
				   //获取列车时刻表信息
				   List<TrainTimeInfo> subList = trainTimeService.getTrainTimes(trainId);
				  // System.err.println("subList.size==" + subList.size());
				   if(subList != null && subList.size() > 0){
					   List<TrainlineTemplateSubDto> stationList = new ArrayList<TrainlineTemplateSubDto>();
					   for(TrainTimeInfo subDto :subList ){
						   TrainlineTemplateSubDto  tempDto = new TrainlineTemplateSubDto();
						   //站点名称
						   tempDto.setName(subDto.getStnName());
						   int rundays = subDto.getRunDays();
						   String date = DateUtil.getDateByDay(runDate, -rundays);
						   //到站时间
						   tempDto.setSourceTime(date + " " + subDto.getArrTime());
						   //出发时间
						   tempDto.setTargetTime(date + " " + subDto.getDptTime());
						   tempDto.setTrackName(subDto.getTrackName());
						   tempDto.setChildIndex(subDto.getChildIndex());
						   stationList.add(tempDto);
					   }
					   //System.err.println("stationList.size==" + stationList.size());
					   trainLineDto.setStationList(stationList);
				   }
			     }
				   trainsList.add(trainLineDto);
			   }
		   }
		   long time2 = System.currentTimeMillis();
		   logger.debug("所花时间：" +(time2-time1)/1000);
		   return trainsList;
	}
	

	/**
	 * 对站点设置基本信息
	 * 
	 * @param map
	 * @param stationType
	 * @return
	 */
	private TrainlineTemplateSubDto setSubDtoValue(Map<String, Object> map,
			String stationType) {
		TrainlineTemplateSubDto subDto = new TrainlineTemplateSubDto();
		/**
		 * String bureauName = StringUtil.objToStr(targetItemMap.get("bureauName"));
								dto.setEndBureauFull(bureauName ==null? "":bureauName);
		 */
		subDto.setName(StringUtil.objToStr(map.get("name")));
		
		//subDto.setBureauName(bureauName ==null? "":bureauName);
		subDto.setSourceTime(StringUtil.objToStr(map.get("sourceTimeDto2")));
		subDto.setTargetTime(StringUtil.objToStr(map.get("targetTimeDto2")));
		subDto.setIndex(StringUtil.strToInteger(StringUtil.objToStr(map
				.get("index"))));
		String bureauName = StringUtil.objToStr(map.get("bureauName"));
		//subDto.setStnBureauFull(bureauName ==null? "":bureauName);
		subDto.setStnBureauFull(bureauName);
		// TODO:局码,现在后台报文还没这个字段
		subDto.setStnBureau(StringUtil.objToStr(map.get("")));
		// 站点类型 【1：始发 2：经由 3：终到】
		subDto.setStationType(stationType);
		//股道
		subDto.setTrackName(StringUtil.objToStr(map.get("trackName")));
		//运行天数
		String sourceTime = StringUtil.objToStr(map.get("sourceTimeDto2"));
		if(sourceTime != null && !"".equals(sourceTime)&& sourceTime.length()>0 ){
			subDto.setRunDays(Integer.valueOf(sourceTime.substring(0,1)));
		}
		
		return subDto;
	}
	
}
