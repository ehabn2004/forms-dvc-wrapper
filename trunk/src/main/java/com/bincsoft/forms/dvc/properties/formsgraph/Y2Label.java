package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.GraphText;

public class Y2Label extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("SET_Y2_TITLE retrieved string " + sParams);
            GraphText graphText = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());

            graph.getGraph().getY2Title().setVisible(true);
            log("SET_Y2_TITLE Text: " + graphText.getText());
            graph.getGraph().getY2Title().setText(graphText.getText());

            log("SET_Y2_TITLE Font: " + graphText.getFont().getFontName());
            log("SET_Y2_TITLE Font Style: " + graphText.getFont().getStyle());
            graph.getGraph().getY2Title().setFont(graphText.getFont());
            graph.getGraph().repaint();
        } else {
            log("SET_Y2_TITLE: No attributes passed");
        }
        return true;
    }
}
