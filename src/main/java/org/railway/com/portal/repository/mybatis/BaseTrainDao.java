package org.railway.com.portal.repository.mybatis;

import org.railway.com.portal.entity.RunPlan;

import java.util.List;
import java.util.Map;

/**
 * Created by speeder on 2014/5/28.
 */
@MyBatisRepository
public interface BaseTrainDao {

    List<RunPlan> findBaseTrainByPlanCrossid(Map<String, Object> map);
}
