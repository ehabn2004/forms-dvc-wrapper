package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class NoDataFoundMessage implements IFormsGraphProperty {
    public NoDataFoundMessage() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.setNoDataFoundMessage(sParams);
        return true;
    }
}
