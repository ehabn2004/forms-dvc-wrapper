package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class BaseLine implements IFormsGraphProperty {
    public BaseLine() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            double baseLineVal = 0;
            graph.debugMessage("SET_GRAPH_BASELINE: Trying to set base line val to " + sParams);
            try {
                baseLineVal = new Double(sParams).doubleValue();
                graph.getGraph().getY1BaseLine().setValue(baseLineVal);
                graph.debugMessage("SET_GRAPH_BASELINE: Baseline set to (double) " + baseLineVal);
            } catch (NumberFormatException nfe) {
                graph.debugMessage("SET_GRAPH_BASELINE: argument passed as a base line value is not a valid number format!");
            }
        } else {
            graph.debugMessage("SET_GRAPH_BASELINE: No base line value passed - statement ignored");
        }
        return true;
    }
}
