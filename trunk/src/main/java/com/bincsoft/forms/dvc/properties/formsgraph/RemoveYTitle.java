package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class RemoveYTitle extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        graph.getGraph().getY1Title().setVisible(false);
        log("HIDE_Y_TITLE: set visible to false");
        return true;
    }
}
