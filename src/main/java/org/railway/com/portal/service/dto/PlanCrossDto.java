package org.railway.com.portal.service.dto;

public class PlanCrossDto {
	
	private String planCrossId;
	
	private String planCrossName;
	
	private String baseCrossId;
	
	private String tokenVehBureau;
	
	private String startBureau;

	public String getTokenVehBureau() {
		return tokenVehBureau;
	}

	public void setTokenVehBureau(String tokenVehBureau) {
		this.tokenVehBureau = tokenVehBureau;
	}

	public String getStartBureau() {
		return startBureau;
	}

	public void setStartBureau(String startBureau) {
		this.startBureau = startBureau;
	}

	public String getPlanCrossId() {
		return planCrossId;
	}

	public void setPlanCrossId(String planCrossId) {
		this.planCrossId = planCrossId;
	}

	public String getPlanCrossName() {
		return planCrossName;
	}

	public void setPlanCrossName(String planCrossName) {
		this.planCrossName = planCrossName;
	}

	public String getBaseCrossId() {
		return baseCrossId;
	}

	public void setBaseCrossId(String baseCrossId) {
		this.baseCrossId = baseCrossId;
	} 

}
