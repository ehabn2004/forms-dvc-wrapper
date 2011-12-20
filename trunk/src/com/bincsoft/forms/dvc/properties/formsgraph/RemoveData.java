package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class RemoveData implements IFormsGraphProperty {
    public RemoveData() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            if (graph.getLocalRelationalData().RemoveData(sParams, graph.getDelimiter())) {
                // refresh data shown in the graph
                // m_graph.setLocalRelationalData(lrd.getRelationalData());
                graph.getGraph().setTabularData(graph.getLocalRelationalData().getRelationalData());
            } else {
                graph.debugMessage("REMOVE_DATA: No row matches search criteria!");
            }
        } else {
            graph.debugMessage("REMOVE_DATA: No data passed, command ignored!");
        }
        return true;
    }
}
