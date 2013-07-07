package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.dataView.managers.ViewFormat;
import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.util.format.BaseViewFormat;

public class YDecimalDigits extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            log("SET_Y_DECIMAL_DIGITS: Setting '" + sParams + "' decimal digits...");
            ViewFormat vf = new ViewFormat();
            vf.setScaleFactor(BaseViewFormat.SCALEFACTOR_NONE);
            graph.getGraph().getY1Axis().setViewFormat(vf);
            vf.setDecimalDigit(Integer.parseInt(sParams));
            graph.getGraph().getMarkerText().setViewFormat(vf, BaseGraphComponent.VF_Y1);
        } else {
            log("SET_Y_DECIMAL_DIGITS: no argument specified");
        }
        return true;
    }
}
