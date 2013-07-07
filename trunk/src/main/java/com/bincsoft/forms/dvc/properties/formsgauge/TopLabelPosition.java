package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

import oracle.dss.gauge.GaugeConstants;

public class TopLabelPosition extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        log("SET_TOP_LABEL_POS - Setting top label position to '" + params + "'.");
        if (params.equalsIgnoreCase("ABOVE_GAUGE"))
            formsGauge.getGauge().getTopLabel().setPosition(GaugeConstants.LP_ABOVE_GAUGE);
        else if (params.equalsIgnoreCase("INSIDE_GAUGE"))
            formsGauge.getGauge().getTopLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE);
        else if (params.equalsIgnoreCase("NONE"))
            formsGauge.getGauge().getTopLabel().setPosition(GaugeConstants.LP_NONE);
        else
            log("SET_TOP_LABEL_POS - Failed to set top label position to '" + params + "'.");
        return true;
    }
}
