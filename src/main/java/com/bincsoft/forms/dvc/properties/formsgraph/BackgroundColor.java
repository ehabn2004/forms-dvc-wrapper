package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;
import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.ColorCodeRegistry;


import java.awt.Color;

import oracle.dss.graph.BaseGraphComponent;

public class BackgroundColor extends FormsGraphPropertyHandler {
    /**
     * The background color is passed either in a comma delimited string of RBG vlues in a range of (0...255) , or as a color name, e.g. red
     * Forms property: SET_BACKGROUND_COLOR
     * @param sParams
     * @return
     */
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                log("SET_BACKGROUND_COLOR: setting " + col + " as a new background color");
                graph.getGraph().getGraphBackground().setFillColor(col);
                graph.getGraph().getGraphBackground().getSFX().setFillType(BaseGraphComponent.FT_COLOR);
            } else {
                log("SET_BACKGROUND_COLOR: " + sParams + " passed is not a valid color name or RGB value");
            }
        } else {
            log("SET_BACKGROUND: no color specified");
        }
        return true;
    }
}
