package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class EnableGradient extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        if (sParams.equalsIgnoreCase("TRUE")) {
            log("ENABLE_GRADIENT: true");
            graph.getGraph().setSeriesEffect(oracle.dss.graph.Graph.SE_AUTO_GRADIENT);
        } else {
            log("ENABLE_GRADIENT: false");
            graph.getGraph().setSeriesEffect(oracle.dss.graph.Graph.SE_NONE);
        }
        return true;
    }
}
