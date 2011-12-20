package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.graph.Graph;

public class ScrollBar implements IFormsGraphProperty {
    public ScrollBar() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("SCROLLBAR: setting value to " + sParams);
            if ("TRUE".equalsIgnoreCase(sParams)) {
                graph.getGraph().setScrollbarPresenceGroups(Graph.SP_AS_NEEDED);
            } else if ("FALSE".equalsIgnoreCase(sParams)) {
                // disabling scrollbars
                graph.getGraph().setScrollbarPresenceGroups(Graph.SP_NEVER);
            } else {
                graph.debugMessage("SCROLLBAR: value " + sParams +
                                   " does not contain TRUE or FALSE");
            }

        } else {
            graph.debugMessage("SCROLLBAR: no value associated with command");
        }
        return true;
    }
}
