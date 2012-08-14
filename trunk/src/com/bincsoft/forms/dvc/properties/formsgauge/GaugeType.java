package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

import oracle.dss.gauge.GaugeConstants;

public class GaugeType extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        log("SET_GAUGE_TYPE - Setting gauge type to '" + params + "'.");
        if (params.equalsIgnoreCase("DIAL"))
            formsGauge.getGauge().setGaugeType(GaugeConstants.DIAL);
        else if (params.equalsIgnoreCase("STATUSMETER"))
            formsGauge.getGauge().setGaugeType(GaugeConstants.STATUSMETER);
        else if (params.equalsIgnoreCase("VERTICALSTATUSMETER"))
            formsGauge.getGauge().setGaugeType(GaugeConstants.VERTICALSTATUSMETER);
        else if (params.equalsIgnoreCase("LED"))
            formsGauge.getGauge().setGaugeType(GaugeConstants.LED);
        else
            log("SET_GAUGE_TYPE - Failed to set gauge type '" + params + "'.");
        return true;
    }
}
