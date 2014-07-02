package org.railway.com.portal.repository.mybatis;


import java.util.List;
import java.util.Map;

import org.railway.com.portal.entity.HighLineCrewInfo;

/**
 * HighLinedao
 * Created by speeder on 2014/6/27.
 */
@MyBatisRepository
public interface HighLineCrewDao {
    HighLineCrewInfo findOne(Map<String, Object> map);

    List<HighLineCrewInfo> findList(Map<String, Object> map);

    void addCrew(HighLineCrewInfo crewHighlineInfo);

    void update(HighLineCrewInfo crewHighlineInfo);

    void delete(Map<String,String> reqMap);
}
