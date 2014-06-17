/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.railway.com.portal.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.railway.com.portal.service.ShiroRealm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author calvin
 */
@Controller
public class LoginController {

    private final static Log logger = LogFactory.getLog(LoginController.class);

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
        logger.info("Login:" + userName + " login failed!");
		return "redirect:login";
	}

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        String result = "redirect:login";
        currentUser.logout();
        logger.info("Logout:" + ((ShiroRealm.ShiroUser)currentUser.getPrincipal()).getUsername() + " logout!");
        return result;
    }

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
}
