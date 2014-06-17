package org.railway.com.portal.service.dto;

public class TrainRunDto {
	private String day;
	private String runFlag; 
	
	public TrainRunDto(String day, String runFlag) {
		super();
		this.day = day;
		this.runFlag = runFlag;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getRunFlag() {
		return runFlag;
	}
	public void setRunFlag(String runFlag) {
		this.runFlag = runFlag;
	}

}
