package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class RemoveFooter implements IFormsGraphProperty {
    public RemoveFooter() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("HIDE_FOOTER: hiding");
        graph.getGraph().getDataviewFootnote().setVisible(false);
        return true;
    }
}
