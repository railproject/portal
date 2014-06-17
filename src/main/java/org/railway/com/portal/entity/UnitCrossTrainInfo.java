package org.railway.com.portal.entity;

import java.util.List;

public class UnitCrossTrainInfo {

	private List<UnitCrossTrainSubInfo> trainInfoList ;
	private String unitCrossTrainId;
	private String unitCrossId;
	private int groupSerialNbr;
	public List<UnitCrossTrainSubInfo> getTrainInfoList() {
		return trainInfoList;
	}
	public void setTrainInfoList(List<UnitCrossTrainSubInfo> trainInfoList) {
		this.trainInfoList = trainInfoList;
	}
	public String getUnitCrossTrainId() {
		return unitCrossTrainId;
	}
	public void setUnitCrossTrainId(String unitCrossTrainId) {
		this.unitCrossTrainId = unitCrossTrainId;
	}
	public String getUnitCrossId() {
		return unitCrossId;
	}
	public void setUnitCrossId(String unitCrossId) {
		this.unitCrossId = unitCrossId;
	}
	public int getGroupSerialNbr() {
		return groupSerialNbr;
	}
	public void setGroupSerialNbr(int groupSerialNbr) {
		this.groupSerialNbr = groupSerialNbr;
	} 
	
	
	
}
