package org.railway.com.portal.web.dto;

import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * Created by star on 5/22/14.
 */
public class PlanLineInfoDto {

    private String trainName;

    private String startBureau;

    private String endBureau;

    private String startStn;

    private String endStn;

    private String startTime;

    private String endTime;

    public PlanLineInfoDto(Map<String, Object> map) {
        this.trainName = MapUtils.getString(map, "TRAIN_NAME");
        this.startBureau = MapUtils.getString(map, "START_BUREAU");
        this.endBureau = MapUtils.getString(map, "END_BUREAU");
        this.startStn = MapUtils.getString(map, "START_STN");
        this.endStn = MapUtils.getString(map, "END_STN");
        this.startTime = MapUtils.getString(map, "START_TIME");
        this.endTime = MapUtils.getString(map, "END_TIME");
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getStartBureau() {
        return startBureau;
    }

    public void setStartBureau(String startBureau) {
        this.startBureau = startBureau;
    }

    public String getEndBureau() {
        return endBureau;
    }

    public void setEndBureau(String endBureau) {
        this.endBureau = endBureau;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
