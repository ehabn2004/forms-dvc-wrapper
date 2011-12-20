package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.graph.BaseGraphComponent;

public class ShowPieLabels implements IFormsGraphProperty {
    public ShowPieLabels() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            if (sParams.equalsIgnoreCase("TEXT")) {
                graph.getGraph().getSliceLabel().setTextType(BaseGraphComponent.LD_TEXT);
            } else if (sParams.equalsIgnoreCase("TEXT_PERCENT")) {
                graph.getGraph().getSliceLabel().setTextType(BaseGraphComponent.LD_TEXT_PERCENT);
            } else if (sParams.equalsIgnoreCase("PERCENT")) {
                graph.getGraph().getSliceLabel().setTextType(BaseGraphComponent.LD_PERCENT);
            } else if (sParams.equalsIgnoreCase("VALUE")) {
                graph.getGraph().getSliceLabel().setTextType(BaseGraphComponent.LD_VALUE);
            } else {
                graph.debugMessage("SHOW_PIE_LABELS: " + sParams + " is not a valid argument");
            }
        } else {
            graph.debugMessage("SHOW_PIE_LABELS: Argument must not be null");
        }
        return true;
    }
}
