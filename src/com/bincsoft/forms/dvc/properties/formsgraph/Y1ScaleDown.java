package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.util.format.BaseViewFormat;

public class Y1ScaleDown implements IFormsGraphProperty {
    public Y1ScaleDown() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
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
                graph.debugMessage("SET_Y1_SCALE_DOWN: Wrong argument in call. Use NONE, THOUSANDS, MILLIONS, BILLIONS or TRILLIONS");
            }
        } else {
            graph.debugMessage("SET_Y1_SCALE_DOWN: No argument in call. Use NONE, THOUSANDS, MILLIONS, BILLIONS or TRILLIONS");
        }
        return true;
    }
}
