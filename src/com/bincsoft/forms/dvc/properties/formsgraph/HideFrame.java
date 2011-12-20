package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class HideFrame implements IFormsGraphProperty {
    public HideFrame() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("HIDE_FRAME: hiding");
        if (graph.getSeparateFrame() != null) {
            graph.getSeparateFrame().setVisible(false);
            graph.getSeparateFrame().getContentPane().remove(0);
            graph.getOwnerFrame().add(graph);
            graph.validate();
            graph.setSeparateFrame(null);
        }
        return true;
    }
}
