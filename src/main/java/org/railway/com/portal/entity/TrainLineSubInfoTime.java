package org.railway.com.portal.entity;

public class TrainLineSubInfoTime {

	private String stationFlag;
	private int stnSort;
	private String stnBureau;
	private String stnName;
	private String arrTime;
	private String dptTime;
	private String planTrainStnId;
	
	
	public String getPlanTrainStnId() {
		return planTrainStnId;
	}
	public void setPlanTrainStnId(String planTrainStnId) {
		this.planTrainStnId = planTrainStnId;
	}
	public String getStationFlag() {
		return stationFlag;
	}
	public void setStationFlag(String stationFlag) {
		this.stationFlag = stationFlag;
	}
	public int getStnSort() {
		return stnSort;
	}
	public void setStnSort(int stnSort) {
		this.stnSort = stnSort;
	}
	public String getStnBureau() {
		return stnBureau;
	}
	public void setStnBureau(String stnBureau) {
		this.stnBureau = stnBureau;
	}
	public String getStnName() {
		return stnName;
	}
	public void setStnName(String stnName) {
		this.stnName = stnName;
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
	
	
}
