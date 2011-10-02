package oracle.forms.demos.bigraph.impl;

import oracle.dss.graph.Graph;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class EnableTooltips implements IFGPropImpl {
    public static final ID propertyId = ID.registerProperty("ENABLE_TOOLTIPS");

    public EnableTooltips() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            if ("ALL".equalsIgnoreCase(sParams)) {
                // this is the default setting and you don't have to do
                // anything for this

                graph.DebugMessage("ENABLE_TOOLTIPS: setting all tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.MTT_VALUES_TEXT);
            }
            if ("LABELS".equalsIgnoreCase(sParams)) {
                graph.DebugMessage("ENABLE_TOOLTIPS: setting label tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.MTT_TEXT);

            } else if ("VALUES".equalsIgnoreCase(sParams)) {
                graph.DebugMessage("ENABLE_TOOLTIPS: setting value tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.TLT_MEMBER);
            } else if ("NONE".equalsIgnoreCase(sParams)) {
                graph.DebugMessage("ENABLE_TOOLTIPS: disabling tooltips");
                graph.getGraph().setMarkerTooltipType(Graph.TLT_NONE);
            } else {
                graph.DebugMessage("ENABLE_TOOLTIPS: No valid attribute passed. Use ALL, LABELS,VALUES or NONE");
            }

        } else {
            graph.DebugMessage("ENABLE_TOOLTIPS: No attribute value passed, thus ignoring");
        }
        return true;
    }
}
