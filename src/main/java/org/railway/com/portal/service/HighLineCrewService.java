package org.railway.com.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javasimon.aop.Monitored;
import org.railway.com.portal.common.constants.Constants;
import org.railway.com.portal.common.utils.DateUtil;
import org.railway.com.portal.common.utils.StringUtil;
import org.railway.com.portal.entity.HighLineCrewInfo;
import org.railway.com.portal.entity.QueryResult;
import org.railway.com.portal.entity.RunPlan;
import org.railway.com.portal.repository.mybatis.BaseDao;
import org.railway.com.portal.repository.mybatis.HighLineCrewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 乘务计划服务
 * Created by speeder on 2014/6/27.
 */
@Monitored
@Component
@Transactional
public class HighLineCrewService {

    @Autowired
    private HighLineCrewDao highLineCrewDao;
    
    @Autowired
    private BaseDao baseDao;

    public HighLineCrewInfo findHighLineCrew(Map<String, Object> map) {
        return highLineCrewDao.findOne(map);
    }

    public List<HighLineCrewInfo> findList(String crewDate,String crewType) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	if("all".equals(crewType)){
    		crewType = null;
    	}
    	map.put("crewDate",crewDate);
    	map.put("crewType",crewType);
        return highLineCrewDao.findList(map);
    }

    public void addCrew(HighLineCrewInfo crewHighlineInfo) {
        highLineCrewDao.addCrew(crewHighlineInfo);
    }

    public void update(HighLineCrewInfo crewHighlineInfo) {
        highLineCrewDao.update(crewHighlineInfo);
    }

    public void delete(String crewHighlineId) {
    	Map<String,String> reqMap = new HashMap<String,String>();
    	reqMap.put("crewHighlineId", crewHighlineId);
        highLineCrewDao.delete(reqMap);
    }
    
    /**
	 * 查询PLAN_TRAIN信息
	 * @param 
	 * @return
	 * @throws Exception 
	 */
    @SuppressWarnings("unchecked")
	public QueryResult<RunPlan>  getRunLineListForRunDate(String runDate,String trainNbr,String rownumstart,String rownumend ) throws Exception{
		Map<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("runDate",runDate );
		reqMap.put("trainNbr",trainNbr );
		reqMap.put("rownumstart",rownumstart );
		reqMap.put("rownumend",rownumend );
		return baseDao.selectListForPagingBySql(Constants.HIGHLINECREWDAO_FIND_RUNPLAN_LIST,reqMap);
	}
    
	
	 /**
	 * 查询乘务计划信息
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<HighLineCrewInfo>  getHighlineCrewListForRunDate(String crewDate,String crewType,String trainNbr,String rownumstart,String rownumend ) throws Exception{
		Map<String,String> reqMap = new HashMap<String,String>();
		if("all".equals(crewType)){
			crewType = null;
		}
		reqMap.put("crewDate",crewDate );
		reqMap.put("crewType",crewType );
		reqMap.put("rownumstart",rownumstart );
		reqMap.put("rownumend",rownumend );
		if(trainNbr != null && !"".equals(trainNbr)){
			reqMap.put("trainNbr","%" +trainNbr + "%");
		}else if("".equals(trainNbr)){
			reqMap.put("trainNbr",null);
		}
		return baseDao.selectListForPagingBySql(Constants.HIGHLINECREWDAO_FIND_HIGHLINE_CREW_LIST,reqMap);
	}
    
	/**
	 * 更新submitType字段值为1
	 * @param crewDate 格式yyyy-MM-dd
	 * @param crewType 乘务类型（1车长、2司机、3机械师）
	 * @return
	 */
	public int updateSubmitType(String crewDate,String crewType){
		Map<String,String> reqMap = new HashMap<String,String>();
		reqMap.put("crewDate",crewDate );
		reqMap.put("crewType", crewType);
		return baseDao.updateBySql(Constants.HIGHLINECREWDAO_UPDATE_SUBMIT_TYPE, reqMap);
	}
	
	/**
	 * 根据crewDate和crewType删除表highline_crew表中数据
	 * @param crewDate 格式：yyyyMMdd
	 * @param crewType
	 * @param recordPepole 
	 * @return 成功删除数据的条数
	 */
	public int deleteHighlineCrewForCrewDate(String crewDate,String crewType,String recordPepole ){
		Map<String,Object> reqMap = new HashMap<String,Object>();
		reqMap.put("crewDate",crewDate);
		reqMap.put("crewType", crewType);
		reqMap.put("recordPepole", recordPepole);
		
		return baseDao.deleteBySql(Constants.HIGHLINECREWDAO_DELETE_HIGHLINE_CREW_FOR_CREWDATE, reqMap);
		
	}
	
	/**
	 * 获取表highline_crew中RecordPeopleOrg字段的值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getRecordPeopleOrgList(){
		List<String> listRecordPeopleOrg = new ArrayList<String>();
		List<Map<String,Object>> list =  baseDao.selectListBySql(Constants.HIGHLINECREWDAO_GET_RECORD_PEOPLE_ORG, "");
	    if(list != null && list.size() > 0 ){
	    	for(Map<String,Object> map : list){ 
	    		listRecordPeopleOrg.add(StringUtil.objToStr(map.get("RECORDPEOPLEORG")));
	    	}
	    }
	    return listRecordPeopleOrg;
	}
	
	/**
	 * 对表highline_crew进行条件分页查询
	 * @param reqMap
	 * 主要有这些字段：
	 * crewStartDate;crewEndDate;crewType;
       crewBureau;recordPeopleOrg;trainNbr;name;rownumstart;rownumend
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<HighLineCrewInfo>   getHighlineCrewBaseInfoForPage(Map<String,Object> reqMap)throws Exception{
		return baseDao.selectListForPagingBySql(Constants.HIGHLINECREWDAO_GET_HIGHLINE_CREW_BASE_INFO_FOR_PAGE, reqMap);
	}
	
	/**
	 * 对表highline_crew进行条件查询
	 * @param reqMap
	 * 主要有这些字段：
	 * crewStartDate;crewEndDate;crewType;
       crewBureau;recordPeopleOrg;trainNbr;name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HighLineCrewInfo> getHighlineCrewBaseInfo(Map<String,Object> reqMap){
			return baseDao.selectListBySql(Constants.HIGHLINECREWDAO_GET_HIGHLINE_CREW_BASE_INFO, reqMap);
	}
	
}
