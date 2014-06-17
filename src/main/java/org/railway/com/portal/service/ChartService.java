package org.railway.com.portal.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javasimon.aop.Monitored;
import org.railway.com.portal.repository.mybatis.ChartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by speeder on 2014/5/27.
 */
@Component
@Transactional
@Monitored
public class ChartService {

    private static final Log logger = LogFactory.getLog(ChartService.class);

    @Autowired
    private ChartDao chartDao;

    public Map<String, Object> getPlanTypeChart(Map<String, Object> map) {
        logger.debug("getPlanTypeChart::::");
        return chartDao.getPlanTypeCount(map);
    }

    public Map<String, Object> getPlanLineCount(Map<String, Object> map) {
        logger.debug("getPlanLineCount::");
        return chartDao.getPlanLineCount(map);
    }

    public Map<String, Object> getLev1CheckCount(Map<String, Object> map) {
        logger.debug("getPlanTypeChart::::");
        return chartDao.getLev1CheckCount(map);
    }

    public Map<String, Object> getLev2CheckCount(Map<String, Object> map) {
        logger.debug("getPlanLineCount::");
        return chartDao.getLev2CheckCount(map);
    }
}
