package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.GraphText;

public class XLabel extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        if (sParams != null && !sParams.isEmpty()) {
            GraphText graphText = DvcHelper.getTitleFromString(sParams, graph.getDelimiter());
            log(graphText.toString());
            graph.getGraph().getO1Title().setVisible(true);
            graph.getGraph().getO1Title().setText(graphText.getText());
            graph.getGraph().getO1Title().setFont(graphText.getFont());
            graph.getGraph().repaint();
        }
        return true;
    }
}
