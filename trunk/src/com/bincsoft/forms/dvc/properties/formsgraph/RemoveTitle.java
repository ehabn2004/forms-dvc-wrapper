package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class RemoveTitle extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        log("HIDE_TITLE: hiding");
        graph.getGraph().getDataviewTitle().setVisible(false);
        return true;
    }
}
