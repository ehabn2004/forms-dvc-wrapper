package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Font;

public class XValueFont implements IFormsGraphProperty {
    public XValueFont() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("SET_X_VALUE_FONT: " + sParams);
            sParams = "blabla" + graph.getDelimiter() + sParams;
            Object[] mo = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            if (mo.length > 1) {
                graph.debugMessage("SET_X_VALUE_FONT Font: " + ((Font)mo[1]).getFontName());
                graph.debugMessage("SET_X_VALUE_FONT Font Style: " + ((Font)mo[1]).getStyle());
                graph.getGraph().getO1TickLabel().setFont((Font)mo[1]);
            }
        } else {
            graph.debugMessage("SET_X_VALUE_FONT: No attributes passed");
        }
        return true;
    }
}
