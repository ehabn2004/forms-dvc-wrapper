package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class RemoveSubTitle implements IFormsGraphProperty {
    public RemoveSubTitle() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("HIDE_SUBTITLE: hiding");
        graph.getGraph().getDataviewSubtitle().setVisible(false);
        return true;
    }
}
