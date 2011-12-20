package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class EnableMarkerText implements IFormsGraphProperty {
    public EnableMarkerText() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        graph.getGraph().getMarkerText().setVisible(bEnable);
        graph.debugMessage("ENABLE_MARKERTEXT: " + bEnable);
        return true;
    }
}
