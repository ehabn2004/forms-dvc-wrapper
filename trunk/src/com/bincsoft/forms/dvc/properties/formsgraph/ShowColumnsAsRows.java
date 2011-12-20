package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class ShowColumnsAsRows implements IFormsGraphProperty {
    public ShowColumnsAsRows() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            if (sParams.equalsIgnoreCase("TRUE")) {
                graph.getGraph().setDataRowShownAsASeries(true);
                graph.setShowGraphAsSeries(true);
                graph.getGraph().getDataFilter().refresh();
            } else if (sParams.equalsIgnoreCase("FALSE")) {
                graph.getGraph().setDataRowShownAsASeries(false);
                graph.setShowGraphAsSeries(false);
                graph.getGraph().getDataFilter().refresh();
            } else {
                // ignore
                graph.debugMessage("SHOW_COLUMNS_AS_ROWS attribute is not true nor false: Ignored");
            }
        }
        return true;
    }
}
