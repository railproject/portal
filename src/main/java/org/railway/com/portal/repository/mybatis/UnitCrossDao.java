package org.railway.com.portal.repository.mybatis;

import org.railway.com.portal.entity.PlanCross;

import java.util.List;

/**
 * Created by speeder on 2014/5/28.
 */
@MyBatisRepository
public interface UnitCrossDao {

    List<PlanCross> findPlanCross(List<String> planCrossIds);
}
