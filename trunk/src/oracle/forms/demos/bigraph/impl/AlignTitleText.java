package oracle.forms.demos.bigraph.impl;

import java.util.StringTokenizer;

import javax.swing.SwingConstants;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class AlignTitleText implements IFGPropImpl {
    public static final ID propertyId =
        ID.registerProperty("ALIGN_TITLE_TEXT");

    public AlignTitleText() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        String _sObject = sParams;
        _sObject = graph.handleTokenNullvaluesInStartAndEnd(_sObject);

        StringTokenizer st =
            new StringTokenizer(_sObject, graph.getDelimiter());
        int tokenLength = st.countTokens();
        String firstToken = "", secondToken = "", thirdToken = "";

        graph.DebugMessage("Property ALIGN_TITLE_TEXT has received " +
                           tokenLength + " tokens and a value of " + sParams);

        if (tokenLength > 0) {
            firstToken = (String)st.nextElement();
            String tValue =
                firstToken.substring(firstToken.toLowerCase().indexOf("title=") +
                                     6);
            graph.DebugMessage("ALIGN_TITLE_TEXT: FIRST TOKEN: " + tValue);
            if ("right".equalsIgnoreCase(tValue)) {
                graph.getGraph().getDataviewTitle().setHorizontalAlignment(SwingConstants.RIGHT);
            } else if ("left".equalsIgnoreCase(tValue)) {
                graph.getGraph().getDataviewTitle().setHorizontalAlignment(SwingConstants.LEFT);
            } else // center
            {
                graph.getGraph().getDataviewTitle().setHorizontalAlignment(SwingConstants.CENTER);
            }
        }

        if (tokenLength > 1) {
            secondToken = (String)st.nextElement();
            String tValue =
                secondToken.substring(secondToken.toLowerCase().indexOf("subtitle=") +
                                      9);
            graph.DebugMessage("ALIGN_TITLE_TEXT: SECOND TOKEN: " + tValue);

            if ("right".equalsIgnoreCase(tValue)) {
                graph.getGraph().getDataviewSubtitle().setHorizontalAlignment(SwingConstants.RIGHT);
            } else if ("left".equalsIgnoreCase(tValue)) {
                graph.getGraph().getDataviewSubtitle().setHorizontalAlignment(SwingConstants.LEFT);
            } else // center
            {
                graph.getGraph().getDataviewSubtitle().setHorizontalAlignment(SwingConstants.CENTER);
            }
        }

        if (tokenLength > 2) {
            thirdToken = (String)st.nextElement();
            String tValue =
                thirdToken.substring(thirdToken.toLowerCase().indexOf("footnote=") +
                                     9);
            graph.DebugMessage("ALIGN_TITLE_TEXT: THIRD TOKEN: " + tValue);

            if ("right".equalsIgnoreCase(tValue)) {
                graph.getGraph().getDataviewFootnote().setHorizontalAlignment(SwingConstants.RIGHT);
            } else if ("left".equalsIgnoreCase(tValue)) {
                graph.getGraph().getDataviewFootnote().setHorizontalAlignment(SwingConstants.LEFT);
            } else // center
            {
                graph.getGraph().getDataviewFootnote().setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        return true;
    }
}
