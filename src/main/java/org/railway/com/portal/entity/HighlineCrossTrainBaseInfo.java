package org.railway.com.portal.entity;

/**
 * 通过highlineCrossId查询
 * 交路下所有列车的始发站，终到站，始发时间和终到时间
 * @author join
 *
 */
public class HighlineCrossTrainBaseInfo {
	private String trainNbr ;
	private String startStn ;
	//始发时间，格式yyyy-MM-dd hh:mm:ss
	private String startTime ;
	private String endStn;
	private String endTime ;
	public String getTrainNbr() {
		return trainNbr;
	}
	public void setTrainNbr(String trainNbr) {
		this.trainNbr = trainNbr;
	}
	public String getStartStn() {
		return startStn;
	}
	public void setStartStn(String startStn) {
		this.startStn = startStn;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndStn() {
		return endStn;
	}
	public void setEndStn(String endStn) {
		this.endStn = endStn;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
   
	
}
