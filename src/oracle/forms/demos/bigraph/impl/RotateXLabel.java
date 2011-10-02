package oracle.forms.demos.bigraph.impl;

import oracle.dss.graph.BaseGraphComponent;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class RotateXLabel implements IFGPropImpl {
    public static final ID propertyId = ID.registerProperty("ROTATE_X_LABEL");

    public RotateXLabel() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            String RotationAngle = sParams;
            graph.DebugMessage("ROTATE_X_LABEL: Trying to rotate the X Axis Tick Label " +
                               sParams);
            if ("90".equalsIgnoreCase(RotationAngle)) {
                // Rotate the X Axis Tick Label to 90
                graph.getGraph().getO1TickLabel().setAutomaticRotation(BaseGraphComponent.AR_NO_ROTATE);
                graph.getGraph().getO1TickLabel().setTextRotation(BaseGraphComponent.TR_HORIZ_ROTATE_90);
                graph.DebugMessage("ROTATE_X_LABEL: Successful - " + sParams);
            } else if ("270".equalsIgnoreCase(RotationAngle)) {
                // Rotate the X Axis Tick Label to 270
                graph.getGraph().getO1TickLabel().setAutomaticRotation(BaseGraphComponent.AR_NO_ROTATE);
                graph.getGraph().getO1TickLabel().setTextRotation(BaseGraphComponent.TR_HORIZ_ROTATE_270);
            } else if ("0".equalsIgnoreCase(RotationAngle)) {
                // Rotate the X Axis Tick Label to 0
                graph.getGraph().getO1TickLabel().setAutomaticRotation(BaseGraphComponent.AR_NO_ROTATE);
                graph.getGraph().getO1TickLabel().setTextRotation(BaseGraphComponent.TR_HORIZ);
            } else {
                graph.DebugMessage("ROTATE_X_LABEL: No valid values for Tick Label Rotation. Expected 0,90,270");
            }
        } else {
            graph.DebugMessage("ROTATE_X_LABEL: No valid values for Tick Label Rotation. Expected 0,90,270");
        }
        return true;
    }
}
