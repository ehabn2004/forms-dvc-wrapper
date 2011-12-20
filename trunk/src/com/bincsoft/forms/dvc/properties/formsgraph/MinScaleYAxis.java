package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class MinScaleYAxis implements IFormsGraphProperty {
    public MinScaleYAxis() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (sParams.length() == 0) {
            // go read the manual !
            graph.debugMessage("MIN_SCALE_Y_AXIS, scaling needs a minimum value to be provided. Please refer to the" +
                               " documentation on how to use this property.");
        } else {
            // exceptions may be raised on the way, so we prep for it
            try {
                // check if auto scaling should be enabled
                if ("AUTO".equalsIgnoreCase(sParams)) {
                    graph.getGraph().getY1Axis().setAxisMinAutoScaled(true);
                }
                // try to set fixed scale
                else {
                    int minValue = 0;
                    minValue = new Double(sParams).intValue();
                    graph.getGraph().getY1Axis().setAxisMinAutoScaled(false);
                    graph.getGraph().getY1Axis().setAxisMinValue(minValue);
                }
            } catch (NumberFormatException nfe) {
                graph.debugMessage("MIN_SCALE_Y_AXIS: Not a valid integer format passed");
                graph.debugMessage(nfe.getMessage());
            }
        }
        return true;
    }
}
