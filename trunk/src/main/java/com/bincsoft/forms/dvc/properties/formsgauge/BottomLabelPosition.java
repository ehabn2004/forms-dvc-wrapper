package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

import oracle.dss.gauge.GaugeConstants;

public class BottomLabelPosition extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge)bean;
        log("SET_BOTTOM_LABEL_POS - Setting bottom label position to '" + params + "'.");
        if (params.equalsIgnoreCase("BELOW_GAUGE"))
            formsGauge.getGauge().getBottomLabel().setPosition(GaugeConstants.LP_BELOW_GAUGE);
        else if (params.equalsIgnoreCase("INSIDE_GAUGE"))
            formsGauge.getGauge().getBottomLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE);
        else if (params.equalsIgnoreCase("NONE"))
            formsGauge.getGauge().getBottomLabel().setPosition(GaugeConstants.LP_NONE);
        else
            log("SET_BOTTOM_LABEL_POS - Failed to set bottom label position to '" + params + "'.");
        return true;
    }
}
