package oracle.forms.demos.bigraph.impl;

import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.graph.LegendArea;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class PositionLegendArea implements IFGPropImpl {
    public static final ID propertyId = ID.registerProperty("POSITION_LEGEND");

    public PositionLegendArea() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.DebugMessage("POSITION_LEGEND: position legend to " +
                               sParams);
            if ("AUTO".equalsIgnoreCase(sParams)) {
                LegendArea legend = graph.getGraph().getLegendArea();
                legend.setAutomaticPlacement(BaseGraphComponent.AP_ALWAYS);
            } else {
                if ("LEFT".equalsIgnoreCase(sParams)) {
                    // first, turn off automatic placement
                    LegendArea legend = graph.getGraph().getLegendArea();
                    legend.setAutomaticPlacement(BaseGraphComponent.AP_NEVER);
                    // set the position to the left edge
                    legend.setPosition(BaseGraphComponent.LAP_LEFT);
                } else if ("RIGHT".equalsIgnoreCase(sParams)) {
                    // first, turn off automatic placement
                    LegendArea legend = graph.getGraph().getLegendArea();
                    legend.setAutomaticPlacement(BaseGraphComponent.AP_NEVER);
                    // set the position to the right edge
                    legend.setPosition(BaseGraphComponent.LAP_RIGHT);
                } else if ("TOP".equalsIgnoreCase(sParams)) {
                    // first, turn off automatic placement
                    LegendArea legend = graph.getGraph().getLegendArea();
                    legend.setAutomaticPlacement(BaseGraphComponent.AP_NEVER);
                    // set the position to the top edge
                    legend.setPosition(BaseGraphComponent.LAP_TOP);
                } else if ("BOTTOM".equalsIgnoreCase(sParams)) {
                    // first, turn off automatic placement
                    LegendArea legend = graph.getGraph().getLegendArea();
                    legend.setAutomaticPlacement(BaseGraphComponent.AP_NEVER);
                    // set the position to the bottom edge
                    legend.setPosition(BaseGraphComponent.LAP_BOTTOM);

                } else {
                    graph.DebugMessage("POSITION_LEGEND: value " + sParams +
                                       " passed does not represent a valid argument");
                }
            }
        } else {
            graph.DebugMessage("POSITION_LEGEND: No value passed. Pass LEFT,TOP,BOTTOM,RIGHT,AUTO as an argument when calling set_custom_property()");
        }
        return true;
    }
}
