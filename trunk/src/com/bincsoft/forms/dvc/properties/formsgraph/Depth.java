package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import java.util.StringTokenizer;

import oracle.dss.graph.Graph;

public class Depth implements IFormsGraphProperty {
    public Depth() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            String _sObject = sParams;
            _sObject = DvcHelper.handleTokenNullvaluesInStartAndEnd(_sObject, graph.getDelimiter());
            StringTokenizer st = new StringTokenizer(_sObject, graph.getDelimiter());
            int tokenLength = st.countTokens();
            String firstToken = "", secondToken = "";

            if (tokenLength > 0) {
                firstToken = (String)st.nextElement();
            }
            if (tokenLength > 1) {
                secondToken = (String)st.nextElement();
            }

            graph.debugMessage("SET_DEPTH: tokens = " + firstToken + " , " + secondToken);

            try {
                // set first token as depth value and second token as radius
                if (graph.getGraph().getGraphType() == Graph.PIE) {
                    // in a pie there is a depth value and a rotation angle
                    graph.getGraph().setPieDepth(new Double(firstToken).intValue());
                    graph.getGraph().setPieRotation(new Double(secondToken).intValue());
                } else {
                    // all other graphs have a depth and a depth angle
                    graph.getGraph().setDepthRadius(new Double(firstToken).intValue());
                    graph.getGraph().setDepthAngle(new Double(secondToken).intValue());
                }

            } catch (NumberFormatException nfe) {
                graph.debugMessage("SET_DEPTH: Could not turn String value into integer - " + firstToken + " , " +
                             secondToken);
            }

        } else {
            graph.debugMessage("SET_DEPTH: No values passed");
        }
        return true;
    }
}
