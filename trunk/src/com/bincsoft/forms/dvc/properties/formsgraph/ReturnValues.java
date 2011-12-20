package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class ReturnValues implements IFormsGraphProperty {
    // identifier used to define the data returned by a mouseclick on the Graph
    public static final int ALL_DATA = 0;
    public static final int DATA_LABEL = 1;
    public static final int DATA_COLUMN = 2;
    public static final int DATA_VALUE = 3;
    public static final int DATA_PRIMARY_KEY = 4;
    public static final int NO_DATA = 5;
    
    public ReturnValues() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("RETURN VALUES: trying to set return value ....");
            if ("ALL".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(ALL_DATA);
                graph.debugMessage("RETURN VALUES: ... to ALL");
            } else if ("ROWLABEL".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(DATA_LABEL);
                graph.debugMessage("RETURN VALUES: ... to ROWLABEL");
            } else if ("COLUMNLABEL".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(DATA_COLUMN);
                graph.debugMessage("RETURN VALUES: ... to COLUMNLABEL");
            } else if ("VALUE".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(DATA_VALUE);
                graph.debugMessage("RETURN VALUES: ... to VALUE");
            } else if ("PRIMARY_KEY".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(DATA_PRIMARY_KEY);
                graph.debugMessage("RETURN VALUES: ... to PRIMARY_KEY");
            } else if ("NONE".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(NO_DATA);
                graph.debugMessage("RETURN VALUES: ... to NONE");
            } else {
                graph.debugMessage("Property RETURN_VALUES: wrong attribute passed. Statement ignored");
                graph.debugMessage("allowed: \"ALL\",\"ROWLABEL\",\"COLUMNLABEL\",\"CELLVALUE\"or \"PRIMARY_KEY\"");
            }

        } else {
           graph.debugMessage("Property RETURN_VALUES: no attribute passed. Statement ignored");
        }
        return true;
    }
}
