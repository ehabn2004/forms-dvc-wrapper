package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class RemoveSubTitle extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        log("HIDE_SUBTITLE: hiding");
        graph.getGraph().getDataviewSubtitle().setVisible(false);
        return true;
    }
}
