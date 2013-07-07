package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.dvc.FormsGauge;
import com.bincsoft.forms.BincsoftBean;

public class TopLabelText extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        FormsGauge formsGauge = (FormsGauge)bean;
        log("SET_TOP_LABEL_TEXT: " + params);
        formsGauge.getGauge().getTopLabel().setText(params);
        return true;
    }
}
