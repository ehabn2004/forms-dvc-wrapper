package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class LineGraphMarkers implements IFormsGraphProperty {
    public LineGraphMarkers() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("SET_LINEGRAPH_MARKER: received " + sParams + " as an argument");
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        graph.getGraph().setMarkerDisplayed(bEnable);
        return true;
    }
}
