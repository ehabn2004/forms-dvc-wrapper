package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.ColorCodeRegistry;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Color;

public class PlotAreaColor implements IFormsGraphProperty {
    public PlotAreaColor() {
        super();
    }

    /**
     * The background color of the Plot areais passed either in a comma delimited string of RBG vlues in a range of (o...255) , or as a color name, e.g. red
     * @param sParams
     * @param graph
     * @return
     */
    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                graph.debugMessage("SET_PLOT_AREA_COLOR: setting " + sParams + " as a new plot area color");
                graph.getGraph().getPlotArea().setFillColor(col);
            } else {
                graph.debugMessage("SET_PLOT_AREA_COLOR: " + sParams +
                                   " passed is not a valid color name or RGB value");
            }
        } else {
            graph.debugMessage("SET_PLOT_AREA_COLOR: no color specified");
        }
        return true;
    }
}
