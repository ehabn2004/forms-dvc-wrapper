package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class ModifyData extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("MODIFY_DATA: Trying to update row in graph");
            if (graph.getLocalRelationalData().ModifyData(sParams, graph.getDelimiter(), graph.isShowGraphAsSeries())) {
                // refresh graph data
                // m_graph.setLocalRelationalData(lrd.getRelationalData());
                graph.getGraph().setTabularData(graph.getLocalRelationalData().getRelationalData());
            } else {
                log("MODIFY_DATA: No matching column/row found in graph. Statement ignored");
            }
        } else {
            log("MODIFY_DATA: No values passed with request. Statement ignored");
        }
        return true;
    }
}
