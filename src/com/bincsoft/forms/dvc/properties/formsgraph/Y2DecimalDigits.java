package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.dataView.managers.ViewFormat;
import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.util.format.BaseViewFormat;

public class Y2DecimalDigits implements IFormsGraphProperty {
    public Y2DecimalDigits() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (!sParams.equals("")) {
            graph.debugMessage("SET_Y2_DECIMAL_DIGITS: Setting '" + sParams + "' decimal digits...");
            ViewFormat vf = new ViewFormat();
            vf.setScaleFactor(BaseViewFormat.SCALEFACTOR_NONE);
            graph.getGraph().getY2Axis().setViewFormat(vf);
            vf.setDecimalDigit(Integer.parseInt(sParams));
            graph.getGraph().getMarkerText().setViewFormat(vf, BaseGraphComponent.VF_Y2);
        } else {
            graph.debugMessage("SET_Y2_DECIMAL_DIGITS: no argument specified");
        }
        return true;
    }
}
