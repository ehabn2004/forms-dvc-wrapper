package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

import oracle.dss.gauge.GaugeConstants;

public class MetricLabelPosition extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge)bean;
        log("SET_METRIC_LABEL_POS - Setting metric label position to '" + params + "'.");
        if (params.equalsIgnoreCase("BELOW_GAUGE"))
            formsGauge.getGauge().getMetricLabel().setPosition(GaugeConstants.LP_BELOW_GAUGE);
        else if (params.equalsIgnoreCase("INSIDE_GAUGE"))
            formsGauge.getGauge().getMetricLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE);
        else if (params.equalsIgnoreCase("INSIDE_GAUGE_LEFT"))
            formsGauge.getGauge().getMetricLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE_LEFT);
        else if (params.equalsIgnoreCase("INSIDE_GAUGE_RIGHT"))
            formsGauge.getGauge().getMetricLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE_RIGHT);
        else if (params.equalsIgnoreCase("NONE"))
            formsGauge.getGauge().getMetricLabel().setPosition(GaugeConstants.LP_NONE);
        else if (params.equalsIgnoreCase("WITH_BOTTOM_LABEL"))
            formsGauge.getGauge().getMetricLabel().setPosition(GaugeConstants.LP_WITH_BOTTOM_LABEL);
        else
            log("SET_METRIC_LABEL_POS - Failed to set metric label position to '" + params + "'.");
        return true;
    }
}
