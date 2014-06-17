package org.railway.com.portal.repository.mybatis;

import java.util.Map;

/**
 * Created by speeder on 2014/5/27.
 */
@MyBatisRepository
public interface ChartDao {

    Map<String, Object> getPlanTypeCount(Map<String, Object> map);

    Map<String, Object> getPlanLineCount(Map<String, Object> map);

    Map<String, Object> getLev1CheckCount(Map<String, Object> map);

    Map<String, Object> getLev2CheckCount(Map<String, Object> map);
}
