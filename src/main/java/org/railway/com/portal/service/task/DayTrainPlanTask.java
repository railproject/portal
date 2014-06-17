package org.railway.com.portal.service.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.railway.com.portal.service.TrainInfoService;

/**
 * Map中的值有：
 * runDate,totalCount
 * @author join
 *
 */
public class DayTrainPlanTask implements Callable<Map<String,Object>>{

	
	private String runDate;
	private String chartId;
	private String operation;
	private int totalCount;
	private int pageSize = 20;
	private int threadCount;
	private int totalThreadCount = 20;
	private TrainInfoService trainInfoService;
	public DayTrainPlanTask(String runDate,String chartId,String operation,int totalCount,TrainInfoService trainInfoService){
		this.runDate = runDate;
		this.totalCount = totalCount;
		this.chartId = chartId;
		this.operation = operation;
		this.trainInfoService = trainInfoService;
		if (this.totalCount % pageSize >0) {
			threadCount = this.totalCount / pageSize + 1;
		} else {
			threadCount = this.totalCount / pageSize;
		}
		
		
	}
	/**
	 * 返回的是runDay 格式yyyy-mm-dd
	 */
	@Override
	public Map<String,Object> call() throws Exception {
		
		//对所有数据进行分页
		ExecutorService service =Executors.newFixedThreadPool(totalThreadCount);
		CompletionService<Integer> completion = new ExecutorCompletionService<Integer>(service);
		int temp = threadCount;
		
		while(threadCount >0){
			DaytaskDto dto = new DaytaskDto();
			dto.setChartId(chartId);
			dto.setOperation(operation);
			dto.setRunDate(runDate);
			dto.setRownumend(threadCount*pageSize);
			dto.setRownumstart((threadCount-1)*pageSize+1);
			completion.submit(new TrainsTask(dto,trainInfoService));
			threadCount --;
		}
		
		int totalCount =0;
		
		for(int i =1;i<=temp;i++){
			try {
				int count = 0;
				
				count = completion.take().get();
				
				totalCount = totalCount + count;
				//System.err.print("count==" + count);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("totalCount", totalCount);
		returnMap.put("runDate", runDate);
		service.shutdown();
		return returnMap;
	}

	
	
}
