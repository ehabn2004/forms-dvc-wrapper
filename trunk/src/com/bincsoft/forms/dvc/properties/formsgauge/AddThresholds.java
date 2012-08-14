package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

public class AddThresholds extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        String[] aParams = params.split(formsGauge.getDelimiter());
        log("ADD_THRESHOLDS: Received " + aParams.length + " thresholds.");
        for (int i = 0; i < aParams.length; i++) {
            formsGauge.getDataStore().addThreshold(new Double(aParams[i]).doubleValue());
        }
        return true;
    }
}
