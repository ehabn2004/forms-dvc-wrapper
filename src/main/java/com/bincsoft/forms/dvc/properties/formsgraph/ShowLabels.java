package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;

import java.util.StringTokenizer;

public class ShowLabels extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        String _sObject = sParams;
        _sObject = DvcHelper.handleTokenNullvaluesInStartAndEnd(_sObject, graph.getDelimiter());
        StringTokenizer st =
            new StringTokenizer(_sObject, graph.getDelimiter());
        int tokenLength = st.countTokens();
        String firstToken = "", secondToken = "";

        log("SHOW_LABELS has received " + tokenLength +
                           " tokens and a value of " + sParams);

        if (tokenLength > 0) {
            firstToken = (String)st.nextElement();
            String txValue =
                firstToken.substring(firstToken.indexOf("x=") + 2);
            log("SHOW_LABELS: FIRST TOKEN: " + txValue);
            graph.getGraph().getX1Axis().setVisible(txValue.equalsIgnoreCase("FALSE") ?
                                                    false : true);
        }
        if (tokenLength > 1) {
            secondToken = (String)st.nextElement();
            String ty1Value =
                secondToken.substring(secondToken.indexOf("y1=") + 3);
            log("SHOW_LABELS: SECOND TOKEN: " + ty1Value);
            graph.getGraph().getY1Axis().setVisible(ty1Value.equalsIgnoreCase("FALSE") ?
                                                    false : true);
        }

        return true;
    }
}
