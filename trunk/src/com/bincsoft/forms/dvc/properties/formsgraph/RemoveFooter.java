package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class RemoveFooter extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        log("HIDE_FOOTER: hiding");
        graph.getGraph().getDataviewFootnote().setVisible(false);
        return true;
    }
}
