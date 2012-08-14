package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.ColorCodeRegistry;

import java.awt.Color;

public class PlotAreaColor extends FormsGraphPropertyHandler {
    /**
     * The background color of the Plot areais passed either in a comma delimited string of RBG vlues in a range of (o...255) , or as a color name, e.g. red
     * @param sParams
     * @param graph
     * @return
     */
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                log("SET_PLOT_AREA_COLOR: setting " + sParams + " as a new plot area color");
                graph.getGraph().getPlotArea().setFillColor(col);
            } else {
                log("SET_PLOT_AREA_COLOR: " + sParams +
                                   " passed is not a valid color name or RGB value");
            }
        } else {
            log("SET_PLOT_AREA_COLOR: no color specified");
        }
        return true;
    }
}
