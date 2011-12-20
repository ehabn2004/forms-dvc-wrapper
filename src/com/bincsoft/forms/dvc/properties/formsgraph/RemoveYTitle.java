package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class RemoveYTitle implements IFormsGraphProperty {
    public RemoveYTitle() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.getGraph().getY1Title().setVisible(false);
        graph.debugMessage("HIDE_Y_TITLE: set visible to false");
        return true;
    }
}
