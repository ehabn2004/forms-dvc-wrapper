package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.dataView.managers.ViewFormat;

public class Y1NumType implements IFormsGraphProperty {
    public Y1NumType() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            // check for percent argument
            if ("%".equalsIgnoreCase(sParams)) {
                graph.getGraph().getY1Axis().getViewFormat().setNumberType(ViewFormat.NUMTYPE_PERCENT);
            } else if ("GENERAL".equalsIgnoreCase(sParams)) {
                graph.getGraph().getY1Axis().getViewFormat().setNumberType(ViewFormat.NUMTYPE_GENERAL);
            } else {
                // assume that the provided string is a currency
                graph.getGraph().getY1Axis().getViewFormat().setNumberType(ViewFormat.NUMTYPE_CURRENCY);
                graph.getGraph().getY1Axis().getViewFormat().setCurrencySymbol(sParams);
            }
        } else {
            graph.debugMessage("Not a valid argument in call to SET_Y1_NUMBER_TYPE");
        }
        return true;
    }
}
