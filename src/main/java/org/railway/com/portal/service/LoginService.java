package org.railway.com.portal.service;

import org.javasimon.aop.Monitored;
import org.railway.com.portal.repository.mybatis.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 5/15/14.
 */
@Component
@Monitored
public class LoginService {

    @Autowired
    private UserDao userDao;

    public List<Map<String, Object>> getAccountByLoginName(String username) {
        return userDao.getAccountbyUsername(username);
    }
}
