package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class GraphType implements IFormsGraphProperty {
    public GraphType() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("GRAPH_TYPE: trying to set graph type");
        graph.getGraph().setGraphType((sParams.equals("") ? graph.getGraphType() : graph.getInternalGraphType(sParams)));
        return true;
    }
}
