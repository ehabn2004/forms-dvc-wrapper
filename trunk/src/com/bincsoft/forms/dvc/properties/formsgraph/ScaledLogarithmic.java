package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;

import java.util.StringTokenizer;

public class ScaledLogarithmic extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            sParams = DvcHelper.handleTokenNullvaluesInStartAndEnd(sParams, graph.getDelimiter());
            StringTokenizer st = new StringTokenizer(sParams, graph.getDelimiter());
            int tokenLength = st.countTokens();
            String axis = "";
            String enable = "false";
            double base = 0; // unrealistic initial value

            // parse string of arguments
            for (int i = 0; i < tokenLength && i < 3; i++) {
                switch (i) {
                    // axis is Y or X
                case 0:
                    axis = (String)st.nextElement();
                    break;
                    // enable id true or false
                case 1:
                    enable = (String)st.nextElement();
                    break;
                    // value is double or nothing
                case 2:
                    try {
                        base =
(new Double((String)st.nextElement())).doubleValue();
                    } catch (NumberFormatException nfe) {
                        log("SET_SCALED_LOGARITHMIC: argument specified as base cannot be parsed to double! Argument ignored");
                    }
                    break;
                default:
                    break;
                }
            }

            // set axis values
            if ("Y".equalsIgnoreCase(axis)) {
                if ("FALSE".equalsIgnoreCase(enable)) {
                    log("SET_SCALED_LOGARITHMIC: disabling logarithmic scale for Y axis");
                    graph.getGraph().getY1Axis().setScaledLogarithmic(false);
                } else {
                    log("SET_SCALED_LOGARITHMIC: enabling logarithmic scale for Y axis");
                    graph.getGraph().getY1Axis().setScaledLogarithmic(true);
                    if (base != 0) {
                        log("SET_SCALED_LOGARITHMIC: setting base to " +
                                           base);
                        graph.getGraph().getY1Axis().setLogarithmicBase(base);
                    }
                }
            } else if ("X".equalsIgnoreCase(axis)) {
                if ("FALSE".equalsIgnoreCase(enable)) {
                    log("SET_SCALED_LOGARITHMIC: disabling logarithmic scale for X axis");
                    graph.getGraph().getX1Axis().setScaledLogarithmic(false);
                } else {
                    log("SET_SCALED_LOGARITHMIC: enabling logarithmic scale for X axis");
                    graph.getGraph().getX1Axis().setScaledLogarithmic(true);
                    if (base != 0) {
                        log("SET_SCALED_LOGARITHMIC: setting base to " +
                                           base);
                        graph.getGraph().getX1Axis().setLogarithmicBase(base);
                    }
                }
            } else {
                log("SET_SCALED_LOGARITHMIC: not a valid Axis identifier!");
            }

        } else {
            log("SET_SCALED_LOGARITHMIC: no arguments passed with call to set property. Statement ignored!");
        }
        return true;
    }
}
