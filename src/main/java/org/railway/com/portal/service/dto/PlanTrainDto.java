package org.railway.com.portal.service.dto;

public class PlanTrainDto {
	  //列车id
		private String planTrainId;//PLAN_TRAIN_ID;
		//车次
		private String trainNbr;//TRAIN_NBR;
		//始发站
		private String startStn;//START_STN;
		//终点站
		private String endStn;//END_STN;
		//开行时间
		private String runDate;
		
		public String getPlanTrainId() {
			return planTrainId;
		}
		public void setPlanTrainId(String planTrainId) {
			this.planTrainId = planTrainId;
		}
		public String getTrainNbr() {
			return trainNbr;
		}
		public void setTrainNbr(String trainNbr) {
			this.trainNbr = trainNbr;
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
		public String getRunDate() {
			return runDate;
		}
		public void setRunDate(String runDate) {
			this.runDate = runDate;
		}
		
		
}
