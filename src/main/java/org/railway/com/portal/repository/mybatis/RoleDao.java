package org.railway.com.portal.repository.mybatis;

import org.railway.com.portal.entity.Permission;
import org.railway.com.portal.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 5/15/14.
 */
@MyBatisRepository
public interface RoleDao {

    List<Role> getRoleByAccId(int accId);
    
    List<Permission> getPermissionByRoleIds(Map<String, String> queryMap);
}
