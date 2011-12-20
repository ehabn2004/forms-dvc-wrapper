package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class HideGraph implements IFormsGraphProperty {
    public HideGraph() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("HIDE_GRAPH");
        graph.getGraph().setVisible(false);
        // in case that the graph was replaced by the No data found panel, indicating that no data was provided for the graph
        graph.getNoDataFoundPanel().setVisible(false);
        return true;
    }
}
