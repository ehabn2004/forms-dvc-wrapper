package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class Delimiter implements IFormsGraphProperty {
    public Delimiter() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("SET_DELIMITER: Trying to set delimiter value to '" + sParams + "'");
        graph.setDelimiter(sParams.equals("") ? graph.getDelimiter() : sParams);
        graph.debugMessage("SET_DELIMITER: Delimiter value is now '" + graph.getDelimiter() + "'");
        return true;
    }
}
