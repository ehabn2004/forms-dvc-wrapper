package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;

import java.util.StringTokenizer;

import javax.swing.SwingConstants;


public class AlignTitleText extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        String _sObject = sParams;
        _sObject = DvcHelper.handleTokenNullvaluesInStartAndEnd(_sObject, graph.getDelimiter());

        StringTokenizer st =
            new StringTokenizer(_sObject, graph.getDelimiter());
        int tokenLength = st.countTokens();
        String firstToken = "", secondToken = "", thirdToken = "";

        log("Property ALIGN_TITLE_TEXT has received " +
                           tokenLength + " tokens and a value of " + sParams);

        if (tokenLength > 0) {
            firstToken = (String)st.nextElement();
            String tValue =
                firstToken.substring(firstToken.toLowerCase().indexOf("title=") +
                                     6);
            log("ALIGN_TITLE_TEXT: FIRST TOKEN: " + tValue);
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
            log("ALIGN_TITLE_TEXT: SECOND TOKEN: " + tValue);

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
            log("ALIGN_TITLE_TEXT: THIRD TOKEN: " + tValue);

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
