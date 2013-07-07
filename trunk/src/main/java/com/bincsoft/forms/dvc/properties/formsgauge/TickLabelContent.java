package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

import oracle.dss.gauge.GaugeAttributes;

public class TickLabelContent extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        String[] aParams = params.split(formsGauge.getDelimiter());
        log("SET_TICK_LABEL_CONTENT: " + params);
        int iContent = 0;
        for (int i = 0; i < aParams.length; i++) {
            if (aParams[i].equalsIgnoreCase("NONE"))
                iContent |= GaugeAttributes.TC_NONE;
            if (aParams[i].equalsIgnoreCase("METRIC"))
                iContent |= GaugeAttributes.TC_METRIC;
            if (aParams[i].equalsIgnoreCase("MIN_MAX"))
                iContent |= GaugeAttributes.TC_MIN_MAX;
            if (aParams[i].equalsIgnoreCase("INCREMENTS"))
                iContent |= GaugeAttributes.TC_INCREMENTS;
            if (aParams[i].equalsIgnoreCase("THRESHOLD"))
                iContent |= GaugeAttributes.TC_THRESHOLD;
        }
        if (iContent != 0)
            formsGauge.getGauge().getTickLabel().setContent(iContent);
        return true;
    }
}
