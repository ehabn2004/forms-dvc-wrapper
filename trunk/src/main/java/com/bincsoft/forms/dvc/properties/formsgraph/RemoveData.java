package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class RemoveData extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            if (graph.getLocalRelationalData().RemoveData(sParams, graph.getDelimiter(), graph.isShowGraphAsSeries())) {
                // refresh data shown in the graph
                // m_graph.setLocalRelationalData(lrd.getRelationalData());
                graph.getGraph().setTabularData(graph.getLocalRelationalData().getRelationalData());
            } else {
                log("REMOVE_DATA: No row matches search criteria!");
            }
        } else {
            log("REMOVE_DATA: No data passed, command ignored!");
        }
        return true;
    }
}
