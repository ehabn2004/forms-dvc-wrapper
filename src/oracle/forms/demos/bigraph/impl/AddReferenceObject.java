package oracle.forms.demos.bigraph.impl;

import java.awt.Color;

import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.graph.GraphConstants;
import oracle.dss.graph.ReferenceObject;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.demos.bigraph.ColorCodeRegistry;
import oracle.forms.properties.ID;

public class AddReferenceObject implements IFGPropImpl {
    public static final ID propertyId =
        ID.registerProperty("ADD_REFERENCE_OBJECT");

    public AddReferenceObject() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (sParams.length() > 0) {
            String[] aParams = sParams.split(graph.getDelimiter());
            graph.DebugMessage("ADD_REFERENCE_OBJECT: Received " +
                               aParams.length + " parameters.");

            if (aParams.length != 9 && aParams.length != 10) {
                graph.DebugMessage("ADD_REFERENCE_OBJECT: Incorrect no. of parameters, need 9 for a line and 10 for an area.");
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
            graph.DebugMessage("ADD_REFERENCE_OBJECT: No valid parameters passed with request.");
        }
        return true;
    }
}
