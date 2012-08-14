package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.graph.Graph;

public class ScrollBar extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("SCROLLBAR: setting value to " + sParams);
            if ("TRUE".equalsIgnoreCase(sParams)) {
                graph.getGraph().setScrollbarPresenceGroups(Graph.SP_AS_NEEDED);
            } else if ("FALSE".equalsIgnoreCase(sParams)) {
                // disabling scrollbars
                graph.getGraph().setScrollbarPresenceGroups(Graph.SP_NEVER);
            } else {
                log("SCROLLBAR: value " + sParams +
                                   " does not contain TRUE or FALSE");
            }

        } else {
            log("SCROLLBAR: no value associated with command");
        }
        return true;
    }
}
