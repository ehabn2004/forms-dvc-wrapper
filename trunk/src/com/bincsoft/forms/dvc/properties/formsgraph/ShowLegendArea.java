package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class ShowLegendArea implements IFormsGraphProperty {
    public ShowLegendArea() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        graph.getGraph().getLegendArea().setVisible(bEnable);
        graph.debugMessage("SHOW_LEGEND: " + bEnable);
        return true;
    }
}
