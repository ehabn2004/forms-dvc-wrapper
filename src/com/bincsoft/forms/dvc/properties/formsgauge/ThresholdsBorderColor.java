package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.ColorCodeRegistry;

public class ThresholdsBorderColor extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge)bean;
        log("SET_THRESHOLDS_BORDER_COLOR: Setting border color to '" + params + "' for '" +
            formsGauge.getDataStore().getThresholds().size() + "' thresholds...");
        for (int i = 0; i < formsGauge.getDataStore().getThresholds().size(); i++) {
            formsGauge.getGauge().getThreshold().setBorderColor(i, ColorCodeRegistry.getColorCode(params));
        }
        return true;
    }
}
