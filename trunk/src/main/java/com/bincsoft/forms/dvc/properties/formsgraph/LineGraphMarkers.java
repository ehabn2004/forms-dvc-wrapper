package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class LineGraphMarkers extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        log("SET_LINEGRAPH_MARKER: received " + sParams + " as an argument");
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        graph.getGraph().setMarkerDisplayed(bEnable);
        return true;
    }
}
