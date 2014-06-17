package org.railway.com.portal.service;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javasimon.aop.Monitored;
import org.railway.com.portal.repository.mybatis.RunLineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by star on 5/13/14.
 */
@Component
@Monitored
public class RunLineService {

	
    private final static Log logger = LogFactory.getLog(RunLineService.class);

    @Autowired
    private RunLineDao runLineDao;

    public List<Map<String, Object>> findLineTimeTableByLineId(String lineId) {
        logger.debug("findLineTimeTableByLineId::::");
        return runLineDao.findLineTimeTableByLineId(lineId);
    }

    public Map<String, Object> findLineInfoByLineId(String lineId) {
        logger.debug("findLineInfoByLineId::::");
        return runLineDao.findLineInfoByLineId(lineId);
    }

    public Map<String, Object> findUnknownRunLine(String bureau, String date) throws ParseException {
        logger.debug("findUnknownRunLine::::::");
        Map<String, Object> params = Maps.newHashMap();
        params.put("bureau", bureau);
        try {
            params.put("startDate", new Timestamp(DateUtils.parseDate(date + " 00:00:00", new String[] {"yyyy-MM-dd hh:mm:ss"}).getTime()));
            params.put("endDate", new Timestamp(DateUtils.parseDate(date + " 23:59:59", new String[] {"yyyy-MM-dd hh:mm:ss"}).getTime()));
        } catch (ParseException e) {
            logger.error("findUnknownRunLine::::", e);
            throw e;
        }
        return runLineDao.findUnknownRunLine(params);
    }
}
