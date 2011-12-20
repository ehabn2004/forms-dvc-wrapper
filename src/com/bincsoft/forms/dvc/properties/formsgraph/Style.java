package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import java.io.InputStream;

import oracle.dss.util.CustomStyle;

public class Style implements IFormsGraphProperty {
    public Style() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("SET_STYLE: Setting style from name...");
        String sPath = "/oracle/dss/graph/styles/" + sParams.toLowerCase() + ".xml";
        InputStream is = graph.getGraph().getClass().getResourceAsStream(sPath);
        try {
            CustomStyle style = new CustomStyle(DvcHelper.convertStreamToString(is));
            is.close();
            graph.getGraph().setStyle(style);
        } catch (Exception ex) {
            graph.debugMessage("setStyle(): " + ex);
        }
        graph.debugMessage("SET_STYLE: Done!");
        return true;
    }
}
