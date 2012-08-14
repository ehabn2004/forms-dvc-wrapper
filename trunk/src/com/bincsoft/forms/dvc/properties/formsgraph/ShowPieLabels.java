package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.graph.BaseGraphComponent;

public class ShowPieLabels extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            if (sParams.equalsIgnoreCase("TEXT")) {
                graph.getGraph().getSliceLabel().setTextType(BaseGraphComponent.LD_TEXT);
            } else if (sParams.equalsIgnoreCase("TEXT_PERCENT")) {
                graph.getGraph().getSliceLabel().setTextType(BaseGraphComponent.LD_TEXT_PERCENT);
            } else if (sParams.equalsIgnoreCase("PERCENT")) {
                graph.getGraph().getSliceLabel().setTextType(BaseGraphComponent.LD_PERCENT);
            } else if (sParams.equalsIgnoreCase("VALUE")) {
                graph.getGraph().getSliceLabel().setTextType(BaseGraphComponent.LD_VALUE);
            } else {
                log("SHOW_PIE_LABELS: " + sParams + " is not a valid argument");
            }
        } else {
            log("SHOW_PIE_LABELS: Argument must not be null");
        }
        return true;
    }
}
