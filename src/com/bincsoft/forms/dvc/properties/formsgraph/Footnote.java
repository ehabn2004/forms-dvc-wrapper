package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

public class Footnote implements IFormsGraphProperty {
    public Footnote() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            Object[] mo = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            graph.getGraph().getDataviewFootnote().setVisible(true);
            graph.getGraph().getDataviewFootnote().setText((String)mo[0]);
            graph.getGraph().getDataviewFootnote().setFont((Font)mo[1]);
            graph.getGraph().getDataviewFootnote().setForeground((Color)mo[2]);
            graph.getGraph().getDataviewFootnote().setVisible(true);
            graph.getGraph().getDataviewFootnote().setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            graph.debugMessage("SET_FOOTER: no attributes passed");
        }
        return true;
    }
}
