package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.BincsoftBean;

public class ColumnLabels extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        log("SET_COLUMN_LABELS is not implemented.");
        /*String[] aParams = ((String)_object).split(sDelimiter);
                                debugMessage("SET_COLUMN_LABELS: Received '" + aParams.length + "' parameters.");
                                aDataColumnLabels = aParams;*/
        return true;
    }
}
