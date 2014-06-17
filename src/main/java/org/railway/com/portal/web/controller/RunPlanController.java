package org.railway.com.portal.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.railway.com.portal.common.constants.StaticCodeType;
import org.railway.com.portal.common.utils.StringUtil;
import org.railway.com.portal.service.RunPlanService;
import org.railway.com.portal.service.dto.RunPlanTrainDto;
import org.railway.com.portal.web.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/runPlan")
public class RunPlanController {
	 private static Log logger = LogFactory.getLog(CrossController.class.getName());
	 @Autowired
	 private RunPlanService runPlanService;
	 
	 @RequestMapping(method = RequestMethod.GET)
     public String runPlan() {
		 return "runPlan/run_plan";
     }
	 
	 @ResponseBody
	 @RequestMapping(value = "/getRunPlans", method = RequestMethod.POST)
	 public Result getRunPlans(@RequestBody Map<String,Object> reqMap) throws Exception{
		 Result result = new Result();
		 try{ 
			 List<RunPlanTrainDto> runPlans = runPlanService.getTrainRunPlans(reqMap);
			 result.setData(runPlans);
		 }catch(Exception e){
			 result.setCode("-1");
			 result.setMessage("查询运行线出错:" + e.getMessage());
		 } 
		 return result; 
     }
	 
	 
	 @ResponseBody
	 @RequestMapping(value = "/getPlanCross", method = RequestMethod.POST)
	 public Result getPlanCross(@RequestBody Map<String,Object> reqMap) throws Exception{
		 Result result = new Result();
		 try{ 
			 List<RunPlanTrainDto> runPlans = runPlanService.getPlanCross(reqMap);
			 result.setData(runPlans);
		 }catch(Exception e){
			 result.setCode("-1");
			 result.setMessage("查询运行线出错:" + e.getMessage());
		 } 
		 return result; 
     }
	 
	@ResponseBody
	@RequestMapping(value = "/deletePlanCrosses", method = RequestMethod.POST)
	public Result deletePlanCrosses(@RequestBody Map<String,Object> reqMap){
		 Result result = new Result();
		 try{
			 String crossIds = StringUtil.objToStr(reqMap.get("planCrossIds"));
			 if(crossIds != null){
				String[] crossIdsArray = crossIds.split(",");
				//先删除cross_train表中数据
				int countTrain = runPlanService.deletePlanCrossByPlanCorssIds(crossIdsArray); 
			 }  
		 }catch(Exception e){
			 logger.error("deleteUnitCorssInfo error==" + e.getMessage());
			 result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			 result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	 
		 }
		 return result;
	} 
	 

}
