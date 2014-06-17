package org.railway.com.portal.web.dto;

/**
 * Created by star on 5/14/14.
 */
public class PlanLineGridY {
    private String stnName;
    private int isCurrentBureau;
    private String stationType;
    public PlanLineGridY(String stnName,int isCurrentBureau,String stationType){
    	this(stnName);
    	this.isCurrentBureau = isCurrentBureau;
    	this.stationType = stationType;
    }
    public PlanLineGridY(String stnName) {
        this.stnName = stnName;
    }

    public String getStnName() {
        return stnName;
    }

    public void setStnName(String stnName) {
        this.stnName = stnName;
    }
	public int getIsCurrentBureau() {
		return isCurrentBureau;
	}
	public void setIsCurrentBureau(int isCurrentBureau) {
		this.isCurrentBureau = isCurrentBureau;
	}
	public String getStationType() {
		return stationType;
	}
	public void setStationType(String stationType) {
		this.stationType = stationType;
	}
    
    
}
