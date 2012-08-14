package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

public class SetValue extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        log("SET_VALUE - setting data for display...");
        formsGauge.getDataStore().setCurrentValue(new Double(params).doubleValue());
        boolean bSuccess = formsGauge.setDataAndShowGauge();
        log("SET_VALUE: Gauge replied '" + bSuccess + "' when setting data.");
        return true;
    }
}
