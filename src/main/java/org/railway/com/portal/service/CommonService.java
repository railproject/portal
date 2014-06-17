package org.railway.com.portal.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javasimon.aop.Monitored;
import org.railway.com.portal.common.constants.Constants;
import org.railway.com.portal.entity.Ljzd;
import org.railway.com.portal.entity.TrainType;
import org.railway.com.portal.repository.mybatis.BaseDao;
import org.railway.com.portal.repository.mybatis.LjzdMybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;




/**
 * 基本的service业务功能
 * @author join
 *
 */
@Component
@Transactional
@Monitored
public class CommonService {
	private static final Logger logger = Logger.getLogger(CommonService.class);
	
	private static Map<String, Ljzd> map = new HashMap<String, Ljzd>();
	
	@Autowired
	private LjzdMybatisDao ljzdDao;
	
	@Autowired
	private BaseDao baseDao;
	/**
	 * 通过路局全称查询路基基本信息
	 */
	public Ljzd getLjInfo(String ljqc) {
		if(map.get(ljqc) != null){
			return map.get(ljqc);
		}
		Ljzd dto = (Ljzd)baseDao.selectOneBySql(Constants.LJZDDAO_GET_LJ_INFO, ljqc);
		map.put(ljqc, dto);
		return dto;
	}

	/**
	 * 通过id查询列车类型信息
	 * @param id
	 * @return
	 */
	public  TrainType getTrainType(String id){
		TrainType trainType = (TrainType)baseDao.selectOneBySql(Constants.LJZDDAO_GET_TRAIN_TYPE, id);
		return trainType;
	}
	
	/**
    * 从基础数据库中获取18个路局的基本信息
    * @return
    */
  
    public List<Ljzd> getFullStationInfo(){
    	List<Ljzd> list = baseDao.selectListBySql(Constants.LJZDDAO_GET_FULL_STATION_INFO, null);
    	return list;
    }
	    
	    
}
