package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class Antialiasing implements IFormsGraphProperty {
    public Antialiasing() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        graph.getGraph().setGraphicAntialiasing(bEnable);
        graph.getGraph().setTextAntialiasing(bEnable);
        graph.debugMessage("SET_GRAPHIC_ANTIALIASING: Setting graph and text antialiasing to: " + bEnable);
        return true;
    }
}
