package org.railway.com.portal.repository.mybatis;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 5/13/14.
 */
@MyBatisRepository
public interface BureauDao {
    List<Map<String, Object>> getBureauList();
}
