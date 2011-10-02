package oracle.forms.demos.bigraph.impl;

import java.util.StringTokenizer;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class ShowLabels implements IFGPropImpl {
    public static final ID propertyId = ID.registerProperty("SHOW_LABELS");

    public ShowLabels() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        String _sObject = sParams;
        _sObject = graph.handleTokenNullvaluesInStartAndEnd(_sObject);
        StringTokenizer st =
            new StringTokenizer(_sObject, graph.getDelimiter());
        int tokenLength = st.countTokens();
        String firstToken = "", secondToken = "";

        graph.DebugMessage("SHOW_LABELS has received " + tokenLength +
                           " tokens and a value of " + sParams);

        if (tokenLength > 0) {
            firstToken = (String)st.nextElement();
            String txValue =
                firstToken.substring(firstToken.indexOf("x=") + 2);
            graph.DebugMessage("SHOW_LABELS: FIRST TOKEN: " + txValue);
            graph.getGraph().getX1Axis().setVisible(txValue.equalsIgnoreCase("FALSE") ?
                                                    false : true);
        }
        if (tokenLength > 1) {
            secondToken = (String)st.nextElement();
            String ty1Value =
                secondToken.substring(secondToken.indexOf("y1=") + 3);
            graph.DebugMessage("SHOW_LABELS: SECOND TOKEN: " + ty1Value);
            graph.getGraph().getY1Axis().setVisible(ty1Value.equalsIgnoreCase("FALSE") ?
                                                    false : true);
        }

        return true;
    }
}
