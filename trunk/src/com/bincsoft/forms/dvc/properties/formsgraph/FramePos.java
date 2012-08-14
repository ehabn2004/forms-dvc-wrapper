package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import java.util.StringTokenizer;

public class FramePos extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("FRAME_POS: trying to set position to " + sParams);
            StringTokenizer st = new StringTokenizer(sParams, graph.getDelimiter());
            int tokenLength = st.countTokens();
            String firstToken = "", secondToken = "";
            int xPos = 0, yPos = 0;
            try {
                if (tokenLength > 0) {
                    firstToken = (String)st.nextElement();
                    xPos = new Double(firstToken).intValue();
                    graph.setSeparateFrameXPos(xPos);
                }
                if (tokenLength > 1) {
                    secondToken = (String)st.nextElement();
                    yPos = new Double(secondToken).intValue();
                    graph.setSeparateFrameYPos(yPos);
                }

            } catch (NumberFormatException nfe) {
                log("FRAME_POS Exception: Data passed for x and y postion could not be converted to int");
            } catch (Exception e) {
                log("FRAME_POS: Exception" + e.getMessage());
            }
        } else {
            log("FRAME_POS: No positional attributes passed - Ignoring command");
        }
        return true;
    }
}
