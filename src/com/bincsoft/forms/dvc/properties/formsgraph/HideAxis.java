package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class HideAxis implements IFormsGraphProperty {
    public HideAxis() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (sParams.length() > 0) {
            boolean X1 = true;
            boolean Y1 = true;
            boolean Y2 = true;
            //boolean bIsVisible;
            int iTickStyle = -1;

            // check if argument passed contains X, Y1 or Y2
            String args = sParams == null ? "" : sParams.toUpperCase();
            if (args.indexOf("X") > -1) {
                X1 = false;
            } else {
                X1 = true;
            }
            if (args.indexOf("Y1") > -1) {
                Y1 = false;
            } else {
                Y1 = true;
            }
            if (args.indexOf("Y2") > -1) {
                Y2 = false;
            } else {
                Y2 = true;
            }

            iTickStyle = graph.getGraph().getY1MajorTick().getTickStyle();
            //bIsVisible = m_graph.getY1MajorTick().isVisible(); // Deprecated
            graph.getGraph().getY1Axis().setVisible(Y1);
            //m_graph.getY1MajorTick().setVisible(bIsVisible); // Deprecated
            graph.getGraph().getY1MajorTick().setTickStyle(iTickStyle);

            iTickStyle = graph.getGraph().getY2MajorTick().getTickStyle();
            //bIsVisible = m_graph.getY2MajorTick().isVisible(); // Deprecated
            graph.getGraph().getY2Axis().setVisible(Y2);
            //m_graph.getY2MajorTick().setVisible(bIsVisible); // Deprecated
            graph.getGraph().getY2MajorTick().setTickStyle(iTickStyle);

            iTickStyle = graph.getGraph().getO1MajorTick().getTickStyle();
            //bIsVisible = m_graph.getO1MajorTick().isVisible(); // Deprecated
            graph.getGraph().getO1Axis().setVisible(X1);
            //m_graph.getO1MajorTick().setVisible(bIsVisible); // Deprecated
            graph.getGraph().getO1MajorTick().setTickStyle(iTickStyle);
        } else {
            graph.debugMessage("HIDE_AXIS: No arguments passed, thus showing all axis labels.");

            // Show all axis labels
            graph.getGraph().getY1Axis().setVisible(true);
            graph.getGraph().getY2Axis().setVisible(true);
            graph.getGraph().getO1Axis().setVisible(true);
        }
        return true;
    }
}
