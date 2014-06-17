package org.railway.com.portal.web.dto;

/**
 * Created by star on 5/21/14.
 */
public class PlanLineCheckResultDto {

    private int isTrainInfoMatch;

    private int isTimeTableMatch;

    private int isRoutingMatch;

    public int getIsTrainInfoMatch() {
        return isTrainInfoMatch;
    }

    public void setIsTrainInfoMatch(int isTrainInfoMatch) {
        this.isTrainInfoMatch = isTrainInfoMatch;
    }

    public int getIsTimeTableMatch() {
        return isTimeTableMatch;
    }

    public void setIsTimeTableMatch(int isTimeTableMatch) {
        this.isTimeTableMatch = isTimeTableMatch;
    }

    public int getIsRoutingMatch() {
        return isRoutingMatch;
    }

    public void setIsRoutingMatch(int isRoutingMatch) {
        this.isRoutingMatch = isRoutingMatch;
    }
}
