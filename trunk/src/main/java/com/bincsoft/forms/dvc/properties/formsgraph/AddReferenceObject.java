package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.ColorCodeRegistry;

import java.awt.Color;

import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.graph.GraphConstants;
import oracle.dss.graph.ReferenceObject;


public class AddReferenceObject extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("ADD_REFERENCE_OBJECT: Delimiter is '" + graph.getDelimiter() + "'.");
            String[] aParams = sParams.split(graph.getDelimiter());
            log("ADD_REFERENCE_OBJECT: Received " + aParams.length + " parameters.");
            for (String s : aParams)
                log(s);

            if (aParams.length != 9 && aParams.length != 10) {
                log("ADD_REFERENCE_OBJECT: Incorrect no. of parameters, need 9 for a line and 10 for an area.");
                return true;
            }

            ReferenceObject refObj = graph.getGraph().createReferenceObject();

            // Object type
            refObj.setType(aParams[0].equalsIgnoreCase("AREA") ?
                           BaseGraphComponent.RO_AREA :
                           BaseGraphComponent.RO_LINE);

            // Object text
            refObj.setText(aParams[1]);

            // Association axis
            if (aParams[2].equalsIgnoreCase("Y1AXIS"))
                refObj.setAssociation(GraphConstants.Y1AXIS);
            if (aParams[2].equalsIgnoreCase("Y2AXIS"))
                refObj.setAssociation(GraphConstants.Y2AXIS);
            if (aParams[2].equalsIgnoreCase("X1AXIS"))
                refObj.setAssociation(GraphConstants.X1AXIS);
            if (aParams[2].equalsIgnoreCase("SERIES"))
                refObj.setAssociation(GraphConstants.SERIES);

            // Color
            Color newColor = ColorCodeRegistry.getColorCode(aParams[3]);
            refObj.setColor(newColor != null ? newColor : Color.red);

            // Show in legend
            refObj.setDisplayedInLegend(aParams[4].equalsIgnoreCase("true"));

            // Line width
            refObj.setLineWidth(Integer.valueOf(aParams[5]).intValue());

            // Line style
            if (aParams[6].equalsIgnoreCase("SOLID"))
                refObj.setLineStyle(BaseGraphComponent.LS_SOLID);
            if (aParams[6].equalsIgnoreCase("DASH"))
                refObj.setLineStyle(BaseGraphComponent.LS_DASH);
            if (aParams[6].equalsIgnoreCase("DOTTED"))
                refObj.setLineStyle(BaseGraphComponent.LS_DOTTED);
            if (aParams[6].equalsIgnoreCase("DASH_DOT"))
                refObj.setLineStyle(BaseGraphComponent.LS_DASH_DOT);

            // Set the reference object to front or back
            refObj.setLocation(aParams[7].equalsIgnoreCase("FRONT") ?
                               BaseGraphComponent.RO_FRONT :
                               BaseGraphComponent.RO_BACK);

            //Object value
            if (aParams[0].equalsIgnoreCase("AREA")) {
                refObj.setLowValue(Double.valueOf(aParams[8]).doubleValue());
                refObj.setHighValue(Double.valueOf(aParams[9]).doubleValue());
            } else
                refObj.setLineValue(Double.valueOf(aParams[8]).doubleValue());
        } else {
            log("ADD_REFERENCE_OBJECT: No valid parameters passed with request.");
        }
        return true;
    }
}
