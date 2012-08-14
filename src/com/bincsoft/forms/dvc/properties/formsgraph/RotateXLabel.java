package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.graph.BaseGraphComponent;

public class RotateXLabel extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            String RotationAngle = sParams;
            log("ROTATE_X_LABEL: Trying to rotate the X Axis Tick Label " +
                               sParams);
            if ("90".equalsIgnoreCase(RotationAngle)) {
                // Rotate the X Axis Tick Label to 90
                graph.getGraph().getO1TickLabel().setAutomaticRotation(BaseGraphComponent.AR_NO_ROTATE);
                graph.getGraph().getO1TickLabel().setTextRotation(BaseGraphComponent.TR_HORIZ_ROTATE_90);
                log("ROTATE_X_LABEL: Successful - " + sParams);
            } else if ("270".equalsIgnoreCase(RotationAngle)) {
                // Rotate the X Axis Tick Label to 270
                graph.getGraph().getO1TickLabel().setAutomaticRotation(BaseGraphComponent.AR_NO_ROTATE);
                graph.getGraph().getO1TickLabel().setTextRotation(BaseGraphComponent.TR_HORIZ_ROTATE_270);
            } else if ("0".equalsIgnoreCase(RotationAngle)) {
                // Rotate the X Axis Tick Label to 0
                graph.getGraph().getO1TickLabel().setAutomaticRotation(BaseGraphComponent.AR_NO_ROTATE);
                graph.getGraph().getO1TickLabel().setTextRotation(BaseGraphComponent.TR_HORIZ);
            } else {
                log("ROTATE_X_LABEL: No valid values for Tick Label Rotation. Expected 0,90,270");
            }
        } else {
            log("ROTATE_X_LABEL: No valid values for Tick Label Rotation. Expected 0,90,270");
        }
        return true;
    }
}
