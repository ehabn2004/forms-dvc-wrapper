package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class ShowColumnsAsRows extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
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
                log("SHOW_COLUMNS_AS_ROWS attribute is not true nor false: Ignored");
            }
        }
        return true;
    }
}
