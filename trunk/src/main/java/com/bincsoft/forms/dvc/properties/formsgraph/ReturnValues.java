package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

/**
 * FIXME: These static finals should reference something in oracle.dss.graph.Graph
 */
public class ReturnValues extends FormsGraphPropertyHandler {
    // identifier used to define the data returned by a mouseclick on the Graph
    public static final int ALL_DATA = 0;
    public static final int DATA_LABEL = 1;
    public static final int DATA_COLUMN = 2;
    public static final int DATA_VALUE = 3;
    public static final int DATA_PRIMARY_KEY = 4;
    public static final int NO_DATA = 5;
    
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("RETURN VALUES: trying to set return value ....");
            if ("ALL".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(ALL_DATA);
                log("RETURN VALUES: ... to ALL");
            } else if ("ROWLABEL".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(DATA_LABEL);
                log("RETURN VALUES: ... to ROWLABEL");
            } else if ("COLUMNLABEL".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(DATA_COLUMN);
                log("RETURN VALUES: ... to COLUMNLABEL");
            } else if ("VALUE".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(DATA_VALUE);
                log("RETURN VALUES: ... to VALUE");
            } else if ("PRIMARY_KEY".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(DATA_PRIMARY_KEY);
                log("RETURN VALUES: ... to PRIMARY_KEY");
            } else if ("NONE".equalsIgnoreCase(sParams)) {
                graph.setReturnValueSelection(NO_DATA);
                log("RETURN VALUES: ... to NONE");
            } else {
                log("Property RETURN_VALUES: wrong attribute passed. Statement ignored");
                log("allowed: \"ALL\",\"ROWLABEL\",\"COLUMNLABEL\",\"CELLVALUE\"or \"PRIMARY_KEY\"");
            }

        } else {
           log("Property RETURN_VALUES: no attribute passed. Statement ignored");
        }
        return true;
    }
}
