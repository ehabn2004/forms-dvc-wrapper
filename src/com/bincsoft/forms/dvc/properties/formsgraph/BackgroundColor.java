package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.ColorCodeRegistry;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Color;

import oracle.dss.graph.BaseGraphComponent;

public class BackgroundColor implements IFormsGraphProperty {
    public BackgroundColor() {
        super();
    }

    /**
     * The background color is passed either in a comma delimited string of RBG vlues in a range of (0...255) , or as a color name, e.g. red
     * Forms property: SET_BACKGROUND_COLOR
     * @param sParams
     * @return
     */
    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                graph.debugMessage("SET_BACKGROUND_COLOR: setting " + sParams + " as a new background color");
                graph.getGraph().getGraphBackground().setFillColor(col);
                graph.getGraph().getGraphBackground().getSFX().setFillType(BaseGraphComponent.FT_COLOR);
            } else {
                graph.debugMessage("SET_BACKGROUND_COLOR: " + sParams + " passed is not a valid color name or RGB value");
            }
        } else {
            graph.debugMessage("SET_BACKGROUND: no color specified");
        }
        return true;
    }
}
