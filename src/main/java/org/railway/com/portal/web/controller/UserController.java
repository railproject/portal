package org.railway.com.portal.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.railway.com.portal.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 5/15/14.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final static Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "{username}/account", method = RequestMethod.GET)
    public List<Map<String, Object>> getAccounts(@PathVariable String username) {
        logger.debug("-X GET /user/" + username + "/account");
        return loginService.getAccountByLoginName(username);
    }
}
