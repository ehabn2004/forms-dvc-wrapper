package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class ShowLegendArea extends FormsGraphPropertyHandler {
   @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
       super.handleProperty(sParams, bean);
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        graph.getGraph().getLegendArea().setVisible(bEnable);
        log("SHOW_LEGEND: " + bEnable);
        return true;
    }
}
