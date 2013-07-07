package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

public class BottomLabelText extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge) bean;
        log("SET_BOTTOM_LABEL_TEXT: " + params);
        formsGauge.getGauge().getBottomLabel().setText(params);
        return true;
    }
}
