package org.railway.com.portal.service;

import org.javasimon.aop.Monitored;
import org.railway.com.portal.common.constants.Constants;
import org.railway.com.portal.entity.SchemeInfo;
import org.railway.com.portal.repository.mybatis.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
@Component
@Transactional
@Monitored
public class SchemeService {
	
	@Autowired
	private BaseDao baseDao;
	  
    public java.util.List<SchemeInfo> getSchemes(){
	   return baseDao.selectListBySql(Constants.SCHEME_GETSCHEMEINFO, null);
    }

}
