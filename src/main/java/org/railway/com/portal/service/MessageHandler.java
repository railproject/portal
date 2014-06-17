package org.railway.com.portal.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.railway.com.portal.common.utils.StringUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageHandler implements MessageListener{

	private static Log logger = LogFactory.getLog(MessageHandler.class.getName());

	@Autowired
	private PlanTrainStnService planTrainStnService;
	@Override
	public void onMessage(Message message) {
		
		logger.debug("----------------------------------------------");
		logger.debug("response==" + new String(message.getBody()));
		//JSONObject json = new JSONObject();
		//JSONObject map = json.fromObject(object)
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> msg = null;
		try {
			 msg= mapper.readValue(message.getBody(), Map.class);
			 logger.debug("msg======" + msg);
			 //System.err.println("msg======" + msg);
			 if(msg != null && msg.size()>0){
				 Map<String,Object> result = ((Map<String,Object>)msg.get("result"));
				 String resultString = (String)result.get("result");
				 JSONObject json = new JSONObject();
				 JSONObject result1 = json.fromObject(resultString);
				 //System.err.println("result1==" + result1);
				// Map<String,Object> result1 = mapper.readValue(resultString, Map.class);
				 if(result1 != null && result1.size()>0){
					 String code = StringUtil.objToStr(result.get("code"));
					 String userparamStr = (String)result.get("userparam");
					 JSONObject paramObj = json.fromObject(userparamStr);
					 //成功
					 if("0".equals(code)){
						 Map<String,Object> reqMap = new HashMap<String,Object>();
						 String baseTrainId = StringUtil.objToStr(paramObj.get("sourceEntityId"));
						 String daylyPlanId = StringUtil.objToStr(result1.get("id"));
						 String planTrainId = StringUtil.objToStr(paramObj.get("planTrainId"));
						 reqMap.put("planTrainId", planTrainId);
						 reqMap.put("daylyPlanId",daylyPlanId );
						 //更新表plan_train中字段DAILYPLAN_FLAG值为0
						 planTrainStnService.updatePlanTrainDaylyPlanFlag(reqMap);
					 }
				 }	 
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("----------------------------------------------");
		
	}


}
