package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class ModifyData implements IFormsGraphProperty {
    public ModifyData() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("MODIFY_DATA: Trying to update row in graph");
            if (graph.getLocalRelationalData().ModifyData(sParams, graph.getDelimiter())) {
                // refresh graph data
                // m_graph.setLocalRelationalData(lrd.getRelationalData());
                graph.getGraph().setTabularData(graph.getLocalRelationalData().getRelationalData());
            } else {
                graph.debugMessage("MODIFY_DATA: No matching column/row found in graph. Statement ignored");
            }
        } else {
            graph.debugMessage("MODIFY_DATA: No values passed with request. Statement ignored");
        }
        return true;
    }
}
