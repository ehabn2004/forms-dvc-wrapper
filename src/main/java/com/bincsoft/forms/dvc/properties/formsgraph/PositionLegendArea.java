package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.graph.LegendArea;

public class PositionLegendArea extends FormsGraphPropertyHandler {
   @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("POSITION_LEGEND: position legend to " +
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
                    log("POSITION_LEGEND: value " + sParams +
                                       " passed does not represent a valid argument");
                }
            }
        } else {
            log("POSITION_LEGEND: No value passed. Pass LEFT,TOP,BOTTOM,RIGHT,AUTO as an argument when calling set_custom_property()");
        }
        return true;
    }
}
