package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.GraphText;

import javax.swing.SwingConstants;


public class Title extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("SET_TITLE retrieved string " + sParams);
            GraphText graphText = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            //graph.getGraph().getDataviewTitle().setVisible(true);
            graph.getGraph().getTitle().setVisible(true);
            log("SET_TITLE Text: " + graphText.getText());
            //graph.getGraph().getDataviewTitle().setText(graphText.getText());
            graph.getGraph().getTitle().setText(graphText.getText());

            log("SET_TITLE Font: " + graphText.getFont().getFontName());
            log("SET_TITLE Font Style: " + graphText.getFont().getStyle());
            //graph.getGraph().getDataviewTitle().setFont(graphText.getFont());
            graph.getGraph().getTitle().setFont(graphText.getFont());

            //graph.getGraph().getDataviewTitle().setForeground(graphText.getColor());

            //graph.getGraph().getDataviewTitle().setHorizontalAlignment(SwingConstants.CENTER);
            graph.getGraph().getTitle().setHorizontalAlignment(SwingConstants.CENTER);
            graph.getGraph().repaint();
        } else {
            log("Property SET_TITLE: No attributes passed");
        }
        return true;
    }
}
