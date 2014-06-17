package org.railway.com.portal.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.javasimon.aop.Monitored;
import org.railway.com.portal.common.constants.Constants;
import org.railway.com.portal.common.utils.DateUtil;
import org.railway.com.portal.common.utils.StringUtil;
import org.railway.com.portal.entity.PlanTrain;
import org.railway.com.portal.repository.mybatis.BaseDao;
import org.railway.com.portal.service.dto.TrainlineTemplateDto;
import org.railway.com.portal.service.dto.TrainlineTemplateSubDto;
import org.railway.com.portal.service.task.DaytaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Monitored
public class TrainInfoService {
	private static final Logger logger = Logger.getLogger(TrainInfoService.class);
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 根据列车id查询起始站和终到站时刻信息
	 */
	public List<TrainlineTemplateSubDto> getStartEndTrainInfoForTrainId(String baseTrainId){
		return baseDao.selectListBySql(Constants.TRAININFO_GET_START_END_TRAINTIME_FOR_TRAINID, baseTrainId);
		
	}
	/**
	 * 根据列车id查询经由时刻信息
	 * @param baseTrainId
	 * @return
	 */
	public List<TrainlineTemplateSubDto>  getTrainTimeInfoForTrainId(String baseTrainId){
		return baseDao.selectListBySql(Constants.TRAININFO_GETTRAIN_TIME_INFO_FOR_TRAINID, baseTrainId);
	}
	
	/**
	 * 根据列车id查询列车基本信息
	 * @param baseTrainId
	 * @return
	 */
	public TrainlineTemplateDto getTrainInfoForTrainId(String baseTrainId){
		return (TrainlineTemplateDto)baseDao.selectOneBySql(Constants.TRAININFO_GETTRAININFO_FOR_TRAINID, baseTrainId);
	}
	/**
	 * 解析从数据库中获取的数据为列车的List对象
	 * @param reqDto
	 * @return
	 */
	public List<TrainlineTemplateDto>  getTrainsAndTimesForList(DaytaskDto reqDto){
		//运行日期格式：yyyy-mm-dd
		String runDate = reqDto.getRunDate();
		String chartId = reqDto.getChartId();
		List<TrainlineTemplateDto> returnList = new ArrayList<TrainlineTemplateDto>();
		//从数据库获取的数据列表
		List<Map<String,Object>> listMap = getTrainsAndTimesForPage(reqDto.getChartId(),reqDto.getOperation(),reqDto.getRownumstart(),reqDto.getRownumend());
		
		int size = listMap.size();
		
		//不是同一列车,
		List<TrainlineTemplateSubDto> stationList = new ArrayList<TrainlineTemplateSubDto>();
		TrainlineTemplateDto dto = new TrainlineTemplateDto();
		//设置一个主键，为后面存库做准备
		dto.setPlanTrainId(UUID.randomUUID().toString());
		
		if(listMap != null && size > 0){
			
			for(int i = 0;i<size;i++){
				
				Map<String,Object> map = listMap.get(i);
				TrainlineTemplateSubDto subDto = new TrainlineTemplateSubDto();
				
				
				String baseTrainId = StringUtil.objToStr(map.get("BASE_TRAIN_ID"));
				String baseTrainIdNext = "";
				if(i+1 < size){
					
					Map<String,Object> mapnext = listMap.get(i+1);
					baseTrainIdNext = StringUtil.objToStr(mapnext.get("BASE_TRAIN_ID"));
					if(baseTrainIdNext.equals(baseTrainId)){
						dto = setTrainlineTemplateDto(map,dto,runDate,chartId);
						//设置经由中的planTrainId
						subDto = setTrainlineTemplateSubDto(map,subDto,runDate);
						subDto.setPlanTrainId(dto.getPlanTrainId());//设置经由
						stationList.add(subDto);
					}else{
						//System.err.println("~~~~最后一个站" + subDto.getIndex() + "@@@@" + subDto.getName());
						//System.err.println("~~~~ stationList.size()==" + stationList.size());
						dto = setTrainlineTemplateDto(map,dto,runDate,chartId);
						subDto = setTrainlineTemplateSubDto(map,subDto,runDate);
						subDto.setPlanTrainId(dto.getPlanTrainId());//设置经由
						stationList.add(subDto);
						dto.setStationList(stationList);
						returnList.add(dto);
						
						//不是同一列车,
						stationList = new ArrayList<TrainlineTemplateSubDto>();
						dto = new TrainlineTemplateDto();
						//设置一个主键，为后面存库做准备
						dto.setPlanTrainId(UUID.randomUUID().toString());
					}
				}else{
					//System.err.println("最后一个map==" + map);
					dto = setTrainlineTemplateDto(map,dto,runDate,chartId);
					//设置经由中的planTrainId
					subDto = setTrainlineTemplateSubDto(map,subDto,runDate);
					subDto.setPlanTrainId(dto.getPlanTrainId());//设置经由
					stationList.add(subDto);
					
					dto.setStationList(stationList);
					returnList.add(dto);
				}
				
				
			}
		}
		
		return returnList;
	}
	
	/**
	 * 根据baseTrainId获取列车信息和列车时刻表信息
	 * @param baseTrainId
	 * @param type  查询类型 ALL  START_END:只查询始发站和终到站
	 * @return
	 */
	public  TrainlineTemplateDto getTrainInfoAndTimeForTrainId(String baseTrainId,String type){
		//通过baseTrainId和chartId查询列车信息
		TrainlineTemplateDto dto = getTrainInfoForTrainId(baseTrainId);
		
		if(dto != null){
			//根据列车id获取列车时刻表
			List<TrainlineTemplateSubDto> listSubDto = null;
			if(type.equals(Constants.STATION_TYPE_ALL)){
				//获取全部的经由信息
				listSubDto = getTrainTimeInfoForTrainId(baseTrainId);
			}else if(type.equals(Constants.STATION_TYPE_START_END)){
				//只获取起始站和终到站信息
				listSubDto = getStartEndTrainInfoForTrainId(baseTrainId);
			}
			
			//解析经由信息
			Map<String,Object> myscheduleMap = new HashMap<String,Object>();
			if(listSubDto != null && listSubDto.size() > 0){
				int size = listSubDto.size() ;
				 List<TrainlineTemplateSubDto> myRouteItemList = new ArrayList<TrainlineTemplateSubDto>();
				for(int i = 0;i<size;i++){
					TrainlineTemplateSubDto subDto = listSubDto.get(i);
					if(i == 0){
						//起始站对象
						myscheduleMap.put("sourceItemDto", subDto);
					}else if(i == (size -1)){
						//终到站
						myscheduleMap.put("targetItemDto", subDto);
						//终到站的运行天数即为整个列车的运行天数
						if(subDto != null){
							dto.setRundays(subDto.getTargetDay()== null ?0:subDto.getTargetDay());
						}else{
							dto.setRundays(0);
						}
						
					}else{
						//
						myRouteItemList.add(subDto);
					}
				}
				myscheduleMap.put("routeItemDtos", myRouteItemList);
			}
			dto.setScheduleMap(myscheduleMap);
		}
		
		return dto;
	}
	
	/**
	 * 将后台接口返回数据存入本地PLAN_TRAIN和PLAN_TRAIN_STN库中
	 * @param list
	 * @param tempStartDate 格式yyyy-mm-dd
	 */
	public void addTrainPlanLine(List<TrainlineTemplateDto> list) {
		        try{
		        	if(list != null && list.size() > 0){
		        		
						for(TrainlineTemplateDto dto : list){
							
							List<TrainlineTemplateSubDto> subList = dto.getStationList();
							
							if(subList != null && subList.size() > 0){
								
								Map<String,Object>  map = new HashMap<String,Object>();
								
								map.put("trainStnList", subList);
								//批量插入数据表train_plan_stn
								int successCountStn = baseDao.insertBySql(Constants.TRAINPLANDAO_ADD_TRAIN_PLAN_STN, map);
								//System.err.println("count of inserting into train_plan_stn==" + successCountStn);
									
							}
							
						}
						//插入数据库
						Map<String,Object>  trainmap = new HashMap<String,Object>();
						trainmap.put("trainList",list);
					    int successCount = baseDao.insertBySql(Constants.TRAINPLANDAO_ADD_TRAIN_PLAN, trainmap);
						logger.info("count of inserting into train_plan==" + successCount);
					}	
		        }catch(Exception e){
					//不做任何处理，只打印日志
					e.printStackTrace();
					//logger.error("存表plan_train失败，plan_train_id["+trainId +"],runDate["+runDate+"]");
				}
			
	}
		
	
	
	/**
	 * 设置列车信息
	 * @param map
	 * @param dto
	 * @param runDate
	 * @return
	 */
	private TrainlineTemplateDto setTrainlineTemplateDto(Map<String,Object> map,TrainlineTemplateDto dto ,String runDate,String chartId){
		
		//所经局
		String passBureau = StringUtil.objToStr(map.get("PASS_BUREAU"));
		
		dto.setPassBureau(passBureau);
		//trainScope1:直通；0:管内
		if(passBureau !=null && !"".equals(passBureau)){
			if(passBureau.length()==1){
				dto.setTrainScope(0);
			}else{
				dto.setTrainScope(1);
			}
		}else{
			dto.setTrainScope(3);
		}
		dto.setTrainTypeId(StringUtil.objToStr(map.get("TRAIN_TYPE_ID")));
		
		dto.setBaseTrainId(StringUtil.objToStr(map.get("BASE_TRAIN_ID")));
		
		//车次
		String trainNbr = StringUtil.objToStr(map.get("TRAIN_NBR"));
		//始发站
		String startStn = StringUtil.objToStr(map.get("START_STN"));
		//始发时刻
		String startTime = StringUtil.objToStr(map.get("START_TIME"));
		dto.setTrainNbr(trainNbr);
		dto.setStartStn(startStn);
		//方案id
		dto.setBaseChartId(chartId);
		dto.setRunDate(runDate);
		
		if(runDate != null && !"".equals(runDate)){
			dto.setRunDate_8(runDate.replaceAll("-", ""));
		}
		
		dto.setEndStn(StringUtil.objToStr(map.get("END_STN")));
		dto.setStartBureauFull(StringUtil.objToStr(map.get("START_BUREAU_FULL")));
		dto.setEndBureauFull(StringUtil.objToStr(map.get("END_BUREAU_FULL")));
		dto.setStartBureau(StringUtil.objToStr(map.get("START_BUREAU")));
		dto.setEndBureau(StringUtil.objToStr(map.get("END_BUREAU")));
		//列车运行的天数
//		int runDaysAll = (( BigDecimal)map.get("RUN_DAYS_ALL")).intValue();
		int runDaysAll = MapUtils.getIntValue(map, "RUN_DAYS_ALL", 0);
		dto.setStartTime(runDate + " " + startTime);
		String endRunDate = DateUtil.getDateByDay(runDate, -runDaysAll);
		dto.setEndTime(endRunDate + " " + StringUtil.objToStr(map.get("END_TIME")));
		//始发日期+始发车次+始发站+计划始发时刻
		String planTrainSign  = runDate.replaceAll("-","") + "-" + trainNbr + "-" +startStn + "-" +startTime;
		dto.setPlanTrainSign(planTrainSign);
		return dto;
	}
	
	/**
	 * 设置经由站信息
	 */
	private TrainlineTemplateSubDto setTrainlineTemplateSubDto(Map<String,Object> map,TrainlineTemplateSubDto subDto,String runDate){
		//设置主键，为后面存库做准备
		subDto.setPlanTrainStnId(UUID.randomUUID().toString());
		//同一列车，设置经由站
		//站点名
		subDto.setName(StringUtil.objToStr(map.get("STN_NAME")));
		//设置列车号
		subDto.setTrainNbr(StringUtil.objToStr(map.get("TRAIN_NBR")));
		//所属局简称
		subDto.setBureauShortName(StringUtil.objToStr(map.get("STN_BUREAU")));
		//所属局全称
		subDto.setStnBureauFull(StringUtil.objToStr(map.get("STN_BUREAU_FULL")));
		//股道
		subDto.setTrackName(StringUtil.objToStr(map.get("TRACK_NAME")));
		subDto.setIndex((( BigDecimal)map.get("STN_SORT")).intValue());
		subDto.setRunDays((( BigDecimal)map.get("RUN_DAYS")).intValue());
		
		//int  rundays = (( BigDecimal)map.get("RUN_DAYS")).intValue();
		int rundays = MapUtils.getIntValue(map, "RUN_DAYS", 0);
		String currentRunDate = DateUtil.getDateByDay(runDate, -rundays);	
		//到站时间
		subDto.setSourceTime(currentRunDate + " " + StringUtil.objToStr(map.get("ARR_TIME")) );
		//离站时间
		subDto.setTargetTime(currentRunDate + " " + StringUtil.objToStr(map.get("DPT_TIME")) );
		
		subDto.setBaseArrTime(subDto.getSourceTime());
		subDto.setBaseDptTime(subDto.getTargetTime());
		return subDto;
	}
	/**
	 * 通过方案id，operation查询列车和列车时刻表的总数量
	 * @param chartId 方案id
	 * @param operation 货运 or 客运
	 * @return
	 */
	public int getTrainsAndTimesCount(String chartId,String operation){
		 int count = 0;
		 Map<String,Object> reqMap = new HashMap<String,Object>();
		 reqMap.put("chartId",chartId );
		 reqMap.put("operation",operation );
		 List<Map<String,Object>> list = baseDao.selectListBySql(Constants.TRAININFO_GET_TRAINS_AND_TIMES_COUNT, reqMap);
	     if(list != null && list.size() > 0){
	    	//只有一条数据
	    	 Map<String,Object> map = list.get(0);
	    	 count = (( BigDecimal)map.get("COUNT")).intValue();
	     }
	     return count;
	}
	/**
	 * 通过方案id，operation查询列车和列车时刻表(分页)
	 * @param chartId 方案id
	 * @param operation 货运 or 客运
	 * @param rownumstart
	 * @param rownumend
	 * @return
	 */
	 public List<Map<String,Object>> getTrainsAndTimesForPage(String chartId,String operation,int rownumstart,int rownumend){
		 Map<String,Object> reqMap = new HashMap<String,Object>();
		 reqMap.put("chartId",chartId );
		 reqMap.put("operation",operation );
		 reqMap.put("rownumstart", rownumstart);
		 reqMap.put("rownumend", rownumend);
		 return baseDao.selectListBySql(Constants.TRAININFO_GET_TRAINS_AND_TIMES_FORPAGE, reqMap);
	 }
	  /**
	   * 根据方案id等信息查询列车信息列表(分页)
	   */
	  public List<PlanTrain> getTrainsForPage(Map<String, Object> params){
		 return baseDao.selectListBySql(Constants.TRAININFO_GETTRAININFO_PAGE, params);
	  }
	  /**
	   * 根据方案id等信息查询列车信息列表
	   */
	  public List<PlanTrain> getTrains(Map<String, Object> params){
		 return baseDao.selectListBySql(Constants.TRAININFO_GETTRAININFO, params);
	  }
	  /**
	   * 根据方案id等信息查询列车总数
	   */
	  public int getTrainInfoCount(Map<String,Object> params){
		  List<Map<String,Object>> countList = baseDao.selectListBySql(Constants.TRAININFO_GETTRAININFO_COUNT, params);
		  int count= 0;
		  if(countList != null && countList.size() > 0){
			  //只有一条数据
			  Map<String,Object> map = countList.get(0);
			  count = (( BigDecimal)map.get("COUNT")).intValue();
		  }
		  return count;
	  }
}
