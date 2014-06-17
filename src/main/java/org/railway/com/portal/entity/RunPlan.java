package org.railway.com.portal.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Plan_Train对象
 * Created by speeder on 2014/5/28.
 */
public class RunPlan {

    private String planTrainId;
    private String planTrainSign;
    private String planCrossId;
    private String marshallingName;
    private int groupSerialNbr;
    private int trainSort;
    private String preTrainId;
    private String nextTrainId;
    private String runDate;
    private String trainNbr;
    private String startTimeStr;
    private Timestamp startDateTime;
    private String endTimeStr;
    private Timestamp endDateTime;
    private String startBureauShortName;
    private String startBureauFullName;
    private String startStn;
    private String endBureauShortName;
    private String endBureauFullName;
    private String endStn;
    private String passBureau;
    private int trainScope;
    private String trainTypeId;
    private int highLineFlag;
    private String baseChartId;
    private String baseTrainId;
    private int hightLineRule;
    private int commonLineRule;
    private String appointWeek;
    private String appointDay;
    private int dayGap;
    private int spareFlag;
    private int spareApplyFlag;
    private int createType;
    private Date createDateTime;
    private int checkLev1Type;
    private int checkLev2Type;
    // 上图标记
    private int dailyPlanFlag;
    // 上图时间
    private Date dailyPlanTime;
    // 上图次数
    private int dailyPlanTimes;
    private List<RunPlanStn> runPlanStnList;

    public String getPlanTrainId() {
        return planTrainId;
    }

    public void setPlanTrainId(String planTrainId) {
        this.planTrainId = planTrainId;
    }

    public String getPlanTrainSign() {
        return planTrainSign;
    }

    public void setPlanTrainSign(String planTrainSign) {
        this.planTrainSign = planTrainSign;
    }

    public String getPlanCrossId() {
        return planCrossId;
    }

    public void setPlanCrossId(String planCrossId) {
        this.planCrossId = planCrossId;
    }

    public String getPreTrainId() {
        return preTrainId;
    }

    public void setPreTrainId(String preTrainId) {
        this.preTrainId = preTrainId;
    }

    public String getNextTrainId() {
        return nextTrainId;
    }

    public void setNextTrainId(String nextTrainId) {
        this.nextTrainId = nextTrainId;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getTrainNbr() {
        return trainNbr;
    }

    public void setTrainNbr(String trainNbr) {
        this.trainNbr = trainNbr;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getStartBureauShortName() {
        return startBureauShortName;
    }

    public void setStartBureauShortName(String startBureauShortName) {
        this.startBureauShortName = startBureauShortName;
    }

    public String getStartBureauFullName() {
        return startBureauFullName;
    }

    public void setStartBureauFullName(String startBureauFullName) {
        this.startBureauFullName = startBureauFullName;
    }

    public String getStartStn() {
        return startStn;
    }

    public void setStartStn(String startStn) {
        this.startStn = startStn;
    }

    public String getEndBureauShortName() {
        return endBureauShortName;
    }

    public void setEndBureauShortName(String endBureauShortName) {
        this.endBureauShortName = endBureauShortName;
    }

    public String getEndBureauFullName() {
        return endBureauFullName;
    }

    public void setEndBureauFullName(String endBureauFullName) {
        this.endBureauFullName = endBureauFullName;
    }

    public String getEndStn() {
        return endStn;
    }

    public void setEndStn(String endStn) {
        this.endStn = endStn;
    }

    public String getPassBureau() {
        return passBureau;
    }

    public void setPassBureau(String passBureau) {
        this.passBureau = passBureau;
    }

    public int getTrainScope() {
        return trainScope;
    }

    public void setTrainScope(int trainScope) {
        this.trainScope = trainScope;
    }

    public String getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(String trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public int getHighLineFlag() {
        return highLineFlag;
    }

    public void setHighLineFlag(int highLineFlag) {
        this.highLineFlag = highLineFlag;
    }

    public String getBaseChartId() {
        return baseChartId;
    }

    public void setBaseChartId(String baseChartId) {
        this.baseChartId = baseChartId;
    }

    public String getBaseTrainId() {
        return baseTrainId;
    }

    public void setBaseTrainId(String baseTrainId) {
        this.baseTrainId = baseTrainId;
    }

    public int getHightLineRule() {
        return hightLineRule;
    }

    public void setHightLineRule(int hightLineRule) {
        this.hightLineRule = hightLineRule;
    }

    public int getCommonLineRule() {
        return commonLineRule;
    }

    public void setCommonLineRule(int commonLineRule) {
        this.commonLineRule = commonLineRule;
    }

    public String getAppointWeek() {
        return appointWeek;
    }

    public void setAppointWeek(String appointWeek) {
        this.appointWeek = appointWeek;
    }

    public String getAppointDay() {
        return appointDay;
    }

    public void setAppointDay(String appointDay) {
        this.appointDay = appointDay;
    }

    public int getDayGap() {
        return dayGap;
    }

    public void setDayGap(int dayGap) {
        this.dayGap = dayGap;
    }

    public int getSpareFlag() {
        return spareFlag;
    }

    public void setSpareFlag(int spareFlag) {
        this.spareFlag = spareFlag;
    }

    public int getSpareApplyFlag() {
        return spareApplyFlag;
    }

    public void setSpareApplyFlag(int spareApplyFlag) {
        this.spareApplyFlag = spareApplyFlag;
    }

    public int getCreateType() {
        return createType;
    }

    public void setCreateType(int createType) {
        this.createType = createType;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public int getCheckLev1Type() {
        return checkLev1Type;
    }

    public void setCheckLev1Type(int checkLev1Type) {
        this.checkLev1Type = checkLev1Type;
    }

    public int getCheckLev2Type() {
        return checkLev2Type;
    }

    public void setCheckLev2Type(int checkLev2Type) {
        this.checkLev2Type = checkLev2Type;
    }

    public int getDailyPlanTimes() {
        return dailyPlanTimes;
    }

    public void setDailyPlanTimes(int dailyPlanTimes) {
        this.dailyPlanTimes = dailyPlanTimes;
    }

    public List<RunPlanStn> getRunPlanStnList() {
        return runPlanStnList;
    }

    public void setRunPlanStnList(List<RunPlanStn> runPlanStnList) {
        this.runPlanStnList = runPlanStnList;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getMarshallingName() {
        return marshallingName;
    }

    public void setMarshallingName(String marshallingName) {
        this.marshallingName = marshallingName;
    }

    public int getGroupSerialNbr() {
        return groupSerialNbr;
    }

    public void setGroupSerialNbr(int groupSerialNbr) {
        this.groupSerialNbr = groupSerialNbr;
    }

    public int getTrainSort() {
        return trainSort;
    }

    public void setTrainSort(int trainSort) {
        this.trainSort = trainSort;
    }

    public int getDailyPlanFlag() {
        return dailyPlanFlag;
    }

    public void setDailyPlanFlag(int dailyPlanFlag) {
        this.dailyPlanFlag = dailyPlanFlag;
    }

    public Date getDailyPlanTime() {
        return dailyPlanTime;
    }

    public void setDailyPlanTime(Date dailyPlanTime) {
        this.dailyPlanTime = dailyPlanTime;
    }
}
