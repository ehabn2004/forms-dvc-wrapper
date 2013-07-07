package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;

import java.io.InputStream;

import oracle.dss.util.CustomStyle;


public class Style extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        log("SET_STYLE: Setting style from name...");
        String sPath = "/oracle/dss/graph/styles/" + sParams.toLowerCase() + ".xml";
        InputStream is = graph.getGraph().getClass().getResourceAsStream(sPath);
        try {
            CustomStyle style = new CustomStyle(DvcHelper.convertStreamToString(is));
            is.close();
            graph.getGraph().setStyle(style);
        } catch (Exception ex) {
            log("setStyle(): " + ex);
        }
        log("SET_STYLE: Done!");
        return true;
    }
}
