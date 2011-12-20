package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Font;

public class YLabel implements IFormsGraphProperty {
    public YLabel() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("SET_Y_LABEL retrieved string " + sParams);
            Object[] mo = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            if (mo.length > 0) {
                graph.getGraph().getY1Title().setVisible(true);
                graph.debugMessage("SET_Y_LABEL Text: " + (String)mo[0]);
                graph.getGraph().getY1Title().setText((String)mo[0]);
            }
            if (mo.length > 1) {
                graph.debugMessage("SET_Y_LABEL Font: " + ((Font)mo[1]).getFontName());
                graph.debugMessage("SET_Y_LABEL Font Style: " + ((Font)mo[1]).getStyle());
                graph.getGraph().getY1Title().setFont((Font)mo[1]);
                graph.getGraph().repaint();
            }
            /*
             * if (mo.length >2){
             * m_graph.getY1Title().setForeground((Color)mo[2]); }
             * m_graph.getY1Title().setHorizontalAlignment(AlignCenter);
             */
        } else {
            graph.debugMessage("SET_Y_LABEL: No attributes passed");
        }
        return true;
    }
}
