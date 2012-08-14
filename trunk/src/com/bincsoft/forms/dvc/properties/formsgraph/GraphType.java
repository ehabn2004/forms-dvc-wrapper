package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.GraphTypeRegistry;

public class GraphType extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            int graphType = GraphTypeRegistry.getTypeForString(sParams);
            graph.getGraph().setGraphType(graphType == GraphTypeRegistry.NO_GRAPH_TYPE_FOUND ? graph.getDefaultGraphType() : graphType);
        }
        return true;
    }
}
