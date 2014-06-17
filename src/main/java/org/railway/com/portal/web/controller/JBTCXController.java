package org.railway.com.portal.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.railway.com.portal.common.constants.StaticCodeType;
import org.railway.com.portal.entity.SchemeInfo;
import org.railway.com.portal.entity.TrainTimeInfo;
import org.railway.com.portal.service.JBTCXService;
import org.railway.com.portal.service.SchemeService;
import org.railway.com.portal.service.TrainInfoService;
import org.railway.com.portal.service.TrainTimeService;
import org.railway.com.portal.service.dto.PagingResult;
import org.railway.com.portal.web.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/jbtcx")
public class JBTCXController {
	private static Log logger = LogFactory.getLog(JBTCXController.class.getName());
	
	
	@Autowired
	private JBTCXService jbtcxService;
	
	@Autowired
	private SchemeService schemeService;
	
	@Autowired
	private TrainTimeService trainTimeService;
	
	@Autowired
	private TrainInfoService trainInfoService;
	
	 @RequestMapping(method = RequestMethod.GET)
     public String content() {
		 return "plan/plan_runline_check";
     }
	 
		@ResponseBody
		@RequestMapping(value = "/querySchemes", method = RequestMethod.POST)
		public Result querySchemes(@RequestBody Map<String,Object> reqMap){
			Result result = new Result();
			try{
				//System.err.println("queryConstructionDetail~~reqMap="+reqMap);
				  
//				//路局全称
//				String startBureauFull = StringUtil.objToStr(reqMap.get("startBureauFull"));
				//调用后台接口
				List<SchemeInfo> schemeInfos = schemeService.getSchemes();
				result.setData(schemeInfos);
			}catch(Exception e){
				logger.error(e.getMessage(), e);
				result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
				result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());		
			}
			return result;
		} 
	 
	/**
	 * 统计路局运行车次信息
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTrains", method = RequestMethod.POST)
	public Result queryTrains(@RequestBody Map<String,Object> reqMap){
		Result result = new Result();
		try{
			//System.err.println("queryConstructionDetail~~reqMap="+reqMap);
			  
//			//路局全称
//			String startBureauFull = StringUtil.objToStr(reqMap.get("startBureauFull"));
			//调用后台接口
			PagingResult page = new PagingResult(trainInfoService.getTrainInfoCount(reqMap), trainInfoService.getTrainsForPage(reqMap));
			result.setData(page);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());		
		}
		return result;
	} 
	
	@ResponseBody
	@RequestMapping(value = "/queryTrainTimes", method = RequestMethod.POST)
	public Result queryTrainTimes(@RequestBody Map<String,Object> reqMap){
		Result result = new Result();
		try{
			//System.err.println("queryTrainTimes~~reqMap="+reqMap);
			  
//			//路局全称
//			String startBureauFull = StringUtil.objToStr(reqMap.get("startBureauFull"));
			//调用后台接口
			List<TrainTimeInfo> times = trainTimeService.getTrainTimes(reqMap.get("trainId").toString());
			result.setData(times);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());		
		}
		return result;
	} 
}
