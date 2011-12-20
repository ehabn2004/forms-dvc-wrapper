package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class RemoveTitle implements IFormsGraphProperty {
    public RemoveTitle() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("HIDE_TITLE: hiding");
        graph.getGraph().getDataviewTitle().setVisible(false);
        return true;
    }
}
