package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;

public class AddGraphToBean implements IFormsGraphProperty {
    public AddGraphToBean() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("GRAPH: adding to container");
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
