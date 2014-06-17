package org.railway.com.portal.repository.mybatis;

import org.railway.com.portal.entity.RunPlanStn;

import java.util.List;

/**
 * Created by speeder on 2014/5/28.
 */
@MyBatisRepository
public interface RunPlanStnDao {

    int addRunPlanStn(List<RunPlanStn> list);
}
