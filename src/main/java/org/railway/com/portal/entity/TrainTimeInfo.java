package org.railway.com.portal.entity;

public class TrainTimeInfo {
	 private int childIndex;
	 private String stnName;
	 private String bureauShortName;
	 private String trackName;
	 private String arrTime;
	 private String dptTime;
	 private int runDays; 
	
	 
	public int getChildIndex() {
		return childIndex;
	}
	public void setChildIndex(int childIndex) {
		this.childIndex = childIndex;
	}
	public String getStnName() {
		return stnName;
	}
	public void setStnName(String stnName) {
		this.stnName = stnName;
	}
	public String getBureauShortName() {
		return bureauShortName;
	}
	public void setBureauShortName(String bureauShortName) {
		this.bureauShortName = bureauShortName;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getArrTime() {
		return arrTime;
	}
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	public String getDptTime() {
		return dptTime;
	}
	public void setDptTime(String dptTime) {
		this.dptTime = dptTime;
	}
	public int getRunDays() {
		return runDays;
	}
	public void setRunDays(int runDays) {
		this.runDays = runDays;
	}
 


}
