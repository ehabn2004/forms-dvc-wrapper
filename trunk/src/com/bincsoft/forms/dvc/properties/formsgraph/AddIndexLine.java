package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.ColorCodeRegistry;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Color;

import java.util.StringTokenizer;

import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.graph.GraphConstants;
import oracle.dss.graph.ReferenceObject;

public class AddIndexLine implements IFormsGraphProperty {
    public AddIndexLine() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        String theIndexText = "";
        String theAxis = "";
        int theIndexNumber = -1;
        Color theIndexLineColor = Color.red;
        double theValue = 0.0;
        boolean showIndexInLegend = false;
        int theLineWidth = 1;

        if (sParams.length() > 0) {
            StringTokenizer stok =
                new StringTokenizer(sParams, graph.getDelimiter());
            int tokenLength = stok.countTokens();

            graph.debugMessage("ADD_INDEX_LINE - Arguments received: " +
                               sParams);
            graph.debugMessage("ADD_INDEX_LINE - Tokens: " + tokenLength);

            if (tokenLength > 0) {
                // set Text
                theIndexText = (String)stok.nextElement();
            }

            if (tokenLength > 1) {
                // set visibility
                if ("true".equalsIgnoreCase(((String)stok.nextElement()).trim())) {
                    showIndexInLegend = true;
                    graph.debugMessage("ADD_INDEX_LINE: Show index line");
                } else {
                    showIndexInLegend = false;
                    graph.debugMessage("ADD_INDEX_LINE: Don't show index line");
                }
            }

            if (tokenLength > 2) {
                // set axis
                theAxis = ((String)stok.nextElement()).trim();
            }

            if (tokenLength > 3) {
                // set index line - 0 .. 2
                try {
                    theIndexNumber =
                            new Double((String)stok.nextElement()).intValue();
                } catch (Exception e) {
                    graph.debugMessage("ADD_INDEX_LINE: Not a valid index line number. Should be 0 .. 2");
                    return true;
                }
            }

            if (tokenLength > 4) {
                // set value as double
                try {
                    theValue =
                            new Double((String)stok.nextElement()).doubleValue();
                } catch (Exception e) {
                    graph.debugMessage("ADD_INDEX_LINE: Not a valid index line value. Should be like 1000.00");
                    return true;
                }

            }

            if (tokenLength > 5) {
                // set color
                Color newColor = ColorCodeRegistry.getColorCode((String)stok.nextElement());
                theIndexLineColor =
                        newColor != null ? newColor : theIndexLineColor;
            }

            if (tokenLength > 6) {
                try {
                    theLineWidth =
                            new Double((String)stok.nextElement()).intValue();
                } catch (Exception e) {
                    graph.debugMessage("ADD_INDEX_LINE: Not a valid value for the index line width");
                }
            }

            // putting it all together
            ReferenceObject refObj = graph.getGraph().createReferenceObject();
            refObj.setLineValue(theValue);
            refObj.setColor(theIndexLineColor);
            refObj.setDisplayedInLegend(showIndexInLegend);
            refObj.setText(theIndexText);
            theLineWidth = theLineWidth > 0 ? theLineWidth : 1;
            refObj.setLineWidth(theLineWidth);
            refObj.setLocation(BaseGraphComponent.RO_FRONT);

            if (theAxis.equalsIgnoreCase("x-axis"))
                refObj.setAssociation(GraphConstants.X1AXIS);
            if (theAxis.equalsIgnoreCase("X1AXIS"))
                refObj.setAssociation(GraphConstants.X1AXIS);
            if (theAxis.equalsIgnoreCase("Y1AXIS"))
                refObj.setAssociation(GraphConstants.Y1AXIS);
            if (theAxis.equalsIgnoreCase("Y2AXIS"))
                refObj.setAssociation(GraphConstants.Y2AXIS);
            if (theAxis.equalsIgnoreCase("SERIES"))
                refObj.setAssociation(GraphConstants.SERIES);


        } else {
            graph.debugMessage("ADD_INDEX_LINE: No valid arguments passed with request");
        }
        return true;
    }
}
