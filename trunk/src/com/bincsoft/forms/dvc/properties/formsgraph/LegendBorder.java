package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.ColorCodeRegistry;
import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Color;

import java.util.StringTokenizer;

public class LegendBorder implements IFormsGraphProperty {
    public LegendBorder() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        String bgColor = "";
        String brdColor = "";
        Color bgC = null;
        Color brdC = null;

        if (!sParams.equals("")) {
            String _sObject = sParams;
            _sObject = DvcHelper.handleTokenNullvaluesInStartAndEnd(_sObject, graph.getDelimiter());
            StringTokenizer st = new StringTokenizer(_sObject, graph.getDelimiter());
            int ct = st.countTokens();
            graph.debugMessage("SET_LEGEND_BORDER: Token count = " + ct);
            for (int i = 0; i < ct; i++) {
                // get/set colors for border and background
                switch (i) {
                case 0:
                    brdColor = (String)st.nextElement();
                    if ("TRANSPARENT".equalsIgnoreCase(brdColor)) {
                        graph.getGraph().getLegendArea().setBorderTransparent(true);
                    } else {
                        graph.debugMessage("SET_LEGEND_BORDER: Border color value = " + brdColor);
                        brdC = ColorCodeRegistry.getColorCode(brdColor);
                        graph.getGraph().getLegendArea().setBorderColor(brdC);
                    }
                    break;
                case 1:
                    bgColor = (String)st.nextElement();
                    if ("TRANSPARENT".equalsIgnoreCase(bgColor)) {
                        graph.getGraph().getLegendArea().setFillTransparent(true);
                    } else {
                        graph.debugMessage("SET_LEGEND_BORDER: Background color value = " + bgColor);
                        bgC = ColorCodeRegistry.getColorCode(bgColor);
                        graph.getGraph().getLegendArea().setFillColor(bgC);
                    }
                    break;
                default:
                }
            }
        } else {
            graph.debugMessage("SET_LEGEND_BORDER: No value passed for setting the border color");
        }
        return true;
    }
}
