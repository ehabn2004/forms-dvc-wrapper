package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

import oracle.dss.gauge.GaugeConstants;

public class MetricLabelNumberType extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge)bean;
        log("SET_METRIC_LABEL_NUMBER_TYPE - Setting metric label number type to '" + params + "'.");
        if (params.equalsIgnoreCase("NUMBER"))
            formsGauge.getGauge().getMetricLabel().setNumberType(GaugeConstants.NT_NUMBER);
        else if (params.equalsIgnoreCase("PERCENT"))
            formsGauge.getGauge().getMetricLabel().setNumberType(GaugeConstants.NT_PERCENT);
        else
            log("SET_METRIC_LABEL_NUMBER_TYPE - Failed to set metric label number type to '" + params + "'.");
        return true;
    }
}
