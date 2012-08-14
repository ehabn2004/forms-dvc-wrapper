package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

public class AddGaugeData extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        String[] aParams = params.split(formsGauge.getDelimiter());
        log("ADD_GAUGE_DATA: Setting the following values...");
        log("ADD_GAUGE_DATA: Label '" + aParams[0] + "'.");
        log("ADD_GAUGE_DATA: Current value '" + aParams[1] + "'.");
        log("ADD_GAUGE_DATA: Min value '" + aParams[2] + "'.");
        log("ADD_GAUGE_DATA: Max value '" + aParams[3] + "'.");
        formsGauge.getDataStore().reset();
        if (aParams.length == 4) {
            formsGauge.getDataStore().setLabel(aParams[0]);
            formsGauge.getDataStore().setCurrentValue(new Double(aParams[1]).doubleValue());
            formsGauge.getDataStore().setMinValue(new Double(aParams[2]).doubleValue());
            formsGauge.getDataStore().setMaxValue(new Double(aParams[3]).doubleValue());
        } else {
            log("ADD_GAUGE_DATA: Incorrect no. of parameters, need 4 - <Label>, <Current value>, <Min value>, <Max value>.");
        }
        return true;
    }
}
