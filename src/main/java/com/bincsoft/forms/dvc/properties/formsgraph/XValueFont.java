package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.GraphText;

public class XValueFont extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("SET_X_VALUE_FONT: " + sParams);
            sParams = "blabla" + graph.getDelimiter() + sParams;
            GraphText graphText = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            log("SET_X_VALUE_FONT Font: " + graphText.getFont().getFontName());
            log("SET_X_VALUE_FONT Font Style: " + graphText.getFont().getStyle());
            graph.getGraph().getO1TickLabel().setFont(graphText.getFont());
        } else {
            log("SET_X_VALUE_FONT: No attributes passed");
        }
        return true;
    }
}
