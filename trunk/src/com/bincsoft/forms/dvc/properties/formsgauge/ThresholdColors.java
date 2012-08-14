package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.ColorCodeRegistry;

public class ThresholdColors extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        String[] aParams = params.split(formsGauge.getDelimiter());
        log("SET_THRESHOLD_COLORS: Received " + aParams.length + " colors.");
        for (int i = 0; i < aParams.length; i++) {
            formsGauge.getDataStore().addThresholdColor(ColorCodeRegistry.getColorCode(aParams[i]));
        }
        return true;
    }
}
