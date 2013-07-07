package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

import oracle.dss.gauge.GaugeConstants;

public class IndicatorType extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        log("SET_INDICATOR_TYPE - Setting indicator type to '" + params + "'.");
        if (params.equalsIgnoreCase("LINE"))
            formsGauge.getGauge().getIndicator().setType(GaugeConstants.IT_LINE);
        else if (params.equalsIgnoreCase("NEEDLE"))
            formsGauge.getGauge().getIndicator().setType(GaugeConstants.IT_NEEDLE);
        else if (params.equalsIgnoreCase("FILL"))
            formsGauge.getGauge().getIndicator().setType(GaugeConstants.IT_FILL);
        else
            log("SET_INDICATOR_TYPE - Failed to set indicator type '" + params + "'.");
        return true;
    }
}
