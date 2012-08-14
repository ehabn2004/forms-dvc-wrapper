package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.graph.Graph;

public class EnableTooltips extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            if ("ALL".equalsIgnoreCase(sParams)) {
                // this is the default setting and you don't have to do
                // anything for this

                log("ENABLE_TOOLTIPS: setting all tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.MTT_VALUES_TEXT);
            }
            if ("LABELS".equalsIgnoreCase(sParams)) {
                log("ENABLE_TOOLTIPS: setting label tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.MTT_TEXT);
            } else if ("VALUES".equalsIgnoreCase(sParams)) {
                log("ENABLE_TOOLTIPS: setting value tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.MTT_VALUES);
            } else if ("NONE".equalsIgnoreCase(sParams)) {
                log("ENABLE_TOOLTIPS: disabling tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.MTT_NONE);
            } else {
                log("ENABLE_TOOLTIPS: No valid attribute passed. Use ALL, LABELS,VALUES or NONE");
            }

        } else {
            log("ENABLE_TOOLTIPS: No attribute value passed, thus ignoring");
        }
        return true;
    }
}
