package oracle.forms.demos.bigraph.impl;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class MinScaleY2Axis implements IFGPropImpl {
    public static final ID propertyId =
        ID.registerProperty("MIN_SCALE_Y2_AXIS");

    public MinScaleY2Axis() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (sParams.length() == 0) {
            // go read the manual !
            graph.DebugMessage("MIN_SCALE_Y2_AXIS, scaling needs a minimum value to be provided. Please refer to the" +
                               " documentation on how to use this property.");
        } else {
            // exceptions may be raised on the way, so we prep for it
            try {
                // check if auto scaling should be enabled
                if ("AUTO".equalsIgnoreCase(sParams)) {
                    graph.getGraph().getY2Axis().setAxisMinAutoScaled(true);
                }
                // try to set fixed scale
                else {
                    int minValue = 0;
                    minValue = new Double(sParams).intValue();
                    graph.getGraph().getY2Axis().setAxisMinAutoScaled(false);
                    graph.getGraph().getY2Axis().setAxisMinValue(minValue);
                }
            } catch (NumberFormatException nfe) {
                graph.DebugMessage("MIN_SCALE_Y2_AXIS: Not a valid integer format passed");
                graph.DebugMessage(nfe.getMessage());
            }
        }
        return true;
    }
}
