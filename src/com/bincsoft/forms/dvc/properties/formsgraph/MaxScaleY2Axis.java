package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class MaxScaleY2Axis implements IFormsGraphProperty {
    public MaxScaleY2Axis() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (sParams.length() == 0) {
            // go read the manual !
            graph.debugMessage("MAX_SCALE_Y2_AXIS, scaling needs a maximum value to be provided. Please refer to the" +
                               " documentation on how to use this property.");
        } else {
            // exceptions may be raised on the way, so we prep for it
            try {
                // check if auto scaling should be enabled
                if ("AUTO".equalsIgnoreCase(sParams)) {
                    graph.getGraph().getY2Axis().setAxisMaxAutoScaled(true);
                }
                // try to set fixed scale
                else {
                    int maxValue = 0;
                    maxValue = new Double(sParams).intValue();
                    graph.getGraph().getY2Axis().setAxisMaxAutoScaled(false);
                    graph.getGraph().getY2Axis().setAxisMaxValue(maxValue);
                }
            } catch (NumberFormatException nfe) {
                graph.debugMessage("MAX_SCALE_Y2_AXIS: Not a valid integer format passed");
                graph.debugMessage(nfe.getMessage());
            }
        }
        return true;
    }
}
