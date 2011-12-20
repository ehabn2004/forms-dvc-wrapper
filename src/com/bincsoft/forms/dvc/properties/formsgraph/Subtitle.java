package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

public class Subtitle implements IFormsGraphProperty {
    public Subtitle() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            Object[] mo = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            graph.getGraph().getDataviewSubtitle().setVisible(true);
            graph.getGraph().getDataviewSubtitle().setText((String)mo[0]);
            graph.getGraph().getDataviewSubtitle().setFont((Font)mo[1]);
            graph.getGraph().getDataviewSubtitle().setForeground((Color)mo[2]);
            graph.getGraph().getDataviewSubtitle().setVisible(true);
            graph.getGraph().getDataviewSubtitle().setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            graph.debugMessage("SET_SUBTITLE: no attributes passed");
        }
        return true;
    }
}
