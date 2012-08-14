package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

public class UseThresholdFillColor extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        log("SET_USE_THRESHOLD_FILL_COLOR: " + params.equalsIgnoreCase("TRUE"));
        formsGauge.getGauge().getPlotArea().setUseThresholdFillColor(params.equalsIgnoreCase("TRUE"));
        return true;
    }
}
