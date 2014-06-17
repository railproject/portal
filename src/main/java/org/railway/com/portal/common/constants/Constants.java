
package org.railway.com.portal.common.constants;

import org.springframework.beans.factory.annotation.Value;



/**
 * 定义基本常用的参数
 * @author join
 *
 */
public class Constants {
	

	//rest请求超时设置30秒
	public static final int CONNECT_TIME_OUT = 30 * 1000;
	public static final String DATE_FORMAT_1 = "yyyyMMddHHmm";	
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
	public static final String REMOTE_SERVICE_SUCCESS = "200";
	//TODO:后台服务地址，以后要改为配置文件
	//后台接口地址
	//TODO 从配置文件中获取不到值
	
	
	//获取方案列表
	public static final String GET_SCHEME_LIST = "/rail/template/TemplateSchemes";
	//基本图方案包含的基本图运行线
	public static final String GET_TRAINLINE_TEMPLATES = "/rail/template/TrainlineTemplates";
	//5.2.3	查询给定列车车次的基本图运行线
	public static final String GET_TRAINNBR_INFO = "/rail/template/TrainlineTemplates?name=";
	//查询始发日期为给定日期范围的日计划运行线统计数
	public static final String GET_TRAINLINS = "/rail/plan";
	//5.2.4	更新给定列车的基本图运行线车底交路id
	public static final String UPDATE_UNIT_CROSS_ID = "/rail/template/TrainlineTemplates/VehicleCycles";
	//5.2.2	查询给定id的基本图运行线
	public static final String GET_TRAIN_LINES_INFO_WITH_ID="/rail/template/TrainlineTemplates/";
	/****站点类型【1：始发 2：经由 3：终到】 ***/
	public static final String STATION_BEGIN = "1";
	public static final String STATION_ROUTE = "2";
	public static final String STATION_END = "3";
	
	//列车类型：客运
	public static final String ZUOYE_DENGJI_KEYUN = "客运";
	//查询全部的经由
	public static final String STATION_TYPE_ALL = "ALL";
	//查询始发站和终到站
	public static final String STATION_TYPE_START_END = "START_END";
	
	public static final String TYPE_CROSS = "cross";
	public static final String TYPE_UNIT_CROSS="unitcross";
	/******mapper.xml文件中的id常量*****/
	//获取路局信息
	public static final String LJZDDAO_GET_LJ_INFO = "ljzdDao.getLjInfo";
	//获取trainType 货运或客运getTrainType
	public static final String LJZDDAO_GET_TRAIN_TYPE = "ljzdDao.getTrainType";
	//获取18个路局信息getFullStationInfo
	public static final String LJZDDAO_GET_FULL_STATION_INFO = "ljzdDao.getFullStationInfo";
	//更新表train_plan字段check_state  trainPlanDao   updateCheckState
	public static final String TRAINPLANDAO_UPDATE_CHECKSTATE = "trainPlanDao.updateCheckState";
	//更新表train_plan字段 plan_flag updatePlanTrainDaylyPlanFlag
	public static final String TRAINPLANDAO_UPDATE_PLANFLAG = "trainPlanDao.updatePlanTrainDaylyPlanFlag";
	//根据路局名称和运行时间查询列车
	public static final String TRAINPLANDAO_GET_TOTALTRAINS = "trainPlanDao.getTotalTrains";
	//  getTrainShortInfo
	public static final String TRAINPLANDAO_GET_TRAIN_SHORTINFO = "trainPlanDao.getTrainShortInfo";
	//对表train_plan插入计划数据 addTrainPlan
	public static final String TRAINPLANDAO_ADD_TRAIN_PLAN = "trainPlanDao.addTrainPlan";
	//对表train_plan_stn插入经由数据addTrainPlanStn
	public static final String TRAINPLANDAO_ADD_TRAIN_PLAN_STN = "trainPlanDao.addTrainPlanStn";
	//获取最大的train_plan_id   getMaxPlanTrainId
	public static final String TRAINPLANDAO_GET_MAX_PLANTRAIN_ID = "trainPlanDao.getMaxPlanTrainId";
    //getTrainTimeDetail
	public static final String TRAINPLANDAO_GET_TRAIN_TIME_DETAIL = "trainPlanDao.getTrainTimeDetail";
    //统计全路局列车信息 getTotalStationInfo
	public static final String TRAINPLANDAO_GET_TOTAL_STATION_INFO="trainPlanDao.getTotalStationInfo";
    //统计一个路局列车信息getOneStationInfo
	public static final String TRAINPLANDAO_GET_ONE_STATION_INFO = "trainPlanDao.getOneStationInfo";
	//getGatherTotalStationJieru
	public static final String TRAINPLANDAO_GET_TOTAL_STATION_JIERU = "trainPlanDao.getGatherTotalStationJieru";
    //查询某天某局的车次 getGatherPeriodRundateTrains
	public static final String TRAINPLANDAO_GET_PERIOD_RUNDATE_TRAINS = "trainPlanDao.getGatherPeriodRundateTrains";
    //查询数据总的条数 getPeriodRundateTrainsTotal
	public static final String TRAINPLANDAO_GET_RUNDATE_TRAINS_TOTAL = "trainPlanDao.getPeriodRundateTrainsTotal";
	//getRundateTrainCount
	public static final String TRAINPLANDAO_GET_RUNDATE_TRAIN_COUNT = "trainPlanDao.getRundateTrainCount";
    //根据rundate和train_nbr删除表plan_train中的数据 deleteTrainRundateTrainNbr
	public static final String TRAINPLANDAO_DELETE_TRAIN_RUNDATE_NBR = "trainPlanDao.deleteTrainRundateTrainNbr";
    //deleteTrainRundateTrainStn
	public static final String TRAINPLANDAO_DELETE_TRAIN_RUNDATE_TRAIN_STN = "trainPlanDao.deleteTrainRundateTrainStn";
    //导入数据前删除历史数据 deleteTrainRundateTrainStnInit
	public static final String TRAINPLANDAO_DELETE_TRAIN_RUNDATE_TRAIN_STN_INIT = "trainPlanDao.deleteTrainRundateTrainStnInit";
    //
	public static final String TRAINPLANDAO_DELETE_TRAIN_RUNDATE_TRAIN_NBR_INIT = "trainPlanDao.deleteTrainRundateTrainNbrInit";
    //findPlanTrainByStartBureauAndRundate
	public static final String TRAINPLANDAO_FIND_PLANTRAIN_BY_START_BUREAU = "trainPlanDao.findPlanTrainByStartBureauAndRundate";
    //findPlanTrainByStartBureauCount
	public static final String TRAINPLANDAO_FIND_PLANTRAIN_BY_START_BUREAU_COUNT = "trainPlanDao.findPlanTrainByStartBureauCount";

    /****CrossMapper.xml  ****/
	//表plan_cross插入数据
	public static final String CROSSDAO_ADD_PLAN_CROSS_INFO = "crossDao.addPlanCrossInfo";
    //表base_cross插入数据addCrossInfo
	public static final String CROSSDAO_ADD_CROSS_INFO = "crossDao.addCrossInfo";
	//表base_cross_train插入数据addCrossTrainInfo
	public static final String CROSSDAO_ADD_CROSS_TRAIN_INFO = "crossDao.addCrossTrainInfo";
	//查询crossinfo信息
	public static final String CROSSDAO_GET_CROSS_INFO = "crossDao.getCrossInfo";
	//查询crossinfo信息的条数
	public static final String CROSSDAO_GET_CROSS_INFO_COUNT = "crossDao.getCrossInfoTotalCount";
    //通过crossid获取crossinfo信息 
	public static final String  CROSSDAO_GET_CROSS_INFO_FOR_CROSSID = "crossDao.getCrossInfoForCrossid";
	//通过crossid查询crosstrainInfo信息   
	public static final String CROSSDAO_GET_CROSS_TRAIN_INFO_FOR_CROSSID = "crossDao.getCrossTrainInfoForCrossid";
    //插入表unit_cross  addUnitCrossInfo
	public static final String CROSSDAO_ADD_UNIT_CROSS_INFO="crossDao.addUnitCrossInfo";
	//插入表unit_cross_train
	public static final String CROSSDAO_ADD_UNIT_CROSS_TRAIN_INFO="crossDao.addUnitCrossTrainInfo";
    //根据unit_cross_id查询unitcrossInfo信息 getUnitCrossInfoForUnitCrossid
	public static final String CROSSDAO_GET_UNIT_CROSS_INFO_FOR_UNIT_CROSSID="crossDao.getUnitCrossInfoForUnitCrossid";
    public static final String CROSSDAO_GET_UNIT_CROSS_TRAIN_INFO_FOR_UNIT_CROSSID = "crossDao.getUnitCrossTrainInfoForUnitCrossid";
    //getUnitCrossTrainInfoForCrossid
    public static final String CROSSDAO_GET_UNIT_CROSSTRAIN_INFO_FOR_UNIT_CROSSID = "crossDao.getUnitCrossTrainInfoForUnitCrossId";
    //分页查询表unit_cross  getUnitCrossInfo
    public static final String CROSSDAO_GET_UNIT_CROSS_INFO = "crossDao.getUnitCrossInfo";
    //分页查询的总条数 
    public static final String CROSSDAO_GET_UNIT_CROSS_INFO_COUNT = "crossDao.getUnitCrossInfoCount";
    //通过unit_cross_id查询train_nbr  getTrainNbrFromUnitCross
    public static final String CROSSDAO_GET_TRAINNBR_FROM_UNIT_CROSS = "crossDao.getTrainNbrFromUnitCross";
    public static final String CROSSDAO_GET_CROSSNAME_WITH_BASE_CROSSID = "crossDao.getCrossNameWithBaseCrossId";
    public static final String CROSSDAO_GET_TRAINNBR_WITH_BASE_CROSSID = "crossDao.getTrainNbrWithBaseCrossId";
    //通过corssid在表unit_cross中查询unitcrossInfo信息
    public static final String CROSSDAO_GET_UNIT_CROSS_INFO_FOR_CROSSID = "crossDao.getUnitCrossInfoForCrossId";
    //通过cross查询交路基本信息和经由始发和终到信息 
    public static final String CROSSDAO_GET_CROSS_TRAININFO_FOR_CROSSID = "crossDao.getCrossTrainInfoForCrossId";
    //更新base_cross的check_time   
    public static final String CROSSDAO_UPDATE_CROSS_CHECKTIME = "crossDao.updateCrossCheckTime";
    //更新base_cross的creat_unit_time  
    public static final String CROSSDAO_UPDATE_CROSS_CREATETIME = "crossDao.updateCrossUnitCreateTime";
    public static final String CROSSDAO_UPDATE_Unit_CROSS_CREATETIME = "crossDao.updateUnitCrossUnitCreateTime";
    //根据plan_cross_id查询运行线
    public static final String CROSSDAO_GET_TRAINPLANLINE_INFO_FOR_PLANCROSSID="crossDao.getTrainPlanLineInfoForPlanCrossId";
    //通过plan_cross_id查询经由的始发站和终到站 getStationListForPlanCrossId
    public static final String CROSSDAO_GET_STATIONLIST_FOR_PLANCROSSID = "crossDao.getStationListForPlanCrossId";
    //更新base_cross的check_time   
    public static final String CROSSDAO_UPDATE_UNIT_CROSS_CHECKTIME = "crossDao.updateUnitCrossCheckTime";
    //根据crossids批量删除base_cross表中数据
    public static final String CROSSDAO_DELETE_CROSS_INFO_FOR_CROSSIDS = "crossDao.deleteCrossInfoForCrossIds";
    //根据crossIds批量删除base_cross_train表中数据
    public static final String CROSSDAO_DELETE_CROSS_INFO_TRAIN_FOR_CROSSIDS = "crossDao.deleteCrossInfoTrainForCrossIds";
    
  //根据crossids批量删除unit_cross表中数据
    public static final String CROSSDAO_DELETE_UNIT_CROSS_INFO_FOR_CROSSIDS = "crossDao.deleteUnitCrossInfoForCrossIds";
    //根据crossIds批量删除unit_cross_train表中数据
    public static final String CROSSDAO_DELETE_UNIT_CROSS_INFO_TRAIN_FOR_CROSSIDS = "crossDao.deleteUnitCrossInfoTrainForCrossIds";
    //根据chartId查询unit_cross信息 
    public static final String CROSSDAO_GET_UNIT_CROSSINFO_FOR_CHARTID = "crossDao.getUnitCrossInfoForChartId";
    //获取全部方案列表
    public static final String SCHEME_GETSCHEMEINFO = "schemeDao.getSchemeInfo";
    
    //根据方案ID和始发终到局获取列车列表
    public static final String TRAININFO_GETTRAININFO = "trainInfoDao.getTrainInfo";
    public static final String TRAININFO_GETTRAININFO_PAGE = "trainInfoDao.getTrainInfoForPage";
    //
    public static final String TRAININFO_GET_TRAINS_AND_TIMES_COUNT = "trainInfoDao.getTrainsAndTimesCount";
    //
    public static final String TRAININFO_GET_TRAINS_AND_TIMES_FORPAGE = "trainInfoDao.getTrainsAndTimesForPage";
    public static final String TRAININFO_GETTRAININFO_COUNT="trainInfoDao.getTrainInfoTotalCount";
    //根据baseTrainId查询列车基本信息
    public static final String TRAININFO_GETTRAININFO_FOR_TRAINID="trainInfoDao.getTrainInfoForTrainId";
    //根据baseTrainId查询列车时刻表信息 
    public static final String TRAININFO_GETTRAIN_TIME_INFO_FOR_TRAINID = "trainInfoDao.getTrainTimeInfoForTrainId";
    //根据baseTrainId查询列车的起点和终点站信息 
    public static final String TRAININFO_GET_START_END_TRAINTIME_FOR_TRAINID = "trainInfoDao.getStartEndTrainTimeInfoForTrainId";
    //根据方案ID和始发终到局获取列车列表
    public static final String TRAININFO_GETTRAINTIMEINFO_BY_TRAINID = "trainTimeDao.getTrainTimeInfoByTrainId";
    
    public static final String GET_TRAIN_RUN_PLAN = "org.railway.com.portal.repository.mybatis.RunPlanDao.getTrainRunPlans";
	public static final String GET_PLAN_CROSS = "org.railway.com.portal.repository.mybatis.RunPlanDao.getPlanCross";
	public static final String CROSSDAO_DELETE_PLANCROSS_INFO_TRAIN_FOR_CROSSIDS = "org.railway.com.portal.repository.mybatis.RunPlanDao.deletePlanCrossByPlanCrossIds";
	public static final String CROSSDAO_DELETE_PLANTRAIN_INFO_TRAIN_FOR_CROSSIDS = "org.railway.com.portal.repository.mybatis.RunPlanDao.deletePlanTrainsByPlanCrossIds";
	public static final String CROSSDAO_DELETE_PLANTRAINSTN_INFO_TRAIN_FOR_CROSSIDS = "org.railway.com.portal.repository.mybatis.RunPlanDao.deletePlanTrainStnsByPlanCrossIds";
}
