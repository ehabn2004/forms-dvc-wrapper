package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

public class Title implements IFormsGraphProperty {
    public Title() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("SET_TITLE retrieved string " + sParams);
            Object[] mo = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            if (mo.length > 0) {
                graph.getGraph().getDataviewTitle().setVisible(true);
                graph.debugMessage("SET_TITLE Text: " + (String)mo[0]);
                graph.getGraph().getDataviewTitle().setText((String)mo[0]);
            }
            if (mo.length > 1) {
                graph.debugMessage("SET_TITLE Font: " + ((Font)mo[1]).getFontName());
                graph.debugMessage("SET_TITLE Font Style: " + ((Font)mo[1]).getStyle());
                graph.getGraph().getDataviewTitle().setFont((Font)mo[1]);
                graph.getGraph().repaint();
            }
            if (mo.length > 2) {
                graph.getGraph().getDataviewTitle().setForeground((Color)mo[2]);
            }
            graph.getGraph().getDataviewTitle().setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            graph.debugMessage("Property SET_TITLE: No attributes passed");
        }
        return true;
    }
}
