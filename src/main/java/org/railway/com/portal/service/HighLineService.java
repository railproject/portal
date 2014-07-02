package org.railway.com.portal.service;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.railway.com.portal.common.constants.Constants;
import org.railway.com.portal.common.utils.DateUtil;
import org.railway.com.portal.entity.BaseTrainInfo;
import org.railway.com.portal.entity.HighLineCrossTrainInfo;
import org.railway.com.portal.entity.HighlineCrossInfo;
import org.railway.com.portal.entity.HighlineCrossTrainBaseInfo;
import org.railway.com.portal.entity.HighlineTrainRunLine;
import org.railway.com.portal.entity.PlanCrossInfo;
import org.railway.com.portal.entity.PlanTrain;
import org.railway.com.portal.repository.mybatis.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class HighLineService{
	private static final Logger logger = Logger.getLogger(HighLineService.class); 
	
	@Autowired
	private BaseDao baseDao;
	
	public static void main(String[] args) throws IOException, IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
//		InputStream is = new FileInputStream(
//				"C:\\Users\\Administrator\\Desktop\\work\\交路相关\\对数表模板1.xls");
//		
//		CrossService a = new CrossService();
//		System.out.println(StringUtils.isEmpty(""));
//		Date date = dateFormat.parse("20140518");   
//		Calendar calendar = new GregorianCalendar();
//		calendar.setTime(date);
//		calendar.add(Calendar.DATE, 4 - 1);
	 
		//System.out.println(dateFormat.format(calendar.getTime()));
//		a.actionExcel(is); 
//		System.out.println("G11(".substring(0,"G11(".indexOf('(')));
	} 

	/**
	 * 对表plan_cross批量插入数据
	 * @param list
	 * @return
	 */
	public int addPlanCrossInfo(List<PlanCrossInfo> list){
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("trainCrossList",list );
		int count = baseDao.insertBySql(Constants.CROSSDAO_ADD_PLAN_CROSS_INFO, reqMap);
		return count;
	}
	@SuppressWarnings("unchecked")
	public List<BaseTrainInfo> getBaseTrainInfoByParam(Map<String, String> map){
		return this.baseDao.selectListBySql(Constants.CROSSDAO_GET_BASETRAIN_INFO_FOR_PARAM, map);
	}
	
	/**
	 * 通过plan_cross_id查询plancross信息
	 * @param planCrossId 表plan_cross中主键
	 * @return
	 */
	public PlanCrossInfo getPlanCrossInfoForPlanCrossId(String planCrossId){
		return (PlanCrossInfo)this.baseDao.selectOneBySql(Constants.CROSSDAO_GET_PLANCROSSINFO_FOR_PLANCROSSID, planCrossId);
	}

	@SuppressWarnings("unchecked")
	public List<HighlineCrossInfo>  createHighLineCross(String startDate){
		
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("startDate", startDate);
		List<PlanCrossInfo> planCrosses = this.baseDao.selectListBySql(Constants.GET_PLANCROSSINFO_BY_STARTDATE, pMap);
		List<HighlineCrossInfo> hList = new ArrayList<HighlineCrossInfo>();
		List<HighLineCrossTrainInfo> tList = new ArrayList<HighLineCrossTrainInfo>(); 
		if(planCrosses != null && planCrosses.size() > 0){
			Map<String, Object> tMap = new HashMap<String, Object>();
			tMap.put("startDate", startDate);  
			for(PlanCrossInfo pc : planCrosses){ 
				HighlineCrossInfo highlineCrossInfo = new HighlineCrossInfo(); 
				highlineCrossInfo.setHighLineCrossId(UUID.randomUUID().toString());
				try {
					BeanUtils.copyProperties(highlineCrossInfo, pc);  
				} catch (IllegalAccessException e) {
					
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					
					e.printStackTrace();
				} 
				tMap.put("planCrossId", pc.getPlanCrossId());
				tMap.put("groupSerialNbr", pc.getGroupSerialNbr()); 
				List<PlanTrain> trains = this.baseDao.selectListBySql(Constants.TRAINPLANDAO_FIND_BY_GROUPSERIALBRR, tMap);
				for(int i = 0; i < trains.size(); i++){
					PlanTrain ct = trains.get(i);
					if(ct.getTrainSort() == 1){ 
						highlineCrossInfo.setCrossStartDate(DateUtil.format(ct.getStartTime(), "yyyyMMdd"));
						highlineCrossInfo.setCrossStartStn(ct.getStartStn());
						// 如果只有第一个车的时候默认设置为第一个车的终到站和时间
						highlineCrossInfo.setCrossEndDate(DateUtil.format(ct.getEndTime(), "yyyyMMdd"));
						highlineCrossInfo.setEndStn(ct.getEndStn()); 
					}else if(i == trains.size() - 1){ 
						highlineCrossInfo.setCrossEndDate(DateUtil.format(ct.getEndTime(), "yyyyMMdd"));
						highlineCrossInfo.setCrossEndStn(ct.getEndStn()); 
					}
					HighLineCrossTrainInfo ht = new HighLineCrossTrainInfo();
					ht.setHighLineCrossId(highlineCrossInfo.getHighLineCrossId());
					ht.setHighLineTrainId(UUID.randomUUID().toString());
					try {
						BeanUtils.copyProperties(ht, ct);
					} catch (IllegalAccessException e) {
						
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						
						e.printStackTrace();
					} 
					tList.add(ht);
				}  
				hList.add(highlineCrossInfo);
			} 
		}   
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("hList", hList);
		if(hList.size() > 0){
			this.baseDao.insertBySql(Constants.CROSSDAO_ADD_HIGHLINE_CROSS, hMap);
			this.baseDao.insertBySql(Constants.CROSSDAO_ADD_HIGHLINE_CROSS_TRAIN, tList);
		} 
		return hList;
		//insert to database 
	}
	 
	/**
	 * 批量向表highline_cross表中插入数据
	 * @param hList
	 * @return
	 */
	public int  batchAddHighlineCross(List<HighlineCrossInfo> hList){
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("hList", hList);
		return this.baseDao.insertBySql(Constants.CROSSDAO_ADD_HIGHLINE_CROSS, hMap);
	}
	
	/**
	 * 批量向表highline_cross_train表中插入数据
	 * @param tList
	 * @return
	 */
	public int batchAddHighlineCrossTrain(List<HighLineCrossTrainInfo> tList){
		return this.baseDao.insertBySql(Constants.CROSSDAO_ADD_HIGHLINE_CROSS_TRAIN, tList);
	}
	/**
	 * 查询highlineCross信息
	 * @param planCrossId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HighlineCrossInfo> getHighlineCrossList(String crossStartDate){
		Map<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("crossStartDate",crossStartDate );
		return baseDao.selectListBySql(Constants.HIGHLINECROSSDAO_GET_HIGHLINE_CROSS_LIST, reqMap);
	}
	
	/**
	 * 查询highlineCrossTrain信息
	 * @param highlineCrossId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HighLineCrossTrainInfo> getHighlineCrossTrainList(String highlineCrossId){
		Map<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("highlineCrossId",highlineCrossId );
		return baseDao.selectListBySql(Constants.HIGHLINECROSSDAO_GET_HIGHLINE_CROSS_TRAIN_LIST, reqMap);
	}
	
	/**
	 * 通过highlineCrossId查询
     * 交路下所有列车的始发站，终到站，始发时间和终到时间
	 * @param highlineCrossId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HighlineCrossTrainBaseInfo> getHighlineCrossTrainBaseInfoList(String highlineCrossId){
		return baseDao.selectListBySql(Constants.HIGHLINECROSSDAO_GET_HIGHLINE_CROSS_TRAIN_BASEINFO, highlineCrossId);
	}
	
	/**
	 * 通过highlineCrossId查询该交路下的列车经由站信息
	 * @param highlineCrossId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HighlineTrainRunLine> getHighlineTrainTimeForHighlineCrossId(String highlineCrossId){
		return baseDao.selectListBySql(Constants.HIGHLINECROSSDAO_GET_HIGHLINE_TRAINTIME_FOR_HIGHLINE_CROSSID, highlineCrossId);
	}
	
	/**
	 * 根据highlineCrossId删除表highline_cross_train中数据
	 * @param highlineCrossIds,多个id，以逗号分隔
	 * @return 成功删除的数目
	 */
	public int deleteHighlienCrossTrainForHighlineCrossId(String highlineCrossIds){
		Map<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("highlineCrossIds", highlineCrossIds);
		return baseDao.deleteBySql(Constants.HIGHLINECROSSDAO_DELETE_HIGHLINECROSSTRAIN_FOR_ID, reqMap);
	}
	
	
	/**
	 * 根据highlineCrossId删除表highline_cross中数据
	 * @param highlineCrossIds, 多个id，以逗号分隔
	 * @return 成功删除的数目
	 */
	public int deleteHighlienCrossForHighlineCrossId(String highlineCrossIds){
		Map<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("highlineCrossIds", highlineCrossIds);
		return baseDao.deleteBySql(Constants.HIGHLINECROSSDAO_DELETE_HIGHLINECROSS_FOR_ID, reqMap);
	}
	
	/**
	 * 根据highlineCrossId更新车底信息
	 * @param highlineCrossId
	 * @param vehicle1
	 * @param vehicle2
	 * @return
	 */
	public int updateHighLineVehicle(String highlineCrossId,String vehicle1,String vehicle2){
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("highlineCrossId", highlineCrossId);
		reqMap.put("vehicle1",vehicle1 );
		reqMap.put("vehicle2",vehicle2 );
		return baseDao.deleteBySql(Constants.HIGHLINECROSSDAO_UPDATE_HIGHLINE_VEHICLE, reqMap);
	}
	
	
	/**
	 * 更新highlinecross中check的基本信息
	 * @param checkType 审核状态
	 * @param checkPeople  审核人
	 * @param checkPeopleOrg 审核人所属单位
	 * @param highlineCrossIds
	 * @return
	 */
	public int updateHiglineCheckInfo(String checkType,String checkPeople,String checkPeopleOrg,String highlineCrossIds){
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("checkType",checkType );
		reqMap.put("checkPeople", checkPeople);
		reqMap.put("checkPeopleOrg",checkPeopleOrg );
		reqMap.put("highlineCrossIds",highlineCrossIds);
		return baseDao.deleteBySql(Constants.HIGHLINECROSSDAO_UPDATE_HIGHLINE_CHECKINFO, reqMap);
	}
	
}
