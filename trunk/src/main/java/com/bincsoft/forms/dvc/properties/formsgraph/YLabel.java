package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.GraphText;

public class YLabel extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("SET_Y_LABEL retrieved string " + sParams);
            GraphText graphText = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            graph.getGraph().getY1Title().setVisible(true);
            log("SET_Y_LABEL Text: " + graphText.getText());
            graph.getGraph().getY1Title().setText(graphText.getText());

            log("SET_Y_LABEL Font: " + graphText.getFont().getFontName());
            log("SET_Y_LABEL Font Style: " + graphText.getFont().getStyle());
            graph.getGraph().getY1Title().setFont(graphText.getFont());
            graph.getGraph().repaint();
        } else {
            log("SET_Y_LABEL: No attributes passed");
        }
        return true;
    }
}
