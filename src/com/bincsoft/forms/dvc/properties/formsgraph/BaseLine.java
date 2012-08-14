package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class BaseLine extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            double baseLineVal = 0;
            try {
                baseLineVal = new Double(sParams).doubleValue();
                graph.getGraph().getY1BaseLine().setValue(baseLineVal);
                log("SET_GRAPH_BASELINE: Baseline set to (double) " + baseLineVal);
            } catch (NumberFormatException nfe) {
                log("SET_GRAPH_BASELINE: argument passed as a base line value is not a valid number format!");
            }
        } else {
            log("SET_GRAPH_BASELINE: No base line value passed - statement ignored");
        }
        return true;
    }
}
