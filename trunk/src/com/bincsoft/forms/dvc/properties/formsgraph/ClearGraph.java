package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.graph.GraphConstants;

public class ClearGraph implements IFormsGraphProperty {
    public ClearGraph() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.getLocalRelationalData().clearGraphData();
        graph.getGraph().destroyReferenceObjects(GraphConstants.ALL);
        graph.getGraph().setDepthRadius(graph.getGraph().getDepthRadius());
        graph.getGraph().setDepthAngle(graph.getGraph().getDepthAngle());
        return true;
    }
}
