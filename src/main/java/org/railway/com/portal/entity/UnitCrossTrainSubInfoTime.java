package org.railway.com.portal.entity;

/**
 * 交路单元中某辆车的始发站和终到站等信息对象
 * @author join
 *
 */
public class UnitCrossTrainSubInfoTime {

	private String id;
	//站名
	private String stnName;
	//站序
	private int stnSort;
	private String arrTime;
	private String dptTime;
	private int runDays;
	private String trackName;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStnName() {
		return stnName;
	}
	public void setStnName(String stnName) {
		this.stnName = stnName;
	}
	public int getStnSort() {
		return stnSort;
	}
	public void setStnSort(int stnSort) {
		this.stnSort = stnSort;
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
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	
	
}
