package org.railway.com.portal.repository.mybatis;

import java.util.List;
import java.util.Map;

import org.railway.com.portal.entity.CrossInfo;
 
public interface CrossMybatisDao {

	CrossInfo get(String id); 

	List<CrossInfo> search(Map<String, Object> parameters);

	void saveCrossBeach(List<CrossInfo> crosses);

	void delete(String id);
	
	void deleteBach(String ids);
}
