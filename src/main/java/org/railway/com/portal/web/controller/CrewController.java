package org.railway.com.portal.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/crew")
public class CrewController {

	/**
	 * 乘务信息查询跳转
	 * @return
	 */
	@RequestMapping(value = "page/all", method = RequestMethod.GET)
	public String pageCrewAll() {
		return "hightlineCrew/hightline_crew_all";
	}

	/**
	 * 车长乘务信息跳转
	 * @return
	 */
	@RequestMapping(value = "page/cz", method = RequestMethod.GET)
	public String pageCrewCz() {
		return "hightlineCrew/hightline_crew_cz";
	}
	
	/**
	 * 司机乘务信息跳转
	 * @return
	 */
	@RequestMapping(value = "page/sj", method = RequestMethod.GET)
	public String pageCrewSj() {
		return "hightlineCrew/hightline_crew_sj";
	}
	
	/**
	 * 机械师乘务信息跳转
	 * @return
	 */
	@RequestMapping(value = "page/jxs", method = RequestMethod.GET)
	public String pageCrewJxs() {
		return "hightlineCrew/hightline_crew_jxs";
	}
}
