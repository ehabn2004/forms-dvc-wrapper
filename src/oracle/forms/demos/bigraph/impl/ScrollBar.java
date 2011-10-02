package oracle.forms.demos.bigraph.impl;

import oracle.dss.graph.Graph;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class ScrollBar implements IFGPropImpl {
    public static final ID propertyId = ID.registerProperty("SCROLLBAR");

    public ScrollBar() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.DebugMessage("SCROLLBAR: setting value to " + sParams);
            if ("TRUE".equalsIgnoreCase(sParams)) {
                graph.getGraph().setScrollbarPresenceGroups(Graph.SP_AS_NEEDED);
            } else if ("FALSE".equalsIgnoreCase(sParams)) {
                // disabling scrollbars
                graph.getGraph().setScrollbarPresenceGroups(Graph.SP_NEVER);
            } else {
                graph.DebugMessage("SCROLLBAR: value " + sParams +
                                   " does not contain TRUE or FALSE");
            }

        } else {
            graph.DebugMessage("SCROLLBAR: no value associated with command");
        }
        return true;
    }
}
