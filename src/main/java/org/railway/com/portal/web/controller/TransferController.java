package org.railway.com.portal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用于默认的页面跳转
 * @author join
 *
 */
@Controller
@RequestMapping(value = "/default/transfer")
public class TransferController {
    
	@RequestMapping(value = "planReviewLines", method = RequestMethod.GET)
	public String planReviewLines() {
		return "plan/plan_review_lines";
	}
	
	@RequestMapping(value = "planReviewAll", method = RequestMethod.GET)
	public String planReviewAll() {
		return "plan/plan_review_all";
	}
	
	@RequestMapping(value = "planReview", method = RequestMethod.GET)
	public String planReview() {
		return "plan/plan_review";
	}
	
	@RequestMapping(value = "planRunlineBatch", method = RequestMethod.GET)
	public String planRunlineBatch() {
		return "plan/plan_runline_batch";
	}
	
	@RequestMapping(value = "planDesign", method = RequestMethod.GET)
	public String planDesign() {
		return "plan/plan_design";
	}
	
	@RequestMapping(value = "planConstruction", method = RequestMethod.GET)
	public String planConstruction() {
		return "plan/plan_construction";
	}
	
	@RequestMapping(value = "/plan/planView", method = RequestMethod.POST)
	public ModelAndView planReviews(HttpServletRequest request){
		ModelAndView model = new ModelAndView("plan/plan_view");
		
		model.addObject("schemeVal", request.getParameter("schemeVal"));
		model.addObject("schemeText", request.getParameter("schemeText"));
		model.addObject("days",request.getParameter("days"));
		model.addObject("startDate",request.getParameter("startDate"));

		return model;
	}
}
