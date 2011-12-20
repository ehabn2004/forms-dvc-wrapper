package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class RemoveXTitle implements IFormsGraphProperty {
    public RemoveXTitle() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.getGraph().getO1Title().setVisible(false);
        graph.debugMessage("HIDE_X_TITLE: set visible to false");
        return true;
    }
}
