package org.railway.com.portal.entity;

public class HighlineCrossInfo {
//     #{item.startStn,jdbcType=VARCHAR},
//     #{item.crossEndStn,jdbcType=VARCHAR},
//     #{item.spareFlag,jdbcType=VARCHAR},
//     #{item.relevantBureau,jdbcType=VARCHAR},
//     #{item.tokenVehBureau,jdbcType=VARCHAR},
//     #{item.tokenVehDept,jdbcType=VARCHAR},
//     #{item.tokenVehDepot,jdbcType=VARCHAR},
//     #{item.tokenPsgBureau,jdbcType=VARCHAR},
//     #{item.tokenPsgDept,jdbcType=VARCHAR},
//     #{item.crhType,jdbcType=VARCHAR},
//     #{item.note,jdbcType=VARCHAR}, 
//     #{item.createPeople,jdbcType=VARCHAR},  
	//高铁日历交路计划ID（本表ID）
	private String highLineCrossId;
	//交路计划ID（对应PLAN_CROSS表中的PLAN_CROSS_ID）
	private String planCrossId;
	//开始日期（该日历交路第一个车次的始发日期）
	private String crossStartDate;
	//结束日期（该日历交路最后一个车次的终到日期）
	private String crossEndDate;
	private String crossStartStn;
	private String crossEndStn;
	//交路名称
	private String crossName;
	//备用及停运标记（1:开行;2:备用;0:停运）
	private String spareFlag;
	//相关局（局码）
	private String relevantBureau;
	//车辆担当局（局码）
	private String tokenVehBureau;
	//担当车辆段/动车段
	private String tokenVehDept;
	//担当动车所（用于高铁）
	private String tokenVehDepot;
	//客运担当局（局码）
	private String tokenPsgBureau;
	//担当客运段
	private String tokenPsgDept;
	//动车组车型（用于高铁）
	private String crhType;
	//动车组车组号1（用于高铁）
	private String vehicle1;
	//动车组车组号2（用于高铁）
	private String vehicle2;
	//备注
	private String note;
	//创建人
	private String creatPeople;
	//创建人单位
	private String creatPeopleOrg;
	//创建时间（格式：yyyy-mm-dd hh24:mi:ss）
	private String creatTime;
	//审核状态（0:未审核1:审核）
	private int checkType ;
	//审核人
	private String checkPeople;
	
	//始发站
	private String startStn;
	
	//始发站
	private String endStn;
		
	//审核人单位
	private String checkPeopleOrg;
	//审核时间（格式：yyyy-mm-dd hh24:mi:ss）
	private String checkTime;
	 
	public String getCrossStartStn() {
		return crossStartStn;
	}
	public void setCrossStartStn(String crossStartStn) {
		this.crossStartStn = crossStartStn;
	}
	public String getCrossEndStn() {
		return crossEndStn;
	}
	public void setCrossEndStn(String crossEndStn) {
		this.crossEndStn = crossEndStn;
	} 
	
	public String getStartStn() {
		return startStn;
	}
	public void setStartStn(String startStn) {
		this.startStn = startStn;
	}
	public String getEndStn() {
		return endStn;
	}
	public void setEndStn(String endStn) {
		this.endStn = endStn;
	}
	 
	public String getHighLineCrossId() {
		return highLineCrossId;
	}
	public void setHighLineCrossId(String highLineCrossId) {
		this.highLineCrossId = highLineCrossId;
	}
	public String getPlanCrossId() {
		return planCrossId;
	}
	public void setPlanCrossId(String planCrossId) {
		this.planCrossId = planCrossId;
	}
	public String getCrossStartDate() {
		return crossStartDate;
	}
	public void setCrossStartDate(String crossStartDate) {
		this.crossStartDate = crossStartDate;
	}
	public String getCrossEndDate() {
		return crossEndDate;
	}
	public void setCrossEndDate(String crossEndDate) {
		this.crossEndDate = crossEndDate;
	}
	public String getCrossName() {
		return crossName;
	}
	public void setCrossName(String crossName) {
		this.crossName = crossName;
	}
	public String getSpareFlag() {
		return spareFlag;
	}
	public void setSpareFlag(String spareFlag) {
		this.spareFlag = spareFlag;
	}
	public String getRelevantBureau() {
		return relevantBureau;
	}
	public void setRelevantBureau(String relevantBureau) {
		this.relevantBureau = relevantBureau;
	}
	public String getTokenVehBureau() {
		return tokenVehBureau;
	}
	public void setTokenVehBureau(String tokenVehBureau) {
		this.tokenVehBureau = tokenVehBureau;
	}
	public String getTokenVehDept() {
		return tokenVehDept;
	}
	public void setTokenVehDept(String tokenVehDept) {
		this.tokenVehDept = tokenVehDept;
	}
	public String getTokenVehDepot() {
		return tokenVehDepot;
	}
	public void setTokenVehDepot(String tokenVehDepot) {
		this.tokenVehDepot = tokenVehDepot;
	}
	public String getTokenPsgBureau() {
		return tokenPsgBureau;
	}
	public void setTokenPsgBureau(String tokenPsgBureau) {
		this.tokenPsgBureau = tokenPsgBureau;
	}
	public String getTokenPsgDept() {
		return tokenPsgDept;
	}
	public void setTokenPsgDept(String tokenPsgDept) {
		this.tokenPsgDept = tokenPsgDept;
	}
	public String getCrhType() {
		return crhType;
	}
	public void setCrhType(String crhType) {
		this.crhType = crhType;
	}
	public String getVehicle1() {
		return vehicle1;
	}
	public void setVehicle1(String vehicle1) {
		this.vehicle1 = vehicle1;
	}
	public String getVehicle2() {
		return vehicle2;
	}
	public void setVehicle2(String vehicle2) {
		this.vehicle2 = vehicle2;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreatPeople() {
		return creatPeople;
	}
	public void setCreatPeople(String creatPeople) {
		this.creatPeople = creatPeople;
	}
	public String getCreatPeopleOrg() {
		return creatPeopleOrg;
	}
	public void setCreatPeopleOrg(String creatPeopleOrg) {
		this.creatPeopleOrg = creatPeopleOrg;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public int getCheckType() {
		return checkType;
	}
	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}
	public String getCheckPeople() {
		return checkPeople;
	}
	public void setCheckPeople(String checkPeople) {
		this.checkPeople = checkPeople;
	}
	public String getCheckPeopleOrg() {
		return checkPeopleOrg;
	}
	public void setCheckPeopleOrg(String checkPeopleOrg) {
		this.checkPeopleOrg = checkPeopleOrg;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	
	
	
}
