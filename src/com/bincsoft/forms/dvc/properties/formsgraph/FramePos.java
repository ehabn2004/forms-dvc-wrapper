package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import java.util.StringTokenizer;

public class FramePos implements IFormsGraphProperty {
    public FramePos() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("FRAME_POS: trying to set position to " + sParams);
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
                graph.debugMessage("FRAME_POS Exception: Data passed for x and y postion could not be converted to int");
            } catch (Exception e) {
                graph.debugMessage("FRAME_POS: Exception" + e.getMessage());
            }
        } else {
            graph.debugMessage("FRAME_POS: No positional attributes passed - Ignoring command");
        }
        return true;
    }
}
