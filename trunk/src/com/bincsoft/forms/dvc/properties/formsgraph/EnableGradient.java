package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class EnableGradient implements IFormsGraphProperty {
    public EnableGradient() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (sParams.equalsIgnoreCase("TRUE")) {
            graph.debugMessage("ENABLE_GRADIENT: true");
            graph.getGraph().setSeriesEffect(oracle.dss.graph.Graph.SE_AUTO_GRADIENT);
        } else {
            graph.debugMessage("ENABLE_GRADIENT: false");
            graph.getGraph().setSeriesEffect(oracle.dss.graph.Graph.SE_NONE);
        }
        return true;
    }
}
