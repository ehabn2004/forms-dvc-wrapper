package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

public class ShowGauge extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        log("SHOW_GAUGE: Label '" + formsGauge.getDataStore().getLabel() + "'.");
        log("SHOW_GAUGE: Current value '" + formsGauge.getDataStore().getCurrentValue() + "'.");
        log("SHOW_GAUGE: Min value '" + formsGauge.getDataStore().getMinValue() + "'.");
        log("SHOW_GAUGE: Max value '" + formsGauge.getDataStore().getMaxValue() + "'.");
        log("SHOW_GAUGE: Threshold count '" + formsGauge.getDataStore().getThresholds().size() + "'.");
        boolean bSuccess = formsGauge.setDataAndShowGauge();
        log("SHOW_GAUGE: Gauge replied '" + bSuccess + "' when setting data.");
        return true;
    }
}
