package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class Antialiasing extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
            graph.getGraph().setGraphicAntialiasing(bEnable);
            graph.getGraph().setTextAntialiasing(bEnable);
        }
        return true;
    }
}
