package org.railway.com.portal.web.dto;

import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * Created by star on 5/12/14.
 * 审核界面-客运开行计划dto
 */
public class RunPlanDTO {

    // 列车ID
    private String id;

    // 车次
    private String serial = "";

    // 始发站
    private String startSTN = "";

    // 终到站
    private String endSTN = "";

    private String startTime = "";

    private String endTime = "";

    //  开行日期 MM-dd
    private String runDate = "";

    // 命令号
    private String command = "";

    // 文电号
    private String tele = "";

    // 上图标记 0: yes, 1: no
    private String dailyLineFlag;

    // 上图时间 MM-dd hh:mm
    private String dailyLineTime = "";

    // 来源类型
    private String sourceType = "";

    // 备用及停运标记
    private String spareFlag = "";

    // 线路类型
    private String highLineFlag = "";

    // 一级审核状态
    private int checkLev1;

    // 二级审核状态
    private int checkLev2;

    // 生成运行线id
    private String dailyLineId;

    // 是否一级审核
    private int lev1Checked;

    // 是否二级审核
    private int lev2Checked;

    public RunPlanDTO(Map<String, Object> map) {
        this.id = MapUtils.getString(map, "PLAN_TRAIN_ID");
        this.serial = MapUtils.getString(map, "TRAIN_NBR");
        this.startSTN = MapUtils.getString(map, "START_STN");
        this.endSTN = MapUtils.getString(map, "END_STN", "");
        this.runDate = MapUtils.getString(map, "RUN_DATE");
        this.dailyLineFlag = MapUtils.getString(map, "DAILYPLAN_FLAG");
        this.dailyLineTime = MapUtils.getString(map, "DAILYPLAN_TIME");
        this.startTime = MapUtils.getString(map, "START_TIME");
        this.endTime = MapUtils.getString(map, "END_TIME");
        this.sourceType = MapUtils.getString(map, "CREAT_TYPE");
        this.spareFlag = MapUtils.getString(map, "SPARE_FLAG");
        this.highLineFlag = MapUtils.getString(map, "HIGHLINE_FLAG");
        this.checkLev1 = MapUtils.getIntValue(map, "CHECK_LEV1_TYPE");
        this.checkLev2 = MapUtils.getIntValue(map, "CHECK_LEV2_TYPE");
        this.dailyLineId = MapUtils.getString(map, "DAILYPLAN_ID");
        this.lev1Checked = MapUtils.getIntValue(map, "LEV1_CHECKED");
        this.lev2Checked = MapUtils.getIntValue(map, "LEV2_CHECKED");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getStartSTN() {
        return startSTN;
    }

    public void setStartSTN(String startSTN) {
        this.startSTN = startSTN;
    }

    public String getEndSTN() {
        return endSTN;
    }

    public void setEndSTN(String endSTN) {
        this.endSTN = endSTN;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
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

    public String getDailyLineFlag() {
        return dailyLineFlag;
    }

    public void setDailyLineFlag(String dailyLineFlag) {
        this.dailyLineFlag = dailyLineFlag;
    }

    public String getDailyLineTime() {
        return dailyLineTime;
    }

    public void setDailyLineTime(String dailyLineTime) {
        this.dailyLineTime = dailyLineTime;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSpareFlag() {
        return spareFlag;
    }

    public void setSpareFlag(String spareFlag) {
        this.spareFlag = spareFlag;
    }

    public String getHighLineFlag() {
        return highLineFlag;
    }

    public void setHighLineFlag(String highLineFlag) {
        this.highLineFlag = highLineFlag;
    }

    public int getCheckLev1() {

        return checkLev1;
    }

    public void setCheckLev1(int checkLev1) {
        this.checkLev1 = checkLev1;
    }

    public int getCheckLev2() {
        return checkLev2;
    }

    public void setCheckLev2(int checkLev2) {
        this.checkLev2 = checkLev2;
    }

    public String getDailyLineId() {
        return dailyLineId;
    }

    public void setDailyLineId(String dailyLineId) {
        this.dailyLineId = dailyLineId;
    }

    public int getLev1Checked() {
        return lev1Checked;
    }

    public void setLev1Checked(int lev1Checked) {
        this.lev1Checked = lev1Checked;
    }

    public int getLev2Checked() {
        return lev2Checked;
    }

    public void setLev2Checked(int lev2Checked) {
        this.lev2Checked = lev2Checked;
    }
}