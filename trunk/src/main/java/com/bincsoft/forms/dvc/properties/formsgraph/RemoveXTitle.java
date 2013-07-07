package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class RemoveXTitle extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        graph.getGraph().getO1Title().setVisible(false);
        log("HIDE_X_TITLE: set visible to false");
        return true;
    }
}
