package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class HideGraph extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        graph.getGraph().setVisible(false);
        // in case that the graph was replaced by the No data found panel, indicating that no data was provided for the graph
        graph.getNoDataFoundPanel().setVisible(false);
        return true;
    }
}
