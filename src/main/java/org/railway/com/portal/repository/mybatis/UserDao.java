package org.railway.com.portal.repository.mybatis;

import org.railway.com.portal.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 5/15/14.
 */
@MyBatisRepository
public interface UserDao {
    User getUserByUsernameAndAccId(Map<String, Object> params);

    List<Map<String, Object>> getAccountbyUsername(String username);
}
