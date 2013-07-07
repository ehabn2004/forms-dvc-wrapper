package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.GraphText;

import javax.swing.SwingConstants;

public class Footnote extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            GraphText graphText = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            graph.getGraph().getDataviewFootnote().setVisible(true);
            graph.getGraph().getDataviewFootnote().setText(graphText.getText());
            graph.getGraph().getDataviewFootnote().setFont(graphText.getFont());
            graph.getGraph().getDataviewFootnote().setForeground(graphText.getColor());
            graph.getGraph().getDataviewFootnote().setHorizontalAlignment(SwingConstants.CENTER);
            graph.getGraph().repaint();
        } else {
            log("SET_FOOTER: no attributes passed");
        }
        return true;
    }
}
