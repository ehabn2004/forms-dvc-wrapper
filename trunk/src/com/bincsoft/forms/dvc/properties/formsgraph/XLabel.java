package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Font;

public class XLabel implements IFormsGraphProperty {
    public XLabel() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("SET_X_LABEL retrieved string " + sParams);
            Object[] mo = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            if (mo.length > 0) {
                graph.getGraph().getO1Title().setVisible(true);
                graph.debugMessage("SET_X_LABEL Text: " + (String)mo[0]);
                graph.getGraph().getO1Title().setText((String)mo[0]);
            }
            if (mo.length > 1) {
                graph.debugMessage("SET_X_LABEL Font: " + ((Font)mo[1]).getFontName());
                graph.debugMessage("SET_X_LABEL Font Style: " + ((Font)mo[1]).getStyle());
                graph.getGraph().getO1Title().setFont((Font)mo[1]);
                graph.getGraph().repaint();
            }
            /*
             * if (mo.length >2){
             * m_graph.getO1Title().setForeground((Color)mo[2]); }
             * m_graph.getO1Title().setHorizontalAlignment(AlignCenter);
             */
        } else {
            graph.debugMessage("SET_X_LABEL: No attributes passed");
        }
        return true;
    }
}
