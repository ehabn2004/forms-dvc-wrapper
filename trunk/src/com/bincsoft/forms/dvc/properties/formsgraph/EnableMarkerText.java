package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class EnableMarkerText extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        graph.getGraph().getMarkerText().setVisible(bEnable);
        log("ENABLE_MARKERTEXT: " + bEnable);
        return true;
    }
}
