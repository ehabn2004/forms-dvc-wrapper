package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class GraphFontName extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if(super.handleProperty(sParams, bean)) {
            graph.getGraph().setFontName(sParams);
        }
        return true;
    }
}