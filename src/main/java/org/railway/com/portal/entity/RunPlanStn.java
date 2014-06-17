package org.railway.com.portal.entity;

import java.sql.Timestamp;

/**
 * Created by speeder on 2014/5/28.
 */
public class RunPlanStn {

    private String planTrainStnId;
    private String planTrainId;
    private int stnSort;
    private String stnName;
    private String stnBureauShortName;
    private String stnBureauFullName;
    private String arrTrainNbr;
    private String dptTrainNbr;
    private Timestamp arrTime;
    private String arrTimeStr;
    private Timestamp dptTime;
    private String dptTimeStr;
    private Timestamp baseArrTime;
    private Timestamp baseDptTime;
    private String upDown;
    private String trackNbr;
    private String trackName;
    private int platform;
    private int psgFlag;
    private int locoFlag;
    private String tecType;
    private String stnType;
    private int boundaryInOut;
    private int runDays;

    public String getPlanTrainStnId() {
        return planTrainStnId;
    }

    public void setPlanTrainStnId(String planTrainStnId) {
        this.planTrainStnId = planTrainStnId;
    }

    public String getPlanTrainId() {
        return planTrainId;
    }

    public void setPlanTrainId(String planTrainId) {
        this.planTrainId = planTrainId;
    }

    public int getStnSort() {
        return stnSort;
    }

    public void setStnSort(int stnSort) {
        this.stnSort = stnSort;
    }

    public String getStnName() {
        return stnName;
    }

    public void setStnName(String stnName) {
        this.stnName = stnName;
    }

    public String getStnBureauShortName() {
        return stnBureauShortName;
    }

    public void setStnBureauShortName(String stnBureauShortName) {
        this.stnBureauShortName = stnBureauShortName;
    }

    public String getStnBureauFullName() {
        return stnBureauFullName;
    }

    public void setStnBureauFullName(String stnBureauFullName) {
        this.stnBureauFullName = stnBureauFullName;
    }

    public String getArrTrainNbr() {
        return arrTrainNbr;
    }

    public void setArrTrainNbr(String arrTrainNbr) {
        this.arrTrainNbr = arrTrainNbr;
    }

    public String getDptTrainNbr() {
        return dptTrainNbr;
    }

    public void setDptTrainNbr(String dptTrainNbr) {
        this.dptTrainNbr = dptTrainNbr;
    }

    public Timestamp getArrTime() {
        return arrTime;
    }

    public void setArrTime(Timestamp arrTime) {
        this.arrTime = arrTime;
    }

    public Timestamp getDptTime() {
        return dptTime;
    }

    public void setDptTime(Timestamp dptTime) {
        this.dptTime = dptTime;
    }

    public Timestamp getBaseArrTime() {
        return baseArrTime;
    }

    public void setBaseArrTime(Timestamp baseArrTime) {
        this.baseArrTime = baseArrTime;
    }

    public Timestamp getBaseDptTime() {
        return baseDptTime;
    }

    public void setBaseDptTime(Timestamp baseDptTime) {
        this.baseDptTime = baseDptTime;
    }

    public String getUpDown() {
        return upDown;
    }

    public void setUpDown(String upDown) {
        this.upDown = upDown;
    }

    public String getTrackNbr() {
        return trackNbr;
    }

    public void setTrackNbr(String trackNbr) {
        this.trackNbr = trackNbr;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getPsgFlag() {
        return psgFlag;
    }

    public void setPsgFlag(int psgFlag) {
        this.psgFlag = psgFlag;
    }

    public int getLocoFlag() {
        return locoFlag;
    }

    public void setLocoFlag(int locoFlag) {
        this.locoFlag = locoFlag;
    }

    public String getTecType() {
        return tecType;
    }

    public void setTecType(String tecType) {
        this.tecType = tecType;
    }

    public String getStnType() {
        return stnType;
    }

    public void setStnType(String stnType) {
        this.stnType = stnType;
    }

    public int getBoundaryInOut() {
        return boundaryInOut;
    }

    public void setBoundaryInOut(int boundaryInOut) {
        this.boundaryInOut = boundaryInOut;
    }

    public int getRunDays() {
        return runDays;
    }

    public void setRunDays(int runDays) {
        this.runDays = runDays;
    }

    public String getArrTimeStr() {
        return arrTimeStr;
    }

    public void setArrTimeStr(String arrTimeStr) {
        this.arrTimeStr = arrTimeStr;
    }

    public String getDptTimeStr() {
        return dptTimeStr;
    }

    public void setDptTimeStr(String dptTimeStr) {
        this.dptTimeStr = dptTimeStr;
    }
}
