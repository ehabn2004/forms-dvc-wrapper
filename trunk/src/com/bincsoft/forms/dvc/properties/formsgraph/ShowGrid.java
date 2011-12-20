package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.graph.BaseGraphComponent;

public class ShowGrid implements IFormsGraphProperty {
    public ShowGrid() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            if (sParams.equalsIgnoreCase("TRUE")) {
                graph.getGraph().getY1MajorTick().setTickStyle(BaseGraphComponent.GS_AUTOMATIC);
                graph.getGraph().getO1MajorTick().setTickStyle(BaseGraphComponent.GS_AUTOMATIC);
                //m_graph.getY1MajorTick().setVisible(true); // Deprecated
                //m_graph.getO1MajorTick().setVisible(true); // Deprecated
            } else if (sParams.equalsIgnoreCase("FALSE")) {
                graph.getGraph().getY1MajorTick().setTickStyle(BaseGraphComponent.GS_NONE);
                graph.getGraph().getO1MajorTick().setTickStyle(BaseGraphComponent.GS_NONE);
                //m_graph.getY1MajorTick().setVisible(false); // Deprecated
                //m_graph.getO1MajorTick().setVisible(false); // Deprecated
            } else {
                // ignore
                graph.debugMessage("SHOW_GRID attribute is not true nor false: Ignored");
            }
        }
        return true;
    }
}
