package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.graph.Graph;

public class EnableTooltips implements IFormsGraphProperty {
    public EnableTooltips() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            if ("ALL".equalsIgnoreCase(sParams)) {
                // this is the default setting and you don't have to do
                // anything for this

                graph.debugMessage("ENABLE_TOOLTIPS: setting all tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.MTT_VALUES_TEXT);
            }
            if ("LABELS".equalsIgnoreCase(sParams)) {
                graph.debugMessage("ENABLE_TOOLTIPS: setting label tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.MTT_TEXT);

            } else if ("VALUES".equalsIgnoreCase(sParams)) {
                graph.debugMessage("ENABLE_TOOLTIPS: setting value tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.TLT_MEMBER);
            } else if ("NONE".equalsIgnoreCase(sParams)) {
                graph.debugMessage("ENABLE_TOOLTIPS: disabling tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.TLT_NONE);
            } else {
                graph.debugMessage("ENABLE_TOOLTIPS: No valid attribute passed. Use ALL, LABELS,VALUES or NONE");
            }

        } else {
            graph.debugMessage("ENABLE_TOOLTIPS: No attribute value passed, thus ignoring");
        }
        return true;
    }
}
