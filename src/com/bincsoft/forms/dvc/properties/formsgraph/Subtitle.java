package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.GraphText;

import javax.swing.SwingConstants;


public class Subtitle extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            GraphText graphText = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            graph.getGraph().getDataviewSubtitle().setVisible(true);
            graph.getGraph().getDataviewSubtitle().setText(graphText.getText());
            graph.getGraph().getDataviewSubtitle().setFont(graphText.getFont());
            graph.getGraph().getDataviewSubtitle().setForeground(graphText.getColor());
            graph.getGraph().getDataviewSubtitle().setVisible(true);
            graph.getGraph().getDataviewSubtitle().setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            log("SET_SUBTITLE: no attributes passed");
        }
        return true;
    }
}
