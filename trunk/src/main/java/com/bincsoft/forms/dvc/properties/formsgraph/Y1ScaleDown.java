package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.util.format.BaseViewFormat;

public class Y1ScaleDown extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            String scale = sParams;
            if ("NONE".equalsIgnoreCase(scale)) {
                graph.getGraph().getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_NONE);
            } else if ("THOUSANDS".equalsIgnoreCase(scale)) {
                graph.getGraph().getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_THOUSANDS);
            } else if ("MILLIONS".equalsIgnoreCase(scale)) {
                graph.getGraph().getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_MILLIONS);
            } else if ("BILLIONS".equalsIgnoreCase(scale)) {
                graph.getGraph().getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_BILLIONS);
            } else if ("TRILLIONS".equalsIgnoreCase(scale)) {
                graph.getGraph().getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_TRILLIONS);
            } else {
                log("SET_Y1_SCALE_DOWN: Wrong argument in call. Use NONE, THOUSANDS, MILLIONS, BILLIONS or TRILLIONS");
            }
        } else {
            log("SET_Y1_SCALE_DOWN: No argument in call. Use NONE, THOUSANDS, MILLIONS, BILLIONS or TRILLIONS");
        }
        return true;
    }
}
