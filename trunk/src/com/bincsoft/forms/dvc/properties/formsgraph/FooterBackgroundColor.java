package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.ColorCodeRegistry;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Color;

public class FooterBackgroundColor implements IFormsGraphProperty {
    public FooterBackgroundColor() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                graph.debugMessage("SET_FOOTER_BACKGROUND: setting " + sParams + " as a new background color");
                graph.getGraph().getDataviewFootnote().setBackground(col);
            } else {
                graph.debugMessage("SET_FOOTER_BACKGROUND: " + sParams + " passed is not a valid color name or RGB value");
            }
        } else {
            graph.debugMessage("SET_FOOTER_BACKGROUND: no color specified");
        }
        return true;
    }
}
