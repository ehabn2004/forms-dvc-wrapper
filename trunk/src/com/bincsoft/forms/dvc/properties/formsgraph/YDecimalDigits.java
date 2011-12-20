package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.dataView.managers.ViewFormat;
import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.util.format.BaseViewFormat;

public class YDecimalDigits implements IFormsGraphProperty {
    public YDecimalDigits() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("SET_Y_DECIMAL_DIGITS: Setting '" + sParams + "' decimal digits...");
            ViewFormat vf = new ViewFormat();
            vf.setScaleFactor(BaseViewFormat.SCALEFACTOR_NONE);
            graph.getGraph().getY1Axis().setViewFormat(vf);
            vf.setDecimalDigit(Integer.parseInt(sParams));
            graph.getGraph().getMarkerText().setViewFormat(vf, BaseGraphComponent.VF_Y1);
        } else {
            graph.debugMessage("SET_Y_DECIMAL_DIGITS: no argument specified");
        }
        return true;
    }
}
