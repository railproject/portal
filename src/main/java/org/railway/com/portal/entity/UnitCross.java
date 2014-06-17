package org.railway.com.portal.entity;

import java.util.List;

/**
 * Created by speeder on 2014/5/28.
 */
public class UnitCross {

    private String unitCrossId;

    private List<UnitCrossTrain> unitCrossTrainList;

    public String getUnitCrossId() {
        return unitCrossId;
    }

    public void setUnitCrossId(String unitCrossId) {
        this.unitCrossId = unitCrossId;
    }

    public List<UnitCrossTrain> getUnitCrossTrainList() {
        return unitCrossTrainList;
    }

    public void setUnitCrossTrainList(List<UnitCrossTrain> unitCrossTrainList) {
        this.unitCrossTrainList = unitCrossTrainList;
    }
}
