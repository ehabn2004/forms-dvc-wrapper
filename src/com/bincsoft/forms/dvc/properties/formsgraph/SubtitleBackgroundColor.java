package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.ColorCodeRegistry;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Color;

public class SubtitleBackgroundColor implements IFormsGraphProperty {
    public SubtitleBackgroundColor() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                graph.debugMessage("SET_SUBTITLE_BACKGROUND: setting " + sParams + " as a new background color");
                graph.getGraph().getDataviewSubtitle().setBackground(col);
            } else {
                graph.debugMessage("SET_SUBTITLE_BACKGROUND: " + sParams +
                                   " passed is not a valid color name or RGB value");
            }
        } else {
            graph.debugMessage("SET_SUBTITLE_BACKGROUND: no color specified");
        }
        return true;
    }
}
