package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.graph.GraphConstants;

public class ClearGraph extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        graph.getLocalRelationalData().clearGraphData();
        graph.getGraph().destroyReferenceObjects(GraphConstants.ALL);
        graph.getGraph().setDepthRadius(graph.getGraph().getDepthRadius());
        graph.getGraph().setDepthAngle(graph.getGraph().getDepthAngle());
        return true;
    }
}
