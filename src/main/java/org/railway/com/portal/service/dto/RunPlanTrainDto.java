package org.railway.com.portal.service.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class RunPlanTrainDto {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private String trainNbr;
	
	private List<TrainRunDto> runPlans = new LinkedList<TrainRunDto>(); 
	
	public RunPlanTrainDto(String sd, String ed) { 
		try { 
			Date startDay = dateFormat.parse(sd);
			Date endDay = dateFormat.parse(ed);
			int r = Integer.parseInt(String.valueOf((endDay.getTime() - startDay.getTime())/(1000*3600*24)));
			Calendar caleandar = GregorianCalendar.getInstance();
			caleandar.setTime(startDay);
			for(int i = 0; i <= r; i++){ 
				caleandar.add(Calendar.DATE, i == 0 ? 0 : 1);  
				String currDay = dateFormat.format(caleandar.getTime()); 
				this.runPlans.add(new TrainRunDto(currDay, null));
			} 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	} 
	public String getTrainNbr() {
		return trainNbr;
	}

	public void setTrainNbr(String trainNbr) {
		this.trainNbr = trainNbr;
	}

	public List<TrainRunDto> getRunPlans() {
		return runPlans;
	}

	public void setRunPlans(List<TrainRunDto> runPlans) {
		this.runPlans = runPlans;
	}
	
	public void setRunFlag(String runDay, String runFlag) {
		 for(TrainRunDto tr : this.runPlans){
			 if(tr.getDay().equals(runDay)){
				 tr.setRunFlag(runFlag); 
				 return;
			 }
		 }
	}
	
	
	public static void main(String[] args) {
		new RunPlanTrainDto("20140527", "20140706");
	}
 
	

}
