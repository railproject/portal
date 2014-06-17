package org.railway.com.portal.repository.mybatis;

import java.util.List;
import java.util.Map;

import org.railway.com.portal.entity.CrossTrainInfo;
 
public interface CrossTrainMybatisDao {

	CrossTrainInfo get(String id);

	List<CrossTrainInfo> search(Map<String, Object> parameters);

	void saveCrossTrainInfoBeach(List<CrossTrainInfo> crosses);

	void delete(String id);
	
	void deleteBach(String ids);
}
