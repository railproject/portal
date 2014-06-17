package org.railway.com.portal.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.javasimon.aop.Monitored;
import org.railway.com.portal.common.utils.DateUtil;
import org.railway.com.portal.common.utils.StringUtil;
import org.railway.com.portal.service.task.DayTrainPlanTask;
import org.railway.com.portal.service.task.DaytaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Monitored
public class TreadService {
	private static final Logger logger = Logger.getLogger(TreadService.class);

	@Autowired
	private TrainInfoService trainInfoService ;
	
	@Autowired
	private  QuoteService quoteService;
	/**
	 * @param runDate 格式yyyy-mm-dd
	 * @param chartId
	 * @param dayCount
	 * @param operation
	 * @param totalCount
	 * @return
	 */
	public void actionDayWork(final DaytaskDto reqDto,final int dayCount){
		//System.err.println("trainInfoService111===" + trainInfoService);
		//TODO 通过查询数据库得到totalCount
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("chartId", reqDto.getChartId());
		params.put("operation", reqDto.getOperation());
		//列车总数
		final int totalCount = trainInfoService.getTrainInfoCount(params);
		//final int totalCount = 2;
		//final int totalCount = trainInfoService.getTrainsAndTimesCount(reqDto.getChartId(), reqDto.getOperation());
		logger.info("traintotalCount===" + totalCount);
		//System.err.println("traintotalCount===" + totalCount);
		
		(new Thread() {
			public void run() {
				try{
					
				
				
				ExecutorService threadPool = Executors.newFixedThreadPool(dayCount);
				CompletionService<Map<String,Object>> pool = new ExecutorCompletionService<Map<String,Object>>(threadPool);
				
				for(int i =0;i<dayCount;i++){
					//推送某天开始的消息
					// 推送开始某天记录的信息
					quoteService.sendQuotes(reqDto.getRunDate(),dayCount, 0, "plan.day.begin");
					String currentRunDate = DateUtil.getDateByDay(reqDto.getRunDate(), -i);	
					pool.submit(new DayTrainPlanTask(currentRunDate,reqDto.getChartId(),reqDto.getOperation(),totalCount,trainInfoService));
				}
				
				for(int i =0;i<dayCount;i++){
					
						Map<String,Object> returnMap = pool.take().get();
						String runDate = StringUtil.objToStr(returnMap.get("runDate"));
						int count = (Integer)returnMap.get("totalCount");
						//System.err.println("dayTraincount===" + count);
						//推送某天结束的信息
						quoteService.sendQuotes(runDate,dayCount, count, "plan.day.end");
						//System.err.println("rundate==" + runDate);
					
				}
				//关闭线程池
				threadPool.shutdown();	
				//推送全部结束的信息
				quoteService.sendQuotes("", 0, 0, "plan.end");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		
	}
}
